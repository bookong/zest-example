package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.entity.User;
import com.github.bookong.example.zest.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jiangxu
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger      logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public @ResponseBody BaseResponse save(@RequestBody UserParam param) {
        logger.info("user/save");
        User user = userService.save(param);

        return new SaveUserResponse(user.getId());
    }
}
