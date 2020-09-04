package com.github.bookong.example.zest.springmvc.base.mybatis.mapper;

import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    int insert(User record);

    int updateExtInfo(@Param("userId") Long userId, @Param("key") String key, @Param("value") String value);

    List<User> findByCreateTimeRange(@Param("createTimeStart") Date createTimeStart);
}
