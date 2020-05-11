package com.mybatisPlus.service;


import com.mybatisPlus.entity.TableInfo;

import java.io.IOException;
import java.util.List;

/***
 * @ClassName GeneratorService
 * @Description: GeneratorService.java
 * @Author Ahuan
 * @Date 2020/3/30 
 * @Version V1.0
 **/
public interface GeneratorService {

    /**
     * 生成表
     *
     * @param tableName
     */
    void genTable(String tableName) throws IOException;

    /**
     * 获取数据库所有的表
     * @return
     */
    List<String> tableList();

    /**
     * 获取表结构详情
     *
     * @return
     */
    List<TableInfo> tableDetail(String tabelName);
}