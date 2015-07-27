package com.langnatech.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseMenu implements Serializable{

	private static final long serialVersionUID = 5795705904587295970L;

	// 子菜单集合
	private List<BaseMenu> children;

	public BaseMenu() {

	}

	public BaseMenu(String menuId) {
	}

	public List<BaseMenu> getChildren() {
		return children;
	}

	public void setChildren(List<BaseMenu> children) {
		this.children = children;
	}

	public void addChild(BaseMenu menuBean) {
		if (this.children == null) {
			this.children = new ArrayList<BaseMenu>();
		}
		this.children.add(menuBean);
	}

	public void addChildren(List<BaseMenu> menuList) {
		if (this.children == null) {
			this.children = new ArrayList<BaseMenu>();
		}
		this.children.addAll(menuList);
	}

	public void clearChildren() {
		if (this.children != null) {
			this.children.clear();
		}
	}

	// 判断是否是目录
	public boolean isFolder() {
		return false;
	}

	// 判断是否是菜单项
	public boolean isMenuItem() {
		return false;
	}

	// 是否含有菜单项,用于去掉空的目录项
	public boolean isEmptyFolder() {
		boolean isEmpty = true;
		return isEmpty;
	}

	public void sort() {

	}
}
