package zest;

import com.github.bookong.zest.core.annotation.ZestDataSource;
import com.github.bookong.zest.core.annotation.ZestJdbcConn;
import com.github.bookong.zest.core.annotation.ZestTest;
import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.ZestSpringJUnit4ClassRunner;
import com.github.bookong.zest.util.ZestSqlHelper;
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
    public void test1(Param param, @ZestJdbcConn("mysql") Connection conn) throws Exception {
        ZestSqlHelper.showResultSetContent(conn, "select * form xkcd");
        System.out.println("test1()");
    }

    public static class Param implements ZestTestParam {

        private String a;
    }
}
