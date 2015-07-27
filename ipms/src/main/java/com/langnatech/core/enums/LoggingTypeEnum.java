package com.langnatech.core.enums;

public enum LoggingTypeEnum implements BaseEnum<String>{
    // 登录登出日志
    LOGIN_LOG("LOGIN_LOG", "登录登出日志"), // loginLogServiceImpl

    // 菜单访问日志
    VISIT_LOG("VISIT_LOG", "菜单访问日志"), // visitLogServiceImpl

    // 功能操作日志
    OPERATE_LOG("OPERATE_LOG", "功能操作日志"), // operateLogServiceImpl

    // 外部服务接口调用日志
    SERVICE_INVOKE_LOG("SERVICE_INVOKE_LOG", "外部服务接口调用日志"), // serInvokeLogServiceImpl

    // 消息推送日志
    MSG_PUSH_LOG("MSG_PUSH_LOG", "消息推送日志");

    private String code;

    private String desc;

    private LoggingTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}