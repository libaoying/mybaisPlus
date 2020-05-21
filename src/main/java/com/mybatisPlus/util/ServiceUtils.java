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
public class ServiceUtils {
    
    public static String getServiceJava(MybatisPlusProperties properties,String tableName) {
        String date = StringUtils.date2Str(new Date());
        String basePackage = properties.getBasePackage();
        String className = StringUtils.underlineToCamel(tableName);

        return get(basePackage,date,className);
    }
    private static final String SERVICE = "package @basePackage.service;\n\n/**\n * Created by Mybatis Generator on on @date\n */\n\npublic interface  @classNameService{\n\n}";

    public static String  get(String basePackage,String date,String className) {
        StringBuilder sb=new StringBuilder();
        sb.append("package ").append(basePackage).append(".service;\n\n")
                .append("/***\n")
                .append(" *@author Created by Mybatis Generator on on ").append(date).append("\n")
                .append(" */\n")
                .append("public interface ")
                .append(className)
                .append("Service {\n\n}");
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = get("basePackage", "date", "className");
        System.out.println(s);
    }
}