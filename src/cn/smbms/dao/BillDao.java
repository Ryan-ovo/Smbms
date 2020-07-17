package cn.smbms.dao;

import cn.smbms.entity.Bill;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 * @create 2020-7-15
 */
public interface BillDao {

    public Bill findById(int id);

    public List<Bill> findAll();

    public void add(Bill bill);

    public void delete(int id);

    public void modifiedInfo(Bill bill);

    public int findTotalCount(Map<String, String[]> condition);

    /**
     * 根据接收的条件进行分页查找
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    public List<Bill> findByPage(int start, int rows, Map<String, String[]> condition);

}
