package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.module.PageResult;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.RouteService;
import cn.itcast.travel.web.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private final RouteService routeService = new RouteServiceImpl();

    public PageResult<Route> pageQuery(Integer cid, String rname, Integer currentPage, Integer pageSize) {
        return routeService.pageQuery(cid, rname, currentPage, pageSize);
    }

    public ResultInfo findOne(Integer rid) {
        return routeService.findOne(rid);
    }
}
