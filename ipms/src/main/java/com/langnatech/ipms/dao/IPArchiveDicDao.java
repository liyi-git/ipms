package com.langnatech.ipms.dao;

import java.util.List;
import java.util.Map;

import com.langnatech.ipms.entity.IPArchiveDicEntity;


public interface IPArchiveDicDao
{
    List<IPArchiveDicEntity> selectAllIPArchiveDic();

    List<Map<String,String>>  selectAllArchiveDicCity();

}