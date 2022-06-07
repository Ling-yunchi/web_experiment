package cn.itcast.travel.web.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.module.PageResult;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.module.RouteResult;
import cn.itcast.travel.web.service.RouteService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private final RouteDao routeDao = new RouteDaoImpl();
    private final RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private final SellerDao sellerDao = new SellerDaoImpl();
    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageResult<Route> pageQuery(Integer cid, String rname, Integer currentPage, Integer pageSize) {
        List<Route> list = routeDao.findByCidAndRnameLikePageable(cid, rname, currentPage, pageSize);
        int totalCount = routeDao.countByCidAndRnameLike(cid, rname);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        return new PageResult<>(totalCount, totalPage, currentPage, pageSize, list);
    }

    @Override
    public ResultInfo findOne(Integer rid) throws InvocationTargetException, IllegalAccessException {
        Route route = routeDao.findById(rid);
        if (route == null) {
            return new ResultInfo(false, "没有该路线");
        }
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        Seller seller = sellerDao.findById(route.getSid());
        Integer count = favoriteDao.countByRid(rid);
        RouteResult res = new RouteResult();
        BeanUtils.copyProperties(res, route);
        res.setRouteImgList(routeImgList);
        res.setSeller(seller);
        res.setCount(count);
        return new ResultInfo(true, "查询成功", res);
    }
}
