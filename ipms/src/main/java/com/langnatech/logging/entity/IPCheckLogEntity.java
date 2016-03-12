package com.langnatech.logging.entity;

import com.langnatech.core.base.entity.IEntity;
import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.logging.bean.LoggingBean;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IPCheckLogEntity extends LoggingBean implements IEntity {

	private static final long serialVersionUID = 3049653621962912737L;

	@Override
	public LoggingTypeEnum getLogType() {
		return LoggingTypeEnum.IP_CHECK_LOG;
	}

	private String ipAddress;
	private String subnetId;
	private String cityId;
	private String poolId;
	private Integer ipStatus;
	private Integer warnType;
	private String checkCity;
	private String checkDev;
	private String subnetName;
	private String cityName;
	private String poolName;
	private String checkCityName;
	private String checkDevName;
	

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getCheckCityName() {
		return checkCityName;
	}

	public void setCheckCityName(String checkCityName) {
		this.checkCityName = checkCityName;
	}

	public String getCheckDevName() {
		return checkDevName;
	}

	public void setCheckDevName(String checkDevName) {
		this.checkDevName = checkDevName;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date checkTime;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public Integer getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(Integer ipStatus) {
		this.ipStatus = ipStatus;
	}

	public Integer getWarnType() {
		return warnType;
	}

	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}

	public String getCheckCity() {
		return checkCity;
	}

	public void setCheckCity(String checkCity) {
		this.checkCity = checkCity;
	}

	public String getCheckDev() {
		return checkDev;
	}

	public void setCheckDev(String checkDev) {
		this.checkDev = checkDev;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

}
