package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;


public enum IPPoolAssignTypeEnum implements BaseEnum<Integer>
{
    Manual(1, "手动分配IP"), AUTO(2, "自动分配子网"), Broadcast(3, "动态分配子网");

    private Integer code;

    private String desc;

    private IPPoolAssignTypeEnum(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

}
