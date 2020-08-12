package zest;

import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.junit4.ZestSpringJUnit4ClassRunner;
import com.github.bookong.zest.runner.junit4.annotation.ZestDataSource;
import com.github.bookong.zest.runner.junit4.annotation.ZestJdbcConn;
import com.github.bookong.zest.runner.junit4.annotation.ZestTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(ZestSpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class DemoTest {

    @Autowired
    protected @ZestDataSource("db1") DataSource dataSource;

    @Before
    public void before1() throws Exception {
        System.out.println("before1()");
    }

    @Before
    public void before2() throws Exception {
        System.out.println("before2()");
    }

    @ZestTest("001")
    public void test1(Param param, @ZestJdbcConn("db1") Connection conn) throws Exception {
        // ZestSqlHelper.showResultInConsole(conn, "select * from xkcd");
        System.out.println("test1()");
    }

    public static class Param implements ZestTestParam {

        private String a;
    }
}
