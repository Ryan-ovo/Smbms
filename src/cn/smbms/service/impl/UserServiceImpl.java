package cn.smbms.service.impl;

import cn.smbms.dao.impl.UserDaoImpl;
import cn.smbms.entity.PageBean;
import cn.smbms.entity.User;
import cn.smbms.service.UserService;

import javax.tools.ForwardingFileObject;
import java.util.List;

/**
 * @author lxd
 * @create 2020-07-07 21:30
 */
public class UserServiceImpl implements UserService {
    private UserDaoImpl userDao = new UserDaoImpl();
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(String id) {
        userDao.delete(Integer.parseInt(id));
    }

    @Override
    public void deleteSelectedUser(String[] ids) {
        if (ids != null && ids.length > 0){
            //遍历数组
            for (String id : ids) {
                userDao.delete(Integer.parseInt(id));
            }
        }
    }

    /**
     * 填充PageBean里的信息
     * @param _currentPage
     * @param _rows
     * @return
     */
    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //创建空的PageBean对象
        PageBean<User> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用dao查询总记录数
        int totalCount = userDao.findTotalCount();
        pb.setTotalCount(totalCount);
        //调用dao查询List集合
        //记录开始的索引
        int start = (currentPage-1)* rows;
        List<User> list = userDao.findByPage(start,rows);
        pb.setList(list);
        //计算总页码
        int totalPage = (totalCount % rows == 0) ? totalCount / rows : (totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public void modifiedPWD(String password, int id) {
        userDao.modifiedPWD(password, id);
    }
}
