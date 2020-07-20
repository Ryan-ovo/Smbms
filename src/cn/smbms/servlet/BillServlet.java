package cn.smbms.servlet;

import cn.smbms.entity.Bill;
import cn.smbms.entity.PageBean;
import cn.smbms.entity.ResultInfo;
import cn.smbms.entity.User;
import cn.smbms.service.BillService;
import cn.smbms.service.impl.BillServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.ECMAException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Author: zc
 * @Create: 2020-07-15 16:13
 */

@WebServlet("/bill/*")
public class BillServlet extends BaseServlet{
    private BillServiceImpl service = new BillServiceImpl();

    public void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Bill bill = service.findById(Integer.parseInt(id));
        req.getSession().setAttribute("targetBill", bill);
        resp.sendRedirect(req.getContextPath() + "/modifiedBillInfo.html");
    }

    /**
     * 将待修改订单信息写入响应消息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findTarget(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bill targetBill = (Bill) req.getSession().getAttribute("targetBill");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(targetBill);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 分页条件查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");

        Map<String, String[]> condition = req.getParameterMap();

        PageBean pb = service.findBillByPage(currentPage,rows,condition);
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),pb);
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        service.delete(Integer.parseInt(id));
        resp.sendRedirect(req.getContextPath()+"/billList.html");
    }

    public void deleteSelected(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("uid");
        service.deleteSelectedBill(ids);
        resp.sendRedirect(req.getContextPath()+"/billList.html");
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        int category = Integer.parseInt(req.getParameter("category"));
        String count = req.getParameter("count");
        String totalPrice = req.getParameter("totalPrice");
        String isPay = req.getParameter("isPay");
        String providerId = req.getParameter("providerId");

        User user = (User) req.getSession().getAttribute("user");//获取创建订单的员工
        Bill bill = new Bill();

        ResultInfo info = new ResultInfo();
        String errorMsg = "抱歉，订单创建失败！";

        if(productName == null || "".equals(productName) || count == null || "".equals(count) || totalPrice == null || "".equals(totalPrice) || isPay == null || "".equals(isPay) || providerId == null || "".equals(providerId)){
            errorMsg += "请不要保留空项";
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        }else{
            Integer countNum = null;
            Double totalPriceNum = null;

            try{
                countNum = Integer.parseInt(count);
            }catch (Exception e){
                errorMsg += "商品数目请不要输入非数字内容";
                info.setErrorMsg(errorMsg);
                info.setFlag(false);
            }
            if(countNum != null){
                //转换成功不包含非数字符号
                try {
                    totalPriceNum = Double.parseDouble(totalPrice);
                }catch (Exception e){
                    errorMsg += "订单价格不要输入非数字内容";
                    info.setErrorMsg(errorMsg);
                    info.setFlag(false);
                }
            }

            if(countNum != null && totalPriceNum != null){
                //两个数字均转换成功不包含非数字符号
                info.setFlag(true);
                bill.setProductName(productName);
                bill.setCategory(category);
                bill.setCount(countNum);
                bill.setTotalPrice(totalPriceNum);
                bill.setIsPay(Integer.parseInt(isPay));
                bill.setProviderId(Integer.parseInt(providerId));
                bill.setCreatedBy(user.getId());
                bill.setCreatedDate(new Date());

                service.add(bill);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void modifiedInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productName");
        int category = Integer.parseInt(req.getParameter("category"));
        String count = req.getParameter("count");
        String totalPrice = req.getParameter("totalPrice");
        String isPay = req.getParameter("isPay");
        String providerId = req.getParameter("providerId");

        User user = (User) req.getSession().getAttribute("user");//获取修改订单的员工
        Bill bill = new Bill();
        Bill targetBill = (Bill)req.getSession().getAttribute("targetBill");//获取需要修改的订单

        ResultInfo info = new ResultInfo();
        String errorMsg = "抱歉，订单修改失败！";

        if(productName == null || "".equals(productName) || count == null || "".equals(count) || totalPrice == null || "".equals(totalPrice) || isPay == null || "".equals(isPay) || providerId == null || "".equals(providerId)){
            errorMsg += "请不要保留空项";
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        }else if(productName.equals(targetBill.getProductName()) && category==targetBill.getCategory() && count.equals(Integer.toString(targetBill.getCount())) && totalPrice.equals(Double.toString(targetBill.getTotalPrice())) && isPay.equals(Integer.toString(targetBill.getIsPay())) && providerId.equals(Integer.toString(targetBill.getProviderId()))){
            errorMsg += "订单信息未发生修改";
            info.setErrorMsg(errorMsg);
            info.setFlag(false);
        }else{
            Integer countNum = null;
            Double totalPriceNum = null;

            try{
                countNum = Integer.parseInt(count);
            }catch (Exception e){
                errorMsg += "商品数目请不要输入非数字内容";
                info.setErrorMsg(errorMsg);
                info.setFlag(false);
            }
            if(countNum != null){
                //转换成功不包含非数字符号
                try {
                    totalPriceNum = Double.parseDouble(totalPrice);
                }catch (Exception e){
                    errorMsg += "订单价格不要输入非数字内容";
                    info.setErrorMsg(errorMsg);
                    info.setFlag(false);
                }
            }

            if(countNum != null && totalPriceNum != null){
                //两个数字均转换成功不包含非数字符号
                info.setFlag(true);
                bill.setProductName(productName);
                bill.setCategory(category);
                bill.setCount(countNum);
                bill.setTotalPrice(totalPriceNum);
                bill.setIsPay(Integer.parseInt(isPay));
                bill.setProviderId(Integer.parseInt(providerId));
                bill.setModifyBy(user.getId());
                bill.setModifyDate(new Date());
                bill.setId(targetBill.getId());
                //System.out.println("修改后的bill信息"+bill);
                service.modifiedInfo(bill);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
