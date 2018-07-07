package com.jtboot.web.controller;

import com.jtboot.common.util.CookieUtils;
import com.jtboot.common.vo.SysResult;
import com.jtboot.web.pojo.User;
import com.jtboot.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/service/user", "/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {
        try {
            String data = userService.save(user);
            return SysResult.oK(data);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "出错啦");
        }
    }

    /**
     * 1.登录操作
     * 2.获取ticket
     * 3.存入redis
     *
     * @param user
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            String ticket = userService.findUserByUP(user);

            if (StringUtils.isEmpty(ticket)) {

                return SysResult.build(201, "登录失败");
            } else {

                CookieUtils.setCookie(response, "JT_TICKET", ticket);

                return SysResult.oK(ticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "登录失败");
        }
    }
}
