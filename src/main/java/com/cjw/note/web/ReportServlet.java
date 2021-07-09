package com.cjw.note.web;

import com.cjw.note.po.Note;
import com.cjw.note.po.User;
import com.cjw.note.service.NoteService;
import com.cjw.note.util.JSONUtil;
import com.cjw.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    NoteService noteService =new NoteService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","report");
        //得到用户行为
        String actionName= req.getParameter("actionName");
        //判断用户行为
        if ("info".equals(actionName)){
            //进入报表页面
            reportInfo(req,resp);
        }else if ("month".equals(actionName)){
            //通过月份查询数量
            queryNoteMonth(req,resp);
        }
        else if ("location".equals(actionName)){
            //查询用户发布日记时的坐标
            queryNoteLonAndLat(req,resp);
        }
    }

    /**
     * 查询用户发布日记时的坐标
     * @param req
     * @param resp
     */
    private void queryNoteLonAndLat(HttpServletRequest req, HttpServletResponse resp) {
        //作用域中获取用户对象
        User user= (User) req.getSession().getAttribute("user");
        //调用Service的查询方法，返回ResultInfo对象
        ResultInfo<List<Note>> resultInfo =noteService.queryNoteLonAndLat(user.getUserId());
        //将ResultInfo转成字符串，响应给Ajax回调函数
        JSONUtil.toJson(resp,resultInfo);
    }

    /**
     * 通过月份查询数量
     *
     * @param req
     * @param resp
     */
    private void queryNoteMonth(HttpServletRequest req, HttpServletResponse resp) {
        //从作用域获取用户对象
        User user= (User) req.getSession().getAttribute("user");
        //调用Service的查询方法，返回ResultInfo对象
        ResultInfo<Map<String,Object>> map =noteService.queryNoteMonth(user.getUserId());
        //将ResultInfo转成字符串，响应给Ajax回调函数
        JSONUtil.toJson(resp,map);

    }

    /**
     * 进入报表页面
     * @param req
     * @param resp
     */
    private void reportInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页包含的页面值
        req.setAttribute("changePage","report/info.jsp");
        //请求转发到index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
