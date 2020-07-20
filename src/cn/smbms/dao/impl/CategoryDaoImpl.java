package cn.smbms.dao.impl;

import cn.smbms.dao.CategoryDao;
import cn.smbms.entity.Category;
import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-20 15:37
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {

        List<Category> categories = null;
        try {
            String sql = "select * from category";
            categories = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
            return categories;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
