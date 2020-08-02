package com.github.bookong.example.zest.springmvc.tools;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class VelocityToolbox2View extends VelocityView {

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.createVelocityContext(model, request, response);
    }

    @Override
    protected void exposeHelpers(Context velocityContext, HttpServletRequest request, HttpServletResponse response) throws Exception {

        velocityContext.put("esc", org.apache.commons.text.StringEscapeUtils.class);
        velocityContext.put("stringUtils", org.apache.commons.lang3.StringUtils.class);
        velocityContext.put("dateFormatUtils", DateFormatUtils.class);
    }
}
