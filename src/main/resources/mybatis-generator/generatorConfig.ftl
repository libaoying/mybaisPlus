<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="context1" targetRuntime="MyBatis3">

        <!-- 自动识别数据库关键字，默认false，如果设置为true，根据SqlReservedWords中定义的关键字列表； 一般保留默认值，遇到数据库关键字（Java关键字），使用columnOverride覆盖 -->
        <property name="autoDelimitKeywords" value="true"/>
        <!-- 生成的Java文件的编码 -->
        <property name="javaFileEncoding" value="utf-8"/>
        <!-- beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号； -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <!-- 格式化java代码 -->
        <property name="javaFormatter"
                  value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
        <!-- 格式化XML代码 -->
        <property name="xmlFormatter"
                  value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>

        <!--CustomPlugin-->
        ${customPluginS}
        <commentGenerator type="com.mybatisPlus.comment.MyCommentGenerator">
            <property name="suppressAllComments" value="true"/><!-- 是否取消注释 -->
            <property name="suppressDate" value="true"/> <!-- 是否生成注释代时间戳 -->
        </commentGenerator>

        <!-- jdbc连接 -->
        <jdbcConnection driverClass="${jdbcDriver}"
                        connectionURL="${jdbcUrl}" userId="${jdbcUser}"
                        password="${jdbcPassword}"/>

        <!-- 生成实体类地址 -->
        <javaModelGenerator
                targetPackage="${modelLocation}" targetProject="${javaTargetPackage}">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <!-- 生成mapxml文件 -->
        <sqlMapGenerator targetPackage="${xmlLocation}"
                         targetProject="${xmlTargetLocation}">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>
        <!-- 生成mapxml对应client，也就是接口dao -->
        <javaClientGenerator
                targetPackage="${daoLocation}" targetProject="${javaTargetPackage}"
                type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>
        <!-- table可以有多个,每个数据库中的表都可以写一个table，tableName表示要匹配的数据库表,也可以在tableName属性中通过使用%通配符来匹配所有数据库表,只有匹配的表才会自动生成文件 -->
        ${tables}
       <#-- <table schema="general" tableName="${tableName}"
                       domainObjectName="${domainObjectName}" enableCountByExample="false" enableUpdateByExample="false"
                       enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false">
            <property name="useActualColumnNames" value="false"/>
        </table>-->
    </context>
</generatorConfiguration>