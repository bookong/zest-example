package com.github.bookong.example.zest.springboot.mybatis;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import com.github.bookong.example.zest.springboot.controller.MyBatisUserController;
import com.github.bookong.example.zest.springboot.service.MybatisUserService;
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
    private MybatisUserService userService;

    @InjectMocks
    @Autowired
    private MyBatisUserController userController;

    /**
     * 演示针对 /user/save 接口测试时，发生各种数据访问异常的情况
     *
     * <pre>
     * 001.xml - save - An exception occurred when inserting user_auth table data, causing the transaction to roll back
     * </pre>
     *
     * @see MyBatisUserController#save(UserParam)
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSaveSpy() {
        return zestWorker.test(this, Param.class, param -> {
            mockBefore(param);

            AddUserResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            assertEqual("id", expected.getId(), actual);
        });
    }

    private void mockBefore(Param param) {
        if (param.errorWhenInsertUserAuth) {
            // 模拟向 user_auth 表插入数据时发生异常
            // mock an exception occurred when inserting user_auth table data
            BadSqlGrammarException e = new BadSqlGrammarException("zest test", "mock sql", new SQLException());
            doThrow(e).when(userService).addUserAuth(any(User.class));
        } else {
            doNothing().when(userService).addUserAuth(any(User.class));
        }
    }

    public static class Param extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public boolean   errorWhenInsertUserAuth = false;

        public String makeUrl() {
            return makeUrl("/mybatis/user/save");
        }
    }
}
