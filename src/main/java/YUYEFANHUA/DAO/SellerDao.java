/**
 * @author 60417
 * @date 2021/9/21
 * @time 10:17
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.Seller;

public interface SellerDao {
    //需要有一个方法，根据sid，查询商家信息

    Seller findBySid(int sid);
}
