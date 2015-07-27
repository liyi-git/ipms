
package com.langnatech.privilege.shiro.extend;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.langnatech.core.cache.ICacheStrategy;
import com.langnatech.core.cache.bean.CacheConfigBean;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.core.web.IWebInitializable;
import com.langnatech.util.LoggerUtil;

/**
 * @Title:ShiroCacheConfig
 * @Description: shiro的缓存配置注册，把shiro引用的cache缓存配置注册到统一的缓存配置文件中，便于监控缓存信息
 * @author guojy
 * @date Jul 10, 2014 3:46:54 PM
 * 
 */
@Component
public class ShiroCacheConfig implements IWebInitializable {
	private static Logger log = LoggerFactory.getLogger(ShiroCacheConfig.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	private ICacheStrategy cacheStrategy;

	/*
	 * 
	 * @see com.langnatech.core.web.IWebInitializable#initialize()
	 */

	@Override
	public void initialize() {
		CachingSessionDAO sessionDAO = SpringContextHolder
				.getBean(CachingSessionDAO.class);
		if (sessionDAO != null) {
			LoggerUtil.debug(log, "注册shiro的session缓存配置");
			CacheConfigBean configBean = new CacheConfigBean();
			configBean.setModuleKey(sessionDAO.getActiveSessionsCacheName());
			configBean.setAllowRefresh(false);
			configBean.setInitlized(false);
			configBean.setCacheName("权限缓存");
			configBean.setDesc("权限缓存，缓存用户shiro权限session");
			configBean.setModuleName("权限模块");
			configBean.setUpdateTime(DateTime.now().toDate());
			cacheStrategy.registryConfig(configBean);
		}
		AuthorizingRealm realm = SpringContextHolder
				.getBean(AuthorizingRealm.class);
		if (realm != null) {
			LoggerUtil.debug(log, "注册shiro的授权缓存配置");
			CacheConfigBean configBean = new CacheConfigBean();
			configBean.setModuleKey(realm.getAuthorizationCacheName());
			configBean.setAllowRefresh(false);
			configBean.setInitlized(false);
			configBean.setCacheName("授权缓存");
			configBean.setDesc("授权缓存，缓存shiro授权信息");
			configBean.setModuleName("权限模块");
			configBean.setUpdateTime(DateTime.now().toDate());
			cacheStrategy.registryConfig(configBean);
		}
	}

	/*
	 * @return
	 * 
	 * @see com.langnatech.core.web.IWebInitializable#initLog()
	 */

	@Override
	public String initLog() {
		return "初始化Shiro缓存配置";

	}

	/*
	 * 
	 * @see com.langnatech.core.web.IWebInitializable#destroy()
	 */

	@Override
	public void destroy() {
	}

	/*
	 * @return
	 * 
	 * @see com.langnatech.core.web.IWebInitializable#destroyLog()
	 */

	@Override
	public String destroyLog() {
		return null;

	}

	/*
	 * @return
	 * 
	 * @see com.langnatech.core.web.IWebInitializable#setInitDepend()
	 */

	@Override
	public Class<? extends IWebInitializable> setInitDepend() {
		return null;

	}

}
