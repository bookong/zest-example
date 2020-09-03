package com.github.bookong.example.zest.springboot.base.mongo.repository;

import com.github.bookong.example.zest.springboot.base.mongo.entity.ComplexUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Xu
 */
@Repository
public interface ComplexUserRepository extends MongoRepository<ComplexUser, String> {
}
