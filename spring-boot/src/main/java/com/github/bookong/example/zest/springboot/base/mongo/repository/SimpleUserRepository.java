package com.github.bookong.example.zest.springboot.base.mongo.repository;

import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Xu
 */
@Repository
public interface SimpleUserRepository extends MongoRepository<SimpleUser, String>, SimpleUserRepositoryCustom {
}
