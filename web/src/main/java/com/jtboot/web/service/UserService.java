package com.jtboot.web.service;

import com.jtboot.web.pojo.User;

public interface UserService {
    String save(User user);

    String findUserByUP(User user);
}
