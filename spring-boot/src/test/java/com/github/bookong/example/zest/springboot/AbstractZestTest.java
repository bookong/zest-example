package com.github.bookong.example.zest.springboot;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.zest.annotation.ZestSource;
import com.github.bookong.zest.executor.MongoExecutor;
import com.github.bookong.zest.runner.junit5.ZestJUnit5Worker;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @author Jiang Xu
 */
@ActiveProfiles("test")
@SpringBootTest
public abstract class AbstractZestTest {

    protected static final Logger        logger     = LoggerFactory.getLogger(AbstractZestTest.class);

    protected static final MongodStarter starter    = MongodStarter.getDefaultInstance();
    protected static final String        host       = "127.0.0.1";
    protected static final int           port       = 27027;

    protected static MongodExecutable    mongodExe;
    protected static MongodProcess       mongod;

    protected ZestJUnit5Worker           zestWorker = new ZestJUnit5Worker();

    @Autowired
    @ZestSource("mysql")
    protected DataSource                 dataSource;

    @Autowired
    @ZestSource(value = "mongo", executorClass = MongoExecutor.class)
    protected MongoTemplate              mongoTemplate;

    @Autowired
    private WebApplicationContext        context;

    private MockMvc                      mockMvc;

    @BeforeAll
    public static void setUp() throws Exception {
        logger.info("start embed MongoDB on {}:{}...", host, port);
        mongodExe = starter.prepare(new MongodConfigBuilder().version(Version.Main.PRODUCTION) //
                                                             .net(new Net(host, port, Network.localhostIsIPv6())) //
                                                             .build());
        mongod = mongodExe.start();
    }

    @AfterAll
    public static void tearDown() {
        logger.info("stop embed MongoDB...");

        if (mongod != null) {
            mongod.stop();
        }

        if (mongodExe != null) {
            mongodExe.stop();
        }
    }

    @BeforeEach
    public void setupMockMvc() throws Exception {
        logger.info("setup MockMvc...");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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

    /**
     * 这部分没有写在 zest 代码中是因为 JSONObject 的某些行为（例如验证是否为空对象）在不同版本不一致
     */
    protected void doAssertEqual(String key, Long expected, JSONObject actual) {
        if (expected == null) {
            assertTrue(actual.get(key) == null, String.format("%s must null", key));
        } else {
            assertEquals(key, expected, actual.getLong(key));
        }
    }

}
