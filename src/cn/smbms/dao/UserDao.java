package cn.smbms.dao;

import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author lxd
 * @create 2020-06-30 0:11
 */
public class UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 登录方法
     * @param loginUser 只含有用户名和密码
     * @return User返回用户的所有信息
     */
    public User login(User loginUser){
        //ctrl+art+t 快捷生成try catch
        try {
            //编写sql
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getUsername(),loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
