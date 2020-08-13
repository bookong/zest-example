package com.github.bookong.example.zest.springboot;

import com.github.bookong.zest.annotation.ZestConnection;
import com.github.bookong.zest.annotation.ZestDataSource;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.junit5.ZestJUnit5Worker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

// @RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
// @EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
class ApplicationTests {

    private ZestJUnit5Worker                  zestWorker = new ZestJUnit5Worker();

    @Autowired
    private @ZestDataSource("db1") DataSource dataSource;

    @BeforeAll
    static void initAll() {
    }

    @ZestTest("001")
    @TestFactory
    public Stream<DynamicTest> test1() {
        return zestWorker.test(this, Param.class, param -> {
            assertTrue(5 > 4);
            assertTrue(5 > 4);
        });
    }

    public static class Param implements ZestTestParam {

        private @ZestConnection("db1") Connection conn;
        private String                            a;
    }

}
