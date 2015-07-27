package com.langnatech.privilege.shiro.extend;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.langnatech.core.enums.LoginAuthResult;
import com.langnatech.core.web.event.WebVisitEventPublish;


public class FormAuthenticationFilterExt extends FormAuthenticationFilter
{

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
        throws Exception
    {
        WebVisitEventPublish.getInstance().loginEvent(LoginAuthResult.LOGIN_SUCCESS);
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception
    {
        String header = ((HttpServletRequest)request).getHeader("X-Requested-With");
        if (header != null && header.equalsIgnoreCase("XMLHttpRequest"))
        {
            HttpServletResponse httpResponse = ((HttpServletResponse)response);
            httpResponse.setStatus(401);
            httpResponse.sendError(401, "login timeout,please login agin");
            return false;
        }
        return super.onAccessDenied(request, response);
    }

}
