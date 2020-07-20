package cn.smbms.dao;

import cn.smbms.entity.Category;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-20 15:37
 */
public interface CategoryDao {

    List<Category> findAll();
}
