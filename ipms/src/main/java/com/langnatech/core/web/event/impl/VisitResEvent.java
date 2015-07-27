
package com.langnatech.core.web.event.impl;

import java.util.Date;

import com.langnatech.core.web.event.AbstractWebVisitEvent;

/**
 * @Title:资源访问事件,如:(菜单点击、报表点击等)
 * @author liyi
 * @date Dec 21, 2013 4:32:42 PM
 */
public class VisitResEvent extends AbstractWebVisitEvent {

	private static final long serialVersionUID = -5137266049278359826L;

	private String menuId;// 资源编码

	private boolean isSuccess;// 访问结果 1:正常访问 2:访问报错

	private String visitErrorMsg;// 访问报错信息

	private Date visitBeginTime;

	private Date visitEndTime;

	public VisitResEvent(Object source) {
		super(source);

	}

	public Date getVisitBeginTime() {
		return visitBeginTime;
	}

	public void setVisitBeginTime(Date visitBeginTime) {
		this.visitBeginTime = visitBeginTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public VisitResEvent(Object source, String menuId, Date visitBeginTime,
			Date visitEndTime, boolean isSuccess, String visitErrorMsg) {
		super(source);
		this.menuId = menuId;
		this.isSuccess = isSuccess;
		this.visitErrorMsg = visitErrorMsg;
		this.visitBeginTime = visitBeginTime;
		this.visitEndTime = visitEndTime;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getVisitErrorMsg() {
		return visitErrorMsg;
	}

	public void setVisitErrorMsg(String visitErrorMsg) {
		this.visitErrorMsg = visitErrorMsg;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
