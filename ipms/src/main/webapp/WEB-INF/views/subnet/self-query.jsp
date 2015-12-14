<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<style type="text/css">
.query-item {
	line-height: 40px;
}

.query-item label {
	float: left;
	text-align: right;
	width: 100px;
}

fieldset {
	border: 1px solid #cbd5dd;
	margin: 8px;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	-khtml-border-radius: 5px;
	border-radius: 5px;
	background: #fff;
}

legend {
	background: #fff;
	color: #0195FF;
	padding: 0 10px;
	cursor: pointer;
}
</style>
<div class="aui-dimpanel">
	<div class="aui-dimpanel-item">
		<label>IP地址：</label><input type="text" class="aui-input" placeholder="请输入IP" name="subnet.beginIp">
	</div>
	<div class="aui-dimpanel-item">
		<label>子网掩码：</label>
		<div class="aui-dimsel" val="-1" width="280" itemwidth="280" name="subnet.netmask">
			<ul>
				<c:forEach items="${exfn:getNetMask()}" var="it">
					<li val="${it.id}">${it.value}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="aui-dimpanel-func">
		<button type="button" class="aui-btn aui-btn-sm" id="query-button">查询</button>
	</div>
</div>
<div class="aui-panel" style="margin: 0; padding: 5px;">
	<fieldset>
		<legend title="点击展开/收缩">
			<span>▶</span> 规划使用信息
		</legend>
		<div class="aui-grid">
			<div class="aui-grid-6 query-item">
				<label>地址池：</label>
				<div class="aui-dimsel" val="-9" width="250" name="subnet.poolId">
					<ul>
						<li val='-9'>IP地址资源池</li>
						<c:forEach items="${exfn:getIPPool()}" var="it">
							<li val="${it.poolId}">|${exfn:repeat("---",it.deep)}${it.poolName}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-6 query-item">
				<label>归属地域：</label>
				<div class="aui-dimsel" name="subnet.cityId" showall="true">
					<ul>
						<c:forEach items="${exfn:getCity()}" var="it">
							<li val="${it.cityId}">${it.cityName}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-6 query-item">
				<label>规划状态：</label>
				<div class="aui-dimsel" showall="true" name="subnet.planStatus">
					<ul>
						<c:set value="${exfn:getEnumValues('SubNetPlanStatusEnum')}" var="planStatus"></c:set>
						<c:forEach items="${planStatus}" var="status">
							<li val="<c:out value="${status.code}"/>"><c:out value="${status.desc}" /></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-6 query-item">
				<label>使用状态：</label>
				<div class="aui-dimsel" showall="true" name="subnet.useStatus">
					<ul>
						<c:set value="${exfn:getEnumValues('SubNetUseStatusEnum')}" var="useStatus"></c:set>
						<c:forEach items="${useStatus}" var="status">
							<li val="<c:out value="${status.code}"/>"><c:out value="${status.desc}" /></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</fieldset>
	<fieldset>
		<legend title="点击展开/收缩">
			<span>▶</span> 备案信息
		</legend>
		<div class="aui-grid">
			<div class="aui-grid-4 query-item">
				<label>使用方式：</label>
				<div class="aui-dimsel" showall="true" name="archive.useType">
					<ul>
						<c:forEach items="${exfn:getArchiveDic('USE_TYPE')}" var="it">
							<li val="${it.key}">${it.value}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-4 query-item">
				<label>单位所属分类：</label>
				<div class="aui-dimsel" showall="true" name="archive.orgType">
					<ul>
						<c:forEach items="${exfn:getArchiveDic('ORG_TYPE')}" var="it">
							<li val="${it.key}">${it.value}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-4 query-item">
				<label>单位行政级别：</label>
				<div class="aui-dimsel" showall="true" name="archive.orgLevel">
					<ul>
						<c:forEach items="${exfn:getArchiveDic('ORG_LEVEL')}" var="it">
							<li val="${it.key}">${it.value}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-4 query-item">
				<label>单位性质：</label>
				<div class="aui-dimsel" showall="true" name="archive.orgNature">
					<ul>
						<c:forEach items="${exfn:getArchiveDic('ORG_NATURE')}" var="it">
							<li val="${it.key}">${it.value}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-8 query-item">
				<label>所属行业分类：</label>
				<div class="aui-dimsel" showall="true" name="archive.orgTrade">
					<ul>
						<c:forEach items="${exfn:getArchiveDic('ORG_TRADE')}" var="it">
							<li val="${it.key}">${it.value}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="aui-grid-12 query-item">
				<div class="aui-dimpanel-item">
					<label>单位所在地区：</label>
					<div class="aui-dimsel" showall="true" id="org_prov" name="archive.orgProvId"></div>
				</div>
				<div class="aui-dimpanel-item">
					<div class="aui-dimsel" showall="true" id="org_city" name="archive.orgCityId" placeholder="请选择地市"></div>
				</div>
				<div class="aui-dimpanel-item">
					<div class="aui-dimsel" showall="true" id="org_county" name="archive.orgCountyId" placeholder="请选择区县"></div>
				</div>
			</div>
		</div>
	</fieldset>
</div>
<div class="aui-panel" id="queryResult-tab" val="tab-subnet">
	<ul class="aui-tab-panel  fn-hide">
		<li val="tab-subnet" target="query-Table">IP地址段</li>
		<!-- <li val="tab-iplist" target="query-Table">IP地址</li> -->
	</ul>
	<div class="aui-panel-body" id="queryTablePanel">
		<div id="query-Table"></div>
	</div>
</div>
<script type="text/javascript">
	require([ 'jquery', 'underscore', 'aui/popup', 'enums', 'archivecity', 'aui/dimsel', 'aui/tabs', 'table' ], function($, _, popup, Enums,
			archivecity) {
		var source = {
			dataType : "json",
			type : 'POST',
			url : _g_const.ctx + "/selfquery/query",
			dataFields : [ {
				name : 'SUBNET_ID',
				type : 'string'
			}, {
				name : 'SUBNET_DESC',
				type : 'string'
			}, {
				name : 'NETMASK',
				type : 'string'
			}, {
				name : 'PLAN_STATUS',
				type : 'string'
			}, {
				name : 'USE_STATUS',
				type : 'string'
			}, {
				name : 'POOL_NAME',
				type : 'string'
			}, {
				name : 'CITY_NAME',
				type : 'string'
			} ],
			pagesize : 10,
			root : 'data'
		};
		var dataAdapter = new $.jqx.dataAdapter(source, {
			formatData : function(data) {
				var parm = getQueryParm();
				_.extend(data, parm);
				if (data.sortdatafield && data.sortorder) {
					data.orderbyrule = data.sortdatafield + '#' + data.sortorder
				}
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
			text : 'IP地址段',
			dataField : 'SUBNET_ID',
			displayField : 'SUBNET_DESC',
			align : 'center',
			cellsAlign : 'left',
			width : "150",
			pinned : true
		}, {
			text : '子网掩码',
			dataField : 'NETMASK',
			align : 'center',
			cellsAlign : 'right',
			width : "150"
		}, {
			text : '规划状态',
			dataField : 'PLAN_STATUS',
			align : 'center',
			cellsAlign : 'center',
			width : "120",
			cellsRenderer : function(row, column, value, rowData) {
				var status = rowData[column] || '', _style;
				if (status == Enums.planStatus.WAIT_PLAN) {
					_style = "label-wait";
				} else if (status == Enums.planStatus.PLANNING) {
					_style = "label-planning";
				} else if (status == Enums.planStatus.PLANNED) {
					_style = "label-planned";
				}
				if (_style) {
					return "<span class='label "+_style+"'>" + Enums.planStatus.toDesc(status) + "</span>";
				}
				return "";
			}
		}, {
			text : '使用状态',
			dataField : 'USE_STATUS',
			align : 'center',
			cellsAlign : 'center',
			width : "120",
			cellsRenderer : function(row, column, value, rowData) {
				return Enums.useStatus.toDesc(rowData[column]);
			}
		}, {
			text : '地址池',
			dataField : 'POOL_NAME',
			align : 'center',
			cellsAlign : 'center',
			width : "150"
		}, {
			text : '所属地域',
			dataField : 'CITY_NAME',
			align : 'center',
			cellsAlign : 'center',
			width : "150"
		}, {
			text : '操作',
			align : 'center',
			cellsAlign : 'center',
			width : '80',
			cellsRenderer : function(row, column, value, rowData) {
				if(rowData['PLAN_STATUS']==Enums.planStatus.WAIT_PLAN){
					return "";
				}
				return "<a href='javascript:;' evt-handler='view' val='"+rowData['SUBNET_ID']+"' desc='"+rowData['SUBNET_DESC']+"'>查看</a>";
			}
		} ];
		$(document).ready(function() {
			$('.aui-dimsel').dimsel();
			$('#queryResult-tab').tabs();
			$('#org_prov').dimsel('reload', archivecity['-1']);
			$('#org_prov').dimsel('option', {
				onchange : function(id) {
					$('#org_city').dimsel('reload', archivecity[id]);
				}
			});
			$('#org_city').dimsel('option', {
				onchange : function(id) {
					$('#org_county').dimsel('reload', archivecity[id]);
				}
			});
			$('legend').click(function() {
				$(this).siblings('div').toggle();
			});
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
				sortable : true,
				serverProcessing : true,
				columns : columns,
				localization : {
					emptyDataString : "查询无数据"
				}
			});
			$table.delegate('a[evt-handler]', 'click', function() {
				var $e = $(this);
				var val = $e.attr('val');
				var desc = $e.attr('desc');
				var func = $e.attr('evt-handler');
				if (func === 'view') {
					popup.openFrame('查看子网信息 [ ' + desc + ' ] ', _g_const.ctx + '/subnet/' + val + "/show", {minWidth:800,minHeight:500});
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