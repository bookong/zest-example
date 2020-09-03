package com.github.bookong.example.zest.springboot.base.mongo.repository;

import com.github.bookong.example.zest.springboot.base.mongo.entity.ComplexUser;

import java.util.List;

/**
 * @author jiangxu
 */
public interface ComplexUserRepositoryCustom {

    /** 查询一天内新增用户 */
    List<ComplexUser> findUserOneDay();
}
