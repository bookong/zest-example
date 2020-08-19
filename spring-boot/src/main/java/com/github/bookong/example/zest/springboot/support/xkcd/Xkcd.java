package com.github.bookong.example.zest.springboot.support.xkcd;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jiang Xu
 */
public class Xkcd {

    private String title;

    @JsonProperty("safe_title")
    private String safeTitle;

    private String alt;

    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSafeTitle() {
        return safeTitle;
    }

    public void setSafeTitle(String safeTitle) {
        this.safeTitle = safeTitle;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
