package com.langnatech.ipms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface IPStatisticDao
{
    List<Map<String, Object>> statIPPlanStatusForPool(@Param(value = "poolId") String poolId, @Param(value = "cityId") String cityId);

    List<Map<String, Object>> statIPUseStatusForPool(@Param(value = "poolId") String poolId, @Param(value = "cityId") String cityId);

}
