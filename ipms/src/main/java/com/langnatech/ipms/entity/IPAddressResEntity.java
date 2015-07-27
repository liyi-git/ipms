package com.langnatech.ipms.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.langnatech.core.base.entity.IEntity;


public class IPAddressResEntity implements IEntity
{

    private static final long serialVersionUID = 7665428942916155139L;

    private String addressId;

    private String addressIp;

    private String subnetId;

    private Integer addressStatus;

    private Integer addressType;

    private Integer isIpv6;

    private Integer deviceType;

    public IPAddressResEntity(String addressId, String addressIp, String subnetId, Integer addressStatus, Integer addressType, Integer isIpv6)
    {
        super();
        this.addressId = addressId;
        this.addressIp = addressIp;
        this.subnetId = subnetId;
        this.addressStatus = addressStatus;
        this.addressType = addressType;
    }

    public IPAddressResEntity()
    {
        super();
    }

    private String addressUsage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")    
    private Date expireDate;

    private String deviceModel;

    private String devicePosition;

    private String usePort;

    private String operator;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;

    public String getAddressId()
    {
        return addressId;
    }

    public void setAddressId(String addressId)
    {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    public String getAddressIp()
    {
        return addressIp;
    }

    public void setAddressIp(String addressIp)
    {
        this.addressIp = addressIp == null ? null : addressIp.trim();
    }

    public String getSubnetId()
    {
        return subnetId;
    }

    public void setSubnetId(String subnetId)
    {
        this.subnetId = subnetId == null ? null : subnetId.trim();
    }

    public Integer getAddressStatus()
    {
        return addressStatus;
    }

    public void setAddressStatus(Integer addressStatus)
    {
        this.addressStatus = addressStatus;
    }

    public Integer getAddressType()
    {
        return addressType;
    }

    public void setAddressType(Integer addressType)
    {
        this.addressType = addressType;
    }

    public Integer getIsIpv6()
    {
        return isIpv6;
    }

    public void setIsIpv6(Integer isIpv6)
    {
        this.isIpv6 = isIpv6;
    }

    public Integer getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getAddressUsage()
    {
        return addressUsage;
    }

    public void setAddressUsage(String addressUsage)
    {
        this.addressUsage = addressUsage == null ? null : addressUsage.trim();
    }

    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    public String getDeviceModel()
    {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel)
    {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getDevicePosition()
    {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition)
    {
        this.devicePosition = devicePosition == null ? null : devicePosition.trim();
    }

    public String getUsePort()
    {
        return usePort;
    }

    public void setUsePort(String usePort)
    {
        this.usePort = usePort == null ? null : usePort.trim();
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
}