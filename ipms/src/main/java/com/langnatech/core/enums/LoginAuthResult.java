/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.enums;

/**
 * 登录验证结果枚举类（登录结果描述信息可从属性文件中获取，实现国际化）
 */
public enum LoginAuthResult implements BaseEnum<Integer>
{
    LOGIN_SUCCESS(100, "登录成功"), // 登录成功
    LOGIN_FAIL_USER_NOTEXSIST( -101, "用户名或密码不正确"), // 用户名不存在
    LOGIN_FAIL_PWD_INCORRECT( -102, "用户名或密码不正确"), // 密码不正确
    LOGIN_FAIL_USER_LOCK( -103, "账号被锁定"), // 账号被锁定
    LOGIN_FAIL_EXCEPTION( -104, "登录发生异常");// 登录发生异常

    private Integer code;

    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    LoginAuthResult(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

}