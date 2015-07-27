<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-grid" style="margin: 0; padding: 0;">
	<div class="aui-grid-6">
		<div class="aui-panel" style="margin: 0; padding: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">IP规划统计</h3>
			</div>
			<div class="aui-panel-body" id="statchart-plan" style="width:100%;height:350px;"/>
			</div>
		</div>
	</div>
	<div class="aui-grid-6">
		<div class="aui-panel" style="margin: 0; padding: 0;">
			<div class="aui-panel-heading">
				<h3 class="aui-panel-title">IP使用统计</h3>
			</div>
			<div class="aui-panel-body" id="statchart-use" style="width:100%;height:350px;"/>
		</div>
	</div>
</div>
<script type="text/javascript">
require([ 'jquery','underscore','module/main','enums','echart-chart/pie','table'], function($,_,main,enums,echart) {
	$(document).ready(function(){
		loadChart(_g_const.ctx+'/pool/P_${POOL_ID}/statistic/statPlanStatus',{},$("#statchart-plan"),function(data){
			return convertData(data,enums.planStatus);
		});
		loadChart(_g_const.ctx+'/pool/P_${POOL_ID}/statistic/statUseStatus',{},$("#statchart-use"),function(data){
			return convertData(data,enums.useStatus,'待分配');
		});
	});
	function convertData(data,enumObj,other){
		var result=[];
		if(data&&_.isArray(data)){
			$.each(data,function(idx,it){
				result.push({name:enumObj.toDesc(it.name)||other,value:it.value});
			});
		}
		return result;
	}
	function loadChart(url,param,$div,dealDataCallback){
		$.getJSON(url,param,function(data){
			var chartData=dealDataCallback(data);
			var option = {
				    tooltip : {
				        trigger: 'item',
				        formatter: "{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'horizontal',
				        x : 'center',
				        y : 'bottom',
				        data:_.pluck(chartData,'name')
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            saveAsImage : {show: true}
				        }
				    },
				    series : [
				        {
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '40%'],
				            data:chartData
				        }
				    ]
				};
			echart.init($div.get(0)).setOption(option);
		});
	}
});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>