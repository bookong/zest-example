package com.github.bookong.example.zest.springmvc.controller;

import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springmvc.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiang Xu
 */
@Controller
@RequestMapping("/mongo/user")
public class MongoUserController {

    @Autowired
    private MongoUserService mongoUserService;

    @PostMapping("/simple/add")
    @ResponseBody
    public BaseResponse simpleAdd(@Validated @RequestBody UserParam param) {
        mongoUserService.simpleAdd(param);
        return BaseResponse.OK;
    }

    /**
     * 获取最近一天新增用户（演示在 MongoDB 中做一个与当前时间有关的查询）
     */
    @GetMapping("/simple/one-day")
    @ResponseBody
    public UserOneDayResponse findSimpleUserOneDay() {
        return new UserOneDayResponse(mongoUserService.findSimpleUserOneDay());
    }

//    @PostMapping("/complex/add")
//    @ResponseBody
//    public BaseResponse complexAdd(@Validated @RequestBody UserParam param) {
//        mongoUserService.complexAdd(param);
//        return BaseResponse.OK;
//    }
//
//    @GetMapping("/complex/one-day")
//    @ResponseBody
//    public UserOneDayResponse findComplexUserOneDay() {
//        return new UserOneDayResponse(mongoUserService.findComplexUserOneDay());
//    }
}
