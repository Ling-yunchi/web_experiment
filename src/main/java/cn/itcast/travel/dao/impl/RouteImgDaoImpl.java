package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class RouteImgDaoImpl extends BaseDaoImpl<RouteImg> implements RouteImgDao {
    @Override
    public List<RouteImg> findByRid(Integer rid) {
        String sql = "select * from " + super.tableName + " where rid = ?";
        return super.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(), rid);
    }
}
