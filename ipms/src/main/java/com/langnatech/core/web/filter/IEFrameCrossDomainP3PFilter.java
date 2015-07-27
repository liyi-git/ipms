
package com.langnatech.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title:IEFrameCrossDomainP3PFilter
 * @Description: 用于解决IE浏览器，通过IFrame 跨域嵌入系统时，Cookie丢失的问题;
 * @author liyi
 * @date Feb 12, 2014 10:20:02 AM
 */

public class IEFrameCrossDomainP3PFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        HttpServletResponse res = (HttpServletResponse)response;
        // IE iframe引起的内部cookie丢失
        res.setHeader("P3P", "CP=CAO PSA OUR");
        chain.doFilter(request, res);
    }

    public void destroy() {
    }

}
