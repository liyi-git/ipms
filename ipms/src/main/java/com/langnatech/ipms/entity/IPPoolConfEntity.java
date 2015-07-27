package com.langnatech.ipms.entity;

import com.langnatech.core.base.entity.ManagedEntity;


public class IPPoolConfEntity extends ManagedEntity
{

    private static final long serialVersionUID = 3356491041169581180L;

    private String poolId;

    private String poolPid;

    private String poolName;

    private String poolDesc;

    private double warnValve;

    private int isDir;

    private int isPlanCity;

    private int assignType;

    private int sortIndex;

    private int deep;

    public int getDeep()
    {
        return deep;
    }

    public IPPoolConfEntity()
    {
        super();
    }

    public IPPoolConfEntity(String poolId, String poolName)
    {
        super();
        this.poolId = poolId;
        this.poolName = poolName;
    }

    public void setDeep(int deep)
    {
        this.deep = deep;
    }

    public String getPoolId()
    {
        return poolId;
    }

    public void setPoolId(String poolId)
    {
        this.poolId = poolId;
    }

    public String getPoolName()
    {
        return poolName;
    }

    public void setPoolName(String poolName)
    {
        this.poolName = poolName;
    }

    public String getPoolDesc()
    {
        return poolDesc;
    }

    public void setPoolDesc(String poolDesc)
    {
        this.poolDesc = poolDesc;
    }

    public double getWarnValve()
    {
        return warnValve;
    }

    public void setWarnValve(double warnValve)
    {
        this.warnValve = warnValve;
    }

    public boolean isPlanCity()
    {
        return this.getIsPlanCity() != -1;
    }

    public int getIsPlanCity()
    {
        return isPlanCity;
    }

    public void setIsPlanCity(int isPlanCity)
    {
        this.isPlanCity = isPlanCity;
    }

    public boolean isDir()
    {
        return this.getIsDir() != -1;
    }

    public int getIsDir()
    {
        return isDir;
    }

    public void setIsDir(int isDir)
    {
        this.isDir = isDir;
    }

    public int getAssignType()
    {
        return assignType;
    }

    public void setAssignType(int assignType)
    {
        this.assignType = assignType;
    }

    public int getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getPoolPid()
    {
        return poolPid;
    }

    public void setPoolPid(String poolPid)
    {
        this.poolPid = poolPid;
    }

}
