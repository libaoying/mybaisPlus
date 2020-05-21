package com.mybatisPlus.util;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @ClassName ControllerUtils
 * @Description: ControllerUtils.java
 * @Author Ahuan
 * @Date 2020/5/14 
 * @Version V1.0
 **/
public class ControllerUtils {

    public static String getControllerJava(MybatisPlusProperties properties,String tableName) {
        String date = StringUtils.date2Str(new Date());
        String basePackage = properties.getBasePackage();
        String className = StringUtils.underlineToCamel(tableName);
        String classname = StringUtils.initcap(className);
        String s = get(date, basePackage, className, classname);
        return s;
    }

    public static String  get(String date,String basePackage,String className,String classname) {
        StringBuilder controllerSb = new StringBuilder();
        controllerSb.append("package ").append(basePackage)
                .append(".controller;").append("\n\n")
                .append("import org.springframework.beans.factory.annotation.Autowired;").append("\n")
                .append("import org.springframework.web.bind.annotation.RequestMapping;").append("\n")
                .append("import org.springframework.web.bind.annotation.RestController;").append("\n\n")
                .append("import ").append(basePackage).append(".service.").append(className)
                .append("Service;").append("\n\n")
                .append("/***\n")
                .append(" *@author Created by Mybatis Generator on ").append(date).append("\n")
                .append(" */\n")
                .append("@RestController").append("\n")
                .append("@RequestMapping(\"/").append(classname).append("\")").append("\n")
                .append("public class ").append(className).append("Controller {").append("\n")
                .append("\n").append("    @Autowired").append("\n")
                .append("    private ").append(className).append("Service ").append(classname).append("Service;").append("\n\n")
                .append("}");
        return  controllerSb.toString();
    }

}