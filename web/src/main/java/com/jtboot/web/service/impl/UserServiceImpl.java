package com.jtboot.web.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.HttpClientService;
import com.jtboot.common.exception.ServiceException;
import com.jtboot.common.vo.SysResult;
import com.jtboot.web.pojo.User;
import com.jtboot.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpClientService httpClientService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String save(User user) {

        String uri = "http://sso.jt.com/user/register";

        Map<String, String> map = new HashMap<>();

        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());

        try {
            String res = httpClientService.doPost(uri, map);
            SysResult sysResult = objectMapper.readValue(res, SysResult.class);
            return (String) sysResult.getData();
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String findUserByUP(User user) {

        String uri = "http://sso.jt.com/user/login";

        Map<String, String> map = new HashMap<>();
        map.put("u", user.getUsername());
        map.put("p", user.getPassword());

        try {
            String rel = httpClientService.doPost(uri, map);
            SysResult result = objectMapper.readValue(rel, SysResult.class);
            return (String) result.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("失败");
        }
    }
}
