package com.langnatech.core.holder;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.util.StringUtils;


public class SecurityContextHolder
{
    /**
     * <获取当前登录用户>
     */
    public static String getLoginName()
    {
        try
        {
            return (String)SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static boolean hasRole(String roleId)
    {
        return SecurityUtils.getSubject().hasRole(roleId);
    }

    public static boolean hasRole(List<String> roles)
    {
        return SecurityUtils.getSubject().hasAllRoles(roles);
    }

    public static boolean isPermitted(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    public static boolean isPermitted(String... permissions)
    {
        return SecurityUtils.getSubject().isPermittedAll(permissions);
    }

    /** 是否超级管理员 **/
    public static boolean isSuperAdmin()
    {
        String roleId = PropertiesHolder.getProperty("privilege.role.superadmin");
        if (StringUtils.hasText(roleId))
        {
            return hasRole(roleId);
        }
        return false;
    }

    /**
     * <获取当前用户是否已记住>.<br/>
     */
    public static boolean isRemembered()
    {
        return SecurityUtils.getSubject().isRemembered();
    }

    /**
     * <获取当前用户是否已验证>.<br/>
     */
    public static boolean isAuthenticated()
    {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    /**
     * <获取当前用户SessionId>.<br/>
     */
    public static String getSessionId()
    {
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        return sessionId.replaceAll("-", "");
    }

    public static void registerSessionListener(SessionListener sessionListener)
    {
        DefaultWebSessionManager sessionManager = SpringContextHolder.getBean("sessionManager");
        sessionManager.getSessionListeners().add(sessionListener);
    }

}