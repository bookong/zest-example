package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.entity.User;
import com.github.bookong.example.zest.springboot.service.UserService;
import com.github.bookong.example.zest.springboot.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangxu
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger      logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    /**
     * <pre>
     * preHandle()
     * save user
     * postHandle()
     * afterCompletion()
     * 异常：
     * preHandle()
     * save user
     * CustomInterceptAdvice handle exception
     * afterCompletion()
     * </pre>
     * 
     * @param param
     * @return
     */
    @PostMapping("/save")
    public @ResponseBody BaseResponse save(@RequestBody UserParam param) {
        logger.info("save user");
        User user = userService.save(param);

        return new SaveUserResponse(user.getId());
    }
}
