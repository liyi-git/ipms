package com.langnatech.ipms.enums;

import com.langnatech.core.enums.BaseEnum;

public enum MonitorTypeEnum implements BaseEnum<Integer> {
	NOEXIST(1, "地址池中不存在"), MISMATCHING(2, "配置信息不匹配"), NODETECTED(3, "地址已使用，但未监测到"),ILLEGAL(-1, "");
	private Integer code;

	private String desc;

	private MonitorTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
