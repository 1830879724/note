package com.cjw.note;

import com.cjw.note.dao.BaseDao;
import com.cjw.note.dao.UserDao;
import com.cjw.note.po.User;
import com.cjw.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestDB {
    /**
     * 测试方法
     */
    @Test
    public void testDB(){
        System.out.println(DBUtil.getConnection());
        //使用日志
        logger.info("获取数据连接"+DBUtil.getConnection());
    }


    /**
     * 使用日志工厂类，记录日志
     */
    private Logger logger= LoggerFactory.getLogger(TestDB.class);

    /**
     * 测试获取密码
     */
    @Test
    public void testUserByName(){
        UserDao userDao=new UserDao();
        User user= userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }

    @Test
    public void testAdd(){
     String   sql = "insert into tb_user (uname, upwd, Nick, head, mood) values(?,?,?,?,?)";
     List<Object> params =new ArrayList<>();
     params.add("xx");
     params.add("e10adc3949ba59abbe56e057f20f883e");
     params.add("xx");
     params.add("404.jsp");
     params.add("哈喽");
     int row = BaseDao.executeUpdate(sql,params);
        System.out.println(row);
    }
}
