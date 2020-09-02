package com.github.bookong.example.zest.springboot.base.mongo.repository;

import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;

import java.util.List;

/**
 * @author Jiang Xu
 */
public interface SimpleUserRepositoryCustom {

    /** 查询一周内新增用户 */
    List<SimpleUser> addedUserWithinAWeek();
}
