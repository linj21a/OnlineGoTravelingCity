package YUYEFANHUA.Web.Servlet.UserServletDetail;
/*
 *@author 60417
 *@date 2021/9/11
 *@time 21:49
 *@todo
 *
 */

import YUYEFANHUA.Service.ServiceImpl.UserServiceImpl;
import YUYEFANHUA.Service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ActiverUserServlet", value = "/activerUserServlet")
public class ActiverUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //激活，需要保证该验证码合格

        String code = request.getParameter("code");
        if(code==null||"".equals(code)){
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("激活码有误!");
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
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }
}
