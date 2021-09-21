/**
 * @author 60417
 * @date 2021/9/20
 * @time 10:06
 * @todo
 */
package YUYEFANHUA.Service.ServiceImpl;

import YUYEFANHUA.DAO.CategoryDao;
import YUYEFANHUA.DAO.DaoImpl.CategoryDaoImpl;
import YUYEFANHUA.Domain.Category;
import YUYEFANHUA.Service.CategoryService;
import YUYEFANHUA.Util.DBUtils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //缓存优化：
        //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedset排序查询
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        //返回一个二元组，每个二元组第二个参数就是分数
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        System.out.println(categorys);
        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {

            System.out.println("从数据库查询....");
            //3.如果为空,需要从数据库查询,在将数据存入redis
            //3.1 从数据库查询
            cs = categoryDao.findAll();
            //3.2 将集合数据存储到redis中的 category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            System.out.println("从redis中查询.....");

            //4.如果不为空,将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                cs.add(category);

            }
        }
        return cs;
    }
}
