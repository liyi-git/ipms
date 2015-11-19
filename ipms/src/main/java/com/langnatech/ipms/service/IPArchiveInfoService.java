package com.langnatech.ipms.service;

import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;


public interface IPArchiveInfoService {

  public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId);

  public Integer saveArchiveInfo(IPArchiveInfoEntity ipArchiveInfoEntity);

  public void validateArchiveInfo(ApplyInfoBean applyInfoBean) throws Exception;
  
  public Integer delArchiveInfo(String applyCode);
  
  public IPArchiveInfoEntity getArchiveInfoByApplyCode(String applyCode);
}
