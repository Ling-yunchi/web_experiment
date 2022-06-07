package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao extends BaseDao<Favorite> {
    Favorite findByRidAndUid(Integer rid, Integer uid);

    Integer countByRid(Integer rid);
}
