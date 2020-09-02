package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springboot.base.mongo.repository.SimpleUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiang Xu
 */
@Service
public class MongoUserService extends AbstractService {

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    public SimpleUser simpleSave(UserParam param) {
        SimpleUser user = new SimpleUser();
        BeanUtils.copyProperties(param, user);
        user.setPassword(convertPassword(param.getPassword()));

        simpleUserRepository.insert(user);

        return user;
    }
}
