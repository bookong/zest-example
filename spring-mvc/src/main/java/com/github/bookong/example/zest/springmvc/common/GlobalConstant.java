package com.github.bookong.example.zest.springmvc.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface GlobalConstant {

    interface Encoding {

        Charset UTF8  = StandardCharsets.UTF_8;
        String  UTF_8 = "UTF-8";
    }
}
