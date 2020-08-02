package zest;

import com.github.bookong.zest.core.annotation.ZestDataSource;
import com.github.bookong.zest.core.annotation.ZestJdbcConn;
import com.github.bookong.zest.core.annotation.ZestTest;
import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.ZestSpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.activation.DataSource;
import java.sql.Connection;

@RunWith(ZestSpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class Xx {

    @Autowired
    protected @ZestDataSource("mysql") DataSource dataSource;

    @Before
    public void setUp() throws Exception {

    }

    @ZestTest("001.xml")
    public void testCategoriesCategory(Param params, @ZestJdbcConn("mysql") Connection conn) throws Exception {
        System.out.println("xx");
    }

    public class Param implements ZestTestParam {

    }
}
