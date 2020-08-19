package com.github.bookong.example.zest.springboot.base.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jiang Xu
 */
@Document(collection = "remark")
@CompoundIndexes({ @CompoundIndex(name = "idx_userId_createTime", def = "{'userId': 1, 'createTime': 1}") })
public class Remark implements Serializable {

    @Id
    private String id;

    private Long   userId;

    /**
     * http://xkcd.com 漫画ID
     */
    @Indexed
    private Long   xkcdId;

    @Indexed
    private String content;

    private Date   createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getXkcdId() {
        return xkcdId;
    }

    public void setXkcdId(Long xkcdId) {
        this.xkcdId = xkcdId;
    }
}
