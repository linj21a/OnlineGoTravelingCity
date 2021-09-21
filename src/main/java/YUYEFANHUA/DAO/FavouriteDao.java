/**
 * @author 60417
 * @date 2021/9/21
 * @time 12:57
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.Favorite;

public interface FavouriteDao {
    /**
     * 根据用户id和rid来查找对应的收藏信息
     * @param
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    void add(int parseInt, int uid);
    int findCountByRid(int rid);
}
