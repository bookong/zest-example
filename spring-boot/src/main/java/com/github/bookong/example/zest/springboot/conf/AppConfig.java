package com.github.bookong.example.zest.springboot.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jiang Xu
 */
@Configuration
@ConfigurationProperties(prefix = "zestdemo")
public class AppConfig {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
