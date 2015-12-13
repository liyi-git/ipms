package com.langnatech.ipms.webservice.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApplyResultBean implements Serializable {

  private static final long serialVersionUID = 2582451466668647562L;

  private List<String> iplist;

  private String startIP;

  private String endIP;

  private Integer ipCount;

  private Integer applyCount;

  private String netmask;

  @JsonIgnore
  private String subnetId;

  @JsonIgnore
  private String subnetDesc;

  @JsonIgnore
  private String gatewayIp;

  @JsonIgnore
  private String poolId;

  @JsonIgnore
  private String cityId;

  @JsonIgnore
  public String getPoolId() {
    return poolId;
  }

  public void setPoolId(String poolId) {
    this.poolId = poolId;
  }

  @JsonIgnore
  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public Integer getApplyCount() {
    return applyCount;
  }

  public String getEndIP() {
    return endIP;
  }

  @JsonIgnore
  public String getGatewayIp() {
    return gatewayIp;
  }

  public Integer getIpCount() {
    return ipCount;
  }

  public List<String> getIplist() {
    return iplist;
  }

  public String getNetmask() {
    return netmask;
  }

  public String getStartIP() {
    return startIP;
  }

  @JsonIgnore
  public String getSubnetDesc() {
    return subnetDesc;
  }

  @JsonIgnore
  public String getSubnetId() {
    return subnetId;
  }

  public void setApplyCount(Integer applyCount) {
    this.applyCount = applyCount;
  }

  public void setEndIP(String endIP) {
    this.endIP = endIP;
  }

  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  public void setIpCount(Integer ipCount) {
    this.ipCount = ipCount;
  }

  public void setIplist(List<String> iplist) {
    this.iplist = iplist;
  }

  public void setNetmask(String netmask) {
    this.netmask = netmask;
  }

  public void setStartIP(String startIP) {
    this.startIP = startIP;
  }

  public void setSubnetDesc(String subnetDesc) {
    this.subnetDesc = subnetDesc;
  }

  public void setSubnetId(String subnetId) {
    this.subnetId = subnetId;
  }

}
