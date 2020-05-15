package com.mybatisPlus.controller;

import com.mybatisPlus.entity.TableInfo;
import com.mybatisPlus.response.R;
import com.mybatisPlus.service.GeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author huan
 * @version 1.0
 * @since 2020-05-07
 */
@RestController
@RequestMapping({"/table"})
public class GeneratorController {

    private static final Logger log = LoggerFactory.getLogger(GeneratorController.class);
    @Autowired
    GeneratorService generatorService;

    public GeneratorController() {
    }

    @GetMapping({"/list"})
    public R<List<String>> list() {
        List<String> tableList = null;
        try {
            tableList = this.generatorService.tableList();
            return R.ok(tableList);
        } catch (RuntimeException e) {
            return R.error(e.getMessage());
        }
    }

    @GetMapping({"/details/{tableName}"})
    public R<List<TableInfo>> details(@PathVariable String tableName) {
        List<TableInfo> tableInfos = null;
        try {
            tableInfos = this.generatorService.tableDetail(tableName);
            return R.ok(tableInfos);
        } catch (RuntimeException e) {
            return R.error(e.getMessage());
        }
    }

    @GetMapping({"/generator/{tableName}"})
    public R<String> generator(@PathVariable String tableName) throws IOException {
        try {
            this.generatorService.genTable(tableName);
            return R.ok();
        } catch (RuntimeException e) {
            return R.error(e.getMessage());
        }
    }
}
