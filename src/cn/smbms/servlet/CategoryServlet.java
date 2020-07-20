package cn.smbms.servlet;

import cn.smbms.entity.Category;
import cn.smbms.service.CategoryService;
import cn.smbms.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author lxd
 * @create 2020-07-20 15:30
 */
public class CategoryServlet extends BaseServlet{
    private CategoryService categoryService = new CategoryServiceImpl();
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
    }
}
