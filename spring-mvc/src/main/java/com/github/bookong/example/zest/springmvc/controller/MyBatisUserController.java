package com.github.bookong.example.zest.springmvc.controller;

import com.github.bookong.example.zest.springmvc.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springmvc.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.AddUserResponse;
import com.github.bookong.example.zest.springmvc.base.api.resp.user.UserOneDayResponse;
import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;
import com.github.bookong.example.zest.springmvc.exception.ApiException;
import com.github.bookong.example.zest.springmvc.service.MybatisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public AddUserResponse add(@Valid @RequestBody UserParam param) {
        User user = mybatisUserService.add(param);
        return new AddUserResponse(user.getId());
    }

    /**
     * 演示更新 json 类型字段
     */
    @PostMapping("/{userId}/ext-info-update")
    @ResponseBody
    public BaseResponse updateExtInfo(@PathVariable("userId") Long userId, @Valid @RequestBody UserExtInfoParam param) {
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

    public MybatisUserService getMybatisUserService() {
        return mybatisUserService;
    }

    public void setMybatisUserService(MybatisUserService mybatisUserService) {
        this.mybatisUserService = mybatisUserService;
    }
}
