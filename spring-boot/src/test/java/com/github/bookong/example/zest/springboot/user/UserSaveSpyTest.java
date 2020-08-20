package com.github.bookong.example.zest.springboot.user;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.entity.User;
import com.github.bookong.example.zest.springboot.controller.UserController;
import com.github.bookong.example.zest.springboot.service.UserService;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

/**
 * @author Jiang Xu
 */

public class UserSaveSpyTest extends AbstractZestTest {

    @SpyBean
    @Autowired
    private UserService    userService;

    @InjectMocks
    @Autowired
    private UserController userController;

    /**
     * 演示针对 /user/save 接口测试时，发生各种数据访问异常的情况
     *
     * <pre>
     * 001.xml - save - An exception occurred when inserting user_auth table data, causing the transaction to roll back
     * 002.xml - save - 更新时 redis 时发送连接异常
     * </pre>
     *
     * @see UserController#save(UserParam)
     */
    @ZestTest("001")
    // @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSaveSpy() {
        return zestWorker.test(this, Param.class, param -> {
            mockBefore(param);

            SaveUserResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            assertEqual("id", expected.getId(), actual);

            // System.out.println(ZestSqlHelper.query(param.conn, "select * from user"));
        });
    }

    private void mockBefore(Param param) {
        if (param.errorWhenInsertUserAuth) {
            // mock an exception occurred when inserting user_auth table data
            BadSqlGrammarException e = new BadSqlGrammarException("zest test", "mock sql", new SQLException());
            doThrow(e).when(userService).addUserAuth(any(User.class));
        } else {
            doNothing().when(userService).addUserAuth(any(User.class));
        }
    }

    public static class Param extends AbstractZestParam<SaveUserResponse> {

        public UserParam apiParam;

        public boolean   errorWhenInsertUserAuth = false;

        public String makeUrl() {
            return makeUrl("/user/save");
        }
    }
}
