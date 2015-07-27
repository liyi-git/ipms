package com.langnatech.privilege.sysmgr.service.impl;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.privilege.sysmgr.dao.SysUserDao;
import com.langnatech.privilege.sysmgr.entity.SysUserEntity;
import com.langnatech.privilege.sysmgr.service.SysUserService;


@Service
public class SysUserServiceImpl implements SysUserService
{
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";

    private int hashIterations = 2;

    @Autowired
    private SysUserDao userDao;

    public SysUserEntity getByUserId(String userId)
    {
        return userDao.selectUserById(userId);
    }

    public void encryptPassword(SysUserEntity user)
    {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations)
            .toHex();
        user.setPassword(newPassword);
    }

    public boolean createUser(SysUserEntity user)
    {
        this.encryptPassword(user);
        return this.userDao.insert(user) > 0;
    }

    public boolean changePassword(String userId, String newPassword)
    {
        SysUserEntity user = userDao.selectUserById(userId);
        user.setPassword(newPassword);
        this.encryptPassword(user);
        return userDao.update(user) > 0;
    }

    public static void main(String[] args)
    {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String newPassword = new SimpleHash("md5", "admin", ByteSource.Util.bytes("admin"+salt), 2)
            .toHex(); 
        System.out.println(salt);
        System.out.println(newPassword);
    }
}
