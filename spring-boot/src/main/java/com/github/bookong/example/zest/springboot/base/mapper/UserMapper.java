package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByPrimaryKey(Long id);
}