package com.mybatisPlus.util;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;
import com.mybatisPlus.constant.SysTemConstant;
import freemarker.template.Template;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @ClassName GeneratorUtils
 * @Description: GeneratorUtils.java
 * @Author Ahuan
 * @Date 2020/5/9 
 * @Version V1.0
 **/
public class GeneratorUtils {

    /**
     * 代码生成
     * @param properties
     * @param tableName
     * @param templateLocation
     * @param fileNames
     */
    public void generator(MybatisPlusProperties properties,String tableName, String templateLocation, List<String> fileNames) {
        Map<String, Object> dataMap = dataCommandToMap(properties, tableName);
        List<Map<String, String>> templates = getTemplates(dataMap, templateLocation, fileNames);
        for (Map<String, String> template : templates) {
            String fileName = template.get("fileName");
            if (SysTemConstant.GENERATOR_CONFIG.equals(fileName)) {
                generator(new ByteArrayInputStream(template.get("template").getBytes()));
            } else {
                generator(dataMap, fileName);
            }
        }
    }

    /**
     * 封装模板需要dataMap
     *
     * @param tableName
     * @return
     */
    private Map<String, Object> dataCommandToMap(MybatisPlusProperties properties, String tableName) {
        String jdbcUrl = properties.getJdbcUrl();
        jdbcUrl = jdbcUrl.replaceAll("&", "&amp;");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //设置数据库链接
        dataMap.put("jdbcDriver", properties.getJdbcDriver());
        dataMap.put("jdbcUrl", jdbcUrl);
        dataMap.put("jdbcUser", properties.getUsername());
        dataMap.put("jdbcPassword", properties.getPassword());
        //设置代码工程 model dao xml 生成位置
        dataMap.put("javaTargetPackage", properties.getJavaTargetPackage());
        dataMap.put("modelLocation", properties.getEntityPackage());
        dataMap.put("daoLocation", properties.getDaoPackage());
        dataMap.put("xmlTargetLocation", properties.getResourcesTargetPackage());
        dataMap.put("xmlLocation", properties.getXmlPackage());
        //设置 controller service 代码生成配置
        dataMap.put("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        dataMap.put("basePackage", properties.getBasePackage());
        dataMap.put("className", StringUtils.underlineToCamel(tableName));
        dataMap.put("classname", initcap(StringUtils.underlineToCamel(tableName)));
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(tableName)) {
            throw new RuntimeException("tableName is null");
        }
        //多表生成
        if (tableName.contains(",")) {
            Arrays.stream(tableName.split(",")).forEach(
                    e -> {
                        String table = String.format(SysTemConstant.TABLE, e, StringUtils.underlineToCamel(e));
                        sb.append(table).append("\n");
                    });
            dataMap.put("tables", sb.toString());
        } else {
            //单表生成
            String table = String.format(SysTemConstant.TABLE, tableName, StringUtils.underlineToCamel(tableName));
            dataMap.put("tables", table);
        }
        // 设置插件;
        String customPluginS = SysTemConstant.TOSTRING_PLUGIN;
        if (properties.getEnableLombok()) {
            customPluginS = SysTemConstant.LOMBOK_PLUGIN;
        }
        if (properties.getEnableSwagger()) {
            customPluginS += SysTemConstant.SWAGGER2DOC_PLUGIN;
        }
        dataMap.put("customPluginS", customPluginS);
        return dataMap;
    }

    /**
     * 获取
     *
     * @param dataMap
     * @return
     */
    private String getTemplate(Map<String, Object> dataMap, String basePackagePath, String templateName) {
        Template template = null;
        try {
            freemarker.template.Configuration conf = new freemarker.template.Configuration();
            //FTL文件所存在的位置
            conf.setClassForTemplateLoading(this.getClass(), basePackagePath);
            template = conf.getTemplate(templateName); //文件名
            StringWriter w = new StringWriter();
            template.process(dataMap, w);
            String result = w.toString();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("template rendering failed");
        }

    }

    private List<Map<String, String>> getTemplates(Map<String, Object> dataMap, String templateLocation, List<String> fileNames) {
        List<Map<String, String>> list = new ArrayList<>();
        String template = null;
        Map<String, String> templateFiles = null;
        for (String fileName : fileNames) {
            templateFiles = new HashMap<>();
            template = getTemplate(dataMap, templateLocation, fileName);
            templateFiles.put("fileName", fileName);
            templateFiles.put("template", template);
            list.add(templateFiles);
        }
        return list;
    }


    /**
     * entity,mapper,xml代码生成
     *
     * @param is
     */
    private void generator(InputStream is) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            CustomMyBatisGenerator myBatisGenerator = new CustomMyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            for (String warning : warnings) {
                System.out.println(warning);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("code generation error");
        }
    }

    /**
     * controller service 代码生成
     *
     * @param dataMap
     * @param ftlName
     */
    private void generator(Map<String, Object> dataMap, String ftlName) {
        try {
            String template = getTemplate(dataMap, SysTemConstant.TEMPLATE_PACKAGE, ftlName);
            String filename = getFileName(ftlName, (String) dataMap.get("className"), (String) dataMap.get("basePackage"));
            writeFile(filename, template);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("code generation error");
        }
    }

    /**
     * 写入java文件
     *
     * @param fileName
     * @param text
     */
    private void writeFile(String fileName, String text) {
        // 相对路径，如果没有则要建立
        File writeName = new File(fileName);
        try {
            File parentFile = writeName.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            // 创建新文件
            writeName.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
            out.write(text); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取文件名
     */
    private String getFileName(String template, String className, String packageName) {
        String packagePath = "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!StringUtils.isEmpty(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Service.java.ftl")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.ftl")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.ftl")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        return null;
    }

    private static String initcap(String str) {
        if (str == null || " ".equals(str)) {
            return str;
        }
        if (str.length() > 1) {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return str.toLowerCase();
    }
}