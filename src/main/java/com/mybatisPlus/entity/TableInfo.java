package com.mybatisPlus.entity;

/***
 * @ClassName Table
 * @Description: Table.java
 * @Author Ahuan
 * @Date 2020/3/30 
 * @Version V1.0
 **/
public class TableInfo {
    /**
     * 表列名
     */
    private String columnName;

    /**
     * 类型
     */
    private String columnType;

    /**
     * 字段注释
     */
    private String colunmCommint;

    @Override
    public String toString() {
        return "{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", colunmCommint='" + colunmCommint + '\'' +
                '}';
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColunmCommint() {
        return colunmCommint;
    }

    public void setColunmCommint(String colunmCommint) {
        this.colunmCommint = colunmCommint;
    }
}