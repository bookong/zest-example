package com.github.bookong.example.zest.springmvc.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class AbstractController {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    protected String template(String path) {
        return getTemplatePath().concat(path);
    }

    private String getTemplatePath() {
        String name = getClass().getPackage().getName();
        String pkg = StringUtils.substringAfterLast(name, ".controller.");
        if (StringUtils.isBlank(pkg)) {
            return "";
        }
        return StringUtils.replaceChars(pkg, '.', File.separatorChar).concat(File.separator);
    }
}
