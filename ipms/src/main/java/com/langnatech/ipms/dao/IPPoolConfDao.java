package com.langnatech.ipms.dao;

import java.util.List;

import com.langnatech.ipms.entity.IPPoolConfEntity;


public interface IPPoolConfDao
{
    List<IPPoolConfEntity> selectAllPoolConf();

    IPPoolConfEntity selectPoolConfByPoolId();
}
