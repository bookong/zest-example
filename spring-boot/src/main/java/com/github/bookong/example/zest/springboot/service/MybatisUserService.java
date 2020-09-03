package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.UserAuth;
import com.github.bookong.example.zest.springboot.base.mybatis.mapper.UserAuthMapper;
import com.github.bookong.example.zest.springboot.base.mybatis.mapper.UserMapper;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
@Service
public class MybatisUserService extends AbstractService {

    @Autowired
    private UserMapper     userMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;

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

    public void updateExtInfo(Long userId, UserExtInfoParam param) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "user does not exist");
        }

        userMapper.updateExtInfo(userId, param.getKey(), param.getValue());
    }

    public List<User> findSimpleUserOneDay(){
        Date createTimeStart = DateUtils.addDays(new Date(), -1);
        return userMapper.findByCreateTimeRange(createTimeStart);
    }
}
