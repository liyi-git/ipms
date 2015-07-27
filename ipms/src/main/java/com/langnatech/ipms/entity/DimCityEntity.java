package com.langnatech.ipms.entity;

import com.langnatech.core.base.entity.IEntity;


public class DimCityEntity implements IEntity
{

    private static final long serialVersionUID = 1L;

    private String cityId;

    private String cityName;

    private String cityDesc;

    private int sortIndex;

    private int isValid;

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityDesc()
    {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc)
    {
        this.cityDesc = cityDesc;
    }

    public int getSortIndex()
    {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public boolean isValid()
    {
        return this.isValid == 1;
    }

    public int getIsValid()
    {
        return isValid;
    }

    public void setIsValid(int isValid)
    {
        this.isValid = isValid;
    }

}
