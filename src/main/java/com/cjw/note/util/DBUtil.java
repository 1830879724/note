package com.cjw.note.util;


import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {

    //得到配置对象文件
    private  static Properties properties=new Properties();

    static {
        try {
        //加载配置文件（输入流）
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        //通过load（）方法 将输入流的内容加载到配置文件对象中
            properties.load(in);
            //通过配置文件对象getProperty()的方法获取驱动名，并加载驱动
        Class.forName(properties.getProperty("jdbcName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection= null;
        try {
        //得到数据库连接
        String dbUrl = properties.getProperty("dbUrl");
        String dbName = properties.getProperty("dbName");
        String dbPwd = properties.getProperty("dbPwd");
        //得到数据库连接
        connection = DriverManager.getConnection(dbUrl, dbName, dbPwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    /**
     * 关闭资源
     * @param resultSet
     * @param preparedStatement
     * @param connection
     */
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement,
                             Connection connection){
        try {
            //判断资源如果不为空则关闭
            if (resultSet!=null){
                resultSet.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
            if (connection!=null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
