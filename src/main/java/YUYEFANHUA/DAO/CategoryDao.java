/**
 * @author 60417
 * @date 2021/9/20
 * @time 9:59
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有的分类数据
     * @return
     */
    List<Category> findAll();

    Category findByCid(int cid);
}
