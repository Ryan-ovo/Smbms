package cn.smbms.dao.impl;

import cn.smbms.dao.BillDao;
import cn.smbms.entity.Bill;
import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zc
 * @Create: 2020-07-15 14:58
 */
public class BillDaoImpl implements BillDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Bill findById(int id) {
        String sql = "select * from bill where id = ?";
        Bill bill = null;
        try {
            bill = template.queryForObject(sql, new BeanPropertyRowMapper<Bill>(Bill.class), id);
            return bill;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Bill> findAll() {
        List<Bill> bills = null;
        try {
            String sql = "select * from bill";
            bills = template.query(sql, new BeanPropertyRowMapper<>(Bill.class));
            return bills;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Bill bill) {
        String sql = "insert into bill(productName, category, count, totalPrice, isPay, createdBy, createdDate, providerId) values(?, ?, ?, ?, ?, ?, ?, ? );";
        template.update(sql, bill.getProductName(), bill.getCategory(), bill.getCount(), bill.getTotalPrice(), bill.getIsPay(), bill.getCreatedBy(), bill.getCreatedDate(), bill.getProviderId());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from bill where id = ?";
        template.update(sql, id);
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from bill where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<>();
        for (String key : keySet){
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                if(key.equals("productName")){
                    sb.append(" and "+ key + " like ? ");
                    params.add("%"+value+"%");
                }else{
                    sb.append(" and " + key + "= ?");
                    params.add(value);
                }
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select b.id as id, b.productName as productName, c.name as category, b.count as count, b.totalPrice as totalPrice, b.isPay as isPay, u.username as createdBy, b.createdDate as createDate, p.providerName as providerName\n" +
                "from bill as b,category as c,user as u,provider as p\n" +
                "where b.providerId=p.id and b.createdBy=u.id and b.category=c.id";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<>();
        for (String key : keySet){
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                if(key.equals("productName")){
                    sb.append(" and "+ key + " like ? ");
                    params.add("%"+value+"%");
                }else{
                    sb.append(" and " + key + "= ?");
                    params.add(value);
                }
            }
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sb.toString());
        System.out.println(params);
        return template.query(sql, new RowMapper<Map<String,String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet rs, int i) throws SQLException {
                Map<String,String> mp = new HashMap<String,String>();
                mp.put("id", String.valueOf(rs.getInt(1)));
                mp.put("productName", rs.getString(2));
                mp.put("category", String.valueOf(rs.getInt(3)));
                mp.put("count", String.valueOf(rs.getInt(4)));
                mp.put("totalPrice", String.valueOf(rs.getDouble(5)));
                mp.put("isPay", String.valueOf(rs.getInt(6)));
                mp.put("createdBy", rs.getString(7));
                mp.put("createdDate", new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(8)));
                mp.put("providerName", rs.getString(9));
                return mp;
            }
        }, params.toArray());
    }

    @Override
    public void modifiedInfo(Bill bill) {
        String sql = "update bill set productName = ?, category = ?, count = ?, totalPrice = ?, isPay = ?, modifyBy = ?, modifyDate = ?, providerId = ? where id = ?";
        template.update(sql,bill.getProductName(), bill.getCategory(), bill.getCount(), bill.getTotalPrice(), bill.getIsPay(), bill.getModifyBy(), bill.getModifyDate(), bill.getProviderId(), bill.getId());
    }
}
