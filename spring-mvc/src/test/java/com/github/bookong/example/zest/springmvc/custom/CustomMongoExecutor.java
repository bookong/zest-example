package com.github.bookong.example.zest.springmvc.custom;

import com.github.bookong.zest.executor.MongoExecutor;
import com.github.bookong.zest.runner.ZestWorker;
import com.github.bookong.zest.testcase.AbstractTable;
import com.github.bookong.zest.testcase.Source;
import com.github.bookong.zest.testcase.ZestData;
import com.github.bookong.zest.testcase.mongo.Collection;
import com.github.bookong.zest.testcase.mongo.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang Xu
 */
public class CustomMongoExecutor extends MongoExecutor {

    @Override
    protected void removeAll(ZestWorker worker, Source source, Collection collection) {
        MongoOperations operator = worker.getOperator(source.getId(), MongoOperations.class);
        operator.remove(new Query(), collection.getEntityClass());
    }

    @Override
    protected void insertDataList(ZestWorker worker, Source source, Class<?> entityClass, List<Object> initDataList) {
        MongoOperations operator = worker.getOperator(source.getId(), MongoOperations.class);
        operator.insert(initDataList, entityClass);
    }

    @Override
    protected List<?> findData(ZestWorker worker, Source source, Collection collection) {
        Query query = new Query();
        if (collection.getSort() != null) {
            query.with(collection.getSort());
        }
        MongoOperations operator = worker.getOperator(source.getId(), MongoOperations.class);
        return operator.find(query, collection.getEntityClass());
    }
}
