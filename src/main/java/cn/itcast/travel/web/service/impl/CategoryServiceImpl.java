package cn.itcast.travel.web.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.CategoryService;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/6 14:57
 */
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public ResultInfo findAll() {
        List<Category> res = categoryDao.findAll();
        return new ResultInfo(true, "查询成功", res);
    }
}
