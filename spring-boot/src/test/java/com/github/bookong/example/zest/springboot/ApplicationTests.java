package com.github.bookong.example.zest.springboot;

import com.github.bookong.zest.core.testcase.ZestTestParam;
import com.github.bookong.zest.runner.junit4.annotation.ZestTest;
import com.github.bookong.zest.runner.junit5.ZestJUnit5Luancher;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.DynamicTest.stream;

@SpringBootTest
class ApplicationTests {

    private ZestJUnit5Luancher zestJUnit5Luancher = new ZestJUnit5Luancher();

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
