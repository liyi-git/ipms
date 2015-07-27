package com.langnatech.ipms.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPPoolDao;
import com.langnatech.ipms.service.IPPoolService;


@Service
public class IPPoolServiceImpl implements IPPoolService
{
    @Autowired
    private IPPoolDao ipPoolDao;

    public List<Map<String, String>> getIPPoolTreeByPool()
    {
        return ipPoolDao.selectIPPoolTreeByPool();
    }

    public List<Map<String, String>> getIPPoolTreeByCity()
    {
        return ipPoolDao.selectIPPoolTreeByCity();
    }


    public Map<String, Object> statPoolAggregate(List<Map<String, Object>> list,String idField)
    {
        Map<String, Object> resultMap=null;
        if(list!=null){
            resultMap=new HashMap<String,Object>(1);
            int ipCount=0,keepCount=0,useCount=0,waitCount=0;
            for(Map<String,Object> map:list){
                ipCount+=((BigDecimal)map.get("IP_COUNT")).intValue();
                keepCount+=((BigDecimal)map.get("KEEP_COUNT")).intValue();
                useCount+=((BigDecimal)map.get("USE_COUNT")).intValue();
                if(map.get(idField.toUpperCase()).toString().startsWith("-")){
                    waitCount+=((BigDecimal)map.get("IP_COUNT")).intValue();
                }
            }
            resultMap.put("IP_COUNT", ipCount);
            resultMap.put("KEEP_COUNT", keepCount);
            resultMap.put("USE_COUNT", useCount);
            resultMap.put("WAIT_COUNT", waitCount);
            resultMap.put("FREE_COUNT", ipCount-useCount);
            double percent=(ipCount==0)?0:(useCount/ipCount);
            resultMap.put("USE_PERCENT",DecimalFormat.getPercentInstance().format(percent));
        }
        return resultMap;
    }
    /**
     * 查询某个地址池下的子地址池统计信息
     */
    public List<Map<String, Object>> getSubPoolStatByPool(String poolId)
    {
        return ipPoolDao.selectSubPoolStatByPool(poolId);
    }
    /**
     * 查询某个地址池下的地市子池统计列表
     */
    public List<Map<String, Object>> getCitySubPoolStatByPool(String poolId)
    {
        return ipPoolDao.selectCitySubPoolStatByPool(poolId);
    }
    /**
     * 查询某个地址池下的网段统计列表
     */
    public List<Map<String, Object>> getSubnetStatByPool(String poolId)
    {
        return ipPoolDao.selectSubnetStatByPool(poolId,null);
    }
    /**
     * 查询地市地址池下规划的网段统计列表
     */
    public List<Map<String, Object>> getSubnetStatByPool(String poolId,String cityId)
    {
        return ipPoolDao.selectSubnetStatByPool(poolId,cityId);
    }

    /**
     * 查询某个地市下的地址池统计列表
     */
    public List<Map<String, Object>> getPoolStatByCity(String cityId)
    {
        return ipPoolDao.selectPoolStatByCity(cityId);
    }   
    
}
