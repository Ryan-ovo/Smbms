package cn.smbms.servlet;

import cn.smbms.dao.UserDao;
import cn.smbms.entity.ResultInfo;
import cn.smbms.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lxd
 * @create 2020-06-29 23:18
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;utf-8");
        //获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String checkcode = req.getParameter("checkcode");
        //先判断验证码是否正确
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //验证码忽略大小写
        ResultInfo info = new ResultInfo();
        if (checkcode_server !=null && checkcode_server.equalsIgnoreCase(checkcode)){
            //验证码正确
            //判断用户名和密码是否正确
            //封装user对象
            User loginUser = new User();
            loginUser.setUsername(username);
            loginUser.setPassword(password);
            UserDao dao = new UserDao();
            User user = dao.login(loginUser);
            //响应结果给前端
            if (user == null){
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }else {
                //给前端发送登录成功的信息
                info.setFlag(true);
                info.setData(user);
            }
        }else {
            //存储提示信息到request
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //转发到登录页面
        }
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
