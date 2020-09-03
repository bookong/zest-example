package com.github.bookong.example.zest.springboot.mongo;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springboot.controller.MongoUserController;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;
import java.util.stream.Stream;

/**
 * 演示针对 MongoDB 的测试
 * 
 * @author Jiang Xu
 */
public class SimpleUserTest extends AbstractZestTest {

    /**
     * 演示 MongoDB 写入简单数据的例子
     * 
     * <pre>
     * 001.xml - Added successfully
     * 002.xml - Parameter loginName unique index conflict when adding
     * </pre>
     * 
     * @see MongoUserController#simpleAdd(UserParam)
     */
    // @ZestTest("002")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSimpleAdd() {
        return zestWorker.test(this, SimpleSaveParam.class, param -> {
            BaseResponse expected = param.getExpected();
            doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
        });
    }

    public static class SimpleSaveParam extends AbstractZestParam<SaveUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/mongo/user/simple/add");
        }
    }
}
