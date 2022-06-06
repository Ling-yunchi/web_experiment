package cn.itcast.travel.web.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.module.UserResult;
import cn.itcast.travel.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.itcast.travel.util.Const.USER_SESSION_KEY;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public ResultInfo register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return new ResultInfo(false, "用户名已存在");
        }
        userDao.save(user);
        return new ResultInfo(true, "注册成功");
    }

    @Override
    public ResultInfo login(User user, HttpServletRequest res) {
        if (res.getSession().getAttribute(USER_SESSION_KEY) != null) {
            return new ResultInfo(false, "用户已登录");
        }
        User u = userDao.findByUsername(user.getUsername());
        if (u == null) {
            return new ResultInfo(false, "用户名不存在");
        }
        if (!u.getPassword().equals(user.getPassword())) {
            return new ResultInfo(false, "密码错误");
        }
        if (!"Y".equals(u.getStatus())) {
            return new ResultInfo(false, "用户未激活");
        }
        // 转换为UserResult对象，对密码脱敏
        res.getSession().setAttribute(USER_SESSION_KEY, new UserResult(u));
        return new ResultInfo(true, "登录成功");
    }

    @Override
    public ResultInfo self(HttpServletRequest res) {
        UserResult user = (UserResult) res.getSession().getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return new ResultInfo(false, "用户未登录");
        }
        return new ResultInfo(true, "获取用户信息成功", user);
    }

    @Override
    public void logout(HttpServletRequest res, HttpServletResponse resp) throws IOException {
        res.getSession().invalidate();
        resp.sendRedirect(res.getContextPath() + "/login.html");
    }

    @Override
    public ResultInfo active(Integer id, String code) {
        if (code == null) {
            return new ResultInfo(false, "激活码不能为空");
        }
        User user = userDao.findById(id);
        if (user == null) {
            return new ResultInfo(false, "用户不存在");
        }
        user.setCode(code);
        user.setStatus("Y");
        userDao.save(user);
        return new ResultInfo(true, "激活成功");
    }
}
