package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User findUserByUsername(String username) {
        User res = null;
        String sql = "select * from tab_user where username = ?";
        res = super.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        return res;
    }
}
