package com.cjw.note.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class indexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","index");
       //设置首页动态包含的页面
        req.setAttribute("changPage","note/list.jsp");
        //请求转发到index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
