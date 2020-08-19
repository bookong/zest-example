package com.github.bookong.example.zest.springboot.base.api.resp.remark;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.support.xkcd.Xkcd;

/**
 * @author Jiang Xu
 */
public class RemarkWithComicResponse extends RemarkResponse {

    private Xkcd xkcd;

    public RemarkWithComicResponse(){
        super();
    }

    public RemarkWithComicResponse(Remark remark, Xkcd xkcd){
        super(remark);
        this.xkcd = xkcd;
    }

    public Xkcd getXkcd() {
        return xkcd;
    }

    public void setXkcd(Xkcd xkcd) {
        this.xkcd = xkcd;
    }
}
