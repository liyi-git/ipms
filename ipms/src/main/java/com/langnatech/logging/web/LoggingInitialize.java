/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.logging.web;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.IWebInitializable;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.logging.pool.LoggingBufferQueue;


@Component
public class LoggingInitialize implements IWebInitializable
{

    private static Logger logger = LoggerFactory.getLogger(LoggingInitialize.class);

    public void initialize()
    {
        SecurityContextHolder.registerSessionListener(new SessionListenerAdapter()
        {

            public void onStart(Session session)
            {
                super.onStart(session);
                logger.debug("创建Session,SessionId=[{}]", session.getId());
            }

            public void onStop(Session session)
            {
                super.onStop(session);
                logger.debug("Session注销,SessionId=[{}]", session.getId());
            }

            public void onExpiration(Session session)
            {
                WebVisitEventPublish.getInstance().logoutEvent(true);
                super.onExpiration(session);
                logger.debug("Session超时,SessionId=[{}]", session.getId());
            }
        });
    }

    public String initLog()
    {
        return "初始化日志模块";
    }

    public void destroy()
    {
        LoggingBufferQueue.INSTANCE.close();
    }

    public String destroyLog()
    {
        return "销毁日志模块";
    }

    public Class<? extends IWebInitializable> setInitDepend()
    {
        return null;
    }

}