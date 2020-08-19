package com.github.bookong.example.zest.springboot.base.api.resp.remark;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.entity.Remark;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang Xu
 */
public class RemarkOneDayResponse extends BaseResponse {

    private List<RemarkResponse> list = new ArrayList<>();

    public RemarkOneDayResponse(){
        super();
    }

    public RemarkOneDayResponse(List<Remark> remarks){
        super();
        for (Remark remark : remarks) {
            getList().add(new RemarkResponse(remark));
        }
    }

    public List<RemarkResponse> getList() {
        return list;
    }

    public void setList(List<RemarkResponse> list) {
        this.list = list;
    }
}
