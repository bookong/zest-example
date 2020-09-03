package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springboot.base.mongo.repository.SimpleUserRepository;
import com.github.bookong.example.zest.springboot.service.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/complex/add")
    @ResponseBody
    public BaseResponse complexAdd(@Validated @RequestBody UserParam param) {
        mongoUserService.complexAdd(param);
        return BaseResponse.OK;
    }
}
