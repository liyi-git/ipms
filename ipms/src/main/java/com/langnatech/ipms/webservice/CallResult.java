package com.langnatech.ipms.webservice;

public class CallResult {
	private Integer code;
	private String msg;
	private String resultJSON;

	public Integer getCode() {
		return code;
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
