<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>	
<div class="ipms-error" >
	<div class="ipms-error-box" >
		<div class="ipms-error-type _500">
		</div>
		<h1>很抱歉,您访问的页面出错了...</h1>	
		<dl>
			<dt><a href="javascript:void(0);" id="ipms-showError">点击查看错误信息</a></dt>
			<dd>
				<span id="error-message" style="display: none;">${exception.message}</span>
			</dd>
		</dl>
	</div>
</div>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>