package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.Xkcd;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XkcdMapper {
    Xkcd selectByPrimaryKey(Long id);
}