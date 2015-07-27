<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<c:import url="/WEB-INF/views/pool/view/fragment/fragment-breadcrumb.jsp"/>
<div class="aui-panel aui-nomargin" id="pool-view-tab" val="tab-overview">
	<ul class="aui-tab-panel fn-hide">
		<li val="tab-overview" target="panel-overview" url="<c:url value="/pool/PC_${POOL_ID}-${CITY_ID}/summary"/>">地址池概况</li>
		<li val="tab-stat" target="panel-stat" url="<c:url value="/pool/PC_${POOL_ID}-${CITY_ID}/statistic"/>">地址池统计</li>
	</ul>
	<div class="aui-panel-body">
		<div id="panel-overview" class="fn-hide"></div>
		<div id="panel-assign" class="fn-hide"></div>
		<div id="panel-stat" class="fn-hide"></div>
	</div>
</div>
<script type="text/javascript" src="<c:url value="/static/modules/pool/poolview.js"/>"></script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>
