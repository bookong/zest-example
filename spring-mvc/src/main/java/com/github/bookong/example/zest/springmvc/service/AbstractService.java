package com.github.bookong.example.zest.springmvc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

/**
 * @author Jiang Xu
 */
public abstract class AbstractService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String convertPassword(String original) {
        return DigestUtils.md5DigestAsHex(original.getBytes());
    }
}
