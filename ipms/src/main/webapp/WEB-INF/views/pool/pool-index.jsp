<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-panel-grid">
	<div class="aui-left" id="pool-tree-panel">
		<div class="aui-panel aui-nomargin aui-noborder" style="box-shadow:none;" id="pool-viewby" val="Pool">
			<ul class="aui-tab-panel fn-hide">
				<li val="Pool" target="panel-tree">按地址池查看</li>
				<li val="City" target="panel-tree">按地市查看</li>
			</ul>
			<div class="aui-panel-body">
				<div id="panel-tree" class="fn-hide" style="height:500px;">
					<ul id="poolTree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="aui-right">
		<div class="aui-panel page-container" id="pool-view-panel" style="margin: 0;"></div>
	</div>
</div>
<script type="text/javascript" src="<c:url value="/static/modules/pool/pool-index.js"/>"></script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>