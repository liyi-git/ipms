package com.langnatech.ipms.service;

import java.util.List;

import com.langnatech.ipms.entity.IPPoolConfEntity;


public interface IPPoolConfService
{
    public List<IPPoolConfEntity> getAllIPPoolConf();

    public IPPoolConfEntity getIPPoolConfByPoolId(String poolId);

}
