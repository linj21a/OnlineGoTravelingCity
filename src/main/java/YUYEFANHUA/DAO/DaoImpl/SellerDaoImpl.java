/**
 * @author 60417
 * @date 2021/9/21
 * @time 10:17
 * @todo
 */
package YUYEFANHUA.DAO.DaoImpl;

import YUYEFANHUA.DAO.SellerDao;
import YUYEFANHUA.Domain.RouteImage;
import YUYEFANHUA.Domain.Seller;
import YUYEFANHUA.Util.DBUtils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public Seller findBySid(int sid) {
        String sql = "select sid,sname,sphone,address from seller where sid = ?";
        List<Seller> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        if(query.size()==0){
            return null;
        }
        return query.get(0);//只会有一个

    }
}
