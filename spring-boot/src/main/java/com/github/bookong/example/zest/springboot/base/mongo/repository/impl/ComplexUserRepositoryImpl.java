package com.github.bookong.example.zest.springboot.base.mongo.repository.impl;

import com.github.bookong.example.zest.springboot.base.mongo.entity.ComplexUser;
import com.github.bookong.example.zest.springboot.base.mongo.repository.ComplexUserRepositoryCustom;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
public class ComplexUserRepositoryImpl implements ComplexUserRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ComplexUser> findUserOneDay() {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(DateUtils.addDays(new Date(), -1)));

        return mongoTemplate.find(query, ComplexUser.class);
    }
}
