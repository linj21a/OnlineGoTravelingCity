/**
 * @author 60417
 * @date 2021/9/11
 * @time 20:32
 * @todo
 */
package YUYEFANHUA.DAO.DaoImpl;

import YUYEFANHUA.DAO.UserDao;
import YUYEFANHUA.Domain.User;
import YUYEFANHUA.Util.DBUtils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    /**
     * 查找用户
     * @param  username String
     * @return user
     */
    @Override
    public User findUser(String username) {
        String sql = "select * from user where username=?";
        User user1 = null ;
        try{
//            Incorrect result size: expected 1, actual 0
            List<User> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
            if(query.size()>=1){
                user1 = query.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user1;
    }

    @Override
    public boolean saveUser(User user) {
        //用户名、密码、姓名、生日、性别、电话、邮箱、激活状态、激活码
        String sql = "insert into user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(),
                user.getEmail(), user.getStatus(), user.getCode());
        System.out.println("注册返回update"+update);
        return update!=0;
    }
    /**
     * 根据激活码查询用户对象
     * @param code 激活码
     * @return uid   Integer.MIN_VALUE  表示不能存在
     */
    @Override
    public int findByCode(String code) {
            String sql = "select uid from user where code = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, code);

        System.out.println(maps);
        if(maps.size()==0){//这里maps不可能为null，只可能为{}
            return Integer.MIN_VALUE;//不存在
        }
        return (Integer)(maps.get(0).get("uid"));

    }

    /**
     * 修改指定用户激活状态
     * @param Uid 用户唯一id
     */
    @Override
    public void updateStatus(int Uid) {
        String sql = " update user set status = 'Y' where uid=?";
        jdbcTemplate.update(sql, Uid);

    }

    /**
     * 根据用户名和密码查询的方法
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from user where username = ? and password = ?";
            //2.执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
