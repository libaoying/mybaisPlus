package com.mybatisPlus.service.impl;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;
import com.mybatisPlus.constant.SysTemConstant;
import com.mybatisPlus.entity.TableInfo;
import com.mybatisPlus.service.GeneratorService;
import com.mybatisPlus.util.CustomMyBatisGenerator;
import com.mybatisPlus.util.GeneratorUtils;
import com.mybatisPlus.util.JDBCUtils;
import com.mybatisPlus.util.StringUtils;
import freemarker.template.Template;
import jdk.nashorn.internal.scripts.JD;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.*;
import java.util.*;
import java.util.stream.StreamSupport;

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
        String[] str={SysTemConstant.GENERATOR_CONFIG,SysTemConstant.CONTROLLER,SysTemConstant.SERVICE,SysTemConstant.SERVICEIMPL};
        GeneratorUtils gen = new GeneratorUtils();
        gen.generator(mybatisPlusProperties, tableName,SysTemConstant.TEMPLATE_PACKAGE,Arrays.asList(str));
    }
}
