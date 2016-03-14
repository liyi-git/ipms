package com.langnatech.logging.service;

import java.util.List;
import java.util.Map;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.IPCheckLogEntity;


public interface IPCheckLogService
{
    public PageList<IPCheckLogEntity> getLogsByCond(IPCheckLogEntity ipCheckLog, PageQuery pageQuery);
    
    public List<Map<String,Integer>> selectCountData();

}
