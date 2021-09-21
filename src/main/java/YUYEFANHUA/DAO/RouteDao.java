/**
 * @author 60417
 * @date 2021/9/20
 * @time 12:27
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.Route;

import java.util.List;

public interface RouteDao {
    //需要有两个方法1、查询总的记录数  用于填充totalSize
    //2、查询对应的分页数据，需要两个参数 start，end
    //分页查询数据
    /**
     * 查询对应分类cid的总的记录数的个数 totalSize
     * @return
     */
    int findTotalSize(int cid, String rname);

    /**
     * 查询对应cid分类下的对应页的数据
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    //需要根据rid，查询到对应的route，返回：
    Route findByRid(int rid);
}
