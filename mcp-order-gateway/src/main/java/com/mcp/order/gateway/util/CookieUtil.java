/**
 *
 */
package com.mcp.order.gateway.util;

import com.mcp.core.util.MD5Util;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ming.li
 */
public class CookieUtil {

    private static Logger log = Logger.getLogger(CookieUtil.class);

    /**
     * 身份字符串
     *
     * @param username 用户名
     * @param password 密码
     * @param time     最后登录时间，单位为秒
     * @return
     */
    public static String getUserMd5(String username, String password, long time) {
        log.info(username + password + time / 1000);
        return MD5Util.MD5(username + password + time / 1000);
    }

    /**
     * 更新用户的cookie
     */
    public static void updateUserInfoCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String userId, int userType, String userMd5) {
        Cookie userIdCookie = new Cookie("userId", userId);
        userIdCookie.setPath("/");
        userIdCookie.setDomain(httpServletRequest.getServerName());
        userIdCookie.setMaxAge(9999999);


        Cookie userTypeCookie = new Cookie("userType", String.valueOf(userType));
        userTypeCookie.setPath("/");
        userTypeCookie.setDomain(httpServletRequest.getServerName());
        userTypeCookie.setMaxAge(9999999);

        Cookie userMd5Cookie = new Cookie("userMd5", userMd5);
        userMd5Cookie.setPath("/");
        userMd5Cookie.setDomain(httpServletRequest.getServerName());
        userMd5Cookie.setMaxAge(9999999);

        httpServletResponse.addCookie(userIdCookie);
        httpServletResponse.addCookie(userTypeCookie);
        httpServletResponse.addCookie(userMd5Cookie);
    }

    public static String getCookieValueByName(HttpServletRequest httpServletRequest, String name) {
        if (httpServletRequest == null) {
            return null;
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }
}
