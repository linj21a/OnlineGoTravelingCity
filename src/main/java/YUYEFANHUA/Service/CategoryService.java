/**
 * @author 60417
 * @date 2021/9/20
 * @time 10:05
 * @todo
 */
package YUYEFANHUA.Service;

import YUYEFANHUA.Domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public interface CategoryService {
    /**
     * 返回所有的目录信息
     * @return
     */
    public List<Category> findAll();

}
