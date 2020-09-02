package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
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

    @PostMapping("/simple/save")
    @ResponseBody
    public SaveUserResponse simpleSave(@Validated @RequestBody UserParam param) {
        SimpleUser user = mongoUserService.simpleSave(param);
        return new SaveUserResponse(user.getId());
    }
}
