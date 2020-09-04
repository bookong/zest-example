package com.github.bookong.example.zest.springboot.mongo;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.UserResponse;
import com.github.bookong.example.zest.springboot.controller.MongoUserController;
import com.github.bookong.example.zest.springboot.custom.ComplexMongoExecutor;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * 演示针对 MongoDB 的测试（复杂对象）
 * 
 * @author Jiang Xu
 */
public class ComplexUserTest extends AbstractZestTest {

    /**
     * 复杂的 MongoDB 需要自己对 MongoExecutor 进行扩展，这里需要写一个子类 ComplexMongoExecutor
     */
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

    /**
     * 通过在测试用例文件（xml文件）中定义 CurrentTime 属性，来实现与当前时间有关的查询
     *
     * <pre>
     * 001.xml - By defining the CurrentTime property, the query related to the current time is realized
     * </pre>
     *
     * @see MongoUserController#findComplexUserOneDay()
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testFindComplexUserOneDay() {
        return zestWorker.test(this, FindComplexUserOneDayParam.class, param -> {
            UserOneDayResponse expected = param.getExpected();
            JSONObject actual = doGetAndBaseVerify(param.makeUrl(), expected, true);

            List<UserResponse> expectedUsers = expected.getUsers();
            JSONArray actualUsers = actual.getJSONArray("users");

            assertEquals("users.size()", expectedUsers.size(), actualUsers.size());
            for (int i = 0; i < expectedUsers.size(); i++) {
                String msg = String.format("users[%d]", i);
                UserResponse expectedUser = expectedUsers.get(i);
                JSONObject actualUser = actualUsers.getJSONObject(i);
                assertEquals(msg.concat("nickname"), expectedUser.getNickname(), actualUser.getString("nickname"));
            }
        });
    }

    public static class ComplexAddParam extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/mongo/user/complex/add");
        }
    }

    public static class FindComplexUserOneDayParam extends AbstractZestParam<UserOneDayResponse> {

        public String makeUrl() {
            return makeUrl("/mongo/user/complex/one-day");
        }
    }
}
