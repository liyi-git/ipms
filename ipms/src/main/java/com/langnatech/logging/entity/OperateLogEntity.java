package com.langnatech.logging.entity;

import com.langnatech.core.base.entity.IEntity;
import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.logging.bean.LoggingBean;


public class OperateLogEntity extends LoggingBean implements IEntity
{

    private static final long serialVersionUID = -5139468024307048809L;

    private String operateType;

    public LoggingTypeEnum getLogType()
    {
        return LoggingTypeEnum.OPERATE_LOG;
    }

    private String operateObj;

    private String objType;

    private String poolId;

    public String getPoolId()
    {
        return poolId;
    }

    public void setPoolId(String poolId)
    {
        this.poolId = poolId;
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getPoolName()
    {
        return poolName;
    }

    public void setPoolName(String poolName)
    {
        this.poolName = poolName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    private String cityId;

    private String poolName;

    private String cityName;

    private String operateCont;

    public String getOperateObj()
    {
        return operateObj;
    }

    public void setOperateObj(String operateObj)
    {
        this.operateObj = operateObj;
    }

    public String getObjType()
    {
        return objType;
    }

    public void setObjType(String objType)
    {
        this.objType = objType;
    }

    private String operateSource;

    public String getOperateType()
    {
        return operateType;
    }

    public void setOperateType(String operateType)
    {
        this.operateType = operateType;
    }

    public String getOperateCont()
    {
        return operateCont;
    }

    public void setOperateCont(String operateCont)
    {
        this.operateCont = operateCont == null ? null : operateCont.trim();
    }

    public String getOperateSource()
    {
        return operateSource;
    }

    public void setOperateSource(String operateSource)
    {
        this.operateSource = operateSource == null ? null : operateSource.trim();
    }
}