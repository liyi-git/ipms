package com.langnatech.privilege.sysmgr.service;

import com.langnatech.privilege.sysmgr.entity.SysUserEntity;


public interface SysUserService
{

    public SysUserEntity getByUserId(String userId);

    public boolean createUser(SysUserEntity user);
    
    public boolean changePassword(String userId, String newPassword);
}
