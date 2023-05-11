package com.gaswell.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCUtils {

    //数据库url、用户名和密码
    private static String driver;//Ctrl+Alt+F抽取全局静态变量
    private static String url;
    private static String username;
    private static String password;

    /*读取属性文件，获取jdbc信息*/
    static{
            ResourceBundle bundle = ResourceBundle.getBundle("db");
            driver = bundle.getString("driver");
            url = bundle.getString("url");
            username = bundle.getString("username");
            password = bundle.getString("password");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //1、注册JDBC驱动
            Class.forName(driver);
            /* 2、获取数据库连接 */
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /*关闭结果集、数据库操作对象、数据库连接*/
    public static void release(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if(resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
