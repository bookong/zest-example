package com.github.bookong.example.zest.springmvc.base.mongo.repository;

import com.github.bookong.example.zest.springmvc.base.mongo.entity.ComplexUser;
import org.apache.commons.lang3.time.DateUtils;
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
public class ComplexUserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ComplexUser insert(ComplexUser entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    public List<ComplexUser> findUserOneDay() {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").gte(DateUtils.addDays(new Date(), -1)));

        return mongoTemplate.find(query, ComplexUser.class);
    }
}
