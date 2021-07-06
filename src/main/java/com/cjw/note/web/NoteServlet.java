package com.cjw.note.web;

import com.cjw.note.po.Note;
import com.cjw.note.po.NoteType;
import com.cjw.note.po.User;
import com.cjw.note.service.NoteTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/note")
public class NoteServlet  extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","note");
        //得到用户行为
        String actionName = req.getParameter("actionName");
        //判断用户行为
        if ("view".equals(actionName)){
            //进入发布云记页面
            noteView(req,resp);
        }
    }

    /**
     * 进入发布云记页面
             1. 从Session对象中获取用户对象
             2. 通过用户ID查询对应的类型列表
             3. 将类型列表设置到request请求域中
             4、  设置首页动态包含的页面值
             5、  请求转发跳转到index.jsp
     * @param req
     * @param resp
     */
    private void noteView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 从Session对象中获取用户对象
        User user= (User) req.getSession().getAttribute("user");
        //2. 通过用户ID查询对应的类型列表
        List<NoteType> typeList =new NoteTypeService().findTypeList(user.getUserId());
        //3. 将类型列表设置到request请求域中
        req.setAttribute("typeList",typeList);
        //4、  设置首页动态包含的页面值
        req.setAttribute("changePage","note/view.jsp");
        // 5、  请求转发跳转到index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
