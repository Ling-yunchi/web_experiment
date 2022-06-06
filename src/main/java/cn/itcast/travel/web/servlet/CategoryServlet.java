package cn.itcast.travel.web.servlet;

import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

/**
 * @author wangrong
 * @date 2022/6/6 14:57
 */
public class CategoryServlet extends BaseServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    public ResultInfo findAll() {
        return categoryService.findAll();
    }
}
