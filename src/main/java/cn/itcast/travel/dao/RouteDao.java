package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao extends BaseDao<Route> {
    List<Route> findByCidAndRnameLikePageable(Integer cid, String rname, Integer currentPage, Integer pageSize);

    int countByCidAndRnameLike(Integer cid, String rname);
}
