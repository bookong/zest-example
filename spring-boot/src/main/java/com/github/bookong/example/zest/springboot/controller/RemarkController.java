package com.github.bookong.example.zest.springboot.controller;

import com.github.bookong.example.zest.springboot.base.api.param.remark.RemarkParam;
import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.remark.RemarkOneDayResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.remark.RemarkWithComicResponse;
import com.github.bookong.example.zest.springboot.base.api.resp.remark.SaveRemarkResponse;
import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import com.github.bookong.example.zest.springboot.service.RemarkService;
import com.github.bookong.example.zest.springboot.support.xkcd.Xkcd;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiang Xu
 */
@Controller
@RequestMapping("/remark")
public class RemarkController {

    @Autowired
    private RemarkService remarkService;

    /**
     * 新增一个评论（演示对 MongoDB 新增）
     */
    @PostMapping("/add")
    @ResponseBody
    public SaveRemarkResponse add(@Validated @RequestBody RemarkParam param) {
        Remark remark = remarkService.add(param);
        return new SaveRemarkResponse(remark.getId());
    }

    /**
     * 获取一个评论并且附带对应的 XKCD 漫画信息（演示接口调用三方接口情况）
     */
    @GetMapping("/{remarkId}/comic")
    @ResponseBody
    public RemarkWithComicResponse getRemarkWithComic(@PathVariable("remarkId") String remarkId) {
        if (StringUtils.isBlank(remarkId)) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "remarkId is empty");
        }

        Pair<Remark, Xkcd> pair = remarkService.getRemarkWithComic(remarkId);
        return new RemarkWithComicResponse(pair.getLeft(), pair.getRight());
    }

    /**
     * 获取用户某个用户最近一天的评论（演示在 MongoDB 中做一个与当前时间有关的查询）
     */
    @GetMapping("/user/{userId}/one-day")
    @ResponseBody
    public RemarkOneDayResponse findUserOneDay(@PathVariable("userId") Long userId) {
        if (userId == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "userId is null");
        }
        return new RemarkOneDayResponse(remarkService.findUserOneDay(userId));
    }

}
