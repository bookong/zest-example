package com.github.bookong.example.zest.springboot.base.api.param.remark;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Jiang Xu
 */
public class RemarkParam {

    @NotNull(message = "userId cannot be Null")
    private Long   userId;

    @NotNull(message = "xkcdId cannot be Null")
    private Long   xkcdId;

    @NotEmpty(message = "content cannot be empty")
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getXkcdId() {
        return xkcdId;
    }

    public void setXkcdId(Long xkcdId) {
        this.xkcdId = xkcdId;
    }
}
