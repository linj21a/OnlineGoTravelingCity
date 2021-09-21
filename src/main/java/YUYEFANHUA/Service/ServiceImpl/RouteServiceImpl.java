/**
 * @author 60417
 * @date 2021/9/20
 * @time 12:23
 * @todo
 */
package YUYEFANHUA.Service.ServiceImpl;

import YUYEFANHUA.DAO.*;
import YUYEFANHUA.DAO.DaoImpl.*;
import YUYEFANHUA.Domain.*;
import YUYEFANHUA.Service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private FavouriteDao favouriteDao = new FavouriteDaoImpl();


    @Override
    public PageBean<Route> queryForPageBean(int cid, int currentPage, int pageSize,String rname) {
        if(pageSize<=0){
            pageSize = 5;
        }
        if(currentPage<=0){
            currentPage = 1;
        }
        //1、设置一个pageBean
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setPageSize(pageSize);
        pageBean.setCurrentPage(currentPage);
        int totalPage;
        int totalSize;
        totalSize = routeDao.findTotalSize(cid,rname);
        totalPage = totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1;
        pageBean.setTotalPage(totalPage);
        pageBean.setTotalSize(totalSize);
        int start = (currentPage-1)*pageSize;//如当前页为1，则start=0；
        List<Route> Page = routeDao.findByPage(cid, start, pageSize,rname);
        //设置数据：
        pageBean.setList(Page);
        return pageBean;
    }

    @Override
    public Route findRouteDetail(int rid_int) {

        //我们这里需要查找的信息有：1、根据rid查找sid，然后查找商家表 seller
        ///根据rid，查找对应的图片信息routeImg对象，应该是一个list
        //根据rid，获取cid，然后查找其category
//        Seller seller =
        Route route = routeDao.findByRid(rid_int);
        //还需要两个成员变量：routeImageDao、SellerDao
        int cid = route.getCid();
        int sid = route.getSid();
        Category category = categoryDao.findByCid(cid);
        List<RouteImage> imageList = routeImageDao.findByRid(rid_int);
        Seller seller = sellerDao.findBySid(sid);
        route.setCategory(category);
        route.setRouteImgList(imageList);
        route.setSeller(seller);
        //4. 查询收藏次数
        int count = favouriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
