package com.langnatech.ipms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.IPArchiveDicDao;
import com.langnatech.ipms.entity.IPArchiveDicEntity;
import com.langnatech.ipms.service.IPArchiveDicService;


@Service
public class IPArchiveDicServiceImpl implements IPArchiveDicService
{
    @Autowired
    private IPArchiveDicDao ipArchiveDicDao;

    public List<IPArchiveDicEntity> getAllIPArchiveDic()
    {
        return ipArchiveDicDao.selectAllIPArchiveDic();
    }

    public List<Map<String,String>>  getAllArchiveDicCity()
    {
        return ipArchiveDicDao.selectAllArchiveDicCity();
    }

}
