package ${basePackage}.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ${basePackage}.service.${className}Service;
import ${basePackage}.dao.${className}Mapper;

/**
 * Created by Mybatis Generator on ${date}
 */
@Service("${classname}Service")
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    ${className}Mapper  ${classname}Mapper;

}