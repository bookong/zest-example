package zest;

import com.github.bookong.zest.annotation.ZestConnection;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.runner.junit4.ZestSpringJUnit4ClassRunner;
import com.github.bookong.zest.testcase.ZestTestParam;
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
public class SpringMvcTest {

    @Autowired
    protected @ZestSource("db1") DataSource dataSource;

    @Before
    public void before1() throws Exception {
        System.out.println("before1()");
    }

    @Before
    public void before2() throws Exception {
        System.out.println("before2()");
    }

    @ZestTest("001")
    public void test1(Param param) {
        ZestSqlHelper.showResultInConsole(param.conn, "select * from xkcd");
        System.out.println("test1()");
    }

    public static class Param implements ZestTestParam {

        public @ZestConnection("db1") Connection conn;
        private String                           a;
    }
}
