package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Jiang Xu
 */
@Mapper
@Component
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    int insert(User record);

    int updateByPrimaryKeySelective(User record);

    int updateExtInfo(@Param("userId") Long userId, @Param("key") String key, @Param("value") String value);
}
