package com.langnatech.logging.service;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.OperateLogEntity;


public interface OperateLogService
{
    public PageList<OperateLogEntity> getLogsByCond(OperateLogEntity operateLog, PageQuery pageQuery);

}
