package com.langnatech.ipms.dao;

import java.util.List;

import com.langnatech.ipms.entity.DimCityEntity;


public interface DimCityDao
{
    List<DimCityEntity> selectAllCity();

    DimCityEntity selectCityById(String cityId);
}
