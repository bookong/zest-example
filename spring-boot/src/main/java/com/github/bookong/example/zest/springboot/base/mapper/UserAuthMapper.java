package com.github.bookong.example.zest.springboot.base.mapper;

import com.github.bookong.example.zest.springboot.base.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Jiang Xu
 */
@Mapper
@Component
public interface UserAuthMapper {

    int insert(UserAuth userAuth);
}
