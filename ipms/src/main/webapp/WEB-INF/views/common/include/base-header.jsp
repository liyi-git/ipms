<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="exfn" uri="/WEB-INF/tags/basefunc.prop.tld"%>
<c:if test="${_IS_AJAX==false}">
	<!DOCTYPE html>
	<html>
		<head>
			<title>${exfn:prop("ipms.project.name.cn") }</title>
			<!-- meta定义 -->
			<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
			<meta http-equiv="Cache-Control" content="no-store" />
			<meta http-equiv="Pragma" content="no-cache" />
			<meta http-equiv="Expires" content="0" />
			<link type="text/css" href="<c:url value="/static/common/css/main.css"/>" rel="stylesheet"  /> 
					
			<!-- require引入 -->
			<script type="text/javascript">window['_g_const']={ctx:"${_CONTEXT_PATH}"};</script>
			<script type="text/javascript" src="<c:url value="/static/assets/require/2.1.11/require-debug.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/static/common/script/require-config.js"/>"></script>
		</head>
		<body>
</c:if>