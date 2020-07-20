package cn.smbms.service;

import cn.smbms.entity.Category;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-20 15:35
 */
public interface CategoryService {

    List<Category> findAll();
}
