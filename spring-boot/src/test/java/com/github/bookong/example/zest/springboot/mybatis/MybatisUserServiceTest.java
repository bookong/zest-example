package com.github.bookong.example.zest.springboot.mybatis;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.UserAuth;
import com.github.bookong.example.zest.springboot.service.MybatisUserService;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestAssertUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

/**
 * 这里演示不使用 MockMvc 直接测试某个 Service ，并且只用 Zest 作为代码和数据分离工具使用
 * 
 * @author Jiang Xu
 */
public class MybatisUserServiceTest extends AbstractZestTest {

    @Autowired
    private MybatisUserService mybatisUserService;

    /**
     * 演示只使用 Zest 作为代码和数据分离工具使用。我们可以用这种方式自己控制对缓存或者 Redis 等的初始化与手动验证<br>
     * 这里也不会写全所有测试情况，只是作为一个例子
     *
     * <pre>
     * 001.xml - A test example
     * </pre>
     *
     * @see MybatisUserService#addAuthToList
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testAddAuthToList() {
        return zestWorker.test(this, AddAuthToListParam.class, param -> {
            List<UserAuth> actualList = param.initList;
            List<UserAuth> expectedList = param.targetList;
            mybatisUserService.addAuthToList(actualList, param.auth);

            assertEquals("list.size()", expectedList.size(), actualList.size());
            for (int i = 0; i < expectedList.size(); i++) {
                String msg = String.format("list[%d].", i);
                UserAuth actual = actualList.get(i);
                UserAuth expected = expectedList.get(i);

                if (expected.getId() == null) {
                    assertNull(msg.concat("id"), actual.getId());
                } else {
                    assertEquals(msg.concat("id"), expected.getId(), actual.getId());
                }

                assertEquals(msg.concat("userId"), expected.getId(), actual.getId());
                assertEquals(msg.concat("auth"), expected.getAuth(), actual.getAuth());

                if ("0001-01-01".equals(DateFormatUtils.format(expected.getExpirationTime(), "yyyy-MM-dd"))) {
                    ZestAssertUtil.verifyFromCurrentTimeRule(param.getZestData(), msg.concat("expirationTime"), 3, 3, Calendar.DAY_OF_YEAR, actual.getExpirationTime());
                } else {
                    ZestAssertUtil.dateEquals(param.getZestData(), msg.concat("expirationTime"), expected.getExpirationTime(), actual.getExpirationTime());
                }
            }
        });
    }

    public static class AddAuthToListParam extends AbstractZestParam<BaseResponse> {

        public String         auth;

        public List<UserAuth> initList;

        public List<UserAuth> targetList;
    }
}
