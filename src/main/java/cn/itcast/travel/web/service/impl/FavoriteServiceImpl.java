package cn.itcast.travel.web.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.FavoriteService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public ResultInfo isFavorite(Integer rid, Integer uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return new ResultInfo(true, "查询成功", favorite != null);
    }

    @Override
    public ResultInfo addFavorite(Integer rid, Integer uid) {
        Date now = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(now);
        Favorite favorite = new Favorite(rid, date, uid);
        try {
            favoriteDao.save(favorite);
        } catch (Exception e) {
            return new ResultInfo(false, "已经收藏，不可重复收藏");
        }
        return new ResultInfo(true, "添加成功");
    }
}
