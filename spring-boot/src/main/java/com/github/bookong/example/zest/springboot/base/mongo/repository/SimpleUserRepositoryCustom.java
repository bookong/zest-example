package com.github.bookong.example.zest.springboot.base.mongo.repository;

import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
public interface SimpleUserRepositoryCustom {

    /** 查询一天内新增用户 */
    List<SimpleUser> findByCreateTimeRange(Date createTimeStart);
}
