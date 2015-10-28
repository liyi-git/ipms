package com.langnatech.ipms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPArchiveInfoDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.service.IPArchiveInfoService;

@Service
public class IPArchiveInfoServiceImpl implements IPArchiveInfoService
{

    @Autowired
    private IPArchiveInfoDao archiveInfoDao;
    
    
    public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId) {
      return archiveInfoDao.selectBySubNetId(subnetId);
    }

    @Override
    public Integer saveArchiveInfo(IPArchiveInfoEntity ipArchiveInfoEntity) {
      return archiveInfoDao.insert(ipArchiveInfoEntity);
    }


}
