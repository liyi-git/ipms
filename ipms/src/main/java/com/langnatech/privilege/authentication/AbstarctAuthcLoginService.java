package com.langnatech.privilege.authentication;

import com.langnatech.core.enums.LoginAuthResult;

/**
 * @Title:AbstarctAuthcLoginService
 * @author liyi
 */
public abstract class AbstarctAuthcLoginService {

    /***
     * <根据用户名、密码验证用户登录>.<br/>
     * 
     * @param userId 用户登录名
     * @param password 用户登录密码
     * @return LoginResultEnum 返回验证结果枚举对象
     */
    public LoginAuthResult authc(String userId, String password) {
        return authc(userId, password, null, false, false);
    }

    /***
     * <根据用户名、密码验证用户登录,记住登录后，下次可免登陆访问系统，但调用验证逻辑时仍需重新登录>.<br/>
     * 
     * @param userId 用户登录名
     * @param password 用户登录密码
     * @param isRemember 是否记住登录
     * @return LoginResultEnum 返回验证结果枚举对象
     */
    public LoginAuthResult authc(String userId, String password, boolean isRemember) {
        return authc(userId, password, null, isRemember, false);
    }

    /***
     * <根据加密的用户名和密码验证,实现系统间的单点登录>.<br/> 适用于Biz的简单单点登录机制<验证逻辑：根据第三方系统传入的用户名密码加密串,调用解密算法后,调用验证接口，传入解密后的用户名、密码验证是否已登录>
     * 
     * @param encryptuserId 加密后的用户名字符串
     * @param encryptPassword 加密后的密码字符串
     * @return LoginResultEnum 返回验证结果枚举对象
     */
    public LoginAuthResult authcByEncryptForSSO(String encryptuserId, String encryptPassword) {
        return authc(encryptuserId, encryptPassword, null, false, true);
    }

    /***
     * <根据加密的用户名和SessionId编码验证登录,实现系统间的单点登录>.<br/> <验证逻辑：根据第三方系统传入的sessionId和用户名,调用验证接口，验证用户是否已登录>
     * 
     * @param userId 登录用户名
     * @param sessionId 调用方系统SessionId
     * @return LoginResultEnum 返回验证结果枚举对象
     */
    public LoginAuthResult authcBySessionidForSSO(String userId, String sessionId) {
        return authc(userId, null, sessionId, false, true);
    }

    /***
     * 登录验证抽象方法，用于子类实现
     * 
     * @param userId 登录用户名
     * @param userId 登录密码
     * @param sessionId 调用方系统SessionId
     * @param isRemember 是否记住登录
     * @param isSSOLogin 是否第三方系统单点登录
     * @return LoginResultEnum 返回验证结果枚举对象
     */
    protected abstract LoginAuthResult authc(String userId, String password, String sessionId, boolean isRemember,
                                             boolean isSSOLogin);

    /**
     * 用户注销方法
     */
    public abstract void logout();
}
