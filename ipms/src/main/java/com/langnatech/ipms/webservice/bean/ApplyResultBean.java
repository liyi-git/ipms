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
  private String netmask;
  @JsonIgnore
  private String subnetId;
  @JsonIgnore
  private String gatewayIp;

  @JsonIgnore
  public String getSubnetId() {
    return subnetId;
  }

  public void setSubnetId(String subnetId) {
    this.subnetId = subnetId;
  }

  @JsonIgnore
  public String getGatewayIp() {
    return gatewayIp;
  }

  public void setGatewayIp(String gatewayIp) {
    this.gatewayIp = gatewayIp;
  }

  public List<String> getIplist() {
    return iplist;
  }

  public void setIplist(List<String> iplist) {
    this.iplist = iplist;
  }

  public String getStartIP() {
    return startIP;
  }

  public void setStartIP(String startIP) {
    this.startIP = startIP;
  }

  public String getEndIP() {
    return endIP;
  }

  public void setEndIP(String endIP) {
    this.endIP = endIP;
  }

  public Integer getIpCount() {
    return ipCount;
  }

  public void setIpCount(Integer ipCount) {
    this.ipCount = ipCount;
  }

  public String getNetmask() {
    return netmask;
  }

  public void setNetmask(String netmask) {
    this.netmask = netmask;
  }



}
