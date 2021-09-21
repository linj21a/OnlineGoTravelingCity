/**
 * @author 60417
 * @date 2021/9/20
 * @time 12:09
 * @todo
 */
package YUYEFANHUA.Web.Servlet;

import YUYEFANHUA.Domain.PageBean;
import YUYEFANHUA.Domain.Route;
import YUYEFANHUA.Domain.User;
import YUYEFANHUA.Service.FavouriteService;
import YUYEFANHUA.Service.RouteService;
import YUYEFANHUA.Service.ServiceImpl.FavouriteServiceImpl;
import YUYEFANHUA.Service.ServiceImpl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 路线servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
    public void queryPageRoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //分页查询route
        System.out.println("分页查询！");
        //1、获取前端传过来的相关分页数：
        String currentPageStr = request.getParameter("currentPage");//当前页码，我们查询下一个就需要从当前页开始从pageSize开始拉趣
        String pageSizeStr = request.getParameter("pageSize");//分页的一个页显示的条数
        String cidStr = request.getParameter("cid");//分类目录id
        String rname = request.getParameter("rname");//搜索关键字：
        if(rname==null||"null".equals(rname) || rname.equals("")){
            rname = "";
        }
        System.out.println(rname);
        System.out.println(cidStr);
        int currentPage = 1;//默认为1
        int pageSize = 5;
        int cid = 0;
        if(!(currentPageStr==null||"".equals(currentPageStr))){
            currentPage = Integer.parseInt(currentPageStr);
        }
        if(!(pageSizeStr==null||"".equals(pageSizeStr))){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        try{
            cid = Integer.parseInt(cidStr);
        }catch (NumberFormatException e){
            e.printStackTrace();
            cid = 1;//默认
        }

        //调用service查询对应的数据   service方法：queryForPageBean
        RouteService routeService = new RouteServiceImpl();
        PageBean<Route> routePageBean = routeService.queryForPageBean(cid, currentPage, pageSize,rname);
//        System.out.println(routePageBean.getList().toString());
        //序列化
        writeValue(routePageBean,response);

        //返回分页的数据，然后序列化这些数据
    }

    /**
     * 根据rid，查询对应的旅游路线详情，然后拉去对应的信息，返回前端数据
     * @param request
     * @param response
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String rid = request.getParameter("rid");

        //判断rid是否合格：
        if(rid==null || "".equals(rid)||"nulll".equals(rid)){
            //rid错误，我们展示不了，直接抛出错误：
            throw new RuntimeException("rid 不合法！");
        }
        int rid_int=0;
        try {
            rid_int = Integer.parseInt(rid);
        }catch (NumberFormatException e){
            e.printStackTrace();
            rid_int = 1;
        }

        //调用服务层，查询旅游路线详情
        RouteService routeService = new RouteServiceImpl();
        Route route = routeService.findRouteDetail(rid_int);
        writeValue(route,response);

    }
    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        FavouriteService favoriteService = new FavouriteServiceImpl();
        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路rid
        String rid = request.getParameter("rid");
        //2. 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return ;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        FavouriteService favoriteService = new FavouriteServiceImpl();
        //3. 调用service添加
        favoriteService.add(rid,uid);

    }
}
