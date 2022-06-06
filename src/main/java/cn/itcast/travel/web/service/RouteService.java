package cn.itcast.travel.web.service;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.module.PageResult;
import cn.itcast.travel.module.ResultInfo;

import java.lang.reflect.InvocationTargetException;

public interface RouteService {
    PageResult<Route> pageQuery(Integer cid,String rname, Integer currentPage, Integer pageSize);

    ResultInfo findOne(Integer rid) throws InvocationTargetException, IllegalAccessException;
}
