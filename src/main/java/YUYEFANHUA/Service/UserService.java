/**
 * @author 60417
 * @date 2021/9/11
 * @time 20:30
 * @todo
 */
package YUYEFANHUA.Service;

import YUYEFANHUA.Domain.User;

public interface UserService {
    /**
     * 注册用户的服务
     * @param user
     * @return
     */
    boolean registerUser(User user);

    /**
     * 根据激活码激活用户
     * @param code
     * @return
     */
    boolean activeUser(String code);

    /**
     * 查询数据库是否有此用户，需要验证用户名和密码，返回对应的用户
     * @param user
     * @return
     */
    User login(User user);
}
