package com.langnatech.ipms.service;

import java.util.List;

import com.langnatech.ipms.entity.DimCityEntity;


public interface DimCityService
{
    public List<DimCityEntity> getAllCity();

    public DimCityEntity getCityByCityId(String cityId);
}
