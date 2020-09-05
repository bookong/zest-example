package com.github.bookong.example.zest.springmvc.service;

import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springmvc.base.mongo.repository.SimpleUserRepository;
import com.github.bookong.example.zest.springmvc.exception.ApiException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
@Service
public class MongoUserService extends AbstractService {

    @Autowired
    private SimpleUserRepository simpleUserRepository;

//    @Autowired
//    private ComplexUserRepository complexUserRepository;

    public void simpleAdd(UserParam param) {
        logger.info("add simple user login name \"{}\"", param.getLoginName());
        SimpleUser user = new SimpleUser();
        BeanUtils.copyProperties(param, user);
        user.setPassword(convertPassword(param.getPassword()));
        user.setToken("USER_".concat(String.valueOf(System.currentTimeMillis())));
        user.setCreateTime(new Date());

        try {
            simpleUserRepository.insert(user);
        } catch (DuplicateKeyException e) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "data conflict");
        }
    }

//    public void complexAdd(UserParam param) {
//        logger.info("add complex user login name \"{}\"", param.getLoginName());
//        ComplexUser user = new ComplexUser();
//        BeanUtils.copyProperties(param, user);
//        user.setPassword(convertPassword(param.getPassword()));
//        user.setToken("USER_".concat(String.valueOf(System.currentTimeMillis())));
//        user.setCreateTime(new Date());
//        user.setAuthList(new ArrayList<>());
//
//        Auth auth = new Auth();
//        user.getAuthList().add(auth);
//        auth.setAuth("login");
//        auth.setExpirationTime(DateUtils.addDays(new Date(), 3));
//
//        complexUserRepository.insert(user);
//    }

    public List<SimpleUser> findSimpleUserOneDay() {
        Date createTimeStart = DateUtils.addDays(new Date(), -1);
        return simpleUserRepository.findByCreateTimeRange(createTimeStart);
    }

//    public List<ComplexUser> findComplexUserOneDay() {
//        return complexUserRepository.findUserOneDay();
//    }

}
