package com.langnatech.privilege.sysmgr.entity;

import java.util.Date;

import com.langnatech.core.base.entity.IEntity;


public class SysUserEntity implements IEntity
{

    private static final long serialVersionUID = -8954830004372998472L;

    private String userId;

    private String userName;

    private Short sex;

    private String password;

    private String salt;

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    private String deptId;

    private String jobId;

    private String email;

    private String phone;

    private String address;

    private String postcode;

    private String cityId;

    private String countyId;

    private String remark;

    private Short userStatus;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

    private Boolean locked = Boolean.FALSE;

    public Boolean getLocked()
    {
        return locked;
    }

    public void setLocked(Boolean locked)
    {
        this.locked = locked;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName == null ? null : userName.trim();
    }

    public Short getSex()
    {
        return sex;
    }

    public void setSex(Short sex)
    {
        this.sex = sex;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password == null ? null : password.trim();
    }

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(String jobId)
    {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getCityId()
    {
        return cityId;
    }

    public void setCityId(String cityId)
    {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCountyId()
    {
        return countyId;
    }

    public void setCountyId(String countyId)
    {
        this.countyId = countyId == null ? null : countyId.trim();
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(Short userStatus)
    {
        this.userStatus = userStatus;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getModifyUser()
    {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser)
    {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public String getCredentialsSalt()
    {
        return this.userId + this.salt;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SysUserEntity user = (SysUserEntity)o;

        if (this.userId != null ? !this.userId.equals(user.userId) : user.userId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return this.userId != null ? this.userId.hashCode() : 0;
    }
}