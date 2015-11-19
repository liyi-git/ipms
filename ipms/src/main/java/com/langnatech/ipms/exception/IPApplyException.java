package com.langnatech.ipms.exception;

import com.langnatech.ipms.webservice.IPApplyServRespCode;

@SuppressWarnings("serial")
public class IPApplyException extends Exception {
  private Integer errCode;
  private String errDesc;

  public Integer getErrCode() {

    return errCode;
  }

  public void setErrCode(Integer errCode) {
    this.errCode = errCode;
  }

  public String getErrDesc() {
    return errDesc;
  }

  public void setErrDesc(String errDesc) {
    this.errDesc = errDesc;
  }

  public IPApplyException(IPApplyServRespCode servRespCode) {
    super("错误代码：" + servRespCode.getCode() + "   错误信息：" + servRespCode.getDesc());
    this.errCode=servRespCode.getCode();
    this.errDesc=servRespCode.getDesc();    
  }

  public IPApplyException(Integer errCode, String errDesc) {
    super("错误代码：" + errCode + "   错误信息：" + errDesc);
    this.errCode=errCode;
    this.errDesc=errDesc;
  }

  public IPApplyException(String message, Throwable cause) {
    super(message, cause);
  }

  public IPApplyException(String message) {
    super(message);
  }

}
