<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh_cn">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/modules/login/login.css"/>" media="all" />
<title>IPMS管理系统</title>
</head>
<body>
	<div id="ipms-login">
		<div class="container">
			<div class="desc">
				<h2></h2>
			</div>
			<div class="panel">
				<div class="panel-heading">
					<h1>IPMS 地址管理系统</h1>
				</div>
				<div class="panel-body">
				<c:if test="${LOGIN_ERROR!=null}">
					<c:if test="${LOGIN_ERROR=='org.apache.shiro.authc.LockedAccountException'}">
						<div style="color: red;">帐号被锁定!</div>
					</c:if>
					<c:if test="${LOGIN_ERROR!='org.apache.shiro.authc.LockedAccountException'}">
						<div style="color: red;">登录验证失败</div>
					</c:if>	
				</c:if>
					<form action="<c:url value="/login"/>" method="post">
						<div class="form-group">
							<input  class="form-input" name="username" type="text" value="" placeholder="账号">
						</div>
						<div class="form-group">
							<input class="form-input" name="password" type="password" value="" placeholder="密码">
						</div>
						<div class="form-group">
							<button class="form-button" onclick="document.forms[0]">登录</button>
						</div>
					</form>
				</div>
				<div class="panel-footer">
					<a href="javasctip:;"> <span>没有账户?</span> 联系管理员开通账户
					</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>