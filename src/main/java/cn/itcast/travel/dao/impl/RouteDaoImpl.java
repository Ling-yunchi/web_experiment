package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class RouteDaoImpl extends BaseDaoImpl<Route> implements RouteDao {

    @Override
    public List<Route> findByCidAndRnameLikePageable(Integer cid, String rname, Integer currentPage, Integer pageSize) {
        String sql = "select * from " + super.tableName + " where cid = ? and rname like '%" + rname + "%' limit ?,?";
        return super.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, (currentPage - 1) * pageSize, pageSize);
    }

    @Override
    public int countByCidAndRnameLike(Integer cid, String rname) {
        String sql = "select count(*) from " + super.tableName + " where cid = ? and rname like '%" + rname + "%'";
        return super.jdbcTemplate.queryForObject(sql, Integer.class, cid);
    }
}
