package cn.smbms.servlet;

import cn.smbms.entity.PageBean;
import cn.smbms.entity.Provider;
import cn.smbms.entity.ResultInfo;
import cn.smbms.entity.User;
import cn.smbms.service.impl.ProviderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author lxd
 * @create 2020-07-15 11:52
 */

@WebServlet("/provider/*")
public class ProviderServlet extends BaseServlet {
    ProviderServiceImpl service = new ProviderServiceImpl();

    public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");

        Map<String, String[]> condition = req.getParameterMap();

        PageBean<Provider> pb = service.findProviderByPage(currentPage, rows, condition);
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(), pb);
    }

    /**
     * 删除单条目
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("pid");
        service.deleteProvider(id);
        resp.sendRedirect(req.getContextPath() + "/providerList.html");
    }

    /**
     * 删除选中条目
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteSelected(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("pid");
        service.deleteSelectedProvider(ids);
        resp.sendRedirect(req.getContextPath() + "/providerList.html");
    }

    /**
     * 将待修改用户信息写入响应消息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findAnother(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Provider targetProvider = (Provider) req.getSession().getAttribute("targetProvider");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(targetProvider);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 跳转到修改信息页面，并设置待修改User对象
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void forwardModifiedInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("pid");
        Provider targetProvider = null;
        if (id != null && id != "")
            targetProvider = service.findById(Integer.parseInt(id));

        req.getSession().setAttribute("targetProvider", targetProvider);
        resp.sendRedirect(req.getContextPath() + "/modifiedProviderInfo.html");
    }

    /**
     * 修改用户信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void modifiedInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String providerName = req.getParameter("providerName");
        String contact = req.getParameter("contact");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        Provider targetProvider = (Provider) req.getSession().getAttribute("targetProvider");

        String phoneRegex = "^1[3456789]\\d{9}$";

        String errorMsg = "抱歉，信息修改失败！";
        ResultInfo info = new ResultInfo();
        //检验正确性
        if (providerName == null || providerName == "" || contact == null || contact == "" || phone == null || phone == "" || address == null || address == "") {
            errorMsg += "请不要保留空白项";
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        } else if (!Pattern.matches(phoneRegex, phone)) {
            //手机号码不合法
            errorMsg += "输入的电话号码不合法";
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        } else {
            //修改成功
            targetProvider.setProviderName(providerName);
            targetProvider.setContact(contact);
            targetProvider.setPhone(phone);
            targetProvider.setAddress(address);

            service.modifiedInfo(targetProvider,(User)req.getSession().getAttribute("user"));
            info.setFlag(true);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void addProvider(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String providerName = req.getParameter("providerName");
        String contact = req.getParameter("contact");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        String phoneRegex = "^1[3456789]\\d{9}$";

        String errorMsg = "抱歉，供应商信息添加失败！";
        ResultInfo info = new ResultInfo();
        Provider provider = new Provider();
        //检验正确性
        if (providerName == null || providerName == "" || contact == null || contact == "" || phone == null || phone == "" || address == null || address == "") {
            errorMsg += "请不要保留空白项";
            System.out.println(1111);
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        } else if (!Pattern.matches(phoneRegex, phone)) {
            //手机号码不合法
            errorMsg += "输入的电话号码不合法";
            System.out.println(2222);
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        } else {
            //修改成功
            provider.setProviderName(providerName);
            provider.setContact(contact);
            provider.setPhone(phone);
            provider.setAddress(address);

            service.addProvider(provider,(User)req.getSession().getAttribute("user"));
            info.setFlag(true);
        }
        System.out.println(info.getErrorMsg());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Provider> providers = service.findAll();
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),providers);
    }
}
