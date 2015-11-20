<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<link type="text/css" href="<c:url value="/static/modules/pool/pool-view.css"/>" rel="stylesheet"  />	
<div class='aui-grid' style="padding: 0;">
<c:import url="/WEB-INF/views/pool/view/fragment/fragment-chart.jsp">
	<c:param name="idField" value="POOL"/>
</c:import>
	<div class="aui-grid-12" style="padding: 0;">
		<div class="aui-panel" style="margin: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">子地址池/网段列表</h3>
			</div>
			<div class="aui-panel-body" id="subnet-overview-panel">	
				<div id="pool-stat-Table"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
require([ 'jquery','module/main','echart-chart/pie','table'], function($,main,echart) {
	var jsonData=${exfn:toJSON(DATA_LIST)};
	var source=new $.jqx.dataAdapter({
         dataType: "json",
         localData:jsonData,
         dataFields: [
		     { name: 'POOL_ID', type: 'string' },
		     { name: 'POOL_NAME', type: 'string' },
             { name: 'IP_COUNT', type: 'int' },
             { name: 'KEEP_COUNT', type: 'int' },
             { name: 'USE_COUNT', type: 'int' }
         ]
     });
    var columns=[
        { text: '地址池', dataField: 'POOL_ID',displayField:'POOL_NAME',align: 'center', cellsAlign: 'left',width:"120",pinned: true,cellsRenderer: function (row, column, value, rowData){
 			if(rowData['POOL_ID']>0){
        		return '<a href="javascript:;">'+rowData['POOL_NAME']+'</a>'; 
 			}else{
 				return rowData['POOL_NAME'];
 			}
        }},
        { text: 'IP数量', dataField: 'IP_COUNT',align: 'center', cellsAlign: 'right',width:"100"},
        { text: '预留IP数量', dataField: 'KEEP_COUNT',align: 'center', cellsAlign: 'right',width:"100"},
        { text: '已使用IP数量', dataField: 'USE_COUNT',align: 'center', cellsAlign: 'right',width:"100"},
        { text: '空闲IP数量',align: 'center', cellsAlign: 'right',width:"100",cellsRenderer: function (row, column, value, rowData) {
        	return rowData['IP_COUNT']-rowData['USE_COUNT']-rowData['KEEP_COUNT'];
        } },
        { text: 'IP使用率',align: 'center', cellsAlign: 'left',width:"120",cellsRenderer: function (row, column, value, rowData) {
            var val=!rowData['IP_COUNT']?0:(rowData['USE_COUNT']/rowData['IP_COUNT']*100).toFixed(2);
            var s = '<div style="width:100px;height:20px;border:1px solid #ccc" title="'+val+'%">';
            if(val){
            	s+='<div style="width:' + val + '%;height:18px;background:#5ab0ee;color:#fff;font-size:12px;padding-left:2px 0 2px 3px;" title="'+val+'%">'+ val + '%' + '</div>';
            }else{
            	s+='<div style="font-size:12px;padding:2px 0 2px 3px;">0%</>'
            }
            return s+"</div>";
        } },
        { text: '操作',align: 'center', cellsAlign: 'center',width:"120",cellsRenderer: function (row, column, value, rowData) {
        	return "<a href='javascript:;'>查看</a>"
        }}
    ];
	$(document).ready(function() {
		$('#chart-panel').height($('#overview-panel').height());
		main.loadCircleChart(echart,{
			usePercent:${TOTAL_DATA['USE_COUNT']}/${TOTAL_DATA['IP_COUNT']}*100,
			keepPercent:${TOTAL_DATA['KEEP_COUNT']}/${TOTAL_DATA['IP_COUNT']}*100,
			freePercent:${TOTAL_DATA['FREE_COUNT']}/${TOTAL_DATA['IP_COUNT']}*100
		},'#chart-panel');
		var $statTable=$("#pool-stat-Table");
		$statTable.jqxDataTable({
            width: '100%',
            pageable: false,
            source: source,
            columnsResize: false,
            columns: columns,
            localization:{emptyDataString:"查询无数据"}
        });	
		$statTable.on('rowClick',function(event){
   		    var args = event.args;
   		    if(args.dataField==='POOL_ID'){
				var poolId=args.row['POOL_ID'];
				<c:if test="${CITY_ID=='-999'}">
				main.loadPage( _g_const.ctx+ '/pool/P_'+poolId+"/show",{},"CLOSEST",$(this));
				</c:if>
				<c:if test="${CITY_ID!='-999'}">
				main.loadPage( _g_const.ctx+ '/pool/PC_'+poolId+'-'+${CITY_ID}+"/show",{},"CLOSEST",$(this));
				</c:if>
   		    }
   		});
	});
});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>