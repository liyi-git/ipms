package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;

public enum OperateWayEnum implements BaseEnum<String>
{
    INNER_INVOKE("1", "内部系统调用"), ESOP_INVOKE("11", "ESOP系统调用"),EMOS_INVOKE("12", "EMOS系统调用");
    private String code;

    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public static String getNameByValue(String code)
    {
        OperateWayEnum[] operateCodeEnum = OperateWayEnum.values();
        for (OperateWayEnum o : operateCodeEnum)
        {
            if (o.code.equals(code))
            {
                return o.desc;
            }
        }
        return null;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    private OperateWayEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
}