package com.langnatech.ipms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IPPoolDao
{
    List<Map<String,String>> selectIPPoolTreeByCity();
    
    List<Map<String,String>> selectIPPoolTreeByPool();
    
    List<Map<String,Object>> selectSubPoolStatByPool(@Param("poolId") String poolId);
    
    List<Map<String,Object>> selectCitySubPoolStatByPool(@Param("poolId") String poolId);
    
    List<Map<String,Object>> selectSubnetStatByPool(@Param("poolId") String poolId,@Param("cityId") String cityId);
    
    List<Map<String,Object>> selectPoolStatByCity(@Param("cityId") String cityId);
    
}
