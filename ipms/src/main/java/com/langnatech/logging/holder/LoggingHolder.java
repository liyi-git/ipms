/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.logging.holder;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.logging.bean.LoggingBean;
import com.langnatech.logging.pool.LoggingBufferQueue;
import com.langnatech.logging.service.ILoggingService;


/**
 * @author zangchuqiang
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class LoggingHolder
{

    private static Map<LoggingTypeEnum, ILoggingService<LoggingBean>> loggingServiceMap = null;

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Map<LoggingTypeEnum, ILoggingService<LoggingBean>> getLoggingServiceMap()
    {
        if (loggingServiceMap == null)
        {
            Map<String, ILoggingService> beanMap = SpringContextHolder.getBeansOfType(ILoggingService.class);
            loggingServiceMap = Maps.newHashMapWithExpectedSize(beanMap.size());
            for (ILoggingService<LoggingBean> loggingService : beanMap.values())
            {
                if (loggingService.getLoggingType() != null)
                {
                    loggingServiceMap.put(loggingService.getLoggingType(), loggingService);
                }
            }
        }
        return loggingServiceMap;
    }

    public static void addLog(LoggingBean log)
    {
        LoggingBufferQueue.INSTANCE.add(log);
    }

    public static ILoggingService<LoggingBean> getLoggingService(LoggingTypeEnum loggingType)
    {
        Map<LoggingTypeEnum, ILoggingService<LoggingBean>> loggingServiceMap = getLoggingServiceMap();
        if (loggingType != null && !CollectionUtils.isEmpty(loggingServiceMap))
        {
            return loggingServiceMap.get(loggingType);
        }
        return null;
    }
}