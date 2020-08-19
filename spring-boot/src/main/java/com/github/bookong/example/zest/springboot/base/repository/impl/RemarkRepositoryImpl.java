package com.github.bookong.example.zest.springboot.base.repository.impl;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.base.repository.RemarkRepositoryCustom;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

public class RemarkRepositoryImpl implements RemarkRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /** 查询用户最近一天的评论 */
    public List<Remark> findOneDayRemarks(Long userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("createTime").gt(DateUtils.addDays(new Date(), -1)));

        return mongoTemplate.find(query, Remark.class);
    }
}
