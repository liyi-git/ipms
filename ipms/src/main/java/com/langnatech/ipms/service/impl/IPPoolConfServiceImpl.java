package com.langnatech.ipms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPPoolConfDao;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.service.IPPoolConfService;


@Service
public class IPPoolConfServiceImpl implements IPPoolConfService
{
    @Autowired
    private IPPoolConfDao ipPoolConfDao;

    public List<IPPoolConfEntity> getAllIPPoolConf()
    {
        return ipPoolConfDao.selectAllPoolConf();
    }

    public IPPoolConfEntity getIPPoolConfByPoolId(String poolId)
    {
        return ipPoolConfDao.selectPoolConfByPoolId(poolId);
    }

}
