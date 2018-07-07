package com.jtboot.sso.service;

import com.jtboot.sso.pojo.User;

import java.util.List;

public interface UserService {
    List<User> find(User user);

    Boolean check(String param, int type);

    User saveUser(User user);

    String findUserByUP(String username, String password);

    User checkLogined(String ticket);
}
