package com.github.bookong.example.zest.springboot.base.mongo.repository.impl;

import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springboot.base.mongo.repository.SimpleUserRepositoryCustom;
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
public class SimpleUserRepositoryImpl implements SimpleUserRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SimpleUser> addedUserWithinAWeek() {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(DateUtils.addDays(new Date(), -7)));

        return mongoTemplate.find(query, SimpleUser.class);
    }
}
