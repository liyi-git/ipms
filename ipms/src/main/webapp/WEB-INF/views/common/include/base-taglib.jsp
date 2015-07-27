<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="aitag" %>
<%@ taglib uri="/WEB-INF/tags/basefunc.prop.tld" prefix="exfn" %>  
<c:set var="_SERVER_NAME" value="${pageContext.request.serverName}"/>
<c:set var="_SERVER_PORT" value="${pageContext.request.serverPort}"/>
<c:set var="_CONTEXT_PATH" value="${pageContext.request.contextPath}"/>
<c:set var="_IS_AJAX" value="${(header['X-Requested-With']=='XMLHttpRequest')}"/>
<c:set var="_THEME" value="default"/>
