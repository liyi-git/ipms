/*
 * Copyright 2013-2023 by Langnatech Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.privilege.authentication;

import java.util.Map;

/**
 * @Title:IAuthFilterRuleService
 * @author liyi
 */
public interface IAuthFilterRuleService {

    /**
     * 加载验证过滤Map信息
     */
    public Map<String, String> loadFilterChainMap();

    /**
     * 重新加载权限验证过滤Map信息<权限变更时,需调用此方法>
     */
    public void reloadFilterChainsMap();
}
