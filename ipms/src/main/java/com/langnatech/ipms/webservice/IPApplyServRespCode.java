package com.langnatech.ipms.webservice;

public enum IPApplyServRespCode {
  Success(1, "接口调用成功"),
  Authc_Failure(101, "接口调用鉴权失败"),
  Parameter_Error(102, "接口调用，传入参数不正确"),
  No_Available_IP(111, "IP可用资源不足"),
  Validate_Error(112, "IP申请备案信息校验不正确"),
  ApplyCode_Duplicate(113, "IP申请单号在IP地址管理系统中已存在"),
  ApplyCode_Not_Exist(114, "IP申请单号在IP管理系统中不存在"),
  ApplyCode_Cannot_Cancel(115, "已开通的IP申请不能被取消"),
  Invoke_Error(-1, "接口调用异常");

  private Integer code;
  private String desc;

  IPApplyServRespCode(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
