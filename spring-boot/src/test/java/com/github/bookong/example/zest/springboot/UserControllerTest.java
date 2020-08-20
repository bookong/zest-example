package com.github.bookong.example.zest.springboot;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.controller.UserController;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import com.github.bookong.zest.util.ZestSqlHelper;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

/**
 * @author Jiang Xu
 */
public class UserControllerTest extends AbstractZestTest {

    /**
     * 演示针对 /user/save 接口比较全面的测试
     *
     * <pre>
     * 001.xml - save - No token parameter
     * 002.xml - save - The token parameter is incorrect
     * 003.xml - save - Parameter loginName is empty
     * 004.xml - save - Parameter password is empty
     * 005.xml - save - Parameter nickname is empty
     * 006.xml - save - added successfully
     * 007.xml - save - Parameter loginName unique index conflict when adding
     * 008.xml - save - 模拟新增时发生数据库连接异常
     * 009.xml - save - 更新成功
     * 010.xml - save - 更新时 loginName 主键冲突
     * 011.xml - save - 更新时 redis 里已经有旧缓存数据
     * 012.xml - save - 更新时 redis 时发送连接异常
     * </pre>
     *
     * @see UserController#save(UserParam)
     */
    @ZestTest("007")
    // @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSave() {
        return zestWorker.test(this, SaveParam.class, param -> {
            SaveUserResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            assertEqual("id", expected.getId(), actual);

            // System.out.println(ZestSqlHelper.query(param.conn, "select * from user"));
        });
    }

    public static class SaveParam extends AbstractZestParam<SaveUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/user/save");
        }
    }
}
