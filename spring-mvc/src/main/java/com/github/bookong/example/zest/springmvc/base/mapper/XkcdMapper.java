package com.github.bookong.example.zest.springmvc.base.mapper;

import com.github.bookong.example.zest.springmvc.base.entity.Xkcd;

public interface XkcdMapper {
    Xkcd selectByPrimaryKey(Long id);
}