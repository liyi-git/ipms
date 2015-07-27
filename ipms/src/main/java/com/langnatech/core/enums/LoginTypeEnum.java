package com.langnatech.core.enums;
/**
 * @Title:LoginTypeEnum
 * @Description: 这里用一句话描述这个类的作用
 * @author liyi
 * @date Dec 21, 2013 5:00:52 PM
 */
public enum LoginTypeEnum implements BaseEnum<Integer>{
    LOGIN(1, "登录"), LOGOUT( -1, "注销"), TIMEOUT( -2, "超时退出");

    private Integer code;

    private String desc;

    LoginTypeEnum(Integer code, String desc) {
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