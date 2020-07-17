package cn.smbms.dao;

import cn.smbms.entity.Provider;
import cn.smbms.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author lxd
 * @create 2020-07-15 11:58
 */
public interface ProviderDao {
    int findTotalCount(Map<String, String[]> condition);

    List<Provider> findByPage(int start, int rows, Map<String, String[]> condition);

    void delete(int pid);

    Provider findById(int id);

    void modifiedInfo(Provider provider, User user);

    void addProvider(Provider provider, User user);

    List<Provider> findAll();
}
