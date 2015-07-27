package com.langnatech.ipms.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface IPPoolService
{
    public List<Map<String, String>> getIPPoolTreeByCity();

    public List<Map<String, String>> getIPPoolTreeByPool();

    public List<Map<String, Object>> getCitySubPoolStatByPool(String poolId);

    public List<Map<String, Object>> getSubPoolStatByPool(String poolId);
    
    public List<Map<String, Object>> getSubnetStatByPool(String poolId);

    public List<Map<String, Object>> getSubnetStatByPool(String poolId,String cityId);
    
    public Map<String, Object> statPoolAggregate(List<Map<String, Object>> list,String idField);
    
    public List<Map<String,Object>> getPoolStatByCity(@Param("cityId") String cityId);

}