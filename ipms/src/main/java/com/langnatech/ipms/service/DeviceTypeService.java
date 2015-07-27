package com.langnatech.ipms.service;

import java.util.List;

import com.langnatech.ipms.entity.DeviceTypeEntity;

public interface DeviceTypeService
{
   public List<DeviceTypeEntity> getAllDeviceType();
   
   public DeviceTypeEntity getDeviceTypeById(String deviceTypeId);
}
