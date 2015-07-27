package com.langnatech.logging.entity;

import com.langnatech.core.base.entity.IEntity;
import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.logging.bean.LoggingBean;


public class LoginLogEntity extends LoggingBean implements IEntity
{

    private static final long serialVersionUID = 1545356507488625824L;

    private Integer loginType;

    private String clientIp;

    private String browserType;

    public Integer getLoginType()
    {
        return loginType;
    }

    public LoggingTypeEnum getLogType()
    {
        return LoggingTypeEnum.LOGIN_LOG;
    }

    public void setLoginType(Integer loginType)
    {
        this.loginType = loginType;
    }

    public String getClientIp()
    {
        return clientIp;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getBrowserType()
    {
        return browserType;
    }

    public void setBrowserType(String browserType)
    {
        this.browserType = browserType;
    }

}