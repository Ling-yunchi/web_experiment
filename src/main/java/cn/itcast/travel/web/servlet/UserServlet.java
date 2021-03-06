package cn.itcast.travel.web.servlet;

import cn.itcast.travel.anotation.RequestBody;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.module.ResultInfo;
import cn.itcast.travel.web.service.UserService;
import cn.itcast.travel.web.service.impl.UserServiceImpl;
import lombok.extern.apachecommons.CommonsLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CommonsLog
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    public ResultInfo register(@RequestBody User user) {
        return userService.register(user);
    }

    public ResultInfo login(@RequestBody User user, HttpServletRequest res) {
        return userService.login(user, res);
    }

    public void active(String username, String code, HttpServletResponse resp) throws Exception {
        userService.active(username, code, resp);
    }

    public ResultInfo self(HttpServletRequest res) {
        return userService.self(res);
    }

    public void logout(HttpServletRequest res, HttpServletResponse resp) throws Exception {
        userService.logout(res, resp);
    }
}
