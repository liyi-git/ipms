package com.langnatech.logging.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.IPCheckLogEntity;
import com.langnatech.logging.entity.OperateLogEntity;


public interface IPCheckLogDao
{

    int insert(@Param(value = "ipCheckLog") IPCheckLogEntity operLog);

    int insertBatch(@Param(value = "loglist") Collection<IPCheckLogEntity> logList);

    PageList<IPCheckLogEntity> selectLogsByCond(@Param(value = "ipCheckLog") IPCheckLogEntity ipCheckLog, PageQuery pageQuery);
}