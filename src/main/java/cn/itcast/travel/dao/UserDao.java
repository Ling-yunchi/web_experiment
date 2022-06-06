package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao extends BaseDao<User> {
    User findByUsername(String username);
}
