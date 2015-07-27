package com.langnatech.ipms.dao;

import org.apache.ibatis.annotations.Param;

import com.langnatech.ipms.entity.IPArchiveInfoEntity;


public interface IPArchiveInfoDao
{

    IPArchiveInfoEntity selectBySubNetId(@Param(value = "subnetId") String subnetId);

    int deleteByPrimaryKey(String archiveId);

    int insert(IPArchiveInfoEntity record);

    int insertSelective(IPArchiveInfoEntity record);

    IPArchiveInfoEntity selectByPrimaryKey(String archiveId);

    int updateByPrimaryKeySelective(IPArchiveInfoEntity record);

    int updateByPrimaryKey(IPArchiveInfoEntity record);
}