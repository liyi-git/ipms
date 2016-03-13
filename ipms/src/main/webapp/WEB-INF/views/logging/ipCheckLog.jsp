<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-grid-12" style="padding: 15px 10px 0px 10px;" id="warnTypeArea" val="">
   	<div class="aui-grid-4" _id="noexist">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">${noexist}
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">地址池中不存在</p>
   			</div>
   		</div>
   	</div>
   	<div class="aui-grid-4" _id="mismatching">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">${mismatching}
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">配置地市信息不匹配</p>
   			</div>
   		</div>
   	</div>
   	<div class="aui-grid-4" _id="nodetected">
   		<div class="aui-panel aui-nomargin aui-center">
   			<div class="box-info">
   				<p class="size-h2">${nodetected}
   					<span class="size-h4">个</span>
   				</p>
   				<p class="text-muted">已使用，但未检测到</p>
   			</div>
   		</div>
   	</div>
</div>
<div class="aui-clear"></div>
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
			url : _g_const.ctx + "/logging/ipCheck/getData",
			dataFields : [ {
				name : 'ipAddress',
				type : 'string'
			}, {
				name : 'subnetName',
				type : 'string'
			},{
				name : 'cityName',
				type : 'string'
			}, {
				name : 'poolName',
				type : 'string'
			}, {
				name : 'warnType',
				type : 'string'
			}, {
				name : 'checkCityName',
				type : 'string'
			}, {
				name : 'checkDevName',
				type : 'string'
			},
			, {
				name : 'checkTime',
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
			text : 'ip地址',
			dataField : 'ipAddress',
			align : 'center',
			cellsAlign : 'center',
			width : "150",
			cellsRenderer : function(row, column, value, rowData) {
				return rowData[column];
			}
		}, {
			text : '所属网段',
			dataField : 'subnetName',
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
			text : '检测类型',
			dataField : 'warnType',
			align : 'center',
			cellsAlign : 'left',
			width : "200",
			cellsRenderer: function (row, column, value, rowData){
				if(rowData[column]==1){
					return "配置地市信息不匹配";
				}else if(rowData[column]==2){
					return "已使用，但未检测到";
				}else{
					return "地址池中不存在";
				}
				return "未知";
			}
		}, {
			text : '检测所在地市',
			dataField : 'checkCityName',
			align : 'center',
			cellsAlign : 'left',
			width : "100"
		}, {
			text : '采集设备名称',
			dataField : 'checkDevName',
			align : 'left',
			cellsAlign : 'left',
			width : "100"
		} , {
			text : '采集时间',
			dataField : 'checkTime',
			align : 'left',
			cellsAlign : 'left',
			width : "150"
		}];

		$(document).ready(function() {
			$('#query-button').click(function() {
				reloadGrid();
			});
			$("#warnTypeArea").delegate(".aui-grid-4","click",function(event){
				if($(this).attr("_id")==="noexist"){
					$("#warnTypeArea").attr("val",3);
				}else if($(this).attr("_id")==="mismatching"){
					$("#warnTypeArea").attr("val",1);
				}else{
					$("#warnTypeArea").attr("val",2);
				}
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
			parmObj["warnType"]=$("#warnTypeArea").attr("val");
			return parmObj;
		}
	});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>