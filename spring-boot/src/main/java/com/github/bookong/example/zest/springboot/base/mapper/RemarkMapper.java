package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.Remark;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemarkMapper {
    Remark selectByPrimaryKey(Long id);
}