<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>	
<% 
	response.setStatus(403);
%>
<div class="ipms-error" >
	<div class="ipms-error-box" >
		<div class="ipms-error-type _power"></div>
		<h1>很抱歉，当前您没有访问权限！</h1>	
		<dl>
			<dt><a href='mailto:admin@chinamobile.com?subject=${exfn:prop("portal.linkman.email.subject")}'>请联系管理员</a></dt>
			<dd>
			</dd>
		</dl>
	</div>
</div>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>