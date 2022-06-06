package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao extends BaseDao<RouteImg> {
    List<RouteImg> findByRid(Integer rid);
}
