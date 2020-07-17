package cn.smbms.dao.impl;

import cn.smbms.dao.ProviderDao;
import cn.smbms.entity.Provider;
import cn.smbms.entity.User;
import cn.smbms.tools.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * @author lxd
 * @create 2020-07-15 11:58
 */
public class ProviderDaoImpl implements ProviderDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select count(*) from provider where 1 = 1 ";
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
                sb.append(" and "+ key + " like ? ");
                params.add("%"+value+"%");
                System.out.println(value);
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Provider> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from provider where 1 = 1 ";
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
                sb.append(" and "+ key + " like ? ");
                params.add("%"+value+"%");
            }
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sb.toString());
        System.out.println(params);
        return template.query(sql,new BeanPropertyRowMapper<>(Provider.class),params.toArray());
    }

    @Override
    public void delete(int pid) {
        String sql = "delete from provider where id = ?";
        template.update(sql, pid);
    }

    @Override
    public Provider findById(int id) {
        String sql = "select * from provider where id = ?";
        Provider provider = null;
        try {
             provider = template.queryForObject(sql, new BeanPropertyRowMapper<Provider>(Provider.class), id);
            return provider;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void modifiedInfo(Provider provider, User user) {
        String sql = "update provider set providerName = ?, contact = ?, phone = ?, address = ?, modifyBy = ?, modifyDate = ? where id = ?";
        template.update(sql, provider.getProviderName(), provider.getContact(), provider.getPhone(), provider.getAddress(),user.getUsername(),new Date(),provider.getId());
    }

    @Override
    public void addProvider(Provider provider, User user) {
//        String sql = "insert into provider (providerName,contact,phone,address,modifyBy,modifyDate) values(?,?,?,?,?,?)";
        String sql = "insert into provider values(null,?,?,?,?,?,?)";
        template.update(sql,provider.getProviderName(),provider.getContact(),provider.getPhone(),provider.getAddress(),user.getUsername(),new Date());
    }

    @Override
    public List<Provider> findAll() {
        List<Provider> providers = null;
        try {
            String sql = "select * from provider";
            providers = template.query(sql, new BeanPropertyRowMapper<Provider>(Provider.class));
            return providers;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
