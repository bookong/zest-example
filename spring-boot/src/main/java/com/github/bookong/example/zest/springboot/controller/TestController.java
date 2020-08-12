package com.github.bookong.example.zest.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author jiangxu
 */
@Controller
public class TestController {

    @GetMapping("/hi")
    public String hi(Map map) {
        map.put("name", "啦啦啦");
        return "/tt/hello";
    }
}
