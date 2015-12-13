package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;


public enum OperateTypeEnum implements
  BaseEnum<String> {
    UNKNOW("UNKNOW", "未知"),
    ADD_SUBNET("ADD_SUBNET", "注入网段"),
    SPLIT_SUBNET("SPLIT_SUBNET", "拆分网段"),
    ASSIGN_SUBNET("ASSIGN_SUBNET", "分配网段"),
    RECYCLE_SUBNET("RECYCLE_SUBNET", "申请回收"),
    REGISTER_ADDRESS("REGISTER_ADDRESS", "申请开通"),
    RESERVE_ADDRESS("RESERVE_SUBNET", "申请预留"),
    CANCEL_RESERVE("CANCEL_RESERVE", "取消预留");
  private String code;

  private String desc;

  public String getDesc() {
    return desc;
  }

  public static String getNameByValue(String code) {
    OperateTypeEnum[] operateCodeEnum = OperateTypeEnum.values();
    for (OperateTypeEnum o : operateCodeEnum) {
      if (o.code.equals(code)) {
        return o.desc;
      }
    }
    return null;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  private OperateTypeEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
