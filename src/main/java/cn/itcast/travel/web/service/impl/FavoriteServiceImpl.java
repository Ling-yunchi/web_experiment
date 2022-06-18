package cn.itcast.travel.web.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.FavoriteService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * @author wangrong
     * @date 2022/6/6 14:57
     */
    public static class CategoryServiceImpl implements CategoryService {
        private final CategoryDao categoryDao = new CategoryDaoImpl();

        @Override
        public ResultInfo findAll() {
            List<Category> res = categoryDao.findAll();
            return new ResultInfo(true, "查询成功", res);
        }
    }
}
