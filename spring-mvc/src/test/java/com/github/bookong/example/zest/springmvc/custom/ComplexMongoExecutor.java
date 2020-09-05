package com.github.bookong.example.zest.springmvc.custom;

import com.github.bookong.example.zest.springmvc.base.mongo.entity.Auth;
import com.github.bookong.example.zest.springmvc.base.mongo.entity.ComplexUser;
import com.github.bookong.zest.executor.MongoExecutor;
import com.github.bookong.zest.runner.ZestWorker;
import com.github.bookong.zest.testcase.Source;
import com.github.bookong.zest.testcase.ZestData;
import com.github.bookong.zest.testcase.mongo.Collection;
import com.github.bookong.zest.testcase.mongo.Document;
import com.github.bookong.zest.util.ZestAssertUtil;
import com.github.bookong.zest.util.ZestDateUtil;
import com.github.bookong.zest.util.ZestJsonUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Calendar;
import java.util.List;

/**
 * 扩展 MongoExecutor 应对复杂的 MongoDB Document ，需要覆盖两个方法
 * 
 * @author Jiang Xu
 */
public class ComplexMongoExecutor extends CustomMongoExecutor {

    /**
     * 覆盖此方法，控制 Document 对象的创建
     */
    @Override
    public Object createDocumentData(ZestData zestData, Class<?> entityClass, String collectionName, String xmlContent, boolean isVerifyElement) {
        ComplexUser data = ZestJsonUtil.fromJson(xmlContent, ComplexUser.class);

        /** 对时间进行转换是为了测试与当前时间有关的查询 */
        data.setCreateTime(ZestDateUtil.getDateInZest(zestData, data.getCreateTime()));
        List<Auth> authList = data.getAuthList();
        if (authList != null) {
            for (Auth auth : authList) {
                auth.setExpirationTime(ZestDateUtil.getDateInZest(zestData, auth.getExpirationTime()));
            }
        }

        return data;
    }

    /** 覆盖此方法，自己控制单元测试执行后数据库中 Document 内容的验证 */
    @Override
    public void verifyDocument(MongoOperations operator, ZestData zestData, Source source, Collection collection, int rowIdx, Document expectedDocument, Object actualData) {
        ComplexUser expected = (ComplexUser) expectedDocument.getData();
        ComplexUser actual = (ComplexUser) actualData;

        // 以 _RULE_ 的字符串 或 0001-01-01 开头的时间做为标识，这些内容使用规则进行验证
        if ("_RULE_".equals(expected.getId())) {
            ZestAssertUtil.verifyRegExpRule(zestData, "id", "^[0-9a-z]*$", actual.getId());
        } else {
            Assert.assertEquals("id", expected.getId(), actual.getId());
        }

        Assert.assertEquals("loginName", expected.getLoginName(), actual.getLoginName());
        Assert.assertEquals("password", expected.getPassword(), actual.getPassword());
        Assert.assertEquals("nickname", expected.getNickname(), actual.getNickname());

        if ("_RULE_".equals(expected.getToken())) {
            ZestAssertUtil.verifyRegExpRule(zestData, "token", "^USER_[0-9]*$", actual.getToken());
        } else {
            Assert.assertEquals("token", expected.getToken(), actual.getToken());
        }

        if ("0001-01-01".equals(DateFormatUtils.format(expected.getCreateTime(), "yyyy-MM-dd"))) {
            ZestAssertUtil.verifyCurrentTimeRule(zestData, "createTime", actual.getCreateTime());
        } else {
            ZestAssertUtil.dateEquals(zestData, "createTime", expected.getCreateTime(), actual.getCreateTime());
        }

        List<Auth> expectedAuthList = expected.getAuthList();
        List<Auth> actualAuthList = actual.getAuthList();

        if (expectedAuthList == null) {
            Assert.assertNull("authList", actualAuthList);
        } else {
            Assert.assertEquals("authList.size()", expectedAuthList.size(), actualAuthList.size());
            for (int i = 0; i < expectedAuthList.size(); i++) {
                String msg = String.format("authList[%d].", i);
                verifyAuth(zestData, msg, expectedAuthList.get(i), actualAuthList.get(i));
            }
        }
    }

    private void verifyAuth(ZestData zestData, String baseMsg, Auth expected, Auth actual) {
        Assert.assertEquals(baseMsg.concat("auth"), expected.getAuth(), actual.getAuth());
        if ("0001-01-01".equals(DateFormatUtils.format(expected.getExpirationTime(), "yyyy-MM-dd"))) {
            ZestAssertUtil.verifyFromCurrentTimeRule(zestData, baseMsg.concat("expirationTime"), 3, 3, Calendar.DAY_OF_YEAR, actual.getExpirationTime());
        } else {
            ZestAssertUtil.dateEquals(zestData, baseMsg.concat("expirationTime"), expected.getExpirationTime(), actual.getExpirationTime());
        }
    }
}
