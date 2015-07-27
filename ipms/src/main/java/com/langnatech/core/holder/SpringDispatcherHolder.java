/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.holder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
 /**
 * SpringDispatcherHolder是Spring容器持有工具类，用于获取request、session等对象、获取Controller层bean对象。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class SpringDispatcherHolder {

    /** 
     * 获取request对象。<br/>
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @return HttpServletRequest request对象。
     */ 
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    /** 
     * 获取session对象。<br/>
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @return HttpSession session对象。
     */ 
    public static HttpSession getSession() {
        return getRequest().getSession();
    }
    
    /** 
     * 获取ServletContext上下文。<br/>
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @return ServletContext ServletContext上下文。
     */ 
    public static ServletContext getServletContext() {
        return getSession().getServletContext();
    }
    
    /** 
     * 获取WebApplicationContext上下文。<br/>
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @return WebApplicationContext WebApplicationContext上下文。
     */ 
    public static WebApplicationContext getWebApplicationContext() {
        return RequestContextUtils.getWebApplicationContext(getRequest());
    }
    
    /** 
     * 根据name获取bean对象<br/>
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @return T 返回相应的bean对象。
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T)getWebApplicationContext().getBean(name);
    }
    
    /** 
     * 通过class类型获取bean对象。<br/>
     * 详细描述：通过class类型获取bean对象。
     * 使用方式：java代码中可通过类名直接调用此静态方法。<br/>
     * @param clazz Class类。
     * @return T 返回相应的bean对象。
     */ 
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) {
        return (T)getWebApplicationContext().getBean(clazz);
    }
}