/**
 * @author 60417
 * @date 2021/9/20
 * @time 12:22
 * @todo
 */
package YUYEFANHUA.Service;

import YUYEFANHUA.Domain.PageBean;
import YUYEFANHUA.Domain.Route;

public interface RouteService {
    PageBean<Route> queryForPageBean(int cid, int currentPage, int pageSize, String rname);

    Route findRouteDetail(int rid_int);
}
