package cn.smbms.dao.impl;

import cn.smbms.dao.UserDao;
import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lxd
 * @create 2020-07-07 21:31
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //编写sql
            String sql = "select * from user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;
        try {
            String sql = "select * from user";
            users = template.query(sql, new BeanPropertyRowMapper<>(User.class));
            return users;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    /**
     * 查询总记录数
     * @return
     * @param condition
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<>();
        for (String key : keySet){
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                sb.append(" and "+ key + " like ? ");
                params.add("%"+value+"%");
                System.out.println(value);
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {

        String sql = "select * from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<>();
        for (String key : keySet){
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                sb.append(" and "+ key + " like ? ");
                params.add("%"+value+"%");
            }
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sb.toString());
        System.out.println(params);
        return template.query(sql,new BeanPropertyRowMapper<>(User.class),params.toArray());
    }

    @Override
    public void modifiedPWD(String password, int id) {
        String sql = "update user set password = ? where id = ?";
        template.update(sql, password, id);
    }

    @Override
    public void modifiedInfo(User user) {
        String sql = "update user set username = ?, gender = ?, phone = ?, email = ? where id = ?";
        template.update(sql, user.getUsername(), user.getGender(), user.getPhone(), user.getEmail(), user.getId());
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
