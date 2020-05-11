package com.mybatisPlus.autoconfigure;

import com.mybatisPlus.service.GeneratorService;
import com.mybatisPlus.service.impl.GeneratorServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/***
 * @ClassName MybatisPlusAutoConfiguration
 * @Description: MybatisPlusAutoConfiguration.java
 * @Author Ahuan
 * @Date 2020/5/6 
 * @Version V1.0
 **/
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(MybatisPlusProperties.class)
@ConditionalOnClass({GeneratorService.class})
public class MybatisPlusAutoConfiguration {

    private MybatisPlusProperties mybatisPlusProperties;

    public MybatisPlusAutoConfiguration(MybatisPlusProperties mybatisPlusProperties) {
        this.mybatisPlusProperties = mybatisPlusProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public GeneratorService generatorService() {
        return new GeneratorServiceImpl(this.mybatisPlusProperties);
    }
}