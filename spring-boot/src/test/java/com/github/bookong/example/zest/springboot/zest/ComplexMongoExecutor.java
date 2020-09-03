package com.github.bookong.example.zest.springboot.zest;

import com.github.bookong.example.zest.springboot.base.mongo.entity.Auth;
import com.github.bookong.example.zest.springboot.base.mongo.entity.ComplexUser;
import com.github.bookong.zest.executor.MongoExecutor;
import com.github.bookong.zest.testcase.Source;
import com.github.bookong.zest.testcase.ZestData;
import com.github.bookong.zest.testcase.mongo.Collection;
import com.github.bookong.zest.testcase.mongo.Document;
import com.github.bookong.zest.util.ZestAssertUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Calendar;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * @author Jiang Xu
 */
public class ComplexMongoExecutor extends MongoExecutor {

    public void verifyDocument(MongoOperations operator, ZestData zestData, Source source, Collection collection, int rowIdx, Document expectedDocument, Object actualData) {
        ComplexUser expected = (ComplexUser) expectedDocument.getData();
        ComplexUser actual = (ComplexUser) actualData;

        if ("_RULE_".equals(expected.getId())) {
            ZestAssertUtil.verifyRegExpRule(zestData, "id", "^[0-9a-z]*$", actual.getId());
        } else {
            assertEquals("id", expected.getId(), actual.getId());
        }

        assertEquals("loginName", expected.getLoginName(), actual.getLoginName());
        assertEquals("password", expected.getPassword(), actual.getPassword());
        assertEquals("nickname", expected.getNickname(), actual.getNickname());

        if ("_RULE_".equals(expected.getToken())) {
            ZestAssertUtil.verifyRegExpRule(zestData, "token", "^USER_[0-9]*$", actual.getToken());
        } else {
            assertEquals("token", expected.getToken(), actual.getToken());
        }

        if ("0001-01-01".equals(DateFormatUtils.format(expected.getCreateTime(), "yyyy-MM-dd"))) {
            ZestAssertUtil.verifyCurrentTimeRule(zestData, "createTime", actual.getCreateTime());
        } else {
            ZestAssertUtil.dateEquals(zestData, "createTime", expected.getCreateTime(), actual.getCreateTime());
        }

        List<Auth> expectedAuthList = expected.getAuthList();
        List<Auth> actualAuthList = actual.getAuthList();

        if (expectedAuthList == null) {
            assertNull("authList", actualAuthList);
        } else {
            assertEquals("authList.size()", expectedAuthList.size(), actualAuthList.size());
            for (int i = 0; i < expectedAuthList.size(); i++) {
                String msg = String.format("authList[%d].", i);
                verifyAuth(zestData, msg, expectedAuthList.get(i), actualAuthList.get(i));
            }
        }
    }

    private void verifyAuth(ZestData zestData, String baseMsg, Auth expected, Auth actual) {
        assertEquals(baseMsg.concat("auth"), expected.getAuth(), actual.getAuth());
        if ("0001-01-01".equals(DateFormatUtils.format(expected.getExpirationTime(), "yyyy-MM-dd"))) {
            ZestAssertUtil.verifyFromCurrentTimeRule(zestData, baseMsg.concat("expirationTime"), 3, 3, Calendar.DAY_OF_YEAR, actual.getExpirationTime());
        } else {
            ZestAssertUtil.dateEquals(zestData, baseMsg.concat("expirationTime"), expected.getExpirationTime(), actual.getExpirationTime());
        }
    }
}
