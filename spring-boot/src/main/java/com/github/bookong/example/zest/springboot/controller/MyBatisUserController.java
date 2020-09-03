package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import com.github.bookong.example.zest.springboot.service.MybatisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 这里演示对 MySQL 数据库进行存取的接口
 * 
 * @author jiangxu
 */
@Controller
@RequestMapping("/mybatis/user")
public class MyBatisUserController {

    @Autowired
    private MybatisUserService mybatisUserService;

    /**
     * 演示对数据库新增操作
     */
    @PostMapping("/add")
    @ResponseBody
    public AddUserResponse add(@Validated @RequestBody UserParam param) {
        User user = mybatisUserService.add(param);
        return new AddUserResponse(user.getId());
    }

    /**
     * 演示更新 json 类型字段
     */
    @PostMapping("/{userId}/ext-info-update")
    @ResponseBody
    public BaseResponse updateExtInfo(@PathVariable("userId") Long userId, @Validated @RequestBody UserExtInfoParam param) {
        if (userId == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "userId is null");
        }
        mybatisUserService.updateExtInfo(userId, param);
        return BaseResponse.OK;
    }

    /**
     * 演示查询一个与当前时间条件有关的 SQL
     */
    @GetMapping("/one-day")
    @ResponseBody
    public UserOneDayResponse findUserOneDay() {
        return new UserOneDayResponse(mybatisUserService.findSimpleUserOneDay());
    }
}
