package com.mybatisPlus.util;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @ClassName ControllerUtils
 * @Description: ControllerUtils.java
 * @Author Ahuan
 * @Date 2020/5/14 
 * @Version V1.0
 **/
public class GeneratorConfigUtils {

    public static String getGeneratorConfig(MybatisPlusProperties properties, String tableName) {
        // 设置插件;
        String customPluginS = TOSTRING_PLUGIN;
        if (properties.getEnableLombok()) {
            customPluginS = LOMBOK_PLUGIN;
        }
        if (properties.getEnableSwagger()) {
            customPluginS += SWAGGER2DOC_PLUGIN;
        }
        String jdbcDriver = properties.getJdbcDriver();
        String jdbcUrl = properties.getJdbcUrl();
        jdbcUrl = jdbcUrl.replaceAll("&", "&amp;");
        String jdbcUser = properties.getUsername();
        String jdbcPassword = properties.getPassword();

        String javaTargetPackage = properties.getJavaTargetPackage();
        String modelLocation = properties.getEntityPackage();
        String daoLocation = properties.getDaoPackage();
        String xmlTargetLocation = properties.getXmlPackage();
        String xmlLocation = properties.getResourcesTargetPackage();
        StringBuilder sbTable = new StringBuilder();
        String tables = "";
        if (StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        //多表生成
        if (tableName.contains(",")) {
            List<String> tableNames = Arrays.stream(tableName.split(",")).collect(Collectors.toList());
            for (String tn:tableNames) {
                String table = String.format(TABLE, tn, StringUtils.underlineToCamel(tn));
                sbTable.append(table).append("\n");
            }
            tables=sbTable.toString();
        } else {
            //单表生成
            tables = String.format(TABLE, tableName, StringUtils.underlineToCamel(tableName));
        }

        return get(customPluginS, jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword,
                javaTargetPackage, modelLocation, daoLocation, xmlTargetLocation, xmlLocation, tables);
    }

    private static String get(String customPluginS, String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword
            , String javaTargetPackage, String modelLocation, String daoLocation,String xmlLocation,  String xmlTargetLocation, String tables) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE generatorConfiguration PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\"\n        \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">\n<generatorConfiguration>\n    <context id=\"context1\" targetRuntime=\"MyBatis3\">\n        <property name=\"autoDelimitKeywords\" value=\"true\"/>\n        <property name=\"javaFileEncoding\" value=\"utf-8\"/>\n        <property name=\"beginningDelimiter\" value=\"`\"/>\n        <property name=\"endingDelimiter\" value=\"`\"/>\n        <property name=\"javaFormatter\"\n                  value=\"org.mybatis.generator.api.dom.DefaultJavaFormatter\"/>\n        <property name=\"xmlFormatter\"\n                  value=\"org.mybatis.generator.api.dom.DefaultXmlFormatter\"/>\n        <plugin type=\"org.mybatis.generator.plugins.SerializablePlugin\"/>\n").append("        ").append(customPluginS).append("\n").append("        <commentGenerator type=\"com.mybatisPlus.comment.MyCommentGenerator\">").append("\n").append("           <property name=\"suppressAllComments\" value=\"true\"/><!-- 是否取消注释 -->").append("\n").append("            <property name=\"suppressDate\" value=\"true\"/> <!-- 是否生成注释代时间戳 -->").append("\n").append("        </commentGenerator>\n        <jdbcConnection driverClass=\"").append(jdbcDriver).append("\"").append("\n").append("                       connectionURL=\"").append(jdbcUrl).append("\" userId=\"").append(jdbcUser).append("\"\n                        password=\"").append(jdbcPassword).append("\"/>").append("\n").append("        <javaModelGenerator\n                targetPackage=\"").append(modelLocation).append("\" targetProject=\"").append(javaTargetPackage).append("\">\n            <property name=\"enableSubPackages\" value=\"false\"/>\n            <property name=\"trimStrings\" value=\"true\"/>\n        </javaModelGenerator>\n        <sqlMapGenerator targetPackage=\"").append(xmlLocation).append("\"\n                         targetProject=\"").append(xmlTargetLocation).append("\">\n            <property name=\"enableSubPackages\" value=\"false\"/>\n        </sqlMapGenerator>\n        <javaClientGenerator\n                targetPackage=\"").append(daoLocation).append("\" targetProject=\"").append(javaTargetPackage).append("\"\n                type=\"XMLMAPPER\">\n            <property name=\"enableSubPackages\" value=\"false\"/>\n        </javaClientGenerator>\n        ").append(tables).append("\n").append("    </context>\n</generatorConfiguration>");
        return sb.toString();
    }

    //plugs
    private static final String LOMBOK_PLUGIN = "<plugin type=\"com.mybatisPlus.plugin.LombokPlugin\"/>\n";
    private static final String SWAGGER2DOC_PLUGIN = "<plugin type=\"com.mybatisPlus.plugin.Swagger2DocPlugin\"/>\n";
    private static final String TOSTRING_PLUGIN = "<plugin type=\"com.mybatisPlus.plugin.ToStringPlugin\"/>\n";
    //表
    private static final String TABLE="<table schema=\"general\" tableName=\"%s\"\n" +
            "               domainObjectName=\"%s\" enableCountByExample=\"false\" enableUpdateByExample=\"false\"\n" +
            "               enableDeleteByExample=\"false\" enableSelectByExample=\"false\" selectByExampleQueryId=\"false\">\n" +
            "            <property name=\"useActualColumnNames\" value=\"false\"/>\n" +
            "           <generatedKey column=\"id\" sqlStatement=\"JDBC\" identity=\"true\"></generatedKey>"+
            "        </table>";
}