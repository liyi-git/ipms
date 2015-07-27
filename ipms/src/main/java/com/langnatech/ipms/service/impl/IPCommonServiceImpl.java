package com.langnatech.ipms.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.langnatech.ipms.bean.KeyValueBean;
import com.langnatech.ipms.entity.DimCityEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.service.DimCityService;
import com.langnatech.ipms.service.IPCommonService;
import com.langnatech.ipms.service.IPPoolConfService;


@Service
public class IPCommonServiceImpl implements IPCommonService
{
    @Autowired
    private IPPoolConfService poolConfService;

    @Autowired
    private DimCityService cityService;

    private static final String POOL_ROOT_ID = "-9";

    private static final String POOL_ROOT_NAME = "IP地址资源池";

    public List<KeyValueBean> getHierarchyByPoolId(String poolId)
    {
        List<IPPoolConfEntity> list = this.poolConfService.getAllIPPoolConf();
        Map<String, IPPoolConfEntity> map = Maps.newHashMapWithExpectedSize(list.size());
        for (IPPoolConfEntity ipPoolConfEntity : list)
        {
            map.put(ipPoolConfEntity.getPoolId(), ipPoolConfEntity);
        }
        return Lists.reverse(this.recurGetParentPool(map, poolId));
    }

    public List<KeyValueBean> getHierarchyByCityId(String cityId)
    {
        List<KeyValueBean> resultList = Lists.newArrayList();
        resultList.add(new KeyValueBean(POOL_ROOT_ID, POOL_ROOT_NAME));
        DimCityEntity cityEntity = cityService.getCityByCityId(cityId);
        if (null != cityEntity)
        {
            resultList.add(new KeyValueBean("C_" + cityEntity.getCityId(), cityEntity.getCityName()));
        }
        return resultList;
    }

    public List<KeyValueBean> getHierarchyByPoolCity(String poolId, String cityId)
    {
        List<KeyValueBean> resultList = this.getHierarchyByPoolId(poolId);
        DimCityEntity cityEntity = cityService.getCityByCityId(cityId);
        if (null != cityEntity)
        {
            resultList.add(new KeyValueBean("PC_" + poolId + "-" + cityEntity.getCityId(), cityEntity.getCityName()));
        }
        return resultList;
    }

    /**
     * 根据地址池编码，递归获取父地址池
     * @param map
     * @param poolId
     * @return
     */
    private List<KeyValueBean> recurGetParentPool(Map<String, IPPoolConfEntity> map, String poolId)
    {
        List<KeyValueBean> resultList = Lists.newArrayList();
        if ( !poolId.equals(POOL_ROOT_ID) && !poolId.equals("-1"))
        {
            IPPoolConfEntity poolConf = map.get(poolId);
            if (null != poolConf)
            {
                resultList.add(new KeyValueBean("P_" + poolConf.getPoolId(), poolConf.getPoolName()));
                resultList.addAll(recurGetParentPool(map, poolConf.getPoolPid()));
            }
        }
        else
        {
            resultList.add(new KeyValueBean(POOL_ROOT_ID, POOL_ROOT_NAME));
        }
        return resultList;
    }

    /**
     * 根据地址池id获取该地址池下的所有子地址池数组
     * @param poolId
     * @return
     */
    public String[] getSubPoolIdsByPoolId(String poolId)
    {
        List<IPPoolConfEntity> list = poolConfService.getAllIPPoolConf();
        Multimap<String, String> poolIdMap = ArrayListMultimap.create();
        for (IPPoolConfEntity ipPoolConf : list)
        {
            poolIdMap.put(ipPoolConf.getPoolPid(), ipPoolConf.getPoolId());
        }
        Collection<String> subList = this.recurGetSubPools(poolIdMap, poolId);
        subList.add(poolId);
        return subList.toArray(new String[subList.size()]);
    }

    private Collection<String> recurGetSubPools(Multimap<String, String> map, String pid)
    {
        Collection<String> resultList = Lists.newArrayList();
        Collection<String> subList = map.get(pid);
        if (subList != null)
        {
            for (String id : subList)
            {
                Collection<String> subList2 = this.recurGetSubPools(map, id);
                if (subList2 != null)
                {
                    resultList.addAll(subList2);
                }
            }
            resultList.addAll(subList);
        }
        return resultList;
    }
}
