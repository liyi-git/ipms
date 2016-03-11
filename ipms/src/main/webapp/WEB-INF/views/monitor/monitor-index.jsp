<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<%-- <div class="aui-dimpanel">
	<div class="aui-dimpanel-item">
		<label>预警结果：</label>
		<!--1:地址池中不存在 -->
		<!--2:配置信息不匹配  -->
		<!--3:地址已使用，但未监测到  -->
		<c:set value="${exfn:getEnumValues('MonitorTypeEnum')}" var="monitorType"></c:set>
		<div class="aui-dimsel" id="monitorType" name="monitorType">
			<ul>
				<li val="-1">全部</li>
				<c:forEach items="${monitorType}" var="it">
					<c:if test="${it.code!='UNKNOW'}">
						<li val="${it.code}">${it.desc}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="aui-dimpanel-func">
		<button type="button" class="aui-btn aui-btn-sm" id="query-button">查询</button>
	</div>
</div> --%>
<div class="aui-grid-12">
   	<div class="aui-grid-3">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">10
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">全部预警</p>
   			</div>
   		</div>
   	</div>
   	<div class="aui-grid-3">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">10
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">地址池中不存在</p>
   			</div>
   		</div>
   	</div>
   	<div class="aui-grid-3">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">10
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">配置信息不匹配</p>
   			</div>
   		</div>
   	</div>
   	<div class="aui-grid-3">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">10
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">已使用，但未检测到</p>
   			</div>
   		</div>
   	</div>
</div>

<div class="aui-panel">
	<div class="aui-panel-body" id="queryTablePanel">
		<div id="query-Table"></div>
	</div>
</div>

<script type="text/javascript">
	require([ 'jquery', 'underscore', 'enums', 'aui/dimsel', 'aui/tabs', 'table' ], function($, _, Enums) {
		var source = {
			dataType : "json",
			type : 'POST',
			url : _g_const.ctx + "/logging/operate/getData",
			dataFields : [ {
				name : 'operator',
				type : 'string'
			}, {
				name : 'operateTime',
				type : 'string'
			}, {
				name : 'operateType',
				type : 'string'
			}, {
				name : 'cityName',
				type : 'string'
			}, {
				name : 'poolName',
				type : 'string'
			}, {
				name : 'operateObj',
				type : 'string'
			}, {
				name : 'operateCont',
				type : 'string'
			}, {
				name : 'objType',
				type : 'string'
			} ],
			pagesize : 10,
			root : 'data'
		};
		var dataAdapter = new $.jqx.dataAdapter(source, {
			formatData : function(data) {
				var parm = getQueryParm();
				_.extend(data, parm);
				return data;
			},
			downloadComplete : function(data, status, xhr) {
				if (!source.totalRecords) {
					source.totalRecords = parseInt(data["totalCount"]);
				}
			}
		});
		var columns = [ {
			text : '编号',
			align : 'center',
			cellsAlign : 'center',
			width : '50',
			pinned : true,
			cellsRenderer : function(row, column, value, rowData) {
				return row + 1;
			}
		}, {
			text : '操作类型',
			dataField : 'operateType',
			align : 'center',
			cellsAlign : 'center',
			width : "150",
			cellsRenderer : function(row, column, value, rowData) {
				return Enums.opertype.toDesc(rowData[column]);
			}
		}, {
			text : '操作对象',
			dataField : 'operateObj',
			align : 'center',
			cellsAlign : 'left',
			width : "150"
		}, {
			text : '地址池',
			dataField : 'poolName',
			align : 'center',
			cellsAlign : 'left',
			width : "150",
			cellsRenderer: function (row, column, value, rowData) {
				if(!rowData[column]){
					return "IP地址资源池";
				}
				return rowData[column];
			}
		}, {
			text : '地域',
			dataField : 'cityName',
			align : 'center',
			cellsAlign : 'left',
			width : "150"			
		}, {
			text : '操作人',
			dataField : 'operator',
			align : 'center',
			cellsAlign : 'left',
			width : "120"
		}, {
			text : '操作时间',
			dataField : 'operateTime',
			align : 'center',
			cellsAlign : 'left',
			width : "150"
		}, {
			text : '操作内容',
			dataField : 'operateCont',
			align : 'left',
			cellsAlign : 'left',
			width : "400"
		} ];

		$(document).ready(function() {
			$('#operateType,#operateObj,#poolId,#cityId').dimsel();
			$('#query-button').click(function() {
				reloadGrid();
			});
			initGrid();
		});
		function reloadGrid() {
			$("#query-Table").jqxDataTable('destroy');
			initGrid();
		}
		function initGrid() {
			var $table = $("#query-Table");
			if (!$table.length) {
				$table = $('<div id="query-Table"/>').appendTo('#queryTablePanel');
			}
			$table.jqxDataTable({
				width : '100%',
				pageable : true,
				source : dataAdapter,
				columnsResize : true,
				pagerButtonsCount : 10,
				altRows : true,
				sortable : false,
				serverProcessing : true,
				columns : columns,
				localization : {
					emptyDataString : "查询无数据"
				}
			});
		}
		function getQueryParm() {
			var parmObj = {};
			$('.aui-dimsel').each(function(idx, it) {
				var name = $(it).attr('name');
				if (name) {
					parmObj[name] = $(it).attr('val');
				}
			});
			$(':text').each(function(idx, it) {
				parmObj[$(it).attr('name')] = $(it).val();
			});
			return parmObj;
		}
	});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>