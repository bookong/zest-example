package com.github.bookong.example.zest.springmvc.mybatis;

import com.github.bookong.example.zest.springmvc.AbstractZestParam;
import com.github.bookong.example.zest.springmvc.AbstractZestTest;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserResponse;
import com.github.bookong.example.zest.springmvc.controller.MyBatisUserController;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * 演示针对 MySQL 数据库的测试
 *
 * @author Jiang Xu
 */
public class UserTest extends AbstractZestTest {

    /**
     * 演示针对 /user/save 接口比较全面的测试，但不模拟出现异常情况<br>
     * 模拟异常使用 {@link UserSaveSpyTest#testAddSpy(UserSaveSpyTest.Param)} 来测试
     *
     * <pre>
     * 001.xml - No token parameter
     * 002.xml - The token parameter is incorrect
     * 003.xml - Parameter loginName is empty
     * 004.xml - Parameter password is empty
     * 005.xml - Parameter nickname is empty
     * 006.xml - Added successfully
     * 007.xml - Parameter loginName unique index conflict when adding
     * </pre>
     *
     * @see MyBatisUserController#add(UserParam)
     */
    // @ZestTest("003")
    @ZestTest
    public void testAdd(SaveParam param) {
        AddUserResponse expected = param.getExpected();
        JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

        doAssertEqual("id", expected.getId(), actual);

        // System.out.println(ZestSqlHelper.query(dataSource, "select * from user"));
    }

    /**
     * 通过自定义 H2 数据库函数的方式来模拟 MySQL 的函数 json_set<br>
     * 虽然新版本的 H2 已经支持了 json 类型字段，但自定义函数过于复杂<br>
     * 我倾向于将字段类型改为字符串进行测试。自定义 H2 函数是在 schema_h2.sql 文件里通过 ALIAS 设定的
     *
     * <pre>
     * 001.xml - Update completed
     * </pre>
     *
     * @see MyBatisUserController#updateExtInfo(Long, UserExtInfoParam)
     */
    // @ZestTest("001")
    @ZestTest
    public void testUpdateExtInfo(UpdateExtInfoParam param) {
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
     * @see MyBatisUserController#findUserOneDay()
     */
    // @ZestTest("001")
    @ZestTest
    public void testFindUserOneDay(FindUserOneDayParam param) {
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

    public static class SaveParam extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/mybatis/user/add");
        }
    }

    public static class UpdateExtInfoParam extends AbstractZestParam<BaseResponse> {

        public Long             userId;

        public UserExtInfoParam apiParam;

        public String makeUrl() {
            return makeUrl(String.format("/mybatis/user/%d/ext-info-update", userId));
        }
    }

    public static class FindUserOneDayParam extends AbstractZestParam<UserOneDayResponse> {

        public String makeUrl() {
            return makeUrl("/mybatis/user/one-day");
        }
    }
}
