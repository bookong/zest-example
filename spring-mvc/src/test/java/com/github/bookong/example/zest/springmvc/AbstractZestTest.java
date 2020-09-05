package com.github.bookong.example.zest.springmvc;

import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.custom.CustomMongoExecutor;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.runner.junit4.ZestSpringJUnit4ClassRunner;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @author Jiang Xu
 */
@RunWith(ZestSpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public abstract class AbstractZestTest {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractZestTest.class);

    @Autowired
    @ZestSource("mysql")
    protected DataSource          dataSource;

    @Autowired
    @ZestSource(value = "mongo", executorClass = CustomMongoExecutor.class)
    protected MongoTemplate       mongoTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc               mockMvc;

    @BeforeClass
    public static void setUp() throws Exception {
        // TODO
    }

    @AfterClass
    public static void tearDown() {
        // TODO
    }

    protected <T> T getTargetObject(Object proxy) {
        if ((AopUtils.isJdkDynamicProxy(proxy))) {
            try {
                return (T) getTargetObject(((Advised) proxy).getTargetSource().getTarget());
            } catch (Exception e) {
                throw new RuntimeException("Failed to unproxy target.", e);
            }
        }
        return (T) proxy;
    }

    @Before
    public void setupMockMvc() throws Exception {
        logger.info("setup MockMvc ...");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        logger.info("setup Mockito ...");
        MockitoAnnotations.initMocks(this);
    }

    /** Make an http request in post method and simply verify the response content */
    public JSONObject doPostAndBaseVerify(String url, String requestBody, BaseResponse expected, boolean showResponse) {
        String responseJson = "";
        try {
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
            request.content(requestBody);
            MvcResult mvcResult = mockMvc.perform(request).andReturn();
            responseJson = mvcResult.getResponse().getContentAsString();
            assertEquals("http status != 200", HttpStatus.SC_OK, mvcResult.getResponse().getStatus());

            JSONObject actual = JSONObject.fromObject(responseJson);
            if (showResponse) {
                logger.info("response json:\n{}", actual.toString(4));
            }

            assertEquals("code", expected.getCode(), actual.getInt("code"));
            assertEquals("msg", expected.getMsg(), actual.getString("msg"));
            return actual;

        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(responseJson, e);
        }
    }

    public JSONObject doGetAndBaseVerify(String url, BaseResponse expected, boolean showResponse) {
        String responseJson = "";
        try {
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
            MvcResult mvcResult = mockMvc.perform(request).andReturn();
            responseJson = mvcResult.getResponse().getContentAsString();
            assertEquals("http status != 200", HttpStatus.SC_OK, mvcResult.getResponse().getStatus());

            JSONObject actual = JSONObject.fromObject(responseJson);
            if (showResponse) {
                logger.info("response json:\n{}", actual.toString(4));
            }

            assertEquals("code", expected.getCode(), actual.getInt("code"));
            assertEquals("msg", expected.getMsg(), actual.getString("msg"));
            return actual;

        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(responseJson, e);
        }
    }

    protected void doAssertEqual(String key, Long expected, JSONObject actual) {
        if (expected == null) {
            Assert.assertNull(String.format("%s must null", key), actual.get(key));
        } else {
            Assert.assertEquals(key, expected.longValue(), actual.getLong(key));
        }
    }
}
