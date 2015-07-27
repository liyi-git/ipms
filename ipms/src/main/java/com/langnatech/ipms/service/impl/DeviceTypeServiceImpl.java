package com.langnatech.ipms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.ipms.dao.DeviceTypeDao;
import com.langnatech.ipms.entity.DeviceTypeEntity;
import com.langnatech.ipms.service.DeviceTypeService;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService
{
    @Autowired
    private DeviceTypeDao deviceTypeDao;

    @Override
    public List<DeviceTypeEntity> getAllDeviceType()
    {
        return deviceTypeDao.selectAllDeviceType();
    }

    @Override
    public DeviceTypeEntity getDeviceTypeById(String deviceTypeId)
    {
        return deviceTypeDao.selectDeviceTypeById(deviceTypeId);
    }

}
