package com.langnatech.ipms.holder;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.ipms.entity.IPArchiveDicEntity;
import com.langnatech.ipms.service.IPArchiveDicService;


public enum IPArchiveDicHolder
{
    INSTANCE
    {
        private void initDic()
        {
            if (dicTable == null)
            {
                List<IPArchiveDicEntity> dicList = getIpArchiveDicService().getAllIPArchiveDic();
                dicTable = TreeBasedTable.create();
                for (IPArchiveDicEntity dic : dicList)
                {
                    dicTable.put(dic.getDicGroup(), dic.getDicCode(), dic.getDicName());

                }
            }
        }

        private Table<String, String, String> getDicTable()
        {
            if (dicTable == null)
            {
                this.initDic();
            }
            return dicTable;
        }

        private void initCityDic()
        {
            if (cityDicTable == null)
            {
                List<Map<String, String>> cityList = getIpArchiveDicService().getAllArchiveDicCity();
                cityDicTable = TreeBasedTable.create();
                for (Map<String, String> map : cityList)
                {
                    cityDicTable.put(map.get("PID"), map.get("ID"), map.get("TXT"));
                }
            }
        }

        private Table<String, String, String> getCityDicTable()
        {
            if (cityDicTable == null)
            {
                this.initCityDic();
            }
            return cityDicTable;
        }

        public Map<String, String> getDicMapByGroup(String groupId)
        {
            return this.getDicTable().row(groupId);
        }

        public String getNameByCode(String groupId, String id)
        {
            return this.getDicTable().get(groupId, id);
        }

        public boolean existsDic(String groupId, String id)
        {
            return this.getDicTable().contains(groupId, id);
        }

        public Map<String, String> getAllProv()
        {
            return this.getCityDicTable().row("-1");
        }

        public Map<String, String> getCitysByPid(String cityPid)
        {
            return this.getCityDicTable().row(cityPid);
        }

        public boolean existsCity(String cityId)
        {
            return this.getCityDicTable().containsColumn(cityId);
        }

        public Map<String, Map<String, String>> getAllCity()
        {
            Map<String, Map<String, String>> resultMap = Maps.newTreeMap();
            Map<String, String> provMap = this.getAllProv();
            resultMap.put("-1", provMap);
            for (Map.Entry<String, String> prov : provMap.entrySet())
            {
                Map<String, String> cityMap = this.getCitysByPid(prov.getKey());
                resultMap.put(prov.getKey(), cityMap);
                for (Map.Entry<String, String> city : cityMap.entrySet())
                {
                    resultMap.put(city.getKey(), this.getCitysByPid(city.getKey()));
                }
            }
            return resultMap;
        }

        public String getNameByCityId(String pid, String cityId)
        {
            return this.getCityDicTable().get(pid, cityId);
        }

    };

    private static Table<String, String, String> dicTable = null;

    private static Table<String, String, String> cityDicTable = null;

    protected static IPArchiveDicService getIpArchiveDicService()
    {
        return SpringContextHolder.getBean(IPArchiveDicService.class);
    }

    public abstract Map<String, String> getDicMapByGroup(String groupId);

    public abstract String getNameByCode(String groupId, String id);

    public abstract boolean existsDic(String groupId, String id);

    public abstract Map<String, String> getAllProv();

    public abstract Map<String, String> getCitysByPid(String cityPid);

    public abstract String getNameByCityId(String pid, String cityId);

    public abstract boolean existsCity(String cityId);

    public abstract Map<String, Map<String, String>> getAllCity();

    public static Map<String, String> getDic(String groupId)
    {
        return INSTANCE.getDicMapByGroup(groupId);
    }

    public static Map<String, String> getProvs()
    {
        return INSTANCE.getAllProv();
    }

    public static String getNameByDicCode(String groupId, String dicCode)
    {
        return INSTANCE.getNameByCode(groupId, dicCode);
    }

    public static String getCityNameById(String pid,String cityId)
    {
        return INSTANCE.getNameByCityId(pid,cityId);
    }
}