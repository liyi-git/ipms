package com.langnatech.ipms.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.langnatech.core.base.entity.IEntity;

public class IPArchiveInfoEntity implements IEntity{
    
    private static final long serialVersionUID = 842194777767681850L;


    private String archiveId;

    
    private String applyId;

    
    private String poolId;

    
    private String subnetId;

    
    private Integer ipCount;

    
    private String applyReason;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")    
    private Date invalidDate;

    
    private String areaId;

    
    private String applicant;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;

    
    private String beginIp;

    
    private String endIp;

    
    private String orgName;

    
    private String orgType;

    
    private String licenceCode;

    
    private String orgNature;

    
    private String orgProvId;

    
    private String orgCityId;

    
    private String orgCountyId;

    
    private String orgLevel;

    
    private String orgTrade;

    
    private String orgAddress;

    
    private String linkman;

    
    private String contactPhone;

    
    private String contactEmail;

    
    private String gatewayPlace;

    
    private String useType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date useDate;

    
    private String gatewayIp;

    
    public String getArchiveId() {
        return archiveId;
    }

    
    public void setArchiveId(String archiveId) {
        this.archiveId = archiveId == null ? null : archiveId.trim();
    }

    
    public String getApplyId() {
        return applyId;
    }

    
    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    
    public String getPoolId() {
        return poolId;
    }

    
    public void setPoolId(String poolId) {
        this.poolId = poolId == null ? null : poolId.trim();
    }

    
    public String getSubnetId() {
        return subnetId;
    }

    
    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId == null ? null : subnetId.trim();
    }

    
    public Integer getIpCount() {
        return ipCount;
    }

    
    public void setIpCount(Integer ipCount) {
        this.ipCount = ipCount;
    }

    
    public String getApplyReason() {
        return applyReason;
    }

    
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
    }

    
    public Date getInvalidDate() {
        return invalidDate;
    }

    
    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    
    public String getAreaId() {
        return areaId;
    }

    
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    
    public String getApplicant() {
        return applicant;
    }

    
    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    
    public Date getApplyTime() {
        return applyTime;
    }

    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    
    public String getBeginIp() {
        return beginIp;
    }

    
    public void setBeginIp(String beginIp) {
        this.beginIp = beginIp == null ? null : beginIp.trim();
    }

    
    public String getEndIp() {
        return endIp;
    }

    
    public void setEndIp(String endIp) {
        this.endIp = endIp == null ? null : endIp.trim();
    }

    
    public String getOrgName() {
        return orgName;
    }

    
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    
    public String getOrgType() {
        return orgType;
    }

    
    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    
    public String getLicenceCode() {
        return licenceCode;
    }

    
    public void setLicenceCode(String licenceCode) {
        this.licenceCode = licenceCode == null ? null : licenceCode.trim();
    }

    
    public String getOrgNature() {
        return orgNature;
    }

    
    public void setOrgNature(String orgNature) {
        this.orgNature = orgNature == null ? null : orgNature.trim();
    }

    
    public String getOrgProvId() {
        return orgProvId;
    }

    
    public void setOrgProvId(String orgProvId) {
        this.orgProvId = orgProvId == null ? null : orgProvId.trim();
    }

    
    public String getOrgCityId() {
        return orgCityId;
    }

    
    public void setOrgCityId(String orgCityId) {
        this.orgCityId = orgCityId == null ? null : orgCityId.trim();
    }

    
    public String getOrgCountyId() {
        return orgCountyId;
    }

    
    public void setOrgCountyId(String orgCountyId) {
        this.orgCountyId = orgCountyId == null ? null : orgCountyId.trim();
    }

    
    public String getOrgLevel() {
        return orgLevel;
    }

    
    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel == null ? null : orgLevel.trim();
    }

    
    public String getOrgTrade() {
        return orgTrade;
    }

    
    public void setOrgTrade(String orgTrade) {
        this.orgTrade = orgTrade == null ? null : orgTrade.trim();
    }

    
    public String getOrgAddress() {
        return orgAddress;
    }

    
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    
    public String getLinkman() {
        return linkman;
    }

    
    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    
    public String getContactPhone() {
        return contactPhone;
    }

    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    
    public String getContactEmail() {
        return contactEmail;
    }

    
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    
    public String getGatewayPlace() {
        return gatewayPlace;
    }

    
    public void setGatewayPlace(String gatewayPlace) {
        this.gatewayPlace = gatewayPlace == null ? null : gatewayPlace.trim();
    }

    
    public String getUseType() {
        return useType;
    }

    
    public void setUseType(String useType) {
        this.useType = useType == null ? null : useType.trim();
    }

    
    public Date getUseDate() {
        return useDate;
    }

    
    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    
    public String getGatewayIp() {
        return gatewayIp;
    }

    
    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp == null ? null : gatewayIp.trim();
    }
}