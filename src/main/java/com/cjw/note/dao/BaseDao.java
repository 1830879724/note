package com.cjw.note.dao;

import com.cjw.note.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础jdbc操作类
 *  更新操作
 *  查询操作
 *    1、查一个字段 （只会返回一条记录且只有一条字段：常用场景 ：查询总数量）
 *    2、查询集合
 *    3、查询某个对象
 */
public class BaseDao {
    /**
     * 更新操作
     * 查询操作
     * @param sql
     * @param params
     * @return
     */
    public  static  int  executeUpdate(String sql, List<Object> params){
        int row=0;//受影响的行数
        Connection connection=null;
        PreparedStatement preparedStatement=null;

        try {
            // 1. 获取数据库连接
            connection=DBUtil.getConnection();
            // 2. 预编译
            preparedStatement =connection.prepareStatement(sql);
            //如果有参数则设置参数，从下标1开始
            if (params !=null && params.size()>0){
                //循环设置参数，设置参数类型为Object
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行更新，返回受影响的行数
            row =preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(null,preparedStatement,connection);
        }
        return  row;
    }

    /**
     *  查询操作
     *  查一个字段 （只会返回一条记录且只有一条字段：常用场景 ：查询总数量）
     *   1、获取数据库连接
     *  2、 预编译
     *  3、如果有参数则设置参数，从下标1开始
     *  4、执行查询返回结果集
     *  5、判断并分析结果集
     * @param sql
     * @param params
     * @return
     */
    public  static Object findSingLeValue(String sql, List<Object> params){
        Object obj =null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            // 1. 获取数据库连接
            connection=DBUtil.getConnection();
            // 2. 预编译
            preparedStatement =connection.prepareStatement(sql);
            //如果有参数则设置参数，从下标1开始
            if (params !=null && params.size()>0){
                //循环设置参数，设置参数类型为Object
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询返回结果集
            resultSet=preparedStatement.executeQuery();
            //判断并分析结果集
            if (resultSet.next()){
                obj=resultSet.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return  obj;
    }

    /**
     * 查询集合
          1、获取数据库连接
          2、定义sql语句
          3、预编译
          4、如果有参数，则设置参数，从下标1开始（数组或集合、缓缓设置参数）
          5、执行查询，得到结果集
          6、得到结果集的元数据兑现（查询到的字段的数量以及查询了那些字段）
          7、判断并分析结果集
                8、实例化对象
                9、比阿尼查询的字段数量，得到数据库中查询到的每一列
                10、通过反射、使用列名得到对应的field对象
                11、拼接set 得到字符串
                12、通过反射，将set方法的字符串反射成类中指定的set方法
                13、通过invoke调用set方法
                14将对应的javaBean设置到集合中
          15、关闭资源
     * @param sql
     * @param params
     * @param cal
     * @return
     */
    public  static List queryRows(String sql, List<Object> params,Class cal){
        List list =new ArrayList();

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            // 1. 获取数据库连接
            connection=DBUtil.getConnection();
            //2、预编译
            preparedStatement =connection.prepareStatement(sql);
            //如果有参数则设置参数，从下标1开始
            if (params !=null && params.size()>0){
                //循环设置参数，设置参数类型为Object
                for (int i=0;i<params.size();i++){
                    preparedStatement.setObject(i+1,params.get(i));
                }
            }
            //执行查询返回结果集
            resultSet=preparedStatement.executeQuery();
            //得到结果集的元数据兑现（查询到的字段的数量以及查询了那些字段）
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            //得到查询字段的数量
            int fieldNum= resultSetMetaData.getColumnCount();//获取到列的数量
            //判断并分析结果集
            while (resultSet.next()){
                //实例化对象
                Object obj =cal.newInstance();
                //遍历查询的阻断数量得到数据库中查询的每一个列名
                for (int i=1;i<=fieldNum;i++){
                    //得到查询的每一个列名
                    String  columnName=resultSetMetaData.getColumnLabel(i);
                    //通过反射、使用列名得到对应的field对象
                    Field field = cal.getDeclaredField(columnName);
                    //拼接set 得到字符串
                    String setMethod="set" +columnName.substring(0,1).toUpperCase() +columnName.substring(1);
                    //通过反射，将set方法的字符串反射成类中指定的set方法
                    Method method =cal.getDeclaredMethod(setMethod,field.getType());
                    //得到查询的每一个字段对应的值
                    Object value =resultSet.getObject(columnName);
                    //通过invoke调用set方法
                    method.invoke(obj,value);
                }
                //将对应的javaBean设置到集合中
                list.add(obj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return list;
    }

    /**
     * 查询对象
     * @param sql
     * @param params
     * @param cal
     * @return
     */
    public static Object queryRow(String sql, List<Object> params,Class cal){
        List list =queryRows(sql, params, cal);
        Object obj =null;
        //如果集合不为空，则获取查询的第一条数据
        if (list!=null &&list.size()>0){
            obj=list.get(0);
        }
        return obj;
    }
}
