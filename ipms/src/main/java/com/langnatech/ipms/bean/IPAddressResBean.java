package com.langnatech.ipms.bean;

import com.langnatech.ipms.entity.IPAddressResEntity;


public class IPAddressResBean extends IPAddressResEntity
{
    private static final long serialVersionUID = 8535075675366425237L;

    private String netmask;

    private String subnetDesc;

    private String deviceTypeName;


    public String getDeviceTypeName()
    {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName)
    {
        this.deviceTypeName = deviceTypeName;
    }

    public String getNetmask()
    {
        return netmask;
    }

    public void setNetmask(String netmask)
    {
        this.netmask = netmask;
    }

    public String getSubnetDesc()
    {
        return subnetDesc;
    }

    public void setSubnetDesc(String subnetDesc)
    {
        this.subnetDesc = subnetDesc;
    }

}
