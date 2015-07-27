/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.privilege.shiro.extend;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <自定义Shiro登录Token令牌,用于扩展支持两种第三方系统SSO登录方式（1.用于名密码加密串 2.用户名SessionId）>
 * 
 * @author liyi
 */
public class CustomAuthenticationToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 5390860120982799657L;

    private boolean isSSOLogin = false;

    private String sessionId = "";

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSSOLogin() {
        return isSSOLogin;
    }

    public void setSSOLogin(boolean isSSOLogin) {
        this.isSSOLogin = isSSOLogin;
    }

    public CustomAuthenticationToken(final String username, final char[] password) {
        super(username, password);
    }

    public CustomAuthenticationToken(final String username, final String password) {
        super(username, password);
    }

    public CustomAuthenticationToken(final String username, final char[] password, final String host) {
        super(username, password, host);
    }

    public CustomAuthenticationToken(final String username, final String password, final String host) {
        super(username, password, host);
    }

    public void clear() {
        super.clear();
        this.isSSOLogin = false;
        this.sessionId = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(",isSSOLogin=").append(this.isSSOLogin).append(",sessionId=")
            .append(this.sessionId);
        return sb.toString();
    }
}
