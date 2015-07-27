package com.langnatech.ipms.service;

import com.langnatech.ipms.entity.IPArchiveInfoEntity;


public interface IPArchiveInfoService
{
    public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId);

}
