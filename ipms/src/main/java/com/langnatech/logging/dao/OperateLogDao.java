package com.langnatech.logging.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.OperateLogEntity;


public interface OperateLogDao
{

    int insert(@Param(value = "operLog") OperateLogEntity operLog);

    int insertBatch(@Param(value = "loglist") Collection<OperateLogEntity> logList);

    PageList<OperateLogEntity> selectLogsByCond(@Param(value = "operLog") OperateLogEntity operateLog, PageQuery pageQuery);
}