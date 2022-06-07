package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class FavoriteDaoImpl extends BaseDaoImpl<Favorite> implements FavoriteDao {
    @Override
    public Favorite findByRidAndUid(Integer rid, Integer uid) {
        String sql = "select * from " + super.tableName + " where rid = ? and uid = ?";
        try {
            return super.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer countByRid(Integer rid) {
        String sql = "select count(*) from " + super.tableName + " where rid = ?";
        return super.jdbcTemplate.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public int save(Favorite favorite) {
        String sql = "insert into " + super.tableName + "(rid,uid,date) values(?,?,?)";
        return super.jdbcTemplate.update(sql, favorite.getRid(), favorite.getUid(), favorite.getDate());
    }
}
