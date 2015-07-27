package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;


public enum OperateTypeEnum implements BaseEnum<String>
{
    UNKNOW("UNKNOW", "未知"), ADD_SUBNET("ADD_SUBNET", "注入网段"), SPLIT_SUBNET("SPLIT_SUBNET", "拆分网段"), ASSIGN_SUBNET("ASSIGN_SUBNET",
        "分配网段"), RECYCLE_SUBNET("RECYCLE_SUBNET", "回收网段"), REGISTER_ADDRESS("REGISTER_ADDRESS", "使用地址"), RESERVE_ADDRESS("RESERVE_SUBNET", "预留地址");
    private String code;

    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public static String getNameByValue(String code)
    {
        OperateTypeEnum[] operateCodeEnum = OperateTypeEnum.values();
        for (OperateTypeEnum o : operateCodeEnum)
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

    private OperateTypeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
}