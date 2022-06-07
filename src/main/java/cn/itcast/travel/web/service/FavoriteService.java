package cn.itcast.travel.web.service;

import cn.itcast.travel.module.ResultInfo;

public interface FavoriteService {
    ResultInfo isFavorite(Integer rid, Integer uid);

    ResultInfo addFavorite(Integer rid, Integer uid);
}
