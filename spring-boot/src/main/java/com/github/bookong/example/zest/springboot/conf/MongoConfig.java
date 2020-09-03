package com.github.bookong.example.zest.springboot.conf;

import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.List;

/**
 * @author Jiang Xu
 */
@Configuration
public class MongoConfig {

    private MongoTemplate mongoTemplate;

    /** 通过 SpringDataMongo 定义的实体，会自动写入一个_class属性，大多数情况下这个不是必须的，可以通过配置去掉 */
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoMappingContext context) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();
        this.mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;
    }

    /**
     * Spring Data MongoDB 3.x 以后，不支持在实体对象上通过 @Indexed 建立索引
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        IndexOperations indexOpes = mongoTemplate.indexOps(SimpleUser.class);
        indexOpes.ensureIndex(new Index().unique().on("loginName", Sort.Direction.ASC));
        indexOpes.ensureIndex(new Index().on("createTime", Sort.Direction.ASC));
    }
}
