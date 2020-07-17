package cn.smbms.service.impl;

import cn.smbms.dao.impl.BillDaoImpl;
import cn.smbms.entity.Bill;
import cn.smbms.entity.PageBean;
import cn.smbms.entity.User;
import cn.smbms.service.BillService;

import java.util.List;
import java.util.Map;

/**
 * @Author: zc
 * @Create: 2020-07-15 16:07
 */
public class BillServiceImpl implements BillService {
    private BillDaoImpl billDao = new BillDaoImpl();

    @Override
    public List<Bill> findall() {
        return billDao.findAll();
    }

    @Override
    public Bill findById(int id) {
        return billDao.findById(id);
    }

    @Override
    public void add(Bill bill) {
        billDao.add(bill);
    }

    @Override
    public void delete(int id) {
        billDao.delete(id);
    }

    @Override
    public void modifiedInfo(Bill bill) {
        billDao.modifiedInfo(bill);
    }

    @Override
    public void deleteSelectedBill(String[] ids) {
        if (ids != null && ids.length > 0){
            //遍历数组
            for (String id : ids) {
                billDao.delete(Integer.parseInt(id));
            }
        }
    }

    /**
     * 分页条件查询
     * @param _currentPage 当前页码
     * @param _rows 一页行数
     * @param condition 查询条件
     * @return
     */
    @Override
    public PageBean findBillByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //创建空的PageBean对象
        PageBean pb = new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用dao查询总记录数
        int totalCount = billDao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //调用dao查询List集合
        //记录开始的索引
        int start = (currentPage-1)* rows;
        List list = billDao.findByPage(start,rows,condition);
        pb.setList(list);
        //计算总页码
        int totalPage = (totalCount % rows == 0) ? totalCount / rows : (totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
