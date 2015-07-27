package com.langnatech.logging.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.dao.OperateLogDao;
import com.langnatech.logging.entity.OperateLogEntity;
import com.langnatech.logging.service.ILoggingService;
import com.langnatech.logging.service.OperateLogService;


@Service
public class OperateLogServiceImpl implements OperateLogService, ILoggingService<OperateLogEntity>
{
    @Autowired
    private OperateLogDao operateLogDao;

    @Override
    public void insertLogBatch(Collection<OperateLogEntity> logList)
    {
        operateLogDao.insertBatch(logList);
    }

    @Override
    public void insertLog(OperateLogEntity loggingBean)
    {
        operateLogDao.insert(loggingBean);
    }

    @Override
    public LoggingTypeEnum getLoggingType()
    {
        return LoggingTypeEnum.OPERATE_LOG;
    }

    @Override
    public PageList<OperateLogEntity> getLogsByCond(OperateLogEntity operateLog, PageQuery pageQuery)
    {
        return this.operateLogDao.selectLogsByCond(operateLog, pageQuery);
    }

}
