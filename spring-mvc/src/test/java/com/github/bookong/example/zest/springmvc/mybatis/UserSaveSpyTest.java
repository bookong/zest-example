package com.github.bookong.example.zest.springmvc.mybatis;

import com.github.bookong.example.zest.springmvc.AbstractZestParam;
import com.github.bookong.example.zest.springmvc.AbstractZestTest;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;
import com.github.bookong.example.zest.springmvc.controller.MyBatisUserController;
import com.github.bookong.example.zest.springmvc.service.MybatisUserService;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import org.junit.Ignore;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;

/**
 * @author Jiang Xu
 */
public class UserSaveSpyTest extends AbstractZestTest {

    @Autowired
    private MybatisUserService    userService;

    @Autowired
    private MyBatisUserController userController;

    /**
     * 演示针对 /user/save 接口测试时，发生各种数据访问异常的情况
     *
     * <pre>
     * 001.xml - An exception occurred when inserting user_auth table data, causing the transaction to roll back
     * </pre>
     *
     * @see MyBatisUserController#add(UserParam)
     */
    // @ZestTest("001")
    @ZestTest
    @Ignore
    public void testAddSpy(Param param) {
        mockBefore(param);
        AddUserResponse expected = param.getExpected();
        doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
    }

    private void mockBefore(Param param) {
        MybatisUserService spyUserService = Mockito.spy(userService);
        userController.setMybatisUserService(spyUserService);

        if (param.errorWhenInsertUserAuth) {
            // 模拟向 user_auth 表插入数据时发生异常
            // mock an exception occurred when inserting user_auth table data
            BadSqlGrammarException e = new BadSqlGrammarException("init_data test", "mock sql", new SQLException());
            Mockito.doThrow(e).when(spyUserService).addUserAuth(Mockito.any(User.class));
        } else {
            Mockito.doNothing().when(spyUserService).addUserAuth(Mockito.any(User.class));
        }
    }

    public static class Param extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public boolean   errorWhenInsertUserAuth = false;

        public String makeUrl() {
            return makeUrl("/mybatis/user/add");
        }
    }
}
