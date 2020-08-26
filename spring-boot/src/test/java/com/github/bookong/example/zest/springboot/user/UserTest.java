package com.github.bookong.example.zest.springboot.user;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.controller.UserController;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import com.github.bookong.zest.util.ZestSqlHelper;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.util.stream.Stream;

/**
 * 演示针对数据库的测试
 * 
 * @author Jiang Xu
 */
public class UserTest extends AbstractZestTest {

    /**
     * 演示针对 /user/save 接口比较全面的测试，但不模拟出现异常情况，模拟异常使用 {@link UserSaveSpyTest#testSaveSpy()} 来测试
     *
     * <pre>
     * 001.xml - No token parameter
     * 002.xml - The token parameter is incorrect
     * 003.xml - Parameter loginName is empty
     * 004.xml - Parameter password is empty
     * 005.xml - Parameter nickname is empty
     * 006.xml - Added successfully
     * 007.xml - Parameter loginName unique index conflict when adding
     * TODO 009.xml  - 更新成功
     * TODO 010.xml  - 更新时 loginName 主键冲突
     * TODO 011.xml  - 更新时 redis 里已经有旧缓存数据
     * </pre>
     *
     * @see UserController#save(UserParam)
     */
    @ZestTest("006")
    // @ZestTest
    @TestFactory
    public Stream<DynamicTest> testSave() {
        return zestWorker.test(this, SaveParam.class, param -> {
            SaveUserResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            assertEqual("id", expected.getId(), actual);

            // System.out.println(ZestSqlHelper.query(DataSourceUtils.getConnection(dataSource), "select * from user"));
        });
    }

    /**
     * 演示自定义 H2 数据库函数，来模拟 MySQL 的函数 json_set<br>
     * 虽然新版本的 H2 已经支持了 json 类型字段，但自定义函数还是过于复杂，我倾向于将字段类型改为字符串进行测试
     * 
     * <pre>
     * 001.xml - Update completed
     * </pre>
     *
     * @see UserController#updateExtInfo(Long, UserExtInfoParam)
     */
    // @ZestTest("001")
    @ZestTest
    @TestFactory
    public Stream<DynamicTest> testUpdateExtInfo() {
        return zestWorker.test(this, UpdateExtInfoParam.class, param -> {
            BaseResponse expected = param.getExpected();
            doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);
        });
    }

    public static class SaveParam extends AbstractZestParam<SaveUserResponse> {

        public UserParam apiParam;

        public String makeUrl() {
            return makeUrl("/user/save");
        }
    }

    public static class UpdateExtInfoParam extends AbstractZestParam<BaseResponse> {

        public Long             userId;

        public UserExtInfoParam apiParam;

        public String makeUrl() {
            return makeUrl(String.format("/user/%d/ext-info-update", userId));
        }
    }

}
