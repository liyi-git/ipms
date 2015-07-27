<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.langnatech.ipms.enums.*,com.langnatech.util.EnumsUtil"%>
define(['underscore'],function(_) {
	function EnumObj(enumMap, idMap) {
		_.extend(this,enumMap);
		this._idMap = idMap;
	}
	EnumObj.prototype.toDesc = function(code) {
		return this._idMap[code];
	};
	EnumObj.prototype.list= function() {
		var result = [];
		for ( var it in this.idMap) {
			result.push({
				id : it,
				txt : this.idMap[it]
			});
		}
		return result;
	};
	return {
		planStatus : new EnumObj(<%=EnumsUtil.mapJson(SubNetPlanStatusEnum.class)%>),
		useStatus : new EnumObj(<%=EnumsUtil.mapJson(SubNetUseStatusEnum.class)%>),
		ipType:new EnumObj(<%=EnumsUtil.mapJson(IPAddressTypeEnum.class)%>),
		ipStatus:new EnumObj(<%=EnumsUtil.mapJson(IPStatusEnum.class)%>),
		opertype:new EnumObj(<%=EnumsUtil.mapJson(OperateTypeEnum.class)%>)
	}
});