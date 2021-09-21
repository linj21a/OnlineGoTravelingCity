/**
 * @author 60417
 * @date 2021/9/19
 * @time 16:44
 * @todo
 */
package YUYEFANHUA.Web.Servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实现一个servlet来分发servlet，降低servlet的个数
 */
public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest  req, HttpServletResponse res) throws ServletException, IOException {
        //System.out.println("baseServlet的service方法被执行了...");
//HttpServletRequest才有这个方法  getRequestURI
        //完成方法分发
        //1.获取请求路径
        String uri = req.getRequestURI(); //   /travel/user/add
        System.out.println("请求uri:"+uri);//  /travel/user/add
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("方法名称："+methodName);
        //3.获取方法对象Method
        //谁调用我？我代表谁
        System.out.println(this);//UserServlet的对象
        try {
            //获取方法  getMethod只能获取共有方法，无法获取protect和private
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,res);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 序列化数据为json同时返回。
     * @param o
     * @param response
     * @throws IOException
     */
    public void writeValue(Object o,HttpServletResponse response) throws IOException {
        ObjectMapper o1 = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        o1.writeValue(response.getWriter(),o);
    }
    public String writeAsValue(Object o) throws JsonProcessingException {
        ObjectMapper o1 = new ObjectMapper();
        return o1.writeValueAsString(o);
    }
}
