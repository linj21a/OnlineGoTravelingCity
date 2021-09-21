/**
 * @author 60417
 * @date 2021/9/20
 * @time 10:07
 * @todo
 */
package YUYEFANHUA.Web.Servlet;

import YUYEFANHUA.Domain.Category;
import YUYEFANHUA.Service.CategoryService;
import YUYEFANHUA.Service.ServiceImpl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、调用service层


        //2、数据序列化返回


        //3、处理前台页面展示：

        //1.调用service查询所有
        CategoryService service = new CategoryServiceImpl();
        List<Category> cs = service.findAll();
        //2.序列化json返回
       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),cs);*/
        writeValue(cs,response);//抽取到baseServlet里面


    }
}
