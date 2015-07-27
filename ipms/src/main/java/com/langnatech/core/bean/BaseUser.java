package com.langnatech.core.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.google.common.base.Objects;
import com.langnatech.util.Collections3;

public class BaseUser implements Serializable {

    private static final long serialVersionUID = -5612749126100987887L;

    private String userId;

    private String userName;

    private String pwd;

    private String deptId;

    private String jobId;

    private String email;

    private String phone;

    private String cityId;

    private String countyId;

    private Integer userStatus;

    // 用户授权的角色
    private List<BaseRole> roles;


    // 用户授权的地市
    private List<BaseCity> authCitys;

    // 用户授权的所有菜单
    private Set<String> authMenuIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public List<BaseRole> getRoles() {
        return roles;
    }

    public void setRoles(List<BaseRole> roles) {
        this.roles = roles;
    }


    public List<BaseCity> getAuthCitys() {
        return authCitys;
    }

    public void setAuthCitys(List<BaseCity> authCitys) {
        this.authCitys = authCitys;
    }

    public Set<String> getAuthMenuIds() {
        return authMenuIds;
    }

    public void setAuthMenuIds(Set<String> authMenuIds) {
        this.authMenuIds = authMenuIds;
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRoleIds() {
        return (Set<String>)Collections3.extractToSet(this.roles, "roleId");
    }

    @SuppressWarnings("unchecked")
    public Set<String> getRoleNames() {
        return (Set<String>)Collections3.extractToSet(this.roles, "roleName");
    }


    public String toString() {
        return this.userId;
    }

    public int hashCode() {
        return Objects.hashCode(this.userId);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseUser other = (BaseUser)obj;
        if (this.userId == null) {
            if (other.userId != null)
                return false;
        } else if ( !userId.equals(other.userId))
            return false;
        return true;
    }
}
