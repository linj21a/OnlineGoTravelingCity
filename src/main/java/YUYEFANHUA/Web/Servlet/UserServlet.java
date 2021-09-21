/**
 * @author 60417
 * @date 2021/9/19
 * @time 17:12
 * @todo
 */
package YUYEFANHUA.Web.Servlet;

import YUYEFANHUA.Domain.ResultInfo;
import YUYEFANHUA.Domain.User;
import YUYEFANHUA.Service.ServiceImpl.UserServiceImpl;
import YUYEFANHUA.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 注册用户
     * @param req
     * @param resp
     * @throws IOException
     */
    public void registerUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    /**
     * 用户登录
     * @param req
     * @param resp
     * @throws IOException
     */
    public void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException{
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

    /**
     * 查找用户
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findUser(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //查询session
        User user =(User) req.getSession().getAttribute("user");
        if(user==null){
            return;
        }
        ObjectMapper mapper = new ObjectMapper();

        String s = mapper.writeValueAsString(user);
        System.out.println(s);

        //序列化json格式，我们不能够把整个对象都返回吧？
//        user.setUid(-1);
        user.setPassword("");
        user.setCode("");
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),user);

    }

    /**
     * 用户退出
     * @param req
     * @param resp
     * @throws IOException
     */
    public void exitUser(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        //1.销毁session
        req.getSession().invalidate();

        //2.跳转登录页面
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }

    /**
     * 用户激活
     * @param req
     * @param resp
     * @throws IOException
     */
    public void activeUser(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //激活，需要保证该验证码合格

        String code = req.getParameter("code");
        if(code==null||"".equals(code)){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("激活码有误!");
            return;
        }
        //2.调用service完成激活
        System.out.println("code:"+code);
        UserService service = new UserServiceImpl();
        boolean flag = service.activeUser(code);

        //3.判断标记
        String msg;
        if(flag){
            //激活成功
            msg = "激活成功，请<a href='login.html'>登录</a>";
        }else{
            //激活失败
            msg = "激活失败，请联系管理员!";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }
}
