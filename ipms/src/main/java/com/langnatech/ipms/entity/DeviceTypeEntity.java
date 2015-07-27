package com.langnatech.ipms.entity;

import com.langnatech.core.base.entity.IEntity;


public class DeviceTypeEntity implements IEntity
{

    private static final long serialVersionUID = 6829430541780986573L;

    private String deviceTypeId;

    private String deviceTypeName;

    private String deviceTypeDesc;

    private Short sortIndex;

    private Short isValid;

    public String getDeviceTypeId()
    {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId)
    {
        this.deviceTypeId = deviceTypeId == null ? null : deviceTypeId.trim();
    }

    public String getDeviceTypeName()
    {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName)
    {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public String getDeviceTypeDesc()
    {
        return deviceTypeDesc;
    }

    public void setDeviceTypeDesc(String deviceTypeDesc)
    {
        this.deviceTypeDesc = deviceTypeDesc == null ? null : deviceTypeDesc.trim();
    }

    public Short getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(Short sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public Short getIsValid()
    {
        return isValid;
    }

    public void setIsValid(Short isValid)
    {
        this.isValid = isValid;
    }
}