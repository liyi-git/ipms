package com.langnatech.ipms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPArchiveInfoDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;

@Service
public class IPArchiveInfoServiceImpl implements IPArchiveInfoService {

  @Autowired
  private IPArchiveInfoDao archiveInfoDao;


  public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId) {
    return archiveInfoDao.selectBySubNetId(subnetId);
  }

  public Integer saveArchiveInfo(IPArchiveInfoEntity ipArchiveInfoEntity) {
    return archiveInfoDao.insert(ipArchiveInfoEntity);
  }

  // TODO 申请备案信息校验
  public void validateArchiveInfo(ApplyInfoBean applyInfoBean) throws Exception {
    
  }

  public Integer delArchiveInfo(String applyCode) {
    return archiveInfoDao.deleteByApplyId(applyCode);
  }

  public IPArchiveInfoEntity getArchiveInfoByApplyCode(String applyCode) {
    return archiveInfoDao.selectByApplyId(applyCode);
  }
}
