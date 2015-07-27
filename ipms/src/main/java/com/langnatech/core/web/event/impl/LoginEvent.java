
package com.langnatech.core.web.event.impl;

import com.langnatech.core.web.event.AbstractWebVisitEvent;
import com.langnatech.logging.entity.LoginLogEntity;


/**
 * @Title:登录注销事件
 * @author liyi
 * @date Dec 21, 2013 4:29:00 PM
 */

public class LoginEvent extends AbstractWebVisitEvent
{

    private static final long serialVersionUID = 5009199522943324769L;

    private LoginLogEntity loginLog;

    public LoginEvent(Object source, LoginLogEntity loginLog)
    {
        super(source);
        this.loginLog = loginLog;
    }

    public LoginLogEntity getLoginLog()
    {
        return loginLog;
    }

    public void setLoginLog(LoginLogEntity loginLog)
    {
        this.loginLog = loginLog;
    }

}
