package com.langnatech.ipms.service;

import java.util.List;
import java.util.Map;

import com.langnatech.ipms.entity.IPArchiveDicEntity;

public interface IPArchiveDicService
{

    List<IPArchiveDicEntity> getAllIPArchiveDic();

    List<Map<String,String>> getAllArchiveDicCity();
}
