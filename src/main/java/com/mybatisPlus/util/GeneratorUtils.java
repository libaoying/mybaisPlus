package com.mybatisPlus.util;

import com.mybatisPlus.autoconfigure.MybatisPlusProperties;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/***
 * @ClassName GeneratorUtils
 * @Description: GeneratorUtils.java
 * @Author Ahuan
 * @Date 2020/5/9 
 * @Version V1.0
 **/
public class GeneratorUtils {

    private static final Logger log = LoggerFactory.getLogger(GeneratorUtils.class);

    /**
     * 代码生成
     * @param properties
     * @param tableName
     */
    public void generator(MybatisPlusProperties properties, String tableName) {
        String javaTargetPackage = properties.getJavaTargetPackage();
        String basePackage = properties.getBasePackage();
        basePackage = basePackage.replaceAll("\\.", "/");
        String className = StringUtils.underlineToCamel(tableName);

        //model dao xml
        String generatorConfig = GeneratorConfigUtils.getGeneratorConfig(properties, tableName);

        //controller
        String controllerName = getFileName("controller", javaTargetPackage, basePackage, className);
        String controller = ControllerUtils.getControllerJava(properties, tableName);

        //service
        String serviceName = getFileName("service", javaTargetPackage, basePackage, className);
        String service = ServiceUtils.getServiceJava(properties, tableName);

        //serviceImpl
        String serviceImplName = getFileName("serviceImpl", javaTargetPackage, basePackage, className);
        String serviceImpl = ServiceImplUtils.getServiceImplJava(properties, tableName);

        generator(generatorConfig);
        writeFile(controllerName, controller);
        writeFile(serviceName, service);
        writeFile(serviceImplName, serviceImpl);
    }

    /**
     * entity,mapper,xml代码生成
     * @param generatorConfig
     */
    private void generator(String generatorConfig) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(generatorConfig.getBytes());
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
     * 获取文件名
     * @param type
     * @param javaTargetPackage
     * @param basePackage
     * @param className
     * @return
     */
    private static String getFileName(String type, String javaTargetPackage, String basePackage, String className) {
        String filename = "";
        switch (type) {
            case "controller":
                filename = javaTargetPackage + "/" + basePackage + "/controller/" + className + "Controller.java";
                break;
            case "service":
                filename = javaTargetPackage + "/" + basePackage + "/service/" + className + "Service.java";
                break;
            case "serviceImpl":
                filename = javaTargetPackage + "/" + basePackage + "/service/impl/" + className + "ServiceImpl.java";
                break;
            default:
                break;
        }
        return filename;
    }

    /**
     * 写入java文件
     * @param fileName
     * @param text
     */
    private void writeFile(String fileName, String text) {
        // 相对路径，如果没有则要建立
        File writeName = new File(fileName);
        BufferedWriter out =null;
        try {
            File parentFile = writeName.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            // 创建新文件
            writeName.createNewFile();
            out = new BufferedWriter(new FileWriter(writeName));
            out.write(text);
            out.flush();

            log.info("{} 创建成功！", writeName.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}