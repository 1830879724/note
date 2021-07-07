package com.cjw.note.web;

import com.cjw.note.po.Note;
import com.cjw.note.po.User;
import com.cjw.note.service.NoteService;
import com.cjw.note.util.Page;
import com.cjw.note.vo.NoteVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class indexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","index");

        //分页查询列表
        noteList(req,resp);
       //设置首页动态包含的页面
        req.setAttribute("changePage","note/list.jsp");
        //请求转发到index.jsp
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }

    /**
     * 分页查询列表
             1. 接收参数 （当前页、每页显示的数量）
             2. 获取Session作用域中的user对象
             3. 调用Service层查询方法，返回Page对象
             4. 将page对象设置到request作用域中
     * @param req
     * @param resp
     */
    private void noteList(HttpServletRequest req, HttpServletResponse resp) {
        // 1. 接收参数 （当前页、每页显示的数量）
        String pageNum=req.getParameter("pageNum");
        String pageSize=req.getParameter("pageSize");
        //2. 获取Session作用域中的user对象
        User user = (User) req.getSession().getAttribute("user");
        // 3. 调用Service层查询方法，返回Page对象
        Page<Note> page =new NoteService().findNoteListByPage(pageNum,pageSize,user.getUserId());
        //4. 将page对象设置到request作用域中
        req.setAttribute("page",page);
        //通过日期分组查询当前登录用户下的数量
        List<NoteVo> dateInfo =new NoteService().findNoteCountByDate(user.getUserId());
        //设置集合存放在作用域
        req.getSession().setAttribute("dateInfo",dateInfo);
        //通过类型分组查询当前登录用户下的数量
        List<NoteVo> typeInfo =new NoteService().findNoteCountByType(user.getUserId());
        //设置集合存放在作用域
        req.getSession().setAttribute("typeInfo",typeInfo);
    }
}
