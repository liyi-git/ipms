/*
* Copyright 2000-2013 by Langna Tech. Co. Ltd. All Rights Reserved.
* @Description：用一句话描述该文件做什么
*/
package com.langnatech.core.cache.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.langnatech.core.cache.CacheAbstractStrategy;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.util.LoggerUtil;


/**
 * ehcache缓存技术实现
 * @Title:EhCacheImpl
 * @author guojy
 * @date Jun 19, 2014 6:11:29 PM 
 *  
 */

public class EhCacheImpl<T> extends CacheAbstractStrategy<T>
{

    /** logger:日志*/

    private static final Logger logger = LoggerFactory.getLogger(EhCacheImpl.class);

    private CacheManager getCacheManager()
    {
        return SpringContextHolder.getBean("cacheManager");
    }

    private Cache getCache(String moduleKey)
    {
        Cache cache = getCacheManager().getCache(moduleKey);
        if (cache == null)
        {
            logger.error("[{}]模块没有配置缓存信息", moduleKey);
        }
        cache.setStatisticsEnabled(true);
        return cache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(String moduleKey, Object key)
    {
        Element element = this.getCache(moduleKey).get(key);
        if (element == null)
        {
            return null;
        }
        Element cloneEl = null;
        try
        {
            cloneEl = (Element)element.clone();
        }
        catch (CloneNotSupportedException e)
        {
            logger.error("缓存中[{}]模块的[{}]存储的对象没有实现序列化", new Object[] {moduleKey, key});
            return null;
        }
        //		logger.debug("查询缓存接口中[{}]模块的[{}]的值", new Object[]{moduleKey,key});
        return (T)cloneEl.getObjectValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getByModule(String moduleKey)
    {
        //LoggerUtil.debug(logger, "查询缓存接口中[{}]模块集合列表", moduleKey);
        List<T> resultList = null;
        Cache cache = this.getCache(moduleKey);
        List<Object> keysList = cache.getKeys();
        if (keysList == null || keysList.size() == 0)
        {
            return null;
        }
        resultList = new ArrayList<T>();
        for (Object key : keysList)
        {
            T obj = this.get(moduleKey, key);
            resultList.add(obj);
        }
        return resultList;
    }

    @Override
    public void put(String moduleKey, Object key, T obj)
    {
        Cache cache = this.getCache(moduleKey);
        Element element = new Element(key, obj);
        cache.put(element);
        //		LoggerUtil.debug(logger, "put[{}]模块的[{}]的数据", new Object[]{moduleKey,key});
    }

    @Override
    public void remove(String moduleKey, Object key)
    {
        Cache cache = this.getCache(moduleKey);
        cache.remove(key);
        //		LoggerUtil.debug(logger, "remove[{}]模块的[{}]的数据", new Object[]{moduleKey,key});
    }

    @Override
    public void replace(String moduleKey, Object key, T obj)
    {
        Element element = new Element(key, obj);
        Cache cache = this.getCache(moduleKey);
        cache.replace(element);
        //		LoggerUtil.debug(logger, "replace[{}]模块的[{}]的数据", new Object[]{moduleKey,key});
    }

    @Override
    public void clear(String moduleKey)
    {
        LoggerUtil.debug(logger, "清空[{}]模块缓存", moduleKey);
        this.getCache(moduleKey).removeAll();
    }

    @Override
    public void clearAll()
    {
        LoggerUtil.debug(logger, "清空所有缓存");
        this.getCacheManager().clearAll();
    }

    @Override
    public int getCacheSize(String moduleKey)
    {
        return getCache(moduleKey).getSize();
    }

    @Override
    public int getCacheSize()
    {
        String[] cacheNames = this.getCacheManager().getCacheNames();
        if (cacheNames != null && cacheNames.length > 0)
        {
            int size = 0;
            for (String cacheName : cacheNames)
            {
                Cache cache = this.getCacheManager().getCache(cacheName);
                if (cache != null)
                {
                    size += cache.getSize();
                }
            }
            return size;
        }
        else
        {
            return 0;
        }

    }

    @Override
    public long getMemoryStoreSize()
    {
        String[] cacheNames = this.getCacheManager().getCacheNames();
        if (cacheNames != null && cacheNames.length > 0)
        {
            long size = 0;
            for (String cacheName : cacheNames)
            {
                Cache cache = this.getCacheManager().getCache(cacheName);
                if (cache != null)
                {
                    size += cache.calculateInMemorySize();
                }
            }
            return size;
        }
        return 0;
    }

    @Override
    public long getCacheHits()
    {
        String[] cacheNames = this.getCacheManager().getCacheNames();
        if (cacheNames != null && cacheNames.length > 0)
        {
            long size = 0;
            for (String cacheName : cacheNames)
            {
                Cache cache = this.getCacheManager().getCache(cacheName);
                if (cache != null)
                {
                    size += cache.getStatistics().getCacheHits();
                }
            }
            return size;
        }
        return 0;

    }

    @Override
    public long getCacheMisses()
    {
        String[] cacheNames = this.getCacheManager().getCacheNames();
        if (cacheNames != null && cacheNames.length > 0)
        {
            long size = 0;
            for (String cacheName : cacheNames)
            {
                Cache cache = this.getCacheManager().getCache(cacheName);
                if (cache != null)
                {
                    size += cache.getStatistics().getCacheMisses();
                }
            }
            return size;
        }
        return 0;
    }

    @Override
    public long getMemoryStoreSize(String moduleKey)
    {
        Cache cache = this.getCache(moduleKey);
        return cache.calculateInMemorySize();

    }

    @Override
    public long getCacheHits(String moduleKey)
    {
        Cache cache = this.getCache(moduleKey);
        return cache.getStatistics().getCacheHits();

    }

    @Override
    public long getCacheMisses(String moduleKey)
    {
        Cache cache = this.getCache(moduleKey);
        return cache.getStatistics().getCacheMisses();

    }

}
