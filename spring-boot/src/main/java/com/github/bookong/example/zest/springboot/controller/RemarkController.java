package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.api.param.remark.RemarkParam;
import com.github.bookong.example.zest.springboot.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.service.RemarkService;
import com.github.bookong.example.zest.springboot.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jiang Xu
 */
@Controller
@RequestMapping("/remark")
public class RemarkController {

    private Logger        logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemarkService remarkService;

    @PostMapping("/save")
    public @ResponseBody BaseResponse save(@RequestBody String json) {
        logger.info("save remark: {}", json);
        remarkService.save(JsonUtil.fromJson(json, RemarkParam.class));

        return new BaseResponse();
    }
}
