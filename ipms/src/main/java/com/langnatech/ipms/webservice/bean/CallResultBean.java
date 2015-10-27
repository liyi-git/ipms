package com.langnatech.ipms.webservice.bean;

import java.io.Serializable;

public class CallResultBean implements Serializable {
  private static final long serialVersionUID = 8756030908440414028L;
  private Integer code;
  private String msg;
  private String resultJSON;

  public Integer getCode() {
    return code;
  }

  public CallResultBean() {
    super();
  }

  public CallResultBean(Integer code, String msg) {
    super();
    this.code = code;
    this.msg = msg;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getResultJSON() {
    return resultJSON;
  }

  public void setResultJSON(String resultJSON) {
    this.resultJSON = resultJSON;
  }

}
