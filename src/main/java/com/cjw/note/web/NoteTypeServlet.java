package com.cjw.note.web;

import com.cjw.note.po.NoteType;
import com.cjw.note.po.User;
import com.cjw.note.service.NoteTypeService;
import com.cjw.note.util.JSONUtil;
import com.cjw.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {
    private NoteTypeService typeService=new NoteTypeService();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","type");
        //得到用户行为
        String actionName = req.getParameter("actionName");
        //判断用户行为
        if ("list".equals(actionName)){
            //查询类型列表
            typeList(req,resp);
        }else if ("delete".equals(actionName)){
            deleteType(req,resp);
        }else  if ("addOrUpdate".equals(actionName)){
            addOrUpdate(req,resp);
        }
    }

    /**
     * 添加或修改类型
             1. 接收参数 （类型名称、类型ID）
             2. 获取Session作用域中的user对象，得到用户ID
             3. 调用Service层的更新方法，返回ResultInfo对象
             4. 将ResultInfo转换成JSON格式的字符串，响应给ajax的回调函数
     * @param req
     * @param resp
     */
    private void addOrUpdate(HttpServletRequest req, HttpServletResponse resp) {
        //1. 接收参数 （类型名称、类型ID）
        String typeName=req.getParameter("typeName");
        String typeId=req.getParameter("typeId");
        //2. 获取Session作用域中的user对象，得到用户ID
        User user= (User) req.getSession().getAttribute("user");
        //3. 调用Service层的更新方法，返回ResultInfo对象
        ResultInfo<Integer> resultInfo =typeService.addOrUpdate(typeName,user.getUserId(),typeId);
        //4. 将ResultInfo转换成JSON格式的字符串，响应给ajax的回调函数
        JSONUtil.toJson(resp,resultInfo);
    }

    /**
     * 删除类型
             1. 接收参数（类型ID）
             2. 调用Service的更新操作，返回ResultInfo对象
             3. 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
     * @param req
     * @param resp
     */
    private void deleteType(HttpServletRequest req, HttpServletResponse resp) {
        //1. 接收参数（类型ID）
        String typeId=req.getParameter("typeId");
        //2. 调用Service的更新操作，返回ResultInfo对象
        ResultInfo<NoteType> resultInfo=typeService.deleteType(typeId);
        //3. 将ResultInfo对象转换成JSON格式的字符串，响应给ajax的回调函数
        JSONUtil.toJson(resp,resultInfo);

    }

    /**
     * 查询类型列表
             1. 获取Session作用域设置的user对象
             2. 调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
             3. 将类型列表设置到request请求域中
             4. 设置首页动态包含的页面值
             5. 请求转发跳转到index.jsp页面
     * @param req
     * @param resp
     */
    private void typeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取Session作用域设置的user对象
        User user= (User) req.getSession().getAttribute("user");
        // 2. 调用Service层的查询方法，查询当前登录用户的类型集合，返回集合
        List<NoteType> typeList=typeService.findTypeList(user.getUserId());
        //3. 将类型列表设置到request请求域中
        req.setAttribute("typeList",typeList);
        //4. 设置首页动态包含的页面值
        req.setAttribute("changePage","type/list.jsp");
        //5. 请求转发跳转到index.jsp页面
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
