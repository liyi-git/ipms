package com.langnatech.privilege.authentication.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.langnatech.core.bean.BaseUser;
import com.langnatech.core.enums.LoginAuthResult;
import com.langnatech.core.holder.PropertiesHolder;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.privilege.authentication.AbstarctAuthcLoginService;
import com.langnatech.privilege.provider.PrivilegeProviderHolder;
import com.langnatech.privilege.shiro.extend.CustomAuthenticationToken;
import com.langnatech.util.LoggerUtil;

/**
 * <系统的登录验证实现类>
 */
@Service
public class AuthcLoginServiceImpl extends AbstarctAuthcLoginService {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(AuthcLoginServiceImpl.class);

    private LoginAuthResult loginAuthc(Subject subject, String userId, String password, String sessionId,
                                       boolean isRemember, boolean isSSOLogin) {
        if (PropertiesHolder.getBooleanProperty("privilege.userName.isEncrypt")) {
            userId = decryParam(userId);
        }
        if (PropertiesHolder.getBooleanProperty("privilege.passWord.isEncrypt")) {
            password = decryParam(password);
        }
        CustomAuthenticationToken token = new CustomAuthenticationToken(userId, password);
        token.setRememberMe(isRemember);
        token.setSSOLogin(isSSOLogin);
        token.setSessionId(sessionId);
        try {
            subject.login(token);
            return LoginAuthResult.LOGIN_SUCCESS;
        } catch (UnknownAccountException e) {
            return LoginAuthResult.LOGIN_FAIL_USER_NOTEXSIST;
        } catch (IncorrectCredentialsException e) {
            return LoginAuthResult.LOGIN_FAIL_PWD_INCORRECT;
        } catch (LockedAccountException e) {
            return LoginAuthResult.LOGIN_FAIL_USER_LOCK;
        } catch (AuthenticationException e) {
            LoggerUtil.error(logger, e.getMessage());
            return LoginAuthResult.LOGIN_FAIL_EXCEPTION;
        }
    }

    protected LoginAuthResult authc(String userId, String password, String sessionId, boolean isRemember,
                                    boolean isSSOLogin) {
        Subject subject = SecurityUtils.getSubject();
        LoginAuthResult loginAuthResult = LoginAuthResult.LOGIN_SUCCESS;
        // 验证用户是否已登录
        if ( !subject.isAuthenticated()) {
            loginAuthResult = this.loginAuthc(subject, userId, password, sessionId, isRemember, isSSOLogin);
        } else {
            // 避免重复登录,如果记住的用户与验证用户不一致，则注销记住的用户，重新登录验证；否则直接返回登录用户；
            BaseUser baseUser = (BaseUser)subject.getPrincipal();
            if ( !baseUser.getUserId().equalsIgnoreCase(userId)) {
                subject.logout();
                loginAuthResult = this.loginAuthc(subject, userId, password, sessionId, isRemember, isSSOLogin);
            }
        }
        return loginAuthResult;
    }

    /**
     * <用户注销方法>
     **/
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            WebVisitEventPublish.getInstance().logoutEvent(false);
            subject.logout();
        }
    }

    /**
     * <用户名密码解密>
     */
    public String decryParam(String str) {
        if (str != null && !"".equals(str)) {
            return PrivilegeProviderHolder.getProvider().decrypt(str);
        }
        return "";
    }
}