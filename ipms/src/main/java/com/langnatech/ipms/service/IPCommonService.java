package com.langnatech.ipms.service;

import java.util.List;

import com.langnatech.ipms.bean.KeyValueBean;


public interface IPCommonService
{

    public List<KeyValueBean> getHierarchyByPoolId(String poolId);

    public List<KeyValueBean> getHierarchyByCityId(String cityId);

    public List<KeyValueBean> getHierarchyByPoolCity(String poolId, String cityId);
    
    public String[] getSubPoolIdsByPoolId(String poolId);

}
