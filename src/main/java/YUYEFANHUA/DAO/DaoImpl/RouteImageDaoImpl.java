/**
 * @author 60417
 * @date 2021/9/21
 * @time 10:11
 * @todo
 */
package YUYEFANHUA.DAO.DaoImpl;

import YUYEFANHUA.DAO.RouteImageDao;
import YUYEFANHUA.Domain.RouteImage;
import YUYEFANHUA.Util.DBUtils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    /**
     * 根据rid，查询对应的图片资源信息，但是多个图片资源rid可能一样，也就是返回的图片资源应该是一个list
     * @param rid
     * @return
     */
    @Override
    public List<RouteImage> findByRid(int rid) {
        String sql = "select rgid,rid,bigPic,smallPic from route_image where rid = ?";
        List<RouteImage> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImage.class), rid);
        if(query.size()==0){
            return null;
        }
        return query;
    }
}
