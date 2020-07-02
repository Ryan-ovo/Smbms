package cn.smbms.test;

import cn.smbms.dao.UserDao;
import cn.smbms.entity.User;
import org.junit.Test;

/**
 * @author lxd
 * @create 2020-06-30 0:31
 */
public class UserDaoTest {
    @Test
    public void testLogin(){
        User loginUser = new User();
        loginUser.setUsername("linxindi");
        loginUser.setPassword("123");
        UserDao dao = new UserDao();
        User user = dao.login(loginUser);
        System.out.println(user);
    }
}
