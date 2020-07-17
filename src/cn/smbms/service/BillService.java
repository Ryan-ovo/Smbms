package cn.smbms.service;

import cn.smbms.entity.Bill;
import cn.smbms.entity.PageBean;
import cn.smbms.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: zc
 * @Create: 2020-07-15 16:01
 */
public interface BillService {

    public Bill findById(int id);
    public List<Bill> findall();

    public void add(Bill bill);

    public void delete(int id);

    public void modifiedInfo(Bill bill);

    public void deleteSelectedBill(String[] ids);

    public PageBean<Bill> findBillByPage(String _currentPage, String _rows, Map<String, String[]> condition);


}
