<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<style type="text/css">
.archive-list {
	
}

.archive-list li {
	float: left;
	line-height: 25px;
	overflow: hidden;
	height: 25px;
	width: 33%;
}

.archive-list li label {
	width: 120px;
	display: inline-block;
	text-align: left;
	padding: 0 5px;
	font-weight: bold;
}

.archive-list li span {
	padding: 5px 5px;
	overflow: hidden;
}
</style>
<fmt:formatNumber value="${SUBNET.ipUseCount+SUBNET.ipFreeCount+SUBNET.ipKeepCount}" var="ipTotalCount" pattern="#0.00"></fmt:formatNumber>
<fmt:formatNumber value="${(SUBNET.ipFreeCount)/ipTotalCount*100}" var="freePercent" pattern="#0.00"></fmt:formatNumber>
<fmt:formatNumber value="${SUBNET.ipUseCount/ipTotalCount*100}" var="usePercent" pattern="#0.00"></fmt:formatNumber>
<fmt:formatNumber value="${SUBNET.ipKeepCount/ipTotalCount*100}" var="keepPercent" pattern="#0.00"></fmt:formatNumber>
<ol class="aui-breadcrumb" id="pool-breadcrumb" style="border-bottom: 5px solid #d7dce4; margin: 0;">
	<c:forEach items="${HIERARCHY_CRUMB}" var="it" varStatus="s">
		<li><a href="javascript:;" val="${it.key}">${it.value}</a></li>
	</c:forEach>
	<li class="active" val="${SUBNET.subnetId}">${SUBNET.subnetDesc}</li>
</ol>
<div class='aui-grid'>
	<div class="aui-grid-6">
		<div class="aui-panel" style="margin: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">子网概要</h3>
			</div>
			<div class="aui-panel-body" id="subnet-overview-panel">
				<table class="aui-prop-grid">
					<tr>
						<th>子网名称：</th>
						<td><strong>${SUBNET.subnetDesc}</strong></td>
					</tr>
					<tr>
						<th>子网掩码：</th>
						<td>${SUBNET.netmask}</td>
					</tr>
					<c:if test="${not empty HIERARCHY_SUBNET}">
						<tr>
							<th>父网段：</th>
							<td><c:forEach items="${HIERARCHY_SUBNET}" var="it" varStatus="s">
									<a evt-handler="showSubnet" href="javascript:;" val="${it.subnetId}">${it.subnetDesc}</a> ${s.last?"":" -> "}
							</c:forEach></td>
						</tr>
					</c:if>
					<tr>
						<th>所属地址池：</th>
						<td><c:forEach items="${HIERARCHY_CRUMB}" var="it" varStatus="s">
								<a evt-handler="showPool" href="javascript:;" val="${it.key}">${it.value}</a> ${s.last?"":" -> "}
						</c:forEach></td>
					</tr>
					<tr>
						<th>所属地市：</th>
						<td>${SUBNET.cityName}</td>
					</tr>
					<tr>
						<th>子网数量：</th>
						<td>${SUBNET.subnetCount}</td>
					</tr>
					<tr>
						<th>IP数量：</th>
						<td>${SUBNET.ipCount}</td>
					</tr>
					<tr>
						<th>子网状态：</th>
						<td><c:if test="${SUBNET.planStatus==3}">
								<span class="label label-planned">已规划</span>  已使用：<span class="indi">${SUBNET.ipUseCount}</span> | 已预留：<span class="indi">${SUBNET.ipKeepCount}</span></td>
						</c:if>
						<c:if test="${SUBNET.planStatus==2}">
							<span class="label label-planning">规划中</span>
						</c:if>
						<c:if test="${SUBNET.planStatus<=1}">
							<span class="label label-wait">待规划</span>
						</c:if>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="aui-grid-6">
		<div class="aui-panel" style="margin: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">子网IP状态统计图</h3>
			</div>
			<div class="aui-panel-body" id="subnet-chart-panel" style="position: relative;"></div>
		</div>
	</div>
</div>
<c:if test="${ARCHIVE_INFO!=null}">
	<div class="aui-grid-12" style="margin-top: 10px;">
		<div class="aui-panel" style="margin: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">IP段备案信息</h3>
			</div>
		</div>
		<div class="aui-panel-body" style="background: #fff;">
			<ul class="archive-list">
				<li><label>单位名称:</label><span>${ARCHIVE_INFO.orgName}</span></li>
				<li><label>单位所属分类:</label><span>${exfn:getArchiveDicName("ORG_TYPE",ARCHIVE_INFO.orgType)}</span></li>
				<li><label>经营许可证编号:</label><span>${ARCHIVE_INFO.licenceCode}</span></li>
				<li><label>单位性质:</label><span>${exfn:getArchiveDicName("ORG_NATURE",ARCHIVE_INFO.orgNature)}</span></li>
				<li><label>单位行政级别:</label><span>${exfn:getArchiveDicName("ORG_LEVEL",ARCHIVE_INFO.orgLevel)}</span></li>
				<li><label>单位所属行业:</label><span>${exfn:getArchiveDicName("ORG_TRADE",ARCHIVE_INFO.orgTrade)}</span></li>
				<li><label>单位所在省:</label><span>${exfn:gettArchiveCityName("-1",ARCHIVE_INFO.orgProvId)}</span></li>
				<li><label>单位所在市:</label><span>${exfn:gettArchiveCityName(ARCHIVE_INFO.orgProvId,ARCHIVE_INFO.orgCityId)}</span></li>
				<li><label>单位所在县:</label><span>${exfn:gettArchiveCityName(ARCHIVE_INFO.orgCityId,ARCHIVE_INFO.orgCountyId)}</span></li>
				<li style="width: 100%;"><label>单位详细地址:</label><span>${ARCHIVE_INFO.orgAddress}</span></li>
				<li><label>联系人姓名:</label><span>${ARCHIVE_INFO.linkman}</span></li>
				<li><label>联系人电话:</label><span>${ARCHIVE_INFO.contactPhone}</span></li>
				<li><label>联系人邮箱:</label><span>${ARCHIVE_INFO.contactEmail}</span></li>
				<li><label>使用方式:</label><span>${exfn:getArchiveDicName("USE_TYPE",ARCHIVE_INFO.useType)}</span></li>
				<li><label>网关IP地址:</label><span>${ARCHIVE_INFO.gatewayIp}</span></li>
				<li><label>分配使用时间:</label><span><fmt:formatDate value="${ARCHIVE_INFO.useDate}" pattern="yyyy-MM-dd" /></span></li>
				<li style="width: 100%;"><label>网关物理位置:</label><span>${ARCHIVE_INFO.gatewayPlace}</span></li>
				<li><label>申请人:</label><span>${ARCHIVE_INFO.applicant}</span></li>
				<li><label>申请时间:</label><span><fmt:formatDate value="${ARCHIVE_INFO.applyTime}" pattern="yyyy-MM-dd" /></span></li>
				<li><label>申请失效日期:</label><span><fmt:formatDate value="${ARCHIVE_INFO.invalidDate}" pattern="yyyy-MM-dd" /></span></li>
				<li style="width: 100%;"><label>申请原因:</label><span>${ARCHIVE_INFO.applyReason}</span></li>
			</ul>
		</div>
	</div>
</c:if>
<div class="aui-grid-12" style="margin-top: 10px;">
	<div class="aui-panel" style="margin: 0;">
		<div class="aui-panel-heading">
			<h3 class="aui-panel-title">IP地址列表</h3>
		</div>
		<div class="aui-panel-body">
			<div id="iplist-Table"></div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	require([ 'jquery','underscore','enums','module/main','echart-chart/pie','aui/popup','table'], function($,_,enums,main,echart,popup) {
		var source={
  	         dataType: "json",
  	         type:'POST',
   	      	<c:if test="${SHOW_TYPE=='SUBNET'}">
 	  	         url: _g_const.ctx+"/subnet/${SUBNET.subnetId}/listsubnet",
		         dataFields: [
				     { name: 'subnetId', type: 'string' },
				     { name: 'subnetDesc', type: 'string' },
				     { name: 'planStatus', type: 'int' },
				     { name: 'useStatus', type: 'int' },
				     { name: 'netmask', type: 'string' },
				     { name: 'ipCount', type: 'int' },
				     { name: 'poolName', type: 'string' },
				     { name: 'cityName', type: 'string' },
				     { name: 'subnetCount', type: 'number' },
	  	           	 { name: 'ipKeepCount', type: 'number'},
		  	         { name: 'ipUseCount', type: 'number'}
		         ],
	         </c:if>
	   	     <c:if test="${SHOW_TYPE=='IPLIST_1'}">
	  	         url: _g_const.ctx+"/subnet/${SUBNET.subnetId}/listip",
		   	     dataFields: [
				     { name: 'addressId', type: 'string' },
				     { name: 'addressIp', type: 'string' },
				     { name: 'addressStatus', type: 'int' },
				     { name: 'addressType', type: 'int' },
				     { name: 'deviceType', type: 'int' },
				     { name: 'deviceModel', type: 'string' },
				     { name: 'usePort', type: 'string' },
				     { name: 'expireDate', type: 'string' }
		         ],
		     </c:if>
		   	     <c:if test="${SHOW_TYPE=='IPLIST_2'}">
	  	         url: _g_const.ctx+"/subnet/${SUBNET.subnetId}/listip",
		   	     dataFields: [
				     { name: 'addressId', type: 'string' },
				     { name: 'addressIp', type: 'string' },
				     { name: 'addressStatus', type: 'int' },
				     { name: 'addressType', type: 'int' }
		         ],
		     </c:if>
	  	     pagesize: 10,
             root: 'data'
        }
		var dataAdapter=new $.jqx.dataAdapter(source,{
        	formatData: function (data) {
        		return data;
        	},
        	downloadComplete: function (data, status, xhr) {
                if (!source.totalRecords) {
                	source.totalRecords = parseInt(data["totalCount"]);
                }
            }        	
	     });
		var columns=[
		<c:if test="${SHOW_TYPE=='IPLIST_1'}">
			  { text:'',align: 'center', cellsAlign: 'center',width:'30',pinned : true,cellsRenderer: function (row, column, value, rowData){
					  return row+1;
			  }},
	          { text: 'IP地址', dataField: 'addressId',displayField:'addressIp',align: 'center', cellsAlign: 'center',pinned : true,width:"130"},
	          { text: 'IP类型', dataField: 'addressType',align: 'center', cellsAlign: 'center',width:"100",cellsRenderer: function (row, column, value, rowData){
	        	  return enums.ipType.toDesc(rowData[column]);  
	          }},
	          { text: 'IP状态', dataField: 'addressStatus',align: 'center', cellsAlign: 'center',width:"100",cellsRenderer: function (row, column, value, rowData){
	        		return enums.ipStatus.toDesc(rowData[column]);  
	          }},
	          { text: '设备类型', dataField: 'deviceType',align: 'center', cellsAlign: 'center',width:"100"},
	          { text: '设备型号', dataField: 'deviceModel',align: 'center', cellsAlign: 'center',width:"100"},
	          { text: '使用端口', dataField: 'usePort',align: 'center', cellsAlign: 'center',width:"100"},
	          { text: '到期时间', dataField: 'expireDate',align: 'center', cellsAlign: 'center',width:"100"},
	          { text: '操作', align: 'center', cellsAlign: 'center',width:"100",cellsRenderer: function (row, column, value, rowData){
	        	  var handler;
	        	  if(rowData['addressStatus']==enums.ipStatus.AVAILABLE){
	        		  handler={id:'register',desc:'注册'};	        	  
	        	  }else if(rowData['addressStatus']==enums.ipStatus.USED){
	        		  handler={id:'change',desc:'变更'};	  
	        	  }
	        	  if(handler){
	        	  	return "<a href='javascript:;' evt-handler='"+handler.id+"' val='"+rowData['addressId']+"' desc='"+rowData['addressIp']+"'>"+handler.desc+"</a>";
	        	  }
        	  }}
	    </c:if>
		<c:if test="${SHOW_TYPE=='IPLIST_2'}">
			{ text:'编号',align: 'center', cellsAlign: 'center',width:'10%',cellsRenderer: function (row, column, value, rowData){
			  return row+1;
			}},
			{ text: 'IP地址', dataField: 'addressId',displayField:'addressIp',align: 'center', cellsAlign: 'center',width:"30%"},
			{ text: 'IP类型', dataField: 'addressType',align: 'center', cellsAlign: 'center',width:"30%",cellsRenderer: function (row, column, value, rowData){
			 return enums.ipType.toDesc(rowData[column]);  
			}},
			{ text: 'IP状态', dataField: 'addressStatus',align: 'center', cellsAlign: 'center',width:"30%",cellsRenderer: function (row, column, value, rowData){
			return enums.ipStatus.toDesc(rowData[column]);  
			}}
		</c:if>	    
	    <c:if test="${SHOW_TYPE=='SUBNET'}">
		  	{ text:'编号',align: 'center', cellsAlign: 'center',width:'50',pinned: true,cellsRenderer: function (row, column, value, rowData){
			  	return row+1;
	  		}},	    
	        { text: '网段名称', dataField: 'subnetId',displayField:'subnetDesc',align: 'center', cellsAlign: 'left',width:"150",pinned: true,cellsRenderer: function (row, column, value, rowData){
        		return '<a href="javascript:;" evt-handler="showSubnet" val="'+rowData['subnetId']+'">'+rowData['subnetDesc']+'</a>'; 
	        }},
	        { text: '子网掩码', dataField: 'netmask',align: 'center', cellsAlign: 'left',width:"150"},
   	        { text: '子网状态', dataField: 'planStatus',align: 'center', cellsAlign: 'center',width:"80",cellsRenderer: function (row, column, value, rowData) {
   	        	var status=rowData[column]||'',_style;
   	        	if(status==enums.planStatus.WAIT_PLAN){
   	        		_style="label-wait";
   	        	}else if(status==enums.planStatus.PLANNING){
   	        		_style="label-planning";
   	        	}else if(status==enums.planStatus.PLANNED){
   	        		_style="label-planned";
   	        	}
   	        	if(_style){
   	        		var txt="";
   	        		if(rowData['subnetCount']==0&&rowData['ipKeepCount']>0){
   	        			txt="已预留";
   	        			_style="label-keep";
   	        		}else if(rowData['subnetCount']==0&&rowData['ipUseCount']>0){
   	        			txt="已使用";
   	        			_style="label-use";
   	        		}else{
   	        			txt=enums.planStatus.toDesc(status);
   	        		}
 	        		return "<span class='label "+_style+"'>"+txt+"</span>";
   	        	}
   	        	return "";
   	        }},
	        { text: 'IP数量', dataField: 'ipCount',align: 'center', cellsAlign: 'right',width:"80"},
	        { text: '已使用', dataField: 'ipUseCount',align: 'center', cellsAlign: 'right',width:"80"},
	        { text: '已预留', dataField: 'ipKeepCount',align: 'center', cellsAlign: 'right',width:"80"},
   	        { text: '归属池', dataField: 'poolName',align: 'center', cellsAlign: 'center',width:"150"},
	        { text: '归属地市', dataField: 'cityName',align: 'center', cellsAlign: 'center',width:"150"}
	    </c:if>
	    ];
		$(document).ready(function(){
			initBreadcrumb();
			$('#subnet-chart-panel').height($('#subnet-overview-panel').height());
			main.loadCircleChart(echart,{
				usePercent:${usePercent},
				keepPercent:${keepPercent},
				freePercent:${freePercent}
			},'#subnet-chart-panel');
			initGrid();
		});
		//初始化面包屑导航
		function initBreadcrumb(){
			$('#pool-breadcrumb').delegate('a', 'click', function() {
				var val = $(this).attr('val');
				var url = _g_const.ctx;
				if (val === '-9') {
					url += "/pool/show";
				} else if (val && val.substr(0, 2) === 'S_') {
					url += "/subnet/" + val + "/show";
				} else {
					url += "/pool/" + val + "/show";
				}
				main.loadPage(url, {}, 'CLOSEST', $(this));
			});	
			$('#subnet-overview-panel').delegate('a[evt-handler]','click',function(){
				var $e=$(this);
				var func=$e.attr('evt-handler');
				var val=$e.attr('val');
				if(func === 'showPool'){
					if(val<0){
						main.loadPage( _g_const.ctx+ '/pool/show',{},"CLOSEST",$e);
					}else{
						main.loadPage( _g_const.ctx+ '/pool/'+val+"/show",{},"CLOSEST",$e);
					}
				}
				if(func === 'showSubnet'){
					main.loadPage( _g_const.ctx+ '/subnet/'+val+"/show",{},"CLOSEST",$e);
				}
			});			
		}
		//初始化表格
		function initGrid(){
			var $grid=$("#iplist-Table");
			$grid.jqxDataTable({
	            width: '100%',
	            pageable: true,
	            source: dataAdapter,
	            columnsResize: true,
	            pagerButtonsCount: 10,
	            altRows: true,
                serverProcessing: true,
	            columns: columns,
	            localization:{emptyDataString:"查询无数据"}
	        });	
			$grid.delegate('a[evt-handler]', 'click', function() {
				var $e = $(this);
				var val = $e.attr('val');
				var desc = $e.attr('desc');
				var func = $e.attr('evt-handler');
				if (func === 'register') {
					popup.openAjax('IP地址使用注册 [ ' + desc + ' ] ', _g_const.ctx + '/address/' + val + "/register", {minWidth:800});
				}
				if (func === 'change') {
					popup.openAjax('IP地址注册信息修改 [ ' + desc + ' ] ', _g_const.ctx + '/address/' + val + "/change", {minWidth:800});
				}
				if(func==='showSubnet'){
					main.loadPage( _g_const.ctx+ '/subnet/'+val+"/show",{},"CLOSEST",$e);
				}
			});	
		}
	});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>