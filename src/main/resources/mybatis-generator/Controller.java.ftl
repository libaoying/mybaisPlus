package ${basePackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${basePackage}.service.${className}Service;

/**
 * Created by Mybatis Generator on on ${date}
 */
@RestController
@RequestMapping("/${classname}")
public class ${className}Controller{

    @Autowired
    private ${className}Service ${classname}Service;

}