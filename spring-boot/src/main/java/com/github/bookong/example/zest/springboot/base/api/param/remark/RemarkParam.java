package com.github.bookong.example.zest.springboot.base.api.param.remark;

/**
 * @author Jiang Xu
 */
public class RemarkParam {

    private String id;

    private Long   userId;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
