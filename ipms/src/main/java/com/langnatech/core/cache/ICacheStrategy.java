 /*
 * Copyright 2000-2013 by Langna Tech. Co. Ltd. All Rights Reserved.
 * @Description：用一句话描述该文件做什么
 */
package com.langnatech.core.cache;

import java.util.List;
import java.util.Map;

import com.langnatech.core.cache.bean.CacheConfigBean;

 
/**
 * 缓存抽象接口，提供缓存对象的查询get、缓存put、回收evict、清空clear、监控等动作
 * @Title:ICacheStrategy
 * @author guojy
 * @date Jun 19, 2014 5:59:51 PM 
 *  
 */

public interface ICacheStrategy<T> {
	/** 
	 * @Title: get
	 * @Description: 根据key值获取缓存内容
	 * @param moduleKey 缓存块key
	 * @param key 缓存对应的key
	 * @return
	*/ 
	public  T get(String moduleKey,Object key);
	
	/**
	 * 通过模块key取对应的缓存的元素集合
	 * @param moduleKey
	 * @return
	 */
	public List<T> getByModule(String moduleKey);
	/** 
	 * @Title: put
	 * @Description: 根据key值缓存内容
	 * @param moduleKey 缓存块key
	 * @param key  缓存对应的key
	 * @param obj  缓存目标对象
	*/ 
	public void put(String moduleKey,Object key,T obj);
	
	/** 
	 * @Title: remove
	 * @Description: 根据key值回收缓存
	 * @param moduleKey 缓存块key
	 * @param key 缓存对应的key
	*/ 
	public void remove(String moduleKey,Object key);
	
	/** 
	 * @Title: replace
	 * @Description: 更新替换缓存对象
	 * @param moduleKey
	 * @param key
	 * @param obj
	*/ 
	public void replace(String moduleKey,Object key,T obj);
	
	/** 
	 * @Title: refresh
	 * @Description: 根据moduleKey刷新缓存区域
	 * @param moduleKey
	*/ 
	public void refresh(String moduleKey);
	
	/** 
	 * @Title: clear
	 * @Description: 清空缓存
	 * @param moduleKey 缓存块key
	*/ 
	public void clear(String moduleKey);
	
	/** 
	 * @Title: clearAll
	 * @Description: 清空所有缓存
	*/ 
	public void clearAll();
	
	/** 
	 * @Title: registryConfig
	 * @Description: 初始化注册
	 * @param configBean
	*/ 
	public void registryConfig(CacheConfigBean configBean);
	/** 
	 * @Title: getCacheConfig
	 * @Description: 获取缓存配置信息
	 * @return
	*/ 
	public Map<String, CacheConfigBean> getCacheConfig();
	
	/** 
	 * @Title: getCacheSize
	 * @Description: 获取缓存区中的对象数
	 * @param moduleKey
	 * @return
	*/ 
	public int getCacheSize(String moduleKey);
	
	/** 
	 * @Title: getCacheSize
	 * @Description: 获取缓存中所有对象数
	 * @return
	*/ 
	public int  getCacheSize();
	
	/** 
	 * @Title: getMemoryStoreSize
	 * @Description: 获取缓存占用的内存大小
	 * @return
	*/ 
	public long getMemoryStoreSize();
	/** 
	 * @Title: getMemoryStoreSize
	 * @Description: 获取模块占用内存
	 * @param moduleKey
	 * @return
	*/ 
	public long getMemoryStoreSize(String moduleKey);
	/** 
	 * @Title: getCacheHits
	 * @Description: 获取缓存读取的命中次数
	 * @return
	*/ 
	public long getCacheHits();
	/** 
	 * @Title: getCacheHits
	 * @Description: 获取模块缓存命中次数
	 * @param moduleKey
	 * @return
	*/ 
	public long getCacheHits(String moduleKey);
	
	/** 
	 * @Title: getCacheMisses
	 * @Description: 获取缓存读取的错失次数
	 * @return
	*/ 
	public long getCacheMisses();
	/** 
	 * @Title: getCacheMisses
	 * @Description: 获取模块缓存错失次数
	 * @param moduleKey
	 * @return
	*/ 
	public long getCacheMisses(String moduleKey);

}
