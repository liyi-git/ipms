package com.langnatech.ipms.service;

import java.util.List;
import java.util.Map;


public interface IPStatisticService
{

    /**
     * 按网段状态统计IP规划情况
     * @return
     */
    public List<Map<String, Object>> statIPPlanStatusForPool(String poolId, String cityId);

    /**
     * 按网段状态统计IP使用情况
     * @return
     */
    public List<Map<String, Object>> statIPUseStatusForPool(String poolId, String cityId);

}
