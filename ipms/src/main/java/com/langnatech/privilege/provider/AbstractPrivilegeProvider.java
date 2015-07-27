package com.langnatech.privilege.provider;
import java.util.List;
import java.util.Set;

import com.langnatech.core.bean.BaseCity;
import com.langnatech.core.bean.BaseMenu;
import com.langnatech.core.bean.BaseRole;
import com.langnatech.core.bean.BaseUser;
/**
 * @author liyi
 */
public abstract class AbstractPrivilegeProvider {
    /******************************* 验证接口 ******************************/
    /** 根据用户ID、用户密码验证用户密码是否正确 **/
    public abstract BaseUser verifyUser(String userId, String password);

    /** 解密接口 **/
    public abstract String decrypt(String encryptStr);

    /** 加密接口 **/
    public abstract String encrypt(String str);
    
    /** 本地加密 **/
    public abstract String encryptLoacl(String str);

    /******************************* 权限接口 ******************************/

    /** 根据用户ID获取用户对象 **/
    public abstract BaseUser getUserById(String userId);

    /** 根据用户ID获取用户分配的所有角色 **/
    public abstract List<BaseRole> getRolesByUserId(String userId);

    /** 根据用户ID获取用户授权访问的所有菜单 **/
    public abstract List<BaseMenu> getAuthMenuListByUserId(String userId);

    /** 根据用户ID获取用户授权访问的地市 **/
    public abstract List<BaseCity> getAuthCitysByUserId(String userId);

    /** 根据用户ID获取用户所属的地市 **/
    public abstract BaseCity getUserCityByUserId(String userId);
    
    /** 根据用户ID，获取所有的Shiro用户权限字符串**/
    public abstract Set<String> getPermissionsByUserId(String userId);
}