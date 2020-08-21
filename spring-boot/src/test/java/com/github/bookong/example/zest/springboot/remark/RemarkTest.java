package com.github.bookong.example.zest.springboot.remark;

import com.github.bookong.example.zest.springboot.AbstractZestParam;
import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.api.param.remark.RemarkParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.remark.SaveRemarkResponse;
import com.github.bookong.example.zest.springboot.base.repository.RemarkRepository;
import com.github.bookong.zest.annotation.ZestTest;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.bookong.example.zest.springboot.controller.RemarkController;

import java.util.stream.Stream;

/**
 * 演示针对 MongoDB 的测试
 * 
 * @author Jiang Xu
 */
public class RemarkTest extends AbstractZestTest {

    @Autowired
    private RemarkRepository remarkRepository;

    /**
     * 演示针对 /remark/add 的测试
     * 
     * <pre>
     * </pre>
     *
     * @see RemarkController#add(RemarkParam)
     */
    @ZestTest("001")
    // @ZestTest
    @TestFactory
    public Stream<DynamicTest> testAdd() {
        return zestWorker.test(this, AddParam.class, param -> {
            SaveRemarkResponse expected = param.getExpected();
            JSONObject actual = doPostAndBaseVerify(param.makeUrl(), ZestJsonUtil.toJson(param.apiParam), expected, true);

            // TODO id 是 uuid
        });
    }

    public static class AddParam extends AbstractZestParam<SaveRemarkResponse> {

        public RemarkParam apiParam;

        public String makeUrl() {
            return makeUrl("/remark/add");
        }
    }
}
