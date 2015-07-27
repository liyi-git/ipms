package com.langnatech.logging.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import com.langnatech.logging.entity.LoginLogEntity;


public interface LoginLogDao
{
    int insert(@Param(value = "loginlog") LoginLogEntity loginLog);

    int insertBatch(@Param(value = "loglist") Collection<LoginLogEntity> logList);
}