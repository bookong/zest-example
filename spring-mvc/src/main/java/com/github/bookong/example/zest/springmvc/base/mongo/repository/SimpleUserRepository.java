package com.github.bookong.example.zest.springmvc.base.mongo.repository;

import com.github.bookong.example.zest.springmvc.base.mongo.entity.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
@Repository
public class SimpleUserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SimpleUser insert(SimpleUser entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    public List<SimpleUser> findByCreateTimeRange(Date createTimeStart) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(createTimeStart));

        return mongoTemplate.find(query, SimpleUser.class);
    }
}
