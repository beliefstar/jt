package com.jtboot.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {

    private static int DEFAULT_MAX_AGE = 60 * 60 * 2;//默认2小时

    public static void deleteCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }

    public static void setCookie(HttpServletResponse response, String key, String value) {
        setCookie(response, key, value, DEFAULT_MAX_AGE);
    }

    public static void setCookie(HttpServletResponse response, String key, String value, int maxAge) {
        if (value == null) {
            value = "";
        }
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");//526452502
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Map<String, String> valueMap = getCookieValueMap(request);
        return valueMap.get(key);
    }

    private static Map<String, String> getCookieValueMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        if (request == null)
            return map;
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return map;
        }
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        return map;
    }
}
