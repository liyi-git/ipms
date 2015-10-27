package com.langnatech.ipms.service.impl;

import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langnatech.ipms.dao.IPArchiveInfoDao;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.util.IpUtils;
import com.langnatech.util.convert.JsonConvertUtil;

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
