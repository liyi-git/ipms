package com.langnatech.ipms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.langnatech.ipms.entity.DeviceTypeEntity;


public interface DeviceTypeDao
{
    List<DeviceTypeEntity> selectAllDeviceType();

    DeviceTypeEntity selectDeviceTypeById(@Param(value = "deviceTypeId") String deviceTypeId);
}