package com.mybatisPlus.constant;

/**
 * @author ahuan
 * @Title:
 * @Package
 * @Description:
 * @date 2020/3/3112:39
 */
public interface SysTemConstant {

    //sql
    String SQL_TABLE = "select TABLE_NAME  from information_schema.TABLES where TABLE_SCHEMA=(select database())";
    String SQL_TABLE_DETAILS = "select COLUMN_NAME AS 'columnName' ,DATA_TYPE AS 'columnType'  ,COLUMN_COMMENT AS 'colunmCommint'  from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME= '?' ";

    //表
    String TABLE="<table schema=\"general\" tableName=\"%s\"\n" +
            "               domainObjectName=\"%s\" enableCountByExample=\"false\" enableUpdateByExample=\"false\"\n" +
            "               enableDeleteByExample=\"false\" enableSelectByExample=\"false\" selectByExampleQueryId=\"false\">\n" +
            "            <property name=\"useActualColumnNames\" value=\"false\"/>\n" +
            "        </table>";
    //plugs
    String LOMBOK_PLUGIN="<plugin type=\"com.mybatisPlus.plugin.LombokPlugin\"/>\n";
    String SWAGGER2DOC_PLUGIN="<plugin type=\"com.mybatisPlus.plugin.Swagger2DocPlugin\"/>\n";
    String TOSTRING_PLUGIN="<plugin type=\"com.mybatisPlus.plugin.ToStringPlugin\"/>\n";

    //controller
    String CONTROLLER="Controller.java.ftl";

    //Service
    String SERVICE="Service.java.ftl";

    //ServiceImpl
    String SERVICEIMPL="ServiceImpl.java.ftl";

    //TEMPLATE_PACKAGE
    String TEMPLATE_PACKAGE="/mybatis-generator";

    //GENERATOR_CONFIG
    String GENERATOR_CONFIG="generatorConfig.ftl";
}
