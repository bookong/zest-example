package com.github.bookong.example.zest.springboot;

import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.junit4.annotation.ZestDataSource;
import com.github.bookong.zest.runner.junit4.annotation.ZestTest;
import com.github.bookong.zest.runner.junit5.ZestJUnit5Luancher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
// @EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
class ApplicationTests {

    private ZestJUnit5Luancher zestJUnit5Luancher = new ZestJUnit5Luancher();

    @Autowired
    @ZestDataSource("db1")
    private DataSource         dataSource;

    @BeforeAll
    static void initAll() {

    }

    @ZestTest
    @TestFactory
    public Stream<DynamicTest> test1() {
        return stream(zestJUnit5Luancher.iterator(this), zestJUnit5Luancher.display(), //
                      info -> {
                          Param param = zestJUnit5Luancher.before(info, Param.class);
                          assertTrue(5 > 4);
                          zestJUnit5Luancher.after(info);
                      });
    }

    public static class Param implements ZestTestParam {

        private String a;
    }

}
