/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.util.StringUtils;

import com.langnatech.core.enums.BrowserTypeEnum;


public class WebVisitUtil
{
    /**
     * 根据传入的UA信息获取浏览器类型。
     * 
     * @param userAgent
     *            传入UA信息。
     * @return 浏览器类型枚举。
     */
    public static BrowserTypeEnum getBrowseType(String userAgent)
    {
        if (StringUtils.hasText(userAgent))
        {
            List<BrowserTypeEnum> enumList = EnumUtils.getEnumList(BrowserTypeEnum.class);
            for (BrowserTypeEnum browserTypeEnum : enumList)
            {
                if (userAgent.indexOf(browserTypeEnum.getDesc()) != -1)
                {
                    return browserTypeEnum;
                }
            }
        }
        return BrowserTypeEnum.OTHER;
    }

    /**
     * @Title: getIP
     * @Description: 获取客户端ip
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");
        if ( !checkIP(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ( !checkIP(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ( !checkIP(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if ( !checkIP(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if ( !checkIP(ip))
        {
            ip = getRealIpAddress(request.getRemoteAddr());
        }
        return ip;
    }

    public static String getRealIpAddress(String ip)
    {
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1"))
        {
            InetAddress inet = null;
            try
            {
                inet = InetAddress.getLocalHost();
            }
            catch (UnknownHostException e)
            {
            }
            ip = inet.getHostAddress();
        }
        if (ip != null && ip.length() > 15)
        {
            if (ip.indexOf(",") > 0)
            {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    private static boolean checkIP(String ip)
    {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split(".").length != 4)
        {
            return false;
        }
        return true;
    }
}