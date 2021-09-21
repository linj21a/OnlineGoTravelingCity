/**
 * @author 60417
 * @date 2021/9/19
 * @time 15:16
 * @todo
 */
package YUYEFANHUA.Web.Servlet.UserServletDetail;

import YUYEFANHUA.Domain.ResultInfo;
import YUYEFANHUA.Domain.User;
import YUYEFANHUA.Service.ServiceImpl.UserServiceImpl;
import YUYEFANHUA.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {//httpservlet继承于Servlet，servlet里面的service方法把http请求封装到对应的对象
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户名和密码数据
        Map<String, String[]> map = req.getParameterMap();
        System.out.println(map+"--------------------");
//2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

//3.调用Service查询
        UserService service = new UserServiceImpl();
        User u  = service.login(user);

        ResultInfo info = new ResultInfo();

//4.判断用户对象是否为null
        if(u == null){
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
//5.判断用户是否激活
        if(u != null && !"Y".equals(u.getStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请联系管理员激活");
        }
//6.判断登录成功
        if(u != null && "Y".equals(u.getStatus())){
            //登录成功
            info.setFlag(true);
            //添加session对象
            req.getSession().setAttribute("user",u);//登录成功标记
            //这里
        }

//响应数据
        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),info);

    }
}
