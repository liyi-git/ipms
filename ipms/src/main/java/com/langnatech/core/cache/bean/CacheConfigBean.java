 /*
 * Copyright 2000-2013 by Langna Tech. Co. Ltd. All Rights Reserved.
 * @Description：用一句话描述该文件做什么
 */
package com.langnatech.core.cache.bean;

import java.io.Serializable;
import java.util.Date;

 
/**
 * @Title:CacheConfigBean
 * @Description: 缓存配置bean：包含模块标识、缓存调用的接口、方法、是否初始化参数
 * @author guojy
 * @date Jun 24, 2014 11:17:47 AM 
 *  
 */

public class CacheConfigBean implements Serializable {

	 
	private static final long serialVersionUID = -7589823512481538048L;
	
	 /** moduleKey:模块*/
	 
	private String moduleKey;
	
	 /** serviceName:接口名*/
	 
	private String serviceName;
	 /** methodName:方法名*/
	 
	private String methodName;	 
	 
	/** argsObjects:参数*/
	private Object[] argsObjects;
	
	
	/** initlized:是否初始化*/
	private boolean initlized;
	
	 /** cacheName:缓存名称*/
	 
	private String cacheName;
	
	 /** desc:缓存描述信息*/
	 
	private String desc;
	
	 /** updateTime:更新时间*/
	 
	private Date updateTime;
	
	 /** moduleName:模块名称*/
	 
	private String moduleName;
	
	 /** allowRefresh:是否允许刷新*/
	 
	private Boolean allowRefresh = true;
	
	public String getModuleKey() {
		return moduleKey;
	}
	
	public void setModuleKey(String moduleKey) {
		this.moduleKey = moduleKey;
	}
	
	
	public String getServiceName() {
		return serviceName;
	}

	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	public String getMethodName() {
		return methodName;
	}

	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isInitlized() {
		return initlized;
	}
	
	public void setInitlized(boolean initlized) {
		this.initlized = initlized;
	}

	
	public Object[] getArgsObjects() {
		return argsObjects;
	}

	
	public void setArgsObjects(Object[] argsObjects) {
		this.argsObjects = argsObjects;
	}

	
	public String getDesc() {
		return desc;
	}

	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public Date getUpdateTime() {
		return updateTime;
	}

	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCacheName() {
		return cacheName;
	}

	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	
	public Boolean getAllowRefresh() {
		return allowRefresh;
	}

	
	public void setAllowRefresh(Boolean allowRefresh) {
		this.allowRefresh = allowRefresh;
	}
	

}
