package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.module.PageResult;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.module.UserResult;
import cn.itcast.travel.web.service.FavoriteService;
import cn.itcast.travel.web.service.RouteService;
import cn.itcast.travel.web.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.web.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

import static cn.itcast.travel.util.Const.USER_SESSION_KEY;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private final RouteService routeService = new RouteServiceImpl();
    private final FavoriteService favoriteService = new FavoriteServiceImpl();

    public PageResult<Route> pageQuery(Integer cid, String rname, Integer currentPage, Integer pageSize) {
        return routeService.pageQuery(cid, rname, currentPage, pageSize);
    }

    public ResultInfo findOne(Integer rid) throws InvocationTargetException, IllegalAccessException {
        return routeService.findOne(rid);
    }

    public ResultInfo isFavorite(Integer rid, HttpServletRequest res) {
        UserResult user = (UserResult) res.getSession().getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return new ResultInfo(false, "未登录");
        }
        return favoriteService.isFavorite(rid, user.getUid());
    }

    public ResultInfo addFavorite(Integer rid, HttpServletRequest res) {
        UserResult user = (UserResult) res.getSession().getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return new ResultInfo(false, "您尚未登录，请登录后再收藏");
        }
        return favoriteService.addFavorite(rid, user.getUid());
    }
}
