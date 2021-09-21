/**
 * @author 60417
 * @date 2021/9/19
 * @time 16:02
 * @todo
 */
package YUYEFANHUA.Web.Servlet.UserServletDetail;

import YUYEFANHUA.Domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //查询session
        User user =(User) req.getSession().getAttribute("user");
        if(user==null){
            return;
        }
        ObjectMapper mapper = new ObjectMapper();

        String s = mapper.writeValueAsString(user);
        System.out.println(s);

        //序列化json格式，我们不能够把整个对象都返回吧？
        user.setUid(-1);
        user.setPassword("");
        user.setCode("");
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),user);




    }
}
