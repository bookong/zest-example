package com.github.bookong.example.zest.springboot.user;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.controller.UserController;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

/**
 * @author Jiang Xu
 */
public class UserSaveTest extends AbstractZestTest {

    /**
     * 演示针对 /user/save 接口比较全面的测试，但不用 Mockito 进行 spy 处理
     *
     * <pre>
     * 001.xml - save - No token parameter
     * 002.xml - save - The token parameter is incorrect
     * 003.xml - save - Parameter loginName is empty
     * 004.xml - save - Parameter password is empty
     * 005.xml - save - Parameter nickname is empty
     * 006.xml - save - added successfully
     * 007.xml - save - Parameter loginName unique index conflict when adding
     * 009.xml - save - 更新成功
     * 010.xml - save - 更新时 loginName 主键冲突
     * 011.xml - save - 更新时 redis 里已经有旧缓存数据
     * </pre>
     *
     * @see UserController#save(UserParam)
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSave() {
        return zestWorker.test(this, Param.class, param -> {
            SaveUserResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            assertEqual("id", expected.getId(), actual);

            // System.out.println(ZestSqlHelper.query(param.conn, "select * from user"));
        });
    }

    public static class Param extends AbstractZestParam<SaveUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/user/save");
        }
    }
}