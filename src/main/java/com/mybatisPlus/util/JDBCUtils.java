package com.mybatisPlus.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @ClassName JDBCUtils
 * @Description: JDBCUtils.java
 * @Author Ahuan
 * @Date 2020/5/8 
 * @Version V1.0
 **/
public class JDBCUtils {
    // 获取数据库连接
    public static Connection getConnection(String driver, String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw  new RuntimeException("数据库链接失败");
        }
        return conn;
    }

    public static <E> List<E> queryForList(Connection conn, String sql, Class<?> cls) {
        List<E> list = new ArrayList<>();
        Map<String, Object> map = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData rsmd = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (resultSet.next()) {
                map = new HashMap<>();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    map.put(columnName, columnValue);
                }
                if (cls.equals(String.class) ||
                        cls.equals(Integer.class) ||
                        cls.equals(Long.class) ||
                        cls.equals(Double.class) ) {
                    list.add((E) map.values());
                } else if (cls.equals(Map.class)) {
                    list.add((E) map);
                } else {
                    Object o= BeanJsonConvertUtil.jsonToBean(BeanJsonConvertUtil.beanToJson(map), cls);
                    list.add((E) o);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询失败");
        } finally {
            close(conn, preparedStatement, resultSet);
        }
        return list;
    }

    // 释放资源
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        close(conn, stat);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 释放资源
    public static void close(Connection conn, Statement stat) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
