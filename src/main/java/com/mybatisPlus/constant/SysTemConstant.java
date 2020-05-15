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



}
