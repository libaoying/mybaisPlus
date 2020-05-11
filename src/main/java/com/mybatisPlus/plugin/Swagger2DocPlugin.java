package com.mybatisPlus.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author Created by ahuan 2018/4/9.
 * @version ver1.0
 * description 根据数据库注释对实体类增加swagger2文档注解
 */
public class Swagger2DocPlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        String classAnnotation = "@ApiModel(value=\"" + topLevelClass.getType() + "\")";
        if (!topLevelClass.getAnnotations().contains(classAnnotation)) {
            topLevelClass.addAnnotation(classAnnotation);
        }

        String apiModelAnnotationPackage = properties.getProperty("apiModelAnnotationPackage");
        String apiModelPropertyAnnotationPackage = properties.getProperty("apiModelPropertyAnnotationPackage");
        if (null == apiModelAnnotationPackage) apiModelAnnotationPackage = "io.swagger.annotations.ApiModel";
        if (null == apiModelPropertyAnnotationPackage)
            apiModelPropertyAnnotationPackage = "io.swagger.annotations.ApiModelProperty";

        topLevelClass.addImportedType(apiModelAnnotationPackage);
        topLevelClass.addImportedType(apiModelPropertyAnnotationPackage);
        String remarks = introspectedColumn.getRemarks();
        String javaProperty = introspectedColumn.getJavaProperty();
        String value = StringUtils.isEmpty(remarks) ? javaProperty : remarks;
        field.addAnnotation("@ApiModelProperty(value=\"" + value + "\")");
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
}