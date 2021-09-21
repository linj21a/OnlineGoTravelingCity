/**
 * @author 60417
 * @date 2021/9/21
 * @time 10:08
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.RouteImage;

import java.util.List;

public interface RouteImageDao {
    //根据rid，查询对应的图片资源信息，但是多个图片资源rid可能一样，也就是返回的图片资源应该是一个list
    List<RouteImage> findByRid(int rid);
}
