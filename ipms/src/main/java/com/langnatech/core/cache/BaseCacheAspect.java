 /*
 * Copyright 2000-2013 by Langna Tech. Co. Ltd. All Rights Reserved.
 * @Description：用一句话描述该文件做什么
 */
package com.langnatech.core.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.core.web.IWebInitializable;

 
/**
 * 基础缓存切面类,供各模块需要缓存的切换继承
 * @Title:BaseCacheAspect
 * @author guojy
 * @date Jun 19, 2014 5:46:50 PM 
 *  
 */

public abstract class BaseCacheAspect<T> implements IWebInitializable{
	/** logger:日志 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BaseCacheAspect.class);
	
	@SuppressWarnings("unchecked")
	public ICacheStrategy<T> getICache(){
		return SpringContextHolder.getBean(ICacheStrategy.class);
	}

	/** 
	 * @Title: get
	 * @Description: 根据key对应的缓存对象
	 * @param key
	 * @return
	*/ 
	public T get(Object key){
		String moduleKey = getModuleKey();
		return getICache().get(moduleKey,key);
	}
	
	/** 
	 * @Title: getByModule
	 * @Description: 获取存储模块下的缓存列表
	 * @return
	*/ 
	public List<T> getByModule(){
		String moduleKey = getModuleKey();
		return getICache().getByModule(moduleKey);
	}
	
	/** 
	 * @Title: put
	 * @Description: 缓存obj到key值下
	 * @param key
	 * @param obj
	*/ 
	public void put(Object key,T obj){
		String moduleKey = getModuleKey();
		getICache().put(moduleKey,key, obj);
	}
	
	 
	/** 
	 * @Title: replace
	 * @Description: 更新对象
	 * @param key
	 * @param obj
	*/ 
	public void replace(Object key,T obj){
		String moduleKey = getModuleKey();
		getICache().replace(moduleKey, key, obj);
	}
	
	/** 
	 * @Title: remove
	 * @Description: 根据key值移除缓存数据
	 * @param key
	*/ 
	public void remove(Object key){
		String moduleKey = getModuleKey();
		getICache().remove(moduleKey,key);
	}
	

	/** 
	 * @Title: clear
	 * @Description: 清空模块缓存
	*/ 
	public void clear(){
		String moduleKey = getModuleKey();
		getICache().clear(moduleKey);
	}
	

    @Override
    public String destroyLog() {
    	return "缓存销毁";
    	
    }
    
    @Override
    public String initLog() {
    	return "初始化缓存";
    	
    }
    
    @Override
    public void destroy() {
    	this.getICache().clearAll();
    }

	/** 
	 * @Title: getModuleKey
	 * @Description: 设置模块标识
	 * @return
	*/ 
	protected abstract String getModuleKey();
}
