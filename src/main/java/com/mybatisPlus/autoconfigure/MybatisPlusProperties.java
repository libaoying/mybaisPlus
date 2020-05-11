package com.mybatisPlus.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * 说明：MybatisPlus配置类
 *
 * @author ahuan
 * @date 2020-05-03 15:31
 * @since 1.0
 */
@ConfigurationProperties(prefix = MybatisPlusProperties.MYBATIS_PREFIX)
public class MybatisPlusProperties {

    /**
     * 配置文件前缀
     */
    public static final String MYBATIS_PREFIX = "mybatisplus";

    private String jdbcDriver;

    private String jdbcUrl;

    private String username;

    private String password;

    private String project=System.getProperty("user.dir");

    private String basePackage;

    private String entityPackage="entity";

    private String daoPackage="dao";

    private String xmlPackage="mapper";

    private Boolean enableLombok = true;

    private Boolean enableSwagger = true;

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public Boolean getEnableLombok() {
        return enableLombok;
    }

    public void setEnableLombok(Boolean enableLombok) {
        this.enableLombok = enableLombok;
    }

    public Boolean getEnableSwagger() {
        return enableSwagger;
    }

    public void setEnableSwagger(Boolean enableSwagger) {
        this.enableSwagger = enableSwagger;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProject() {
        return this.project ;
    }

    public void setProject(String project) {
        if (project.endsWith("/")){
            project=project.substring(0,project.length()-1);
        }
        this.project = project;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getEntityPackage() {
        return this.basePackage +"."+entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getDaoPackage() {
        return this.basePackage +"."+ daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getXmlPackage() {
        return  xmlPackage;
    }

    public void setXmlPackage(String xmlPackage) {
        this.xmlPackage = xmlPackage;
    }

    public String getResourcesTargetPackage() {
        return this.getProject()+ File.separator +"src"+ File.separator +"main"+ File.separator +"resources" ;
    }

    public String getJavaTargetPackage() {
        return this.getProject()+ File.separator +"src"+ File.separator +"main"+ File.separator +"java" ;
    }
}