package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.base.api.param.user.UserExtInfoParam;
import com.github.bookong.example.zest.springboot.base.api.param.user.UserParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.SaveUserResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.user.UserResponse;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import com.github.bookong.example.zest.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiangxu
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新增或修改一个用户（演示对数据库新增和修改）
     */
    @PostMapping("/save")
    @ResponseBody
    public SaveUserResponse save(@Validated @RequestBody UserParam param) {
        User user = userService.save(param);
        return new SaveUserResponse(user.getId());
    }

    /**
     * 更新用户的扩展信息（演示更新 json 类型字段）
     */
    @PostMapping("/{userId}/ext-info-update")
    @ResponseBody
    public BaseResponse updateExtInfo(@PathVariable("userId") Long userId, @Validated @RequestBody UserExtInfoParam param) {
        if (userId == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "userId is null");
        }
        userService.updateExtInfo(userId, param);
        return BaseResponse.OK;
    }

    /**
     * 查询一个用户信息（演示使用 Redis 的情况）
     */
    @GetMapping("/{userId}")
    @ResponseBody
    public UserResponse get(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "userId is null");
        }
        return new UserResponse(userService.get(userId));
    }

}
