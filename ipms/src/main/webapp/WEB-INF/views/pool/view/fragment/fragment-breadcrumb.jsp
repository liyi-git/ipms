<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<ol class="aui-breadcrumb" id="pool-breadcrumb" style="border-bottom: 5px solid #d7dce4;margin:0;">
	<c:forEach items="${HIERARCHY_CRUMB}" var="it" varStatus="s">
		<c:if test="${!s.last}">
			<li><a href="javascript:;" val="${it.key}">${it.value}</a></li>
		</c:if>
		<c:if test="${s.last}">
			<li class="active" val="${it.key}">${it.value}</li>
		</c:if>
	</c:forEach>
</ol>