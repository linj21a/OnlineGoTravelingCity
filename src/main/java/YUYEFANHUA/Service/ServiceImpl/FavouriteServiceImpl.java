/**
 * @author 60417
 * @date 2021/9/21
 * @time 12:55
 * @todo
 */
package YUYEFANHUA.Service.ServiceImpl;

import YUYEFANHUA.DAO.DaoImpl.FavouriteDaoImpl;
import YUYEFANHUA.DAO.FavouriteDao;
import YUYEFANHUA.Domain.Favorite;
import YUYEFANHUA.Service.FavouriteService;

public class FavouriteServiceImpl implements FavouriteService {
    private FavouriteDao favoriteDao = new FavouriteDaoImpl();
    /**
     * 判断当前的旅游路线是否已经被uid用户收藏过
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    /**
     * 收藏对应的旅游路线id
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
