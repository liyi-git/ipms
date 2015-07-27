package com.langnatech.logging.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.logging.dao.LoginLogDao;
import com.langnatech.logging.entity.LoginLogEntity;
import com.langnatech.logging.service.ILoggingService;
import com.langnatech.logging.service.LoginLogService;


@Service
public class LoginLogServiceImpl implements LoginLogService, ILoggingService<LoginLogEntity>
{
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public void insertLogBatch(Collection<LoginLogEntity> logList)
    {
        loginLogDao.insertBatch(logList);
    }

    @Override
    public void insertLog(LoginLogEntity loggingBean)
    {
        this.loginLogDao.insert(loggingBean);
    }

    @Override
    public LoggingTypeEnum getLoggingType()
    {
        return LoggingTypeEnum.LOGIN_LOG;
    }

}
