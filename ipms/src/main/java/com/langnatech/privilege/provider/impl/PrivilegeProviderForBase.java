package com.langnatech.privilege.provider.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.langnatech.core.bean.BaseCity;
import com.langnatech.core.bean.BaseMenu;
import com.langnatech.core.bean.BaseRole;
import com.langnatech.core.bean.BaseUser;
import com.langnatech.privilege.provider.AbstractPrivilegeProvider;

@Service
public class PrivilegeProviderForBase extends AbstractPrivilegeProvider {

	@Override
	public BaseUser verifyUser(String userId, String password) {
		return null;
	}

	@Override
	public String decrypt(String encryptStr) {
		return null;
	}

	@Override
	public String encrypt(String str) {
		return null;
	}

	@Override
	public String encryptLoacl(String str) {
		return null;
	}

	@Override
	public BaseUser getUserById(String userId) {
		return null;
	}

	@Override
	public List<BaseRole> getRolesByUserId(String userId) {
		return null;
	}

	@Override
	public List<BaseMenu> getAuthMenuListByUserId(String userId) {
		return null;
	}

	@Override
	public List<BaseCity> getAuthCitysByUserId(String userId) {
		return null;
	}

	@Override
	public BaseCity getUserCityByUserId(String userId) {
		return null;
	}

	@Override
	public Set<String> getPermissionsByUserId(String userId) {
		return null;
	}

}