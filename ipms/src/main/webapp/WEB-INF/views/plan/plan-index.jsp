<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<!--条件选择区域 start-->
<div class="aui-dimpanel">
	<button type="button" class="aui-btn aui-btn-md" id="showAddSubnet">注入IP地址段</button>
	<c:set value="${exfn:getEnumValues('SubNetPlanStatusEnum')}" var="planStatus"></c:set>
	<ul class="filter-toggle" id="planStatus">
		<li val="999" class="active">全部</li>
		<c:forEach items="${planStatus}" var="status">
			<li val="<c:out value="${status.code}"/>"><c:out value="${status.desc}" /></li>
		</c:forEach>
	</ul>
</div>
<!--条件选择区域 end-->
<div class="aui-panel">
	<div class="aui-panel-body" id="queryTablePanel" style="padding: 0;">
		<div id="subnet-table"></div>
	</div>
</div>
<script type="text/javascript">
	require([ 'jquery','aui/popup','underscore','enums','treegrid','aui/confirm'], function($,popup,_,Enums) {
    	var source={
  	         dataType: "json",
  	         url: _g_const.ctx+"/subnet/list",
  	         dataFields: [
  			     { name: 'subnetId', type: 'string' },
  			     { name: 'subnetPid', type: 'string' },
  			     { name: 'subnetDesc', type: 'string' },
  			     { name: 'subnetCount', type: 'number' },
  			     { name: 'waitCount', type: 'number' },
  			     { name: 'planningCount', type: 'number' },
  			     { name: 'planStatus', type: 'string' },
  	             { name: 'ipCount', type: 'number'},
  	             { name: 'poolName', type: 'string' },
  	             { name: 'cityName', type: 'string' },
  	             { name: 'poolId', type: 'string' },
  	             { name: 'operateTime', type: 'string' }
  	         ],
  	         hierarchy:{
                 keyDataField: { name: 'subnetId' },
                 parentDataField: { name: 'subnetPid' }
              },
             id:'subnetId'
  	     };
   	    var columns=[
   	        { text: '网段', dataField: 'subnetId',displayField:'subnetDesc',align: 'center', cellsAlign: 'left',width:"20%"},
   	        { text: '规划状态', dataField: 'planStatus',align: 'center', cellsAlign: 'center',width:"10%",cellsRenderer: function (row, column, value, rowData) {
   	        	var status=rowData[column]||'',_style;
   	        	if(status==Enums.planStatus.WAIT_PLAN){
   	        		_style="label-wait";
   	        	}else if(status==Enums.planStatus.PLANNING){
   	        		_style="label-planning";
   	        	}else if(status==Enums.planStatus.PLANNED){
   	        		_style="label-planned";
   	        	}
   	        	if(_style){
 	        			return "<span class='label "+_style+"'>"+Enums.planStatus.toDesc(status)+"</span>";
   	        	}
   	        	return "";
   	        }},
   	        { text: '待规划子网', dataField: 'waitCount',align: 'center', cellsAlign: 'right',width:"10%"},
   	        { text: '规划中子网', dataField: 'planningCount',align: 'center', cellsAlign: 'right',width:"10%"},
   	        { text: 'IP总数量', dataField: 'ipCount',align: 'center', cellsAlign: 'right',width:"10%"},
   	        { text: '分配地址池', dataField: 'poolName',align: 'center', cellsAlign: 'center',width:"10%"},
   	        { text: '分配地市', dataField: 'cityName',align: 'center', cellsAlign: 'center',width:"15%"},
   	        { text: '操作',align: 'center', cellsAlign: 'center',width:"15%",cellsRenderer: function (row, column, value, rowData) {
                  var status=rowData["planStatus"]||'',color;
                  var d_plan="<a href='javascript:;'  val="+rowData['subnetId']+" evt-handler='func_plan'>规划</a>",
                  	d_split="<a href='javascript:;' style='margin-left:10px;' val="+rowData['subnetId']+" evt-handler='func_split'>拆分</a>",
                  	d_merge="<a href='javascript:;'  val="+rowData['subnetId']+" evt-handler='func_merge'>回收子网</a>",
                  	d_delete="<a href='javascript:;' style='margin-left:10px;'  val="+rowData['subnetId']+" evt-handler='func_delete'>删除</a>",
                  	d_updatePlan="<a href='javascript:;' val="+rowData['subnetId']+" evt-handler='func_updatePlan'>重新规划</a>";
                 
                  if(status==Enums.planStatus.WAIT_PLAN){
                	if(rowData['subnetPid']!=-1){
                  		return d_plan+d_split;
                	}else{
                  		return d_plan+d_split+d_delete;
                	}
                  }else if(status==Enums.planStatus.PLANNING){
                      return d_updatePlan+d_split;
                  }else if(status==Enums.planStatus.PLANNED){
                      return d_updatePlan;
                  }else{
                  	return d_merge;
                  }
                  
              }}
   	    ];
   	 	$(document).ready(function(){
   	 		//规划状态切换事件
   			$('.filter-toggle').delegate('>li','click',function(){
   				$(this).addClass('active').siblings().removeClass('active');
   				reloadGrid();
   			});
   			$("#showAddSubnet").click(function() {
                popup.openFrame('注入IP地址段', _g_const.ctx+ '/subnet/plan/showAddSubnet', {width:500,height:650},function(){
                	reloadGrid();
                });
	        });   	 		
   		    initTreeGrid();
   		    $('#queryTablePanel').delegate('a[evt-handler]', 'click', function() {
				var $e = $(this);
				var val = $e.attr('val');
				var func = $e.attr('evt-handler');
            	if(func=="func_plan"){
                    popup.openFrame('规划地址段', _g_const.ctx+ '/subnet/plan/showPlanSubnet/'+val, {width:600,minHeight:450},function(){
                    	reloadGrid();
                    });
                 }else if(func=="func_split"){
                    popup.openFrame('拆分地址段', _g_const.ctx+ '/subnet/plan/showSplitSubnet/'+val, {width:800,minHeight:450},function(){
                        reloadGrid();
                    });
                 }else if(func=="func_merge"){
                    mergeSubnet(val);
                 }else if(func=="func_updatePlan"){
                    popup.openFrame('重新规划地址段', _g_const.ctx+ '/subnet/plan/showPlanSubnet/'+val, {width:600,minHeight:450},function(){
                        reloadGrid();
                    });
                 }else if(func=="func_delete"){
                    delSubnet(val);
                 }				
   		    });
   	 	});
	    function reloadGrid(){
	    	$("#subnet-table").jqxTreeGrid('destroy');
	    	initTreeGrid();
	    }
		//初始化IP地址段树形表格		
	    function initTreeGrid(){
	    	var $table=$("#subnet-table");
	    	if(!$table.length){
	    		$table=$('<div id="subnet-table"/>').appendTo('#queryTablePanel');
	    	}			
	    	$("#subnet-table").jqxTreeGrid({
	    		virtualModeCreateRecords: function(expandedRecord, done){
	    	        var dataAdapter = new $.jqx.dataAdapter(source, {
	    	        	formatData: function (data) {
	    	        		var parm=getParams();
	    	        		if(expandedRecord != null){
	    	        			source.url=_g_const.ctx+"/subnet/"+expandedRecord.subnetId+"/list";
	    	        		}else{
	    	        			source.url=_g_const.ctx+"/subnet/list";
	    	        		}
	    	        		_.extend(data,parm);
	    	        		return data;
	    	        	},
	    	            loadComplete: function (a) {
    	            		done(dataAdapter.records);
	    	            },
	                    loadError: function (xhr, status, error) {
	                        done(false);
	                        throw new Error(error.toString());
	                    }
	    	        });
	    	       dataAdapter.dataBind();
	    	    },
		    	virtualModeRecordCreating: function(record){
		    		 if (record.subnetCount==0) {
		    	           record.leaf = true;
		    	     }	    		
	    	    },
	    	    localization:{emptyDataString:"查询无数据"},
	    	    columns:columns   		
		    });	
	    }
		//获取页面查询过滤参数
	    function getParams(){
	    	return {
	    		planStatus:$('#planStatus >li.active').attr('val')
	    	}
	    }
		// 回收子网段
	    function mergeSubnet(subnetId){
	    	$.confirm({
	    		'title'     : '回收子网',
	               'message'   : '确定要回收子网吗？',
	               'confirm':function(){
	               	var url = _g_const.ctx + '/subnet/plan/mergeSubnet/'+subnetId,
	                   params={};
	                    $.ajax({
	                        type : "post",
	                        url : url,
	                        data : params,
	                        dataType : "html",
	                        contentType : "application/x-www-form-urlencoded;charset=UTF-8",
	                        success : function(data){
	                        	if(data=="true"){
	                        		$.confirm({
                                        'title'     : '回收子网',
                                        'message'   : '子网回收成功！',
                                        'confirm':function(){
                                        	reloadGrid();
                                        } 
                                    });
	                             }else{
	                            	 $.confirm({
	                            		 'title'     : '回收子网',
	                                     'message'   : '子网段中存在已规划或规划中的网段，不能回收！',
	                                     'confirm':function(){} 
	                            	 });
	                             }
	                        }
	                    });
                },
                'cancle':function(){
                	
                }
	    	});
	    }
	    function delSubnet(subnetId){
	    	$.confirm({
	    		'title'     : '删除网段',
                'message'   : '确定要删除网段吗？',
                'confirm':function(){
                	var url = _g_const.ctx + '/subnet/plan/deleteSubnet/'+subnetId,
                    params={};
	                 $.ajax({
	                     type : "post",
	                     url : url,
	                     data : params,
	                     dataType : "html",
	                     contentType : "application/x-www-form-urlencoded;charset=UTF-8",
	                     success : function(data){
	                         if(data){
	                             reloadGrid();
	                         }
	                     }
	                 });
                },
                'cancle':function(){
                    
                }
            });
       }
	});
</script>