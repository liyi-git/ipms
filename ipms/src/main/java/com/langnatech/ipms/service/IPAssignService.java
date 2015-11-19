package com.langnatech.ipms.service;

import com.langnatech.ipms.webservice.bean.ApplyResultBean;

public interface IPAssignService {

  public ApplyResultBean availableQuery(String poolId, String cityId, Integer ipCount);

  public ApplyResultBean assignIpSubnet(String poolId, String cityId, Integer ipCount) throws Exception;
    
}
