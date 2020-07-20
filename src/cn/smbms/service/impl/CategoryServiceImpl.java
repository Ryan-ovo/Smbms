package cn.smbms.service.impl;

import cn.smbms.dao.CategoryDao;
import cn.smbms.dao.impl.CategoryDaoImpl;
import cn.smbms.entity.Category;
import cn.smbms.service.CategoryService;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-20 15:35
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //从redis中查询

        //判断查询的集合是否为空

        //如果为空，需要从数据库中查询，再将数据存入redis

        //如果不为空，直接返回

        return categoryDao.findAll();
    }
}
