package com.langnatech.ipms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.DimCityDao;
import com.langnatech.ipms.entity.DimCityEntity;
import com.langnatech.ipms.service.DimCityService;


@Service
public class DimCityServiceImpl implements DimCityService
{

    @Autowired
    private DimCityDao dimCityDao;

    public List<DimCityEntity> getAllCity()
    {
        return dimCityDao.selectAllCity();
    }

    public DimCityEntity getCityByCityId(String cityId){
        return dimCityDao.selectCityById(cityId);
    }
}
