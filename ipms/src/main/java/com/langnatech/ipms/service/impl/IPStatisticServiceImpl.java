package com.langnatech.ipms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPStatisticDao;
import com.langnatech.ipms.service.IPStatisticService;


@Service
public class IPStatisticServiceImpl implements IPStatisticService
{
    @Autowired
    private IPStatisticDao statisticDao;

    @Override
    public List<Map<String, Object>> statIPPlanStatusForPool(String poolId, String cityId)
    {
        return statisticDao.statIPPlanStatusForPool(poolId, cityId);
    }

    @Override
    public List<Map<String, Object>> statIPUseStatusForPool(String poolId, String cityId)
    {
        return statisticDao.statIPUseStatusForPool(poolId, cityId);
    }
}
