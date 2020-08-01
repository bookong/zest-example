package com.github.bookong.example.zest.springmvc.util;

import com.github.bookong.example.zest.springmvc.common.GlobalConstant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ResponseUtil {

    private static final Logger logger            = LoggerFactory.getLogger(ResponseUtil.class);

    public static final String  CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

    public static void writeString(HttpServletResponse response, String respStr) {
        try {
            writeString(response.getOutputStream(), respStr);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static void writeString(OutputStream outputStream, String respStr) {
        if (StringUtils.isBlank(respStr)) {
            return;
        }

        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(outputStream, GlobalConstant.Encoding.UTF_8);
            osw.write(respStr);
            osw.flush();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            IOUtils.closeQuietly(osw);
        }
    }
}
