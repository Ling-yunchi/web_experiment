package cn.itcast.travel.web.servlet;

import cn.itcast.travel.anotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CommonsLog
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("-------------------------");

        String uri = req.getRequestURI();
        log.info("request uri: " + uri);

        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        log.info("methodName: " + methodName);

        // 获取传入的所有参数
        Map<String, String[]> inParams = req.getParameterMap();
        Map<String, String> inQuery = new HashMap<>(16);
        if (req.getQueryString() != null) {
            for (String s : req.getQueryString().split("&")) {
                String[] tmp = s.split("=");
                inQuery.put(tmp[0], tmp[1]);
            }
        }

        // 获取所有方法
        List<Method> methods = Arrays.asList(this.getClass().getDeclaredMethods());

        // 获取方法名相同的方法并遍历
        List<Method> matchMethods = methods.stream().filter(m -> m.getName().equals(methodName)).collect(Collectors.toList());

        if (matchMethods.size() == 0) {
            log.info("method not found");
            resp.setStatus(404);
            log.info("-------------------------");
            return;
        }

        for (Method method : matchMethods) {
            // 获取方法的参数名称及类型
            List<Parameter> params = Arrays.asList(method.getParameters());
            // 创建传递给方法的参数数组
            Object[] paramValues = new Object[params.size()];
            // 参数是否匹配
            boolean flag = true;
            // 判断参数类型是否与传递的参数类型一致
            for (Parameter p : params) {
                RequestBody annotation = p.getAnnotation(RequestBody.class);
                Object value = null;
                if (annotation != null) {
                    log.debug("parser request body");
                    try {
                        value = p.getType().getConstructor().newInstance();
                        for (String key : inParams.keySet()) {
                            BeanUtils.setProperty(value, key, inParams.get(key)[0]);
                        }
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                        log.error("parser body error: ", e);
                        log.error("cause: " + getRealCause(e));
                        flag = false;
                        break;
                    }
                } else if (p.getType().equals(HttpServletRequest.class)) {
                    log.debug("parser request");
                    value = req;
                } else if (p.getType().equals(HttpServletResponse.class)) {
                    log.debug("parser response");
                    value = resp;
                } else {
                    if (inParams.containsKey(p.getName())) {
                        log.debug("parser request param: " + p.getName());
                        String rawValue = inQuery.get(p.getName());
                        // 尝试将参数值转换为目标类型
                        try {
                            value = p.getType().getConstructor(String.class).newInstance(rawValue);
                        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                            log.error("can not convert value to type: " + p.getType().getName());
                            log.error("cause: " + getRealCause(e));
                            flag = false;
                            break;
                        }
                    } else {
                        log.error("parameter not found: " + p.getName());
                        flag = false;
                        break;
                    }
                }
                paramValues[params.indexOf(p)] = value;
            }

            if (flag) {
                // 调用方法
                Object res = null;
                try {
                    res = method.invoke(this, paramValues);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    log.error("can not invoke method: " + method.getName());
                    log.error("cause: " + getRealCause(e));
                    resp.setStatus(500);
                    log.info("-------------------------");
                    return;
                }
                log.info("response: " + (res == null ? "null" : res.toString()));
                if (res != null && !"".equals(res.toString())) {
                    resp.setStatus(200);
                    // 设置响应编码
                    resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    // 设置响应内容类型
                    resp.setContentType("application/json;charset=utf-8");
                    // 将结果转换为json
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(res);
                    // 响应内容
                    resp.getWriter().write(json);
                }
            } else {
                log.error("parameter type error");
                resp.setStatus(400);
            }
            log.info("-------------------------");
            return;
        }
    }

    static String getRealCause(Throwable e) {
        StringBuilder sb = new StringBuilder();
        Throwable cause = e.getCause();
        while (cause != null) {
            sb.append(cause.getMessage());
            cause = cause.getCause();
        }
        return sb.toString();
    }
}
