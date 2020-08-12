package com.github.bookong.example.zest.springboot.base.entity;

import java.util.Date;

public class Remark {
    private Long id;

    private Long userId;

    private Long xkcdId;

    private String content;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}