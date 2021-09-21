/**
 * @author 60417
 * @date 2021/9/20
 * @time 10:00
 * @todo
 */
package YUYEFANHUA.DAO.DaoImpl;

import YUYEFANHUA.DAO.CategoryDao;
import YUYEFANHUA.Domain.Category;
import YUYEFANHUA.Util.DBUtils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public Category findByCid(int cid) {
        String sql = "select * from category where cid=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class),cid);
    }
}
