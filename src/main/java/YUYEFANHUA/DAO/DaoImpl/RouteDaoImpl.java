/**
 * @author 60417
 * @date 2021/9/20
 * @time 12:27
 * @todo
 */
package YUYEFANHUA.DAO.DaoImpl;

import YUYEFANHUA.DAO.RouteDao;
import YUYEFANHUA.Domain.Route;
import YUYEFANHUA.Domain.Seller;
import YUYEFANHUA.Util.DBUtils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public int findTotalSize(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");
            params.add(cid);//添加？对应的值
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        Integer integer =  jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
        if(integer==null){
            integer = 0;
        }
        return  integer;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//            String sql = "select * from route where cid = ? limit ? , ?";
//            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid,start,pageSize);
        String sql = " select * from route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");
            params.add(cid);//添加？对应的值
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        }

    /**
     * 根据rid，查询到对应的整个route
     * @param rid
     * @return
     */
    @Override
    public Route findByRid(int rid) {
        String sql  = "select * from route where rid = ?";
        List<Route> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        if(query.size()==0){
            return null;
        }
        return query.get(0);//只会有一个
    }
    //需要有两个方法1、查询总的记录数  用于填充totalSize
        //2、查询对应的分页数据，需要两个参数 start，end


}
