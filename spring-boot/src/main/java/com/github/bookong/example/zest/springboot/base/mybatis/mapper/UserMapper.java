package com.github.bookong.example.zest.springboot.base.mybatis.mapper;

import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Jiang Xu
 */
@Mapper
@Component
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    int insert(User record);

    int updateExtInfo(@Param("userId") Long userId, @Param("key") String key, @Param("value") String value);

    List<User> findByCreateTimeRange(@Param("createTimeStart") Date createTimeStart);
}
