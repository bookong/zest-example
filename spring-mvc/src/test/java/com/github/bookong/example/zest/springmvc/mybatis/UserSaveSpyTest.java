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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

/**
 * @author Jiang Xu
 */
public class UserSaveSpyTest extends AbstractZestTest {

    @Spy
    @Autowired
    private MybatisUserService    userService;

    @Autowired
    private MyBatisUserController userController;

    private MybatisUserService    oldUserService;

    /**
     * 演示针对 /user/save 接口测试时，发生各种数据访问异常的情况<br>
     * 
     * <pre>
     * 001.xml - An exception occurred when inserting user_auth table data, causing the transaction to roll back
     * </pre>
     *
     * @see MyBatisUserController#add(UserParam)
     */
    // @ZestTest("001")
    // @Ignore
    @ZestTest
    public void testAddSpy(Param param) {
        mockBefore(param);
        try {
            AddUserResponse expected = param.getExpected();
            doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
        } finally {
            mockAfter();
        }
    }

    private void mockBefore(Param param) {
        oldUserService = userService;
        MybatisUserService spyUserService = Mockito.spy(userService);
        // 常规来说是通过下面的方法对 @Autowired 的对象进行 spy 。但这里不能正常执行的原因是 MyBatisUserController 的 add() 方法上添加了
        // 注释 @Transactional(rollbackFor = Exception.class) ,这样 Spring 就将这个类给代理了，这与 Mockito.spy() 要做的事情冲突了
        // 如果去掉 @Transactional(rollbackFor = Exception.class) 代码可以正常但事务不回滚，与期望不符。 Spring Boot 下没有这个问题
        // https://github.com/mockito/mockito/issues/529
        // https://stackoverflow.com/questions/56803161/spy-a-service-spring-having-transactional-methods?r=SearchResults
        // MybatisUserService spyUserService = mock(MybatisUserService.class, delegatesTo(userService));
        userController.setMybatisUserService(spyUserService);

        if (param.errorWhenInsertUserAuth) {
            // 模拟向 user_auth 表插入数据时发生异常
            // mock an exception occurred when inserting user_auth table data
            BadSqlGrammarException e = new BadSqlGrammarException("init_data test", "mock sql", new SQLException());
            doThrow(e).when(spyUserService).addUserAuth(Mockito.any(User.class));
        } else {
            doNothing().when(spyUserService).addUserAuth(Mockito.any(User.class));
        }
    }

    private void mockAfter() {
        userController.setMybatisUserService(oldUserService);
    }

    public static class Param extends AbstractZestParam<AddUserResponse> {

        public UserParam apiParam;

        public boolean   errorWhenInsertUserAuth = false;

        public String makeUrl() {
            return makeUrl("/mybatis/user/add");
        }
    }
}
