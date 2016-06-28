<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div id="page-header">
	<div class="logo">
		<a href="javascript:;">中国移动</a>
	</div>
	<div class="binding">
		<span class="binding-cn">新疆移动IP资源管理系统</span> 
		<span class="binding-en">IP Resource Managent System</span>
	</div>
	<a class="toggle-nav" title="切换菜单"></a>
	<ul class="header-func">
		<li><i class="icon-user"></i>${loginUser}</li>
		<li><i class="icon-lagout" id="ipms-logout"></i></li>
	</ul>
</div>
<div id="page-container">
	<div class="ipms-nav">
		<ul class="ipms-menu" id="ipms-sidebar">
		  <c:forEach items="${MenuList}" var="it" varStatus="s">
		    <c:if test="${it.menuPid=='-1'}">
	        <li class="hasChild">
	           <a href="javascript:;"> <i class="${it.menuCls }"></i><span>${it.menuName}</span></a>
	           <ul class="ipms-menu">
	           <c:forEach items="${MenuList}" var="subnet" varStatus="s">
	                <c:if test="${it.menuId==subnet.menuPid}">
	                   <li url="<c:url value="/${subnet.urlPath}"/>"><a href="javascript:;">${subnet.menuName}</a></li>
	                </c:if>
	           </c:forEach>
	           </ul>
	        </li>
	        </c:if>
	       </c:forEach>
		</ul>
	</div>
	<div class="ipms-content page-container"></div>
</div>
<script type="text/javascript" src="<c:url value="/static/modules/portal/portal.js"/>"></script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>