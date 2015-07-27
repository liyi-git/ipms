/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.web.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.langnatech.core.web.Servlets;
 /**
 * CacheControlHeaderFilter类是为Response设置客户端缓存控制Header的过滤器，实现了Filter接口。
 * 
 * @author zangchuqiang
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class CacheControlHeaderFilter implements Filter {
	private static final String PARAM_EXPIRES_SECONDS = "expiresSeconds";
	private long expiresSeconds;
	
	/** 
	 * 每次请求与此过滤器相关的servlet或JSP页面时，就执行其doFilter方法。<br/>
	 * 详细描述：每次请求在达到Servlet/JSP之前会自动调用doFilter方法，为Response设置客户端缓存控制Header。<br/>
	 * 使用方式：每次请求在到达Servlet/JSP之前，会自动调用。<br/>
	 * @param req 包含包括表单数据、cookie和HTTP请求头等信息。<br/>
	 * @param res ServletResponse
	 * @param chain FilterChain
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * @throws IOException，ServletException
	*/ 
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		Servlets.setExpiresHeader((HttpServletResponse) res, expiresSeconds);
		chain.doFilter(req, res);
	}

	/** 
	 * init方法只在此过滤器第一次初始化时执行。<br/>
	 * 详细描述：init 方法只在此过滤器第一次初始化时执行，不是每次调用过滤器都执行它
	 * 使用方式：过滤器第一次初始化时，自动调用
	 * @param filterConfig FilterConfig
	 * @see Filter#init(FilterConfig)
	*/ 
	@Override
	public void init(FilterConfig filterConfig) {
		String expiresSecondsParam = filterConfig.getInitParameter(PARAM_EXPIRES_SECONDS);
		if (expiresSecondsParam != null) {
			expiresSeconds = Long.valueOf(expiresSecondsParam);
		} else {
			expiresSeconds = Servlets.ONE_YEAR_SECONDS;
		}
	}

	/** 
	 * 利用一个给定的过滤器对象永久地终止服务器（如关闭服务器）时调用。<br/>
	 * 详细描述：此方法在利用一个给定的过滤器对象永久地终止服务器（如关闭服务器）时调用。大多数过滤器简单地为<br/>
	 * 此方法提供一个空体，不过，可利用它来完成诸如关闭过滤器使用的文件或数据库连接池等清除任务。
	 * @see Filter#destroy()
	*/ 
	@Override
	public void destroy() {
	}
}