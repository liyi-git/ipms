package com.langnatech.logging.service;

import java.util.Collection;

import com.langnatech.core.enums.LoggingTypeEnum;


public interface ILoggingService<LoggingBean>
{

    public void insertLogBatch(Collection<LoggingBean> logList);

    public void insertLog(LoggingBean loggingBean);

    public LoggingTypeEnum getLoggingType();
}