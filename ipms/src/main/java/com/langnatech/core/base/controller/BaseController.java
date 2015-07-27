/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.base.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类是可以被其他所有的controller继承的基类，里面提供了一些基于controller层的公共方法。
 * 
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2014-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class BaseController {

    /**
     * 统一配置异步请求返回的错误信息的key值。
     */
    protected static final String ERROR_MSG_KEY = "errorMsg";

    /**
     * 根据传入的request请求获取绝对路径，不包含http://localhost:8080。<br/>
     * 详细描述：根据传入的request获取当前请求的绝对路径，其路径内容不包含http://localhost:8080。<br/>
     * 使用方式：在controller中继承该基类，通过this.getAbsoluteUri(request)获取当前请求的绝对路径。
     * 
     * @param request 当前请求的request对象。
     * @return 当前请求的绝对路径，不包含http://localhost:8080。
     */
    public final String getAbsoluteUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 根据传入的request请求获取绝对路径，包含http://localhost:8080。<br/>
     * 详细描述：根据传入的request获取当前请求的绝对路径，其路径内容包含http://localhost:8080。<br/>
     * 使用方式：在controller中继承该基类，通过this.getAbsoluteUrl(request)获取当前请求的绝对路径。
     * 
     * @param request 当前请求的request对象。
     * @return 当前请求的绝对路径，包含http://localhost:8080。
     */
    public final String getAbsoluteUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }
}
