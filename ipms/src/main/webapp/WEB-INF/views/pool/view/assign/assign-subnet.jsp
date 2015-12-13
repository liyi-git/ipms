<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div id="queryTablePanel" style="padding: 0;">
	<div id="subnet-table"></div>
</div>
<script type="text/javascript">
require([ 'jquery','aui/popup','underscore','enums','treegrid','aui/confirm'], function($,popup,_,Enums) {
	var source={
        dataType: "json",
        type:'POST',
        <c:if test="${POOL_ID==null}">
		url: _g_const.ctx+"/pool/getAssignSubnet",
		</c:if>
		<c:if test="${POOL_ID!=null}">
		url: _g_const.ctx+"/pool/P_${POOL_ID}/getAssignSubnet",
		</c:if>
		dataFields: [
			{ name: 'subnetId', type: 'string' },
			{ name: 'subnetPid', type: 'string' },
			{ name: 'subnetDesc', type: 'string' },
			{ name: 'netmask', type: 'string' },
			{ name: 'planStatus', type: 'string' },
			{ name: 'ipCount', type: 'number'},
			{ name: 'poolName', type: 'string' },
			{ name: 'poolId', type: 'string' },
			{ name: 'operateTime', type: 'string' }
		],
        id:'subnetId',
	    pagesize: 10,
        root: 'data'        
    };	
	var columns=[
    	{ text: '网段', dataField: 'subnetId',displayField:'subnetDesc',align: 'center', cellsAlign: 'left',width:"130"},
        { text: '子网掩码', dataField: 'netmask',align: 'center', cellsAlign: 'left',width:"120"},
        { text: 'IP总数量', dataField: 'ipCount',align: 'center', cellsAlign: 'right',width:"100"},
        { text: '分配地址池', dataField: 'poolName',align: 'center', cellsAlign: 'center',width:"120"},
        { text: '操作时间', dataField: 'operateTime',align: 'center', cellsAlign: 'center',width:"100"},
        { text: '操作',align: 'center', cellsAlign: 'center',width:"150",cellsRenderer: function (row, column, value, rowData) {
        	return "";
        }}
	];
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
 	$(document).ready(function(){	 		
	    initGrid();
 	});
    function reloadGrid(){
    	$("#subnet-table").jqxTreeGrid('destroy');
    	initGrid();
    }
	//初始化IP地址段树形表格		
    function initGrid(){
    	var $table=$("#subnet-table");
    	if(!$table.length){
    		$table=$('<div id="subnet-table"/>').appendTo('#queryTablePanel');
    	}			
    	$("#subnet-table").jqxDataTable({
    		source: dataAdapter,
    	    localization:{emptyDataString:"查询无数据"},
    	    columns:columns,
            width: '100%',
            pageable: true,
            pagerButtonsCount: 10,
            altRows: true,
            serverProcessing: true,            
            columnsResize: false    
    	});
    }
});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>