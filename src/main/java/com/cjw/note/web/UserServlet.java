package com.cjw.note.web;

import com.cjw.note.po.User;
import com.cjw.note.service.UserService;
import com.cjw.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {

    private  UserService userService=new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置首页导航高亮
        req.setAttribute("menu_page","user");
        //接受用户行为
        String actionName =req.getParameter("actionName");
        //判断用户行为，调用对应的方法
        if ("login".equals(actionName)){
            //用户登录
            userLogin(req,resp);
        }else if ("logout".equals(actionName)){
            //用户退出
            userLogOut(req,resp);
        }else if ("userCenter".equals(actionName)){
            //进入个人中心
            userCenter(req,resp);
        }else if ("userHead".equals(actionName)){
            //加载头像
            userHead(req,resp);
        }else if ("checkNick".equals(actionName)){
            //验证昵称唯一性
            checkNick(req,resp);
        }else if ("updateUser".equals(actionName)){
            //修改用户信息
            updateUser(req,resp);
        }
    }

    /**
     * 修改用户信息
             注：文件上传必须在Servlet类上提那加注解！！！ @MultipartConfig
             1. 调用Service层的方法，传递request对象作为参数，返回resultInfo对象
             2. 将resultInfo对象存到request作用域中
             3. 请求转发跳转到个人中心页面 （user?actionName=userCenter）
     * @param req
     * @param resp
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 调用Service层的方法，传递request对象作为参数，返回resultInfo对象
        ResultInfo<User> resultInfo=userService.updateUser(req);
        //2. 将resultInfo对象存到request作用域中
        req.setAttribute("resultInfo",resultInfo);
        // 3. 请求转发跳转到个人中心页面 （user?actionName=userCenter）
        req.getRequestDispatcher("user?actionName=userCenter").forward(req,resp);



    }

    /**
     * 验证昵称唯一性
         1. 获取参数（昵称）
         2. 从session作用域获取用户对象，得到用户ID
         3. 调用Service层的方法，得到返回的结果
         4. 通过字符输出流将结果响应给前台的ajax的回调函数
         5. 关闭资源
     * @param req
     * @param resp
     */
    private void checkNick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. 获取参数（昵称）
        String nick=req.getParameter("nick");
        //2. 从session作用域获取用户对象，得到用户ID
        User user= (User) req.getSession().getAttribute("user");
        // 3. 调用Service层的方法，得到返回的结果
        Integer code =userService.checkNick(nick,user.getUserId());
        // 4. 通过字符输出流将结果响应给前台的ajax的回调函数
        resp.getWriter().write(code + "");
        //5. 关闭资源
        resp.getWriter().close();
    }

    /**
     * 加载头像
         1. 获取参数 （图片名称）
         2. 得到图片的存放路径 （request.getServletContext().getealPathR("/")）
         3. 通过图片的完整路径，得到file对象
         4. 通过截取，得到图片的后缀
         5. 通过不同的图片后缀，设置不同的响应的类型
         6. 利用FileUtils的copyFile()方法，将图片拷贝给浏览器
     * @param req
     * @param resp
     */
    private void userHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 获取参数 （图片名称）
        String head = req.getParameter("imageName");
        // 2. 得到图片的存放路径 （得到项目的真是路径request.getServletContext().getealPathR("/")）
        String realPath =req.getServletContext().getRealPath("/WEB-INF/upload/");
        //3. 通过图片的完整路径，得到file对象
        File file =new File(realPath + "/" + head);
        // 4. 通过截取，得到图片的后缀
        String pic = head.substring(head.lastIndexOf(".")+1);
        // 5. 通过不同的图片后缀，设置不同的响应的类型
        if ("PNG".equalsIgnoreCase(pic)){
            resp.setContentType("image/png");
        }else if ("JPG".equalsIgnoreCase(pic) || "JPEG".equalsIgnoreCase(pic)){
            resp.setContentType("image/jpeg");
        }else if ("GIF".equalsIgnoreCase(pic)){
            resp.setContentType("image/GIF");
        }
        //6. 利用FileUtils的copyFile()方法，将图片拷贝给浏览器
        FileUtils.copyFile(file,resp.getOutputStream());
    }

    /**
     * 个人中心
     *      1、设置首页动态包含的页面值
     *      2、请求转发到index.jsp
     * @param req
     * @param resp
     */
    private void userCenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1、设置首页动态包含的页面值
        req.setAttribute("changePage","user/info.jsp");
        //2、请求转发到index
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }

    /**
     * 用户退出
     *         1. 销毁Session对象
     *         2. 删除Cookie对象
     *         3. 重定向跳转到登录页面
     * @param req
     * @param resp
     */
    private void userLogOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1. 销毁Session对象
        req.getSession().invalidate();
        //2. 删除Cookie对象
        Cookie cookie=new Cookie("user",null);
        cookie.setMaxAge(0);//设置0表示删除cookie
        resp.addCookie(cookie);
        //3. 重定向跳转到登录页面
        resp.sendRedirect("login.jsp");
    }

    /**
     * 用户登录
             1. 获取参数 （姓名、密码）
             2. 调用Service层的方法，返回ResultInfo对象
             3. 判断是否登录成功
             如果失败
                 将resultInfo对q象设置到request作用域中
                 请求转发跳转到登录页面
             如果成功
                 将用户信息设置到session作用域中
                 判断用户是否选择记住密码（rem的值是1）
                 如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
             如果否，清空原有的cookie对象
                 重定向跳转到index页面
     * @param req
     * @param resp
     */
    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. 获取参数 （姓名、密码）
        String userName =req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");
        //2. 调用Service层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo =userService.userLogin(userName,userPwd);
        //3. 判断是否登录成功
        if (resultInfo.getCode()==1){//如果成功
            // 将用户信息设置到session作用域中
            req.getSession().setAttribute("user",resultInfo.getResult());
            //判断用户是否选择记住密码（rem的值是1）
            String rem =req.getParameter("rem");
            //如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
            if ("1".equals(rem)){
                //得到cookie对象
                Cookie cookie =new Cookie("user",userName +"-"+userPwd);
                //设置失效时长
                cookie.setMaxAge(3*24*60*60);
                //响应给客户端
                resp.addCookie(cookie);
            } else { //如果否，清空原有的cookie对象
                Cookie cookie =new Cookie("user",null);
                //删除cookie
                cookie.setMaxAge(0);
                //响应给客户端
                resp.addCookie(cookie);
            }

            //重定向跳转到index页面
            resp.sendRedirect("index");

        }else { //失败了
            //将resultInfo对象设置到request作用域中
            req.setAttribute("resultInfo",resultInfo);
            //请求转发跳转到登录页面
             req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
