package com.github.bookong.example.zest.springboot.base.repository;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jiang Xu
 */
public interface RemarkRepositoryCustom {

    /** 查询用户最近一天的评论 */
    List<Remark> searchRemarkInTheLastDay(Long userId);
}
