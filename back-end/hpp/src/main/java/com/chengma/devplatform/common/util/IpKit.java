package com.chengma.devplatform.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求ip
 */
public class IpKit {

    /**
     * 获取请求ip
     *
     * @param request HTTP请求
     * @return ip
     */
    public static String getRealIp(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
