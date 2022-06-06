package cn.itcast.travel.web.servlet;

import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.CategoryService;
import cn.itcast.travel.web.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;

/**
 * @author wangrong
 * @date 2022/6/6 14:57
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private final CategoryService categoryService = new CategoryServiceImpl();

    public ResultInfo findAll() {
        return categoryService.findAll();
    }
}
