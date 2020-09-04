package com.github.bookong.example.zest.springmvc.service;

import com.github.bookong.example.zest.springmvc.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.UserAuth;
import com.github.bookong.example.zest.springmvc.base.mybatis.mapper.UserAuthMapper;
import com.github.bookong.example.zest.springmvc.base.mybatis.mapper.UserMapper;
import com.github.bookong.example.zest.springmvc.exception.ApiException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
@Service
public class MybatisUserService extends AbstractService {

    @Autowired
    private UserMapper                   userMapper;

    @Autowired
    private UserAuthMapper               userAuthMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    // @Transactional(rollbackFor = Exception.class)
    public User add(UserParam param) {
        logger.info("add user login name \"{}\"", param.getLoginName());
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
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
            transactionManager.commit(status);

            return user;
        } catch (Exception e) {
            transactionManager.rollback(status);

            if (e instanceof DataAccessException) {
                throw e;
            } else if (e instanceof ApiException) {
                throw e;
            } else {
                throw new RuntimeException(e);
            }
        }
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

    public List<User> findSimpleUserOneDay() {
        Date createTimeStart = DateUtils.addDays(new Date(), -1);
        return userMapper.findByCreateTimeRange(createTimeStart);
    }

    public void addAuthToList(List<UserAuth> list, String auth) {
        Long userId = null;
        for (UserAuth item : list) {
            if (userId == null) {
                userId = item.getUserId();
            }
            if (StringUtils.equals(item.getAuth(), auth)) {
                item.setExpirationTime(DateUtils.addDays(new Date(), 3));
                return;
            }
        }

        if (userId == null) {
            return;
        }

        UserAuth userAuth = new UserAuth();
        list.add(userAuth);
        userAuth.setUserId(userId);
        userAuth.setAuth(auth);
        userAuth.setExpirationTime(DateUtils.addDays(new Date(), 3));
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserAuthMapper getUserAuthMapper() {
        return userAuthMapper;
    }

    public void setUserAuthMapper(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }
}
