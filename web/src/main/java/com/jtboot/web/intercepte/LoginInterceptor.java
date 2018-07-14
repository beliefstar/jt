package com.jtboot.web.intercepte;

import com.jtboot.common.service.RedisService;
import com.jtboot.common.util.CookieUtils;
import com.jtboot.web.constant.AuthConstant;
import com.jtboot.web.pojo.User;
import com.jtboot.web.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String cookie = CookieUtils.getCookie(request, AuthConstant.COOKIE_TICKET_NAME);
        if (cookie != null) {
            User user = redisService.get(cookie, User.class);
            if (user != null) {
                UserThreadLocal.set(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
