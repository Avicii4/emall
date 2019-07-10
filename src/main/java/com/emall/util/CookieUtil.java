package com.emall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Harry Chou
 * @date 2019/7/10
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = ".fff.com";
    private final static String COOKIE_NAME = "emall_login_token";

    // 登录时写入Cookie
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        // 设置在根目录下
        cookie.setPath("/");
        // -1表示永久。不设置此项则Cookie不会写入硬盘，而是写在内存中，只在当前页面有效
        cookie.setMaxAge(60 * 60 * 24 * 365);

        log.info("Write cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());

        response.addCookie(cookie);
    }

    // 登录时读取Cookie
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Read cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    log.info("Return cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 注销时删除Cookie
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    // 有效期设为0，表示删除
                    cookie.setMaxAge(0);
                    log.info("Delete cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
