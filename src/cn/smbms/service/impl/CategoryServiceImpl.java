package cn.smbms.service.impl;

import cn.smbms.dao.CategoryDao;
import cn.smbms.dao.impl.CategoryDaoImpl;
import cn.smbms.entity.Category;
import cn.smbms.service.CategoryService;
import cn.smbms.tools.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lxd
 * @create 2020-07-20 15:35
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //从redis中查询
        Jedis jedis = JedisUtils.getJedis();
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        //判断查询的集合是否为空
        if (categories == null || categories.size() == 0){
            System.out.println("缓存中没有数据，从数据库中查询");
            //如果为空，需要从数据库中查询，再将数据存入redis
            list = categoryDao.findAll();
            for (Category category : list) {
                jedis.zadd("category",category.getId(),category.getName());
            }
        }else {
            //如果不为空，将set的数据存入list
            System.out.println("缓存中有数据，从缓存中查询");
            list = new ArrayList<Category>();
            int i = 1;
            for (Tuple tuple : categories) {
                Category category = new Category();
                category.setId((int)tuple.getScore());
                category.setName(tuple.getElement());
                list.add(category);
            }
        }
        return list;
    }
}
