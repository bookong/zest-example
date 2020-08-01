package com.github.bookong.example.zest.springmvc.base.mapper;

import com.github.bookong.example.zest.springmvc.base.entity.User;

public interface UserMapper {
    User selectByPrimaryKey(Long id);
}