package com.mybatisPlus.comment;


import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.*;

/***
 * mybatis generator生成注释插件
 * @author huan
 * @Date 2020-03-22
 */
public class MyCommentGenerator extends DefaultCommentGenerator {
    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /**
     * 此处获取column的注释
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * "+introspectedColumn.getRemarks());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        String name = method.getName();
        List<Parameter> parameters = method.getParameters();
        FullyQualifiedJavaType returnType = method.getReturnType();
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + ANNOTATION.getOrDefault(name,name));
        for (Parameter param : parameters) {
            String paramName = param.getName();
            method.addJavaDocLine(" * @param " + paramName);
        }
        if (returnType != null) {
            method.addJavaDocLine(" * @return " + returnType.getShortName());
        }
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    private static Map<String, String> ANNOTATION = new HashMap<>();

    static {
        ANNOTATION.put("updateByPrimaryKey", "根据主键更新");
        ANNOTATION.put("updateByPrimaryKeySelective", "根据主键选择更新");
        ANNOTATION.put("selectByPrimaryKey", "根据主键查询");
        ANNOTATION.put("insertSelective", "选择性新增");
        ANNOTATION.put("insert", "新增");
        ANNOTATION.put("deleteByPrimaryKey", "根据主键删除");
    }
}