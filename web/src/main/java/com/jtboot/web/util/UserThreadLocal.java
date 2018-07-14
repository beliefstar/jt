package com.jtboot.web.util;

import com.jtboot.common.exception.NoLoginException;
import com.jtboot.web.pojo.User;

public class UserThreadLocal {

    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        User user = threadLocal.get();
        if (user != null) {
            return user;
        }
        throw new NoLoginException();
    }
}
