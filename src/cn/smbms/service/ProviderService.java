package cn.smbms.service;

import cn.smbms.entity.PageBean;
import cn.smbms.entity.Provider;
import cn.smbms.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author lxd
 * @create 2020-07-15 11:57
 */
public interface ProviderService {
    public PageBean<Provider> findProviderByPage(String currentPage, String rows, Map<String, String[]> condition);

    void deleteProvider(String id);

    void deleteSelectedProvider(String[] ids);

    Provider findById(int id);

    void modifiedInfo(Provider provider, User user);

    void addProvider(Provider provider, User user);

    List<Provider> findAll();
}
