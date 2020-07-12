package cn.smbms.filter;

import cn.smbms.entity.ResultInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println(req);
        //0.强制转换
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)resp;

        response.setHeader("Cache-control","no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);

        //1.获取资源请求路径
        String uri = request.getRequestURI();
        System.out.println(uri);
        //2.判断是否包含登录相关资源路径,要注意排除掉 css/js/图片/验证码等资源
        if("/smbms/".equals(uri) || uri.contains("/login.html") || uri.contains("/user/login") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/font/") || uri.contains("/checkCode")
        || uri.contains("/image/")){
            //包含，用户就是想登录。放行
            chain.doFilter(req, resp);
        }else{
            //不包含，需要验证用户是否登录
            //3.从获取session中获取user
            Object user = request.getSession().getAttribute("user");
            if(user != null){
                //登录了。放行
                chain.doFilter(req, resp);
            }else{
                //没有登录。跳转404页面
                request.getRequestDispatcher("/404.html").forward(request,resp);
            }
        }


        // chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
