/**
 * @author 60417
 * @date 2021/9/11
 * @time 20:30
 * @todo
 */
package YUYEFANHUA.Service.ServiceImpl;

import YUYEFANHUA.DAO.DaoImpl.UserDaoImpl;
import YUYEFANHUA.DAO.UserDao;
import YUYEFANHUA.Domain.User;
import YUYEFANHUA.Service.UserService;
import YUYEFANHUA.Util.MailUtils;
import YUYEFANHUA.Util.UuidUtil;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    public boolean registerUser(User user) {
        //1、判断该user是否已经存在
        User user1 = userDao.findUser(user.getUsername());
        if(user1!=null){
            return false;//用户已经存在,请直接登录
        }
        //2、不存在则注册
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());//激活码
        //3、返回true
        boolean b =  userDao.saveUser(user);//注册成功！
        if(b){
            //需要发生注册用户激活邮件
            //然后跳转到登录页面
            //
            //3.激活邮件发送，邮件正文？
            String content="<a href='http://localhost:8080/OnlineGoTravelingCity_war_exploded/activerUserServlet?code="+user.getCode()+"'>点我激活[旅游网]</a>";
            MailUtils.sendMail(user.getEmail(),content,"在线旅游网激活邮件");
            return true;
        }
        return b;


    }
    /**
     * 根据激活码激活用户
     * @param code
     * @return
     */
    @Override
    public boolean activeUser(String code) {
        int byCode = userDao.findByCode(code);
        if(byCode!=Integer.MIN_VALUE){
            userDao.updateStatus(byCode);
            return true;
        }
        return false;//激活失败
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
