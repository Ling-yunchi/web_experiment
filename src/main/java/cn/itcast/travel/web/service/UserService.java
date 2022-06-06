package cn.itcast.travel.web.service;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.module.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService {
    ResultInfo register(User user);

    ResultInfo login(User user, HttpServletRequest res);

    ResultInfo self(HttpServletRequest res);

    void logout(HttpServletRequest res, HttpServletResponse resp) throws IOException;

    ResultInfo active(Integer id, String code);
}
