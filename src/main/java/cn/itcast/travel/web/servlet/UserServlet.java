package cn.itcast.travel.web.servlet;

import cn.itcast.travel.anotation.RequestBody;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import lombok.extern.apachecommons.CommonsLog;

import javax.servlet.annotation.WebServlet;

@CommonsLog
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    public ResultInfo register(@RequestBody User user, Integer id) throws Exception {
        log.info("register");
        log.info(user.toString());
        log.info(id);
        return new ResultInfo(true, "注册成功");
    }

}
