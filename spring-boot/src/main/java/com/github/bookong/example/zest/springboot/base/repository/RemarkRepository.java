package com.github.bookong.example.zest.springboot.base.repository;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Xu
 */
@Repository
public interface RemarkRepository extends MongoRepository<Remark, String> {
}
