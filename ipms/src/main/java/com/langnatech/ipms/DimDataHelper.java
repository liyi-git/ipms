package com.langnatech.ipms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.ipms.entity.DeviceTypeEntity;
import com.langnatech.ipms.entity.DimCityEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.service.DeviceTypeService;
import com.langnatech.ipms.service.DimCityService;
import com.langnatech.ipms.service.IPPoolConfService;


public class DimDataHelper
{

    public static List<IPPoolConfEntity> getIPPool()
    {
        List<IPPoolConfEntity> list = DimDataHelper.getIPPoolConfService().getAllIPPoolConf();
        Collections.sort(list, new Comparator<IPPoolConfEntity>()
        {

            public int compare(IPPoolConfEntity o1, IPPoolConfEntity o2)
            {
                if (o1.getDeep() > o2.getDeep())
                {
                    return 1;
                }
                else if (o1.getDeep() == o2.getDeep())
                {
                    if (o1.getSortIndex() > o2.getSortIndex())
                    {
                        return 1;
                    }
                }
                return 0;
            }
        });
        return list;
    }

    private static IPPoolConfService getIPPoolConfService()
    {
        return SpringContextHolder.getBean(IPPoolConfService.class);
    }

    private static DimCityService getDimCityService()
    {
        return SpringContextHolder.getBean(DimCityService.class);
    }

    private static DeviceTypeService getDeviceTypeService()
    {
        return SpringContextHolder.getBean(DeviceTypeService.class);
    }

    public static List<DeviceTypeEntity> getDeviceType()
    {
        List<DeviceTypeEntity> list = DimDataHelper.getDeviceTypeService().getAllDeviceType();
        Collections.sort(list, new Comparator<DeviceTypeEntity>()
        {

            public int compare(DeviceTypeEntity o1, DeviceTypeEntity o2)
            {

                if (o1.getSortIndex() > o2.getSortIndex())
                {
                    return 1;
                }
                return 0;
            }
        });
        return list;
    }

    public static List<DimCityEntity> getCity()
    {
        List<DimCityEntity> list = DimDataHelper.getDimCityService().getAllCity();
        Collections.sort(list, new Comparator<DimCityEntity>()
        {

            public int compare(DimCityEntity o1, DimCityEntity o2)
            {

                if (o1.getSortIndex() > o2.getSortIndex())
                {
                    return 1;
                }
                return 0;
            }
        });
        return list;
    }

    public static List<Map<String, Object>> getNetMask()
    {
        List<Map<String, Object>> netMaskList = new ArrayList<Map<String, Object>>();
        Map<String, Object> netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "-9");
        netMaskMap.put("value", "请选择子网掩码");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "8");
        netMaskMap.put("value", "255.0.0.0/8 主机数：16777214");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "9");
        netMaskMap.put("value", "255.128.0.0/9 主机数：8388606");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "10");
        netMaskMap.put("value", "255.192.0.0/10 主机数：4194302");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "11");
        netMaskMap.put("value", "255.224.0.0/11 主机数：2097150");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "12");
        netMaskMap.put("value", "255.240.0.0/12 主机数：1048574");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "13");
        netMaskMap.put("value", "255.248.0.0/13 主机数：524286");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "14");
        netMaskMap.put("value", "255.252.0.0/14 主机数：262142");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "15");
        netMaskMap.put("value", "255.254.0.0/15 主机数：131070");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "16");
        netMaskMap.put("value", "255.255.0.0/16 主机数：65534");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "17");
        netMaskMap.put("value", "255.255.128.0/17 主机数：32766");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "18");
        netMaskMap.put("value", "255.255.192.0/18 主机数：16382");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "19");
        netMaskMap.put("value", "255.255.224.0/19 主机数：8190");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "20");
        netMaskMap.put("value", "255.255.240.0/20 主机数：4094");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "21");
        netMaskMap.put("value", "255.255.248.0/21 主机数：2046");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "22");
        netMaskMap.put("value", "255.255.252.0/22 主机数：1022");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "23");
        netMaskMap.put("value", "255.255.254.0/23 主机数：510");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "24");
        netMaskMap.put("value", "255.255.255.0/24 主机数：254");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "25");
        netMaskMap.put("value", "255.255.255.128/25 主机数：126");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "26");
        netMaskMap.put("value", "255.255.255.192/26 主机数：62");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "27");
        netMaskMap.put("value", "255.255.255.224/27 主机数：30");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "28");
        netMaskMap.put("value", "255.255.255.240/28 主机数：14");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "29");
        netMaskMap.put("value", "255.255.255.248/29 主机数：6");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "30");
        netMaskMap.put("value", "255.255.255.252/30 主机数：2");
        netMaskList.add(netMaskMap);
        netMaskMap = new HashMap<String, Object>();
        netMaskMap.put("id", "31");
        netMaskMap.put("value", "255.255.255.254/31 主机数：2");
        netMaskList.add(netMaskMap);
        return netMaskList;
    }
}
