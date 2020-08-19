package com.github.bookong.example.zest.springboot;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.runner.junit5.ZestJUnit5Worker;
import com.github.bookong.zest.util.ZestJsonUtil;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

/**
 * @author Jiang Xu
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractZestTest {

    protected Logger              logger     = LoggerFactory.getLogger(getClass());

    protected ZestJUnit5Worker    zestWorker = new ZestJUnit5Worker();

    @Autowired
    @ZestSource("mysql")
    private DataSource            dataSource;

    @Autowired
    private WebApplicationContext context;

    private MockMvc               mockMvc;

    @BeforeEach
    public void setupMockMvc() throws Exception {
        logger.info("setup MockMvc...");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    public JSONObject doPostAndBaseVerify(String url, String requestBody, BaseResponse expected) {
        return doPostAndBaseVerify(url, requestBody, expected, false);
    }

    public JSONObject doPostAndBaseVerify(String url, String requestBody, BaseResponse expected, boolean showResponse) {
        String responseJson = "";
        try {
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON);
            request.content(requestBody);
            MvcResult mvcResult = mockMvc.perform(request).andReturn();
            responseJson = mvcResult.getResponse().getContentAsString();
            Assert.assertEquals("http status != 200", HttpStatus.SC_OK, mvcResult.getResponse().getStatus());

            JSONObject actual = JSONObject.fromObject(responseJson);
            if (showResponse) {
                logger.info("response json:\n{}", actual.toString(4));
            }

            Assert.assertEquals("code", expected.getCode(), actual.getInt("code"));
            Assert.assertEquals("msg", expected.getMsg(), actual.getString("msg"));
            return actual;

        } catch (AssertionError e) {
            logger.info(responseJson);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(responseJson, e);
        }
    }

    /**
     * 这部分没有写在 zest 代码中是因为 JSONObject 的某些行为（例如验证是否为空对象）在不同版本不一致
     */
    protected void assertEqual(String key, Long expected, JSONObject actual) {
        if (expected == null) {
            Assert.assertTrue(String.format("%s must null", key), actual.get(key) == null);
        } else {
            Assert.assertEquals(key, expected.longValue(), actual.getLong(key));
        }
    }
}
