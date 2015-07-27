 /*
 * Copyright 2000-2013 by Langna Tech. Co. Ltd. All Rights Reserved.
 * @Description：用一句话描述该文件做什么
 */
package com.langnatech.core.cache;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.langnatech.core.cache.bean.CacheConfigBean;
import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.util.LoggerUtil;
/**
 * @Title:CacheAbstractStrategy
 * @Description: 抽象策略实现，作用是统一完成缓存配置注册的工作以及刷新的方法
 * @author guojy
 * @date Jun 24, 2014 1:18:19 PM 
 *  
 */
public abstract class CacheAbstractStrategy<T> implements ICacheStrategy<T> {
	 /** logger:日志*/
	private static final Logger logger = LoggerFactory.getLogger(CacheAbstractStrategy.class);
	
	 /** configMap:缓存配置信息*/
	private static Map<String, CacheConfigBean> configMap = new HashMap<String, CacheConfigBean>();
	
	@Override
	public void registryConfig(CacheConfigBean configBean) {
		if(configBean ==null)
			return;
		if(StringUtils.isEmpty(configBean.getModuleKey())){
			return;
		}
		configMap.put(configBean.getModuleKey(), configBean);
		LoggerUtil.debug(logger, "注册[{}]模块缓存配置", configBean.getModuleKey());
		if(configBean.isInitlized()){
			//clear
			this.clear(configBean.getModuleKey());
			//更新
			invokeMethod(configBean);
		}
	}
	
	@Override
	public void refresh(String moduleKey) {
	    LoggerUtil.debug(logger, "刷新[{}]模块缓存", moduleKey);
		CacheConfigBean configBean = configMap.get(moduleKey);
		if(configBean != null){
			//clear
			this.clear(moduleKey);
			//更新
			this.invokeMethod(configBean);
		}
	}
	@Override
	public Map<String, CacheConfigBean> getCacheConfig() {
		return configMap;
	}
	/** 
	 * @Title: invokeMethod
	 * @Description: service层方法反射调用
	 * @param configBean
	*/ 
	private void invokeMethod(CacheConfigBean configBean){
		//更新
		try {
			if(StringUtils.isEmpty(configBean.getServiceName())){
				return;
			}
			Class<?> serviceClass = Class.forName(configBean.getServiceName());
			Class<?>[] argClass = null;
			if(configBean.getArgsObjects() != null){
				Object[] argsObjects = configBean.getArgsObjects();
				argClass = new Class[argsObjects.length];
				for(int i = 0;i<argsObjects.length;i++){
					argClass[i] = argsObjects[i].getClass();
				}
			}
			Method method = serviceClass.getDeclaredMethod(configBean.getMethodName(), argClass);
			Object service = SpringContextHolder.getBean(serviceClass);
			method.invoke(service, configBean.getArgsObjects());
			//更新时间
			configBean.setUpdateTime(DateTime.now().toDate());
		} catch (Exception e) {
		    e.printStackTrace();
			throw new BaseRuntimeException("初始化注册"+configBean.getModuleKey()+"模块缓存异常",e);
		}
	}
	
}
