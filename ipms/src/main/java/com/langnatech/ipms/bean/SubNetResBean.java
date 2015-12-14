package com.langnatech.ipms.bean;

import java.util.List;
import com.langnatech.ipms.entity.SubNetResEntity;

public class SubNetResBean extends SubNetResEntity {

    private static final long serialVersionUID = -8564433005212820072L;

    private String poolName;

    private String cityName;
    
    private Short planStatusAll;

    public Short getPlanStatusAll() {
      return planStatusAll;
    }

    public void setPlanStatusAll(Short planStatusAll) {
      this.planStatusAll = planStatusAll;
    }

    private Integer subnetCount; // 子网数量（直接子网）

    private Integer subnetAllCount; // 所有子网数量（递归所有子网）

    private Integer waitCount;// 待规划子网数量

    private Integer planningCount;// 规划中子网数量

    private Integer plannedCount;// 已规划子网数量

    private Integer ipKeepCount;// 已预留IP数量

    private Integer ipUseCount;// 已使用IP数量

    private Integer ipFreeCount;// 空闲IP数量

    public Integer getIpFreeCount() {
        return ipFreeCount;
    }

    public void setIpFreeCount(Integer ipFreeCount) {
        this.ipFreeCount = ipFreeCount;
    }

    public Integer getIpAssignCount() {
        return ipAssignCount;
    }

    public void setIpAssignCount(Integer ipAssignCount) {
        this.ipAssignCount = ipAssignCount;
    }

    private Integer ipAssignCount;// 待分配IP数量

    private List<SubNetResEntity> childSubnetList; // 子网段

    public List<SubNetResEntity> getChildSubnetList() {
        return childSubnetList;
    }

    public void setChildSubnetList(List<SubNetResEntity> childSubnetList) {
        this.childSubnetList = childSubnetList;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getSubnetCount() {
        return subnetCount;
    }

    public boolean hasSubNet() {
        return this.subnetCount > 0;
    }

    public Integer getSubnetAllCount() {
        return subnetAllCount;
    }

    public void setSubnetAllCount(Integer subnetAllCount) {
        this.subnetAllCount = subnetAllCount;
    }

    public Integer getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Integer waitCount) {
        this.waitCount = waitCount;
    }

    public Integer getPlanningCount() {
        return planningCount;
    }

    public void setPlanningCount(Integer planningCount) {
        this.planningCount = planningCount;
    }

    public Integer getPlannedCount() {
        return plannedCount;
    }

    public void setPlannedCount(Integer plannedCount) {
        this.plannedCount = plannedCount;
    }

    public Integer getIpKeepCount() {
        return ipKeepCount;
    }

    public void setIpKeepCount(Integer ipKeepCount) {
        this.ipKeepCount = ipKeepCount;
    }

    public Integer getIpUseCount() {
        return ipUseCount;
    }

    public void setIpUseCount(Integer ipUseCount) {
        this.ipUseCount = ipUseCount;
    }

    public void setSubnetCount(Integer subnetCount) {
        this.subnetCount = subnetCount;
    }

}
