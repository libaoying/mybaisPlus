package com.mybatisPlus.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @ClassName StringUtils
 * @Description: StringUtils.java
 * @Author Ahuan
 * @Date 2020/4/1 
 * @Version V1.0
 **/
public class StringUtils extends org.springframework.util.StringUtils {
    //下划线转驼峰
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                flag = true;
                continue;   //标志设置为true,跳过
            } else {
                if (flag == true) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        String toString = sb.toString();
        if (!StringUtils.isEmpty(toString) ) {
            toString=toString.substring(0,1).toUpperCase().concat(toString.substring(1));
        }
        return toString;
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String initcap(String str) {
        if (str == null || " ".equals(str)) {
            return str;
        }
        if (str.length() > 1) {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return str.toLowerCase();
    }

    /**
     * 时间格式化
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    public static final char UNDERLINE = '_';


}