package com.langnatech.ipms.entity;

import com.langnatech.core.base.entity.IEntity;

public class IPArchiveDicEntity implements IEntity{

    
    private static final long serialVersionUID = 3550063603800569989L;

    private String dicName;

    
    
    private String dicCode;

    public String getDicCode()
    {
        return dicCode;
    }

    public void setDicCode(String dicCode)
    {
        this.dicCode = dicCode;
    }

    public String getDicGroup()
    {
        return dicGroup;
    }

    public void setDicGroup(String dicGroup)
    {
        this.dicGroup = dicGroup;
    }

    
    private String dicGroup;    
    public String getDicName() {
        return dicName;
    }

    
    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }
}