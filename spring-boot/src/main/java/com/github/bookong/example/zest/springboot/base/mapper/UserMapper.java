package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    int insert(User record);
}
