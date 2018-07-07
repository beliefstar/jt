package com.jtboot.sso.controller;

import com.jtboot.common.vo.SysResult;
import com.jtboot.sso.pojo.User;
import com.jtboot.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    @ResponseBody
    public List<User> index() {
        return userService.find(new User());
    }

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object check(@PathVariable String param, @PathVariable Integer type, String callback) {
        Boolean flag = userService.check(param, type);
        MappingJacksonValue jsonp = new MappingJacksonValue(SysResult.oK(flag));
        jsonp.setJsonpFunction(callback);
        return jsonp;
    }

    @RequestMapping("/register")
    @ResponseBody
    public SysResult register(User user) {

        user = userService.saveUser(user);

        return SysResult.oK(user.getUsername());
    }

    @RequestMapping("/login")
    @ResponseBody
    public SysResult login(@RequestParam("u") String username, @RequestParam("p") String password) {

        try {
            String ticket = userService.findUserByUP(username, password);
            return SysResult.oK(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "none");
        }
    }

    @RequestMapping("/query/{ticket}")
    @ResponseBody
    public SysResult queryValidate(@PathVariable String ticket) {

        try {
            User user = userService.checkLogined(ticket);
            return SysResult.oK(user);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "未登录");
        }
    }
}