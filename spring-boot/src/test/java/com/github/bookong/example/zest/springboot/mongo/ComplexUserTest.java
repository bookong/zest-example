package com.github.bookong.example.zest.springboot.mongo;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springboot.controller.MongoUserController;
import com.github.bookong.example.zest.springboot.zest.ComplexMongoExecutor;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.stream.Stream;

/**
 * 演示针对 MongoDB 的测试（复杂对象）
 * 
 * @author Jiang Xu
 */
public class ComplexUserTest extends AbstractZestTest {

    @Autowired
    @ZestSource(value = "complexMongo", executorClass = ComplexMongoExecutor.class)
    protected MongoTemplate mongoTemplate;

    /**
     * 演示 MongoDB 写入复杂数据的例子
     *
     * <pre>
     * 001.xml - Added successfully
     * </pre>
     *
     * @see MongoUserController#complexAdd(UserParam)
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testComplexAdd() {
        return zestWorker.test(this, ComplexAddParam.class, param -> {
            BaseResponse expected = param.getExpected();
            doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
        });
    }

    public static class ComplexAddParam extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/mongo/user/complex/add");
        }
    }
}
