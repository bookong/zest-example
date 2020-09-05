package com.github.bookong.example.zest.springmvc.mongo;

import com.github.bookong.example.zest.springmvc.controller.MongoUserController;
import com.github.bookong.example.zest.springmvc.AbstractZestParam;
import com.github.bookong.example.zest.springmvc.AbstractZestTest;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserResponse;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * 演示针对 MongoDB 的测试（简单对象）
 * 
 * @author Jiang Xu
 */
public class SimpleUserTest extends AbstractZestTest {

    /**
     * 演示 MongoDB 写入简单数据的例子，80% 以上的业务场景都是简单的数据
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
    public void testSimpleAdd(SimpleAddParam param) {
        BaseResponse expected = param.getExpected();
        doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
    }

    /**
     * 通过在测试用例文件（xml文件）中定义 CurrentTime 属性，来实现与当前时间有关的查询
     *
     * <pre>
     * 001.xml - By defining the CurrentTime property, the query related to the current time is realized
     * </pre>
     *
     * @see MongoUserController#findSimpleUserOneDay()
     */
    // @ZestTest("001")
    @ZestTest
    public void testFindSimpleUserOneDay(FindSimpleUserOneDayParam param) {
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
    }

    public static class SimpleAddParam extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/mongo/user/simple/add");
        }
    }

    public static class FindSimpleUserOneDayParam extends AbstractZestParam<UserOneDayResponse> {

        public String makeUrl() {
            return makeUrl("/mongo/user/simple/one-day");
        }
    }
}
