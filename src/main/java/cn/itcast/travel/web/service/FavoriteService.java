package cn.itcast.travel.web.service;

import cn.itcast.travel.module.ResultInfo;

public interface FavoriteService {
    ResultInfo isFavorite(Integer rid, Integer uid);

    ResultInfo addFavorite(Integer rid, Integer uid);

    /**
     * @author wangrong
     * @date 2022/6/6 14:57
     */
    interface CategoryService {
        ResultInfo findAll();
    }
}
