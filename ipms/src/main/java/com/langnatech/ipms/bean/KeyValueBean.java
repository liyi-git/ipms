package com.langnatech.ipms.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class KeyValueBean implements Serializable
{

    private static final long serialVersionUID = 2679231788318056297L;

    private String key;

    private Object value;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public KeyValueBean(String key, Object value)
    {
        super();
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
