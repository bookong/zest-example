package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.UserAuth;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.base.mybatis.mapper.UserAuthMapper;
import com.github.bookong.example.zest.springboot.base.mybatis.mapper.UserMapper;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import com.github.bookong.example.zest.springboot.util.JsonUtil;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiang Xu
 */
@RedisHash
@Service
public class MybatisUserService extends AbstractService {

    @Autowired
    private UserMapper                    userMapper;

    @Autowired
    private UserAuthMapper                userAuthMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public User add(UserParam param) {
        logger.info("add user login name \"{}\"", param.getLoginName());

        User user = new User();
        BeanUtils.copyProperties(param, user);
        user.setPassword(convertPassword(param.getPassword()));
        user.setToken("USER_".concat(String.valueOf(System.currentTimeMillis())));
        user.setCreateTime(new Date());

        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "data conflict");
        }

        addUserAuth(user);
        return user;
    }

    public void addUserAuth(User user) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(user.getId());
        userAuth.setAuth("login");
        userAuth.setExpirationTime(DateUtils.addDays(new Date(), 3));
        userAuthMapper.insert(userAuth);
    }

    public User get(Long userId) {
        User user = getFromRedis(userId);
        if (user != null) {
            return user;
        }

        user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "user does not exist");
        }

        putToRedis(userId, user);
        return user;
    }

    public User getFromRedis(Long userId) {
        String json = redisTemplate.boundValueOps(genRedisKey(userId)).get();
        if (json == null) {
            return null;
        }
        return JsonUtil.fromJson(json, User.class);
    }

    public void putToRedis(Long userId, User user) {
        try {
            redisTemplate.boundValueOps(genRedisKey(userId)).set(JsonUtil.toJson(user), 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.debug("Failed to cache data in redis: {}", e.getMessage());
        }
    }

    private String genRedisKey(Long userId) {
        return String.format("user$%d", userId);
    }

    public void updateExtInfo(Long userId, UserExtInfoParam param) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "user does not exist");
        }

        userMapper.updateExtInfo(userId, param.getKey(), param.getValue());
    }

}
