package cn.smbms.dao;

import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author lxd
 * @create 2020-06-30 0:11
 */
public interface UserDao {
    /**
     * 通过用户名和密码来查找用户实现登录
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password);

    public List<User> findAll();

    public void delete(int id);

    public int findTotalCount();

    public List<User> findByPage(int start, int rows);
}
