/**
 * @author 60417
 * @date 2021/9/10
 * @time 21:47
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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         *  业务代码：
         *  注册一个用户，数据已经经过校验了
         */

        //1、验证码校验  这里不要搞错了check字段
        String checkCode = req.getParameter("check");

        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //无论是否用到倒要清除，保证验证码能够每次刷新，有效
        session.removeAttribute("CHECKCODE_SERVER");
        System.out.println(checkcode_server+"-----"+checkCode);

        if(checkcode_server==null){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码过期");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            //json信息： {flag:true,errorMsg:ddd}
            resp.getWriter().write(json);
            return;
        }
        //2、校验失败，发生提示消息
        if(checkCode==null || !checkcode_server.equalsIgnoreCase(checkCode)){
            //校验失败
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            //json信息： {flag:true,errorMsg:ddd}
            resp.getWriter().write(json);
            return;
        }
        //3、校验成功，使用对应的serviceImpl来调用对应的业务逻辑，操作dao
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        //javaBean util封装 对象
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        boolean b = service.registerUser(user);
        //注册失败
        ResultInfo info = new ResultInfo();
        if(!b){
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //注册成功
        info.setFlag(true);
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        //json信息： {flag:true,errorMsg:ddd}
        resp.getWriter().write(json);
        //4、返回注册成功对应的信息
        //5、跳转登录页面

    }
}





