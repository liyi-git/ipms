package com.langnatech.logging.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.IPCheckLogEntity;


public interface IPCheckLogDao
{

    int insert(@Param(value = "ipCheckLog") IPCheckLogEntity operLog);

    int insertBatch(@Param(value = "loglist") Collection<IPCheckLogEntity> logList);
    
    List<Map<String,Integer>> selectCountData();

    PageList<IPCheckLogEntity> selectLogsByCond(@Param(value = "ipCheckLog") IPCheckLogEntity ipCheckLog, PageQuery pageQuery);
}