package cn.smbms.service;

import cn.smbms.entity.PageBean;
import cn.smbms.entity.User;

import java.util.List;

/**
 * @author lxd
 * @create 2020-07-07 21:29
 */
public interface UserService {
    /**
     * 查询登录信息
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    public void deleteUser(String id);

    public void deleteSelectedUser(String[] ids);

    public PageBean<User> findUserByPage(String currentPage, String rows);

    public void modifiedPWD(String password, int id);

    public void modifiedInfo(User user);

    public User findById(int id);
}
