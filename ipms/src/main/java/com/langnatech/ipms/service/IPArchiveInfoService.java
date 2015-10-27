package com.langnatech.ipms.service;

import com.langnatech.ipms.entity.IPArchiveInfoEntity;


public interface IPArchiveInfoService
{
    public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId);
    
    //对应申请接口(反馈数据)
    public String applyAdd(String invokJSON);
    //对应取消申请接口
    public String cancleAppalyAdd(String invokJSON);
    //对应确认开通接口
    public String appalyUseAdd(String invokJSON);
    //对应回收接口
    public String appalyRecycleAdd(String invokJSON);

}
