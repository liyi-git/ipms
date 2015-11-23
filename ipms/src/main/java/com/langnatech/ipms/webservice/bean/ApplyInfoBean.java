package com.langnatech.ipms.webservice.bean;

import java.io.Serializable;
import java.util.Date;


public class ApplyInfoBean implements Serializable {
  private static final long serialVersionUID = 8931466359281160256L;

  private String applyCode;

  private Integer busiType;

  private Integer ipCount;

  private String applyDesc;

  public ApplyInfoBean() {
    super();
  }

  private String applyCity;

  private String operator;

  private Date expiredDate;

  private String coName;

  private String coClassify;

  private String coLicense;

  private String coNature;

  private String coProvince;

  private String coCity;

  private String coCounty;

  private String coLevel;

  private String coIndustry;

  private String coAddress;

  private String contact;

  private String contactTel;

  private String contactEmail;

  private String gatewayLocation;

  private String useWay;

  public String getApplyCode() {
    return applyCode;
  }

  public void setApplyCode(String applyCode) {
    this.applyCode = applyCode;
  }

  public Integer getBusiType() {
    return busiType;
  }

  public void setBusiType(Integer busiType) {
    this.busiType = busiType;
  }

  public Integer getIpCount() {
    return ipCount;
  }

  public void setIpCount(Integer ipCount) {
    this.ipCount = ipCount;
  }

  public String getApplyDesc() {
    return applyDesc;
  }

  public void setApplyDesc(String applyDesc) {
    this.applyDesc = applyDesc;
  }

  public String getApplyCity() {
    return applyCity;
  }

  public void setApplyCity(String applyCity) {
    this.applyCity = applyCity;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Date getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }

  public String getCoName() {
    return coName;
  }

  public void setCoName(String coName) {
    this.coName = coName;
  }

  public String getCoClassify() {
    return coClassify;
  }

  public void setCoClassify(String coClassify) {
    this.coClassify = coClassify;
  }

  public String getCoLicense() {
    return coLicense;
  }

  public void setCoLicense(String coLicense) {
    this.coLicense = coLicense;
  }

  public String getCoNature() {
    return coNature;
  }

  public void setCoNature(String coNature) {
    this.coNature = coNature;
  }

  public String getCoProvince() {
    return coProvince;
  }

  public void setCoProvince(String coProvince) {
    this.coProvince = coProvince;
  }

  public String getCoCity() {
    return coCity;
  }

  public void setCoCity(String coCity) {
    this.coCity = coCity;
  }

  public String getCoCounty() {
    return coCounty;
  }

  public void setCoCounty(String coCounty) {
    this.coCounty = coCounty;
  }

  public String getCoLevel() {
    return coLevel;
  }

  public void setCoLevel(String coLevel) {
    this.coLevel = coLevel;
  }

  public String getCoIndustry() {
    return coIndustry;
  }

  public void setCoIndustry(String coIndustry) {
    this.coIndustry = coIndustry;
  }

  public String getCoAddress() {
    return coAddress;
  }

  public void setCoAddress(String coAddress) {
    this.coAddress = coAddress;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getContactTel() {
    return contactTel;
  }

  public void setContactTel(String contactTel) {
    this.contactTel = contactTel;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getGatewayLocation() {
    return gatewayLocation;
  }

  public void setGatewayLocation(String gatewayLocation) {
    this.gatewayLocation = gatewayLocation;
  }

  public String getUseWay() {
    return useWay;
  }

  public void setUseWay(String useWay) {
    this.useWay = useWay;
  }
}
