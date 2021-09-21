/*
 * @author 60417
 * @date 2021/9/11
 * @time 20:32
 * @todo
 */
package YUYEFANHUA.DAO;

import YUYEFANHUA.Domain.User;

public interface UserDao {
    /**
     * 查找数据库中的user
     * @param  username String
     * @return
     */
    User findUser(String username);

    /**
     * 保存一个user
     * @param user
     * @return
     */
    boolean saveUser(User user);

    /**
     * 根据激活码查找该用户，存在则表示激活码有效
     * @param code
     * @return int uuid
     *
     */
    int findByCode(String code);
    /**
     * 修改指定用户激活状态
     * @param Uid 用户id
     */
    void updateStatus(int Uid);
    /**
     * 根据用户名和密码查询的方法
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
