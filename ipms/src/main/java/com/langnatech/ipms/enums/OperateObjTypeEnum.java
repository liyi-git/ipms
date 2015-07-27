package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;

public enum OperateObjTypeEnum implements BaseEnum<String>
{
    POOL("POOL", "地址池"), SUBNET("SUBNET", "地址段"), ADDRESS("ADDRESS", "地址");

    private String code;

    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public static String getNameByValue(String code)
    {
        OperateObjTypeEnum[] operateCodeEnum = OperateObjTypeEnum.values();
        for (OperateObjTypeEnum o : operateCodeEnum)
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

    private OperateObjTypeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
}