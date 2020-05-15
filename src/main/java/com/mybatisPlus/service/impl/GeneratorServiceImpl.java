package com.mybatisPlus.service.impl;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;
import com.mybatisPlus.constant.SysTemConstant;
import com.mybatisPlus.entity.TableInfo;
import com.mybatisPlus.service.GeneratorService;
import com.mybatisPlus.util.GeneratorUtils;
import com.mybatisPlus.util.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

/***
 * @ClassName GeneratorServiceImpl
 * @Description: GeneratorServiceImpl.java
 * @Author Ahuan
 * @Date 2020/3/30
 * @Version V1.0
 **/
public class GeneratorServiceImpl implements GeneratorService {
    Logger log = LoggerFactory.getLogger(GeneratorService.class);

    private MybatisPlusProperties mybatisPlusProperties;

    public GeneratorServiceImpl(MybatisPlusProperties mybatisPlusProperties) {
        this.mybatisPlusProperties = mybatisPlusProperties;
    }

    @Override
    public List<String> tableList() {
        String sql = SysTemConstant.SQL_TABLE;
        Connection connection = JDBCUtils.getConnection(mybatisPlusProperties.getJdbcDriver(), mybatisPlusProperties.getJdbcUrl(), mybatisPlusProperties.getUsername(), mybatisPlusProperties.getPassword());
        List<String> list = JDBCUtils.queryForList(connection, sql, String.class);
        return list;
    }

    @Override
    public List<TableInfo> tableDetail(String tableName) {
        String sql = SysTemConstant.SQL_TABLE_DETAILS;
        sql = sql.replace("?", tableName);
        Connection connection = JDBCUtils.getConnection(mybatisPlusProperties.getJdbcDriver(), mybatisPlusProperties.getJdbcUrl(), mybatisPlusProperties.getUsername(), mybatisPlusProperties.getPassword());
        List<TableInfo> list = JDBCUtils.queryForList(connection, sql, TableInfo.class);
        return list;
    }

    @Override
    public void genTable(String tableName) {
        GeneratorUtils gen = new GeneratorUtils();
        gen.generator(mybatisPlusProperties, tableName);
    }
}
