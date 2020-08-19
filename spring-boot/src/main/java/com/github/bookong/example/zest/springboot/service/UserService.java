package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.entity.User;
import com.github.bookong.example.zest.springboot.base.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Jiang Xu
 */
@RedisHash
@Service
public class UserService {

    @Autowired
    private UserMapper                    userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public User save(UserParam param) {
        User record = new User();
        BeanUtils.copyProperties(param, record);
        record.setCreateTime(new Date());
        userMapper.insert(record);

        redisTemplate.opsForValue().set("xxx", "yyy");

        return record;
    }
}
