package cn.smbms.dao;

import cn.smbms.entity.User;

import java.util.List;
import java.util.Map;

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

    public int findTotalCount(Map<String, String[]> condition);

    public List<User> findByPage(int start, int rows, Map<String, String[]> condition);

    public void modifiedPWD(String password, int id);

    public void modifiedInfo(User user);

    public User findById(int id);
}
