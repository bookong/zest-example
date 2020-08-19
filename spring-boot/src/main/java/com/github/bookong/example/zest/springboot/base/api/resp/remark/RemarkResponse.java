package com.github.bookong.example.zest.springboot.base.api.resp.remark;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.entity.Remark;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Jiang Xu
 */
public class RemarkResponse extends BaseResponse {

    private String id;
    private Long   userId;
    private Long   xkcdId;
    private String content;
    private String createTime;

    public RemarkResponse(){
        super();
    }

    public RemarkResponse(Remark remark){
        super();
        this.id = id;
        this.userId = userId;
        this.xkcdId = xkcdId;
        this.content = content;
        this.createTime = DateFormatUtils.format(remark.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getXkcdId() {
        return xkcdId;
    }

    public void setXkcdId(Long xkcdId) {
        this.xkcdId = xkcdId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
