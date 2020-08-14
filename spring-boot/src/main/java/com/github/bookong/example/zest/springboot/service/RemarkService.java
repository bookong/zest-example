package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.api.param.remark.RemarkParam;
import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.base.repository.RemarkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jiang Xu
 */
@Service
public class RemarkService {

    @Autowired
    private RemarkRepository remarkRepository;

    public void save(RemarkParam param) {
        Remark remark = new Remark();
        BeanUtils.copyProperties(param, remark);
        remarkRepository.save(remark);
    }
}
