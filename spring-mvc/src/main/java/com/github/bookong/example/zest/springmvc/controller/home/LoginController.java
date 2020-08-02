package com.github.bookong.example.zest.springmvc.controller.home;

import com.github.bookong.example.zest.springmvc.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController extends AbstractController {

    @RequestMapping(value = "/")
    public String home(HttpSession session, Map<String, Object> map) {
        return template("home");
    }
}
