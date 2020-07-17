package cn.smbms.service.impl;

import cn.smbms.dao.impl.ProviderDaoImpl;
import cn.smbms.entity.PageBean;
import cn.smbms.entity.Provider;
import cn.smbms.entity.User;
import cn.smbms.service.ProviderService;

import java.util.List;
import java.util.Map;

/**
 * @author lxd
 * @create 2020-07-15 11:57
 */
public class ProviderServiceImpl implements ProviderService {
    private ProviderDaoImpl providerDao = new ProviderDaoImpl();

    public PageBean<Provider> findProviderByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //创建空的PageBean对象
        PageBean<Provider> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用dao查询总记录数
        int totalCount = providerDao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //调用dao查询List集合
        //记录开始的索引
        int start = (currentPage-1)* rows;
        List<Provider> list = providerDao.findByPage(start,rows,condition);
        pb.setList(list);
        //计算总页码
        int totalPage = (totalCount % rows == 0) ? totalCount / rows : (totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public void deleteProvider(String id) {
        providerDao.delete(Integer.parseInt(id));
    }

    @Override
    public void deleteSelectedProvider(String[] ids) {
        if (ids != null && ids.length > 0){
            //遍历数组
            for (String id : ids) {
                providerDao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public Provider findById(int id) {
        return providerDao.findById(id);
    }

    @Override
    public void modifiedInfo(Provider provider,User user) {
        providerDao.modifiedInfo(provider,user);
    }

    @Override
    public void addProvider(Provider provider, User user) {
        providerDao.addProvider(provider,user);
    }

    @Override
    public List<Provider> findAll() {
        return providerDao.findAll();
    }
}
