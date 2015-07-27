
package com.langnatech.privilege.shiro.extend;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.langnatech.privilege.sysmgr.entity.SysUserEntity;
import com.langnatech.privilege.sysmgr.service.SysUserService;


public class CustomAuthorizingRealm extends AuthorizingRealm
{

    @Autowired
    private SysUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        //String loginName = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException
    {

        String loginName = (String)token.getPrincipal();

        SysUserEntity user = userService.getByUserId(loginName);

        if (user == null)
        {
            throw new UnknownAccountException();//没找到帐号
        }

        if (Boolean.TRUE.equals(user.getLocked()))
        {
            throw new LockedAccountException(); //帐号锁定
        }
        user.setSalt("5c442f9dea3c6bdfb13961a510054bfa");
        user.setPassword("cfec3f9ac4b6bb4b771c4fc3fbfe404a");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserId(), user.getPassword(),
            ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals)
    {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals)
    {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo()
    {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo()
    {
        getAuthenticationCache().clear();
    }

    public void clearAllCache()
    {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}