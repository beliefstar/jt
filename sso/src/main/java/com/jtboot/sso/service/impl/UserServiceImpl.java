package com.jtboot.sso.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtboot.common.service.RedisService;
import com.jtboot.common.exception.ServiceException;
import com.jtboot.sso.mapper.UserMapper;
import com.jtboot.sso.pojo.User;
import com.jtboot.sso.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisCluster;

    private static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public List<User> find(User user) {
        return userMapper.select(user);
    }

    @Override
    public Boolean check(String param, int type) {
        User user = new User();

        switch (type){
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                throw new ServiceException("参数错误");
        }

        int count = userMapper.selectCount(user);

        return count != 0;
    }

    @Override
    public User saveUser(User user) {

        user.setId(null);
        user.setCreated(new Date());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setEmail(user.getPhone());

        try {
            int insert = userMapper.insert(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String findUserByUP(String u, String p) {
        User user = new User();
        user.setUsername(u);
        user.setPassword(DigestUtils.md5Hex(p));

        try {
            User rel = userMapper.findUserByUP(user.getUsername(), user.getPassword());
            if (rel == null) {
                return null;
            } else {
                String userStr = objectMapper.writeValueAsString(rel);
                String ticket = DigestUtils.md5Hex("JT_TICKET" + System.currentTimeMillis() + u);
                redisCluster.set(ticket, userStr);
                return ticket;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User checkLogined(String ticket) {

        try {
            String s = redisCluster.get(ticket);
            return objectMapper.readValue(s, User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
