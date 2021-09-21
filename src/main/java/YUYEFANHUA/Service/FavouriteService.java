/**
 * @author 60417
 * @date 2021/9/21
 * @time 12:52
 * @todo
 */
package YUYEFANHUA.Service;

public interface FavouriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);
}
