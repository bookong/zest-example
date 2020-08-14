package com.github.bookong.example.zest.springboot.base.repository;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Xu
 */
@Repository
public class RemarkRepository {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public void save(Remark remark) {
        mongoTemplate.insert(remark);
    }
}
