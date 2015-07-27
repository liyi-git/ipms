/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.privilege.authentication.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.langnatech.privilege.authentication.IAuthFilterRuleService;
import com.langnatech.util.LoggerUtil;

/**
 * @param 注
 *            ：如果类没有泛型参数的话可以连标注一起删除
 * @author liyi
 */

public class AuthFilterRuleServiceImpl implements IAuthFilterRuleService {

	// ini配置文件
	private Resource iniConfig;

	private static final Logger logger = LoggerFactory
			.getLogger(AuthFilterRuleServiceImpl.class);

	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	public void setIniConfig(Resource iniConfig) {
		this.iniConfig = iniConfig;
	}

	/**
	 * 从ini配置文件和数据库中获取url过滤策略。<br/>
	 * 详细描述：从ini配置文件和数据库中获取url过滤策略，ini文件的内容为一些特殊的url过滤策略，
	 * 数据库中的url包含了菜单url报表url以及KPI资源url.<br/>
	 * 使用方式：此方法供shiro使用。
	 * 
	 * @return 返回一个Section对象。
	 * @throws 如果出现异常可能抛出相关异常
	 *             。
	 */
	public Map<String, String> loadFilterChainMap() {
		Ini ini = new Ini();
		LoggerUtil.debug(logger, "获取所有的URL权限过滤策略：");
		// 从INI配置文件中加载URL验证规则
		if (null != iniConfig && iniConfig.exists()) {
			try {
				ini.load(iniConfig.getInputStream());
			} catch (ConfigurationException e) {
				LoggerUtil.error(logger, e.getMessage());
			} catch (IOException e) {
				LoggerUtil.error(logger, e.getMessage());
			}
		}
		Ini.Section section = ini.getSection("urls");
		if (section == null) {
			section = ini.addSection("urls");
		}
		Map<String, String> urlStategysMap = this.getUrlFilterRule();
		// 从数据库加载URL验证规则
		if (urlStategysMap != null) {
			section.putAll(urlStategysMap);
		}
        // 增加所有URL都需验证通过的过滤规则
        section.put("/**", "authc");		
		LoggerUtil.debug(logger, section.entrySet().toString());
		return section;
	}

	// private Map<String, String> getUrlRuleSortMap() {
	// // 先解析url中的专题进行排序,再按照url的长度进行排序
	// Map<String, String> urlSortMap = Maps
	// .newTreeMap(new Comparator<String>() {
	//
	// public int compare(String key1, String key2) {
	// String[] urlSection1 = StringUtils.removeStart(key1,
	// "/").split("/");
	// String[] urlSection2 = StringUtils.removeStart(key2,
	// "/").split("/");
	// // 如果URL1和URL2都是系统级URL，则只比较长度
	// if (urlSection1.length == 1 && urlSection2.length == 1) {
	// return Integer.valueOf(urlSection1[0].length())
	// .compareTo(urlSection2[0].length()) * -1;
	// } else if (urlSection1.length == 1) {
	// return 1;
	// } else if (urlSection2.length == 1) {
	// return -1;
	// } else {
	// // 如果URL1和URL2 是一个模块的URL,则比较URL长度
	// if (urlSection1[0].equals(urlSection2[0])) {
	// if (key1.length() < key2.length()) {
	// return 1;
	// } else {
	// return -1;
	// }
	// } else {
	// return urlSection1[0].compareTo(urlSection2[0]);
	// }
	// }
	// }
	// });
	// return urlSortMap;
	// }

	/**
	 * 返回所用url的过滤策略。<br/>
	 * 详细描述： 返回所用url的过滤策略，此策略为一个Map，Map的key为url，value为对应的权限规则。<br/>
	 * 
	 * @return 返回一个Map，此Map为url过滤策略。
	 */
	private Map<String, String> getUrlFilterRule() {
		// List<Map<String, Object>> privilegeUrlMapList =
		// this.basePrivilegeConfService.selectPrivilegeUrlMap();
		// if (CollectionUtils.isEmpty(privilegeUrlMapList)) {
		// return null;
		// }
		// Map<String, Map<String, String>> urlMap = Maps.newHashMap();
		// for (Map<String, Object> privilegeUrlMap : privilegeUrlMapList) {
		// Integer resType =
		// Integer.parseInt(privilegeUrlMap.get("RES_TYPE").toString());
		// String operateCode =
		// String.valueOf(privilegeUrlMap.get("OPERATE_CODE")).trim().toUpperCase();
		// String resId =
		// String.valueOf(privilegeUrlMap.get("RES_ID")).trim().toUpperCase();
		// String url = String.valueOf(privilegeUrlMap.get("URL")).trim();
		// Object urlTypeObj = privilegeUrlMap.get("URL_TYPE");
		// //String urlType =
		// String.valueOf(privilegeUrlMap.get("URL_TYPE")).trim();
		// if(urlTypeObj!=null){
		// operateCode=String.valueOf(urlTypeObj).trim()+operateCode;
		// }
		// if ( !url.startsWith("/")) {
		// url += "/";
		// }
		// Map<String, String> ruleMap = urlMap.get(url);
		// if (ruleMap == null) {
		// ruleMap = new HashMap<String, String>() {
		//
		// public String toString() {
		// String[] keyAry = this.keySet().toArray(new
		// String[this.keySet().size()]);
		// for (int i = 0; i < keyAry.length; i++ ) {
		// keyAry[i] = keyAry[i] + ":" + this.get(keyAry[i]);
		// }
		// return StringUtils.join(keyAry, "||");
		// }
		// };
		// }
		// if (resType.intValue() == ResTypeEnum.M.getId()) {
		// String key = ResTypeEnum.M.name() + ":" + operateCode;
		// String value = ruleMap.get(key);
		// if (value == null) {
		// ruleMap.put(key, resId);
		// } else {
		// ruleMap.put(key, value + "|" + resId);
		// }
		// // 报表权限待实现
		// } else if (resType.intValue() == ResTypeEnum.R.getId()) {
		// } else if (resType.intValue() == ResTypeEnum.K.getId()) {
		//
		// }
		// urlMap.put(url, ruleMap);
		// }
		// Map<String, String> urlSortMap = this.getUrlRuleSortMap();
		// Set<String> urlSet = urlMap.keySet();
		// for (String url : urlSet) {
		// urlSortMap.put(url + "*", "perm[" + urlMap.get(url).toString() +
		// "]");
		// }
		// return urlSortMap;
		return null;
	}

	/**
	 * 重新加载权限验证过滤Map信息<权限变更时,需调用此方法>
	 */
	public synchronized void reloadFilterChainsMap() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
					.getObject();
		} catch (Exception e) {
			logger.error("获取ShiroFilter失败,错误描述:{}!", e);
			throw new RuntimeException("获取ShiroFilter失败!");
		}
		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
				.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
				.getFilterChainManager();
		manager.getFilterChains().clear();
		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean.setFilterChainDefinitionMap(this
				.loadFilterChainMap());
		Map<String, String> chains = shiroFilterFactoryBean
				.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}
	}
}