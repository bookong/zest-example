package com.github.bookong.example.zest.springmvc.base.mapper;

import com.github.bookong.example.zest.springmvc.base.entity.Remark;

public interface RemarkMapper {
    Remark selectByPrimaryKey(Long id);
}