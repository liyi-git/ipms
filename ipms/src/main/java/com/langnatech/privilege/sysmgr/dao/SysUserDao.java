package com.langnatech.privilege.sysmgr.dao;

import org.apache.ibatis.annotations.Param;

import com.langnatech.privilege.sysmgr.entity.SysUserEntity;


public interface SysUserDao
{
    int insert(SysUserEntity user);
    
    int update(SysUserEntity user);

    SysUserEntity selectUserById(@Param(value = "userId") String userId);
}