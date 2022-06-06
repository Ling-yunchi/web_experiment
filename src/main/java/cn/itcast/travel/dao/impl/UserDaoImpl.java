package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User findByUsername(String username) {
        User res = null;
        String sql = "select * from tab_user where username = ?";
        try {
            res = super.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return res;
    }
}
