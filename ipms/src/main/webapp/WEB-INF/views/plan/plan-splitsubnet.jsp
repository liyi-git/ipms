<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-panel aui-nomargin">
	<div class="aui-panel-body">
		<div class="aui-grid-8 aui-grid-offset-2">
			<form id="form-ipsplit">
				<div class="aui-form">
					<div class="aui-form-item">
						<label class="aui-label">IP地址段</label>
						<div class="form-control">
							<input type="text" class="aui-input" value="${entity.subnetDesc}" name="entity.subnetDesc" disabled="disabled"
								id="inputIp">
						</div>
					</div>
					<div class="aui-form-item">
						<label class="aui-label">子网掩码</label>
						<div class="form-control">
							<input type="text" class="aui-input" value="${entity.netmask}" name="entity.netmask" disabled="disabled">
						</div>
					</div>
					<div class="aui-form-item">
						<label class="aui-label">可用IP数量</label>
						<div class="form-control">
							<input type="text" class="aui-input" value="${entity.ipCount}" name="entity.ipCount" id="ipCount" disabled="disabled">
						</div>
					</div>
					<div class="aui-form-item">
						<label class="aui-label">拆分方式</label>
						<div class="form-control" style="width:22%;">
							<input type="radio" class="aui-radio" value="netmask" checked="checked" name="splitmode">按子网掩码
						</div>
						<div class="form-control" style="width:22%;">
							<input type="radio" class="aui-radio" value="subnetnum" name="splitmode">按子网数量
						</div>
						<div class="form-control" style="width:22%;">
							<input type="radio" class="aui-radio" value="ipnum" name="splitmode">按子网内IP数量
						</div>
					</div>

					<div class="aui-form-item" id="netMask">
						<label class="aui-label">子网掩码</label>
						<div class="form-control">
							<div class="aui-dimsel" val="-9" width="250" itemwidth="250" id="inputNetMask">
								<ul>
									<c:forEach items="${exfn:getNetMask()}" var="it">
										<li val="${it.id}">${it.value}</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<!-- 子网数量 -->
					<div class="aui-form-item" id="subnetNum" style="display: none;">
						<label class="aui-label">子网数量</label>
						<div class="form-control">
							<input type="text" class="aui-input" placeholder="请输入子网数量">
						</div>
					</div>
					<!-- 子网内IP数量 -->
					<div class="aui-form-item" id="ipNum" style="display: none;">
						<label class="aui-label">子网内IP数量</label>
						<div class="form-control">
							<input type="text" class="aui-input" placeholder="请输入子网内IP数量">
						</div>
					</div>

				</div>
				<input type="hidden" class="aui-input" value="${entity.poolId}" name="poolId" id="inputPool"> <input
					type="hidden" class="aui-input" value="${entity.cityId}" name="cityId" id="inputArea"> <input type="hidden"
					class="aui-input" value="${entity.subnetId}" name="subnetId"> <input type="hidden" class="aui-input"
					value="${entity.beginIp}" name="beginIp"> <input type="hidden" class="aui-input"
					value="${entity.beginIpDecimal}" name="beginIpDecimal"> <input type="hidden" class="aui-input"
					value="${entity.endIp}" name="endIp"> <input type="hidden" class="aui-input" value="${entity.endIpDecimal}"
					name="endIpDecimal"> <input id="pmaskbits" type="hidden" class="aui-input" value="${entity.maskBits}" name="maskBits">
				<input type="hidden" class="aui-input" value="${entity.subnetPid}" name="subnetPid"> <input type="hidden"
					class="aui-input" value="${entity.isIpv6}" name="isIpv6"> <input type="hidden" class="aui-input"
					value="${entity.useStatus}" name="useStatus"> <input type="hidden" class="aui-input" value="${entity.lft}"
					name="lft"> <input type="hidden" class="aui-input" value="${entity.rgt}" name="rgt">
			</form>
		</div>

		<!--IP段列表 start-->
		<div class="aui-grid-11 aui-grid-offset-1 modal-table" id="ipms-splitTable">
			<table class="aui-table aui-table-striped aui-table-hover aui-table-bordered">
				<thead>
					<tr>
						<th><label class="aui-label"> <input type="checkbox" class="aui-checkbox">
						</label></th>
						<th>起始IP</th>
						<th>结束IP</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<!--IP段列表 end-->

	</div>
	<div class="aui-panel-footer">
		<div class="aui-grid-10 aui-grid-offset-4">
			<button type="button" class="aui-btn aui-btn-md" id="form-ipplan-submit">提交</button>
			<button type="button" class="aui-btn aui-btn-md" id="form-ipplan-cancle">取消</button>
		</div>
	</div>
	<div id="planFucn" class="ipms-plan-func">
	   <a href="javascript:;" class="close">X</a>
		<div class="aui-form-item">
			<div class="aui-dimsel" val="${entity.poolId}" width="180" id="s_selPool">
				<ul>
					<li val='-9' data-isplancity="-1">IP地址资源池</li>
					<c:forEach items="${exfn:getIPPool()}" var="it">
						<li val="${it.poolId}" data-isplancity="${it.isPlanCity}">|${exfn:repeat("---",it.deep)}${it.poolName}</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="aui-form-item">
			<div class="aui-dimsel" val="-1" width="180" class="s_selArea" id="s_areaItem">
				<ul>
					<c:forEach items="${exfn:getCity()}" var="it">
						<li val="${it.cityId}">${it.cityName}</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
<!--条件选择区域 end-->

<script type="text/javascript">
    require([ 'jquery','json','aui/dimsel'],function($) {
        var $split = $ip("#form-ipsplit"),
            $curPlanIp="",splitMask="";
        // 拆分方式选择切换
        $("input[name='splitmode']").each(function(){
            this.onclick=function(){
                if($(this).val()=="netmask"){
                    $("#netMask").show();
                    $("#subnetNum").hide();
                    $("#ipNum").hide();
                }else if($(this).val()=="subnetnum"){
                    $("#netMask").hide();
                    $("#subnetNum").show();
                    $("#ipNum").hide();
                }else if($(this).val()=="ipnum"){
                    $("#netMask").hide();
                    $("#subnetNum").hide();
                    $("#ipNum").show();
                }
            }
        });
       // 下拉列表选取选取        
        $(".aui-dimsel").dimsel({onchange:function(obj){
            var $ele=$(this.element);
            if(!!$ele.attr("id")&&$ele.attr("id")=="inputNetMask"){
                if($ele.is(":visible")&&obj!="-9"){
                	var subnetIp=$("#inputIp").val();
                	subnetIp=subnetIp.substr(0, subnetIp.indexOf('/'));
                	getSubnetData(subnetIp,$("#ipCount").val(),$ele.attr("val")); 
                    
                }
            }
            var $ul=this.getDimPanel(),
            ipPlanCity=$ul.find(".aui-dimsel-item-cursel").attr("data-isplancity");
            if(!!ipPlanCity){
                if(obj=="-9"){
                    if($("#s_areaItem").is(":visible")){
                        $("#s_areaItem").hide();
                    }
                }
                if(ipPlanCity=="1"){
                    $("#s_areaItem").show();
                }else{
                    $("#s_areaItem").hide();
                }
            }
            $(".aui-dimsel-items").find("li").css("minWidth","150px");
        }});
        $("#planFucn").find(".close").click(function(){
        	var areaId="",areaName="",poolId="",poolName="";
        	if($("#s_areaItem").is(":visible")&&$("#s_areaItem")!="-1"){
                areaId = $("#s_areaItem").attr("val");
                areaName=$("#s_areaItem").attr("txt");
            }else{
                areaId = "-1";
            }
        	poolId = $("#s_selPool").attr("val");
        	if($("#s_selPool")!="-9"){
        	   poolName=$("#s_selPool").attr("txt");	
        	}
        	$curPlanIp.siblings().remove();
        	$curPlanIp.parent().append("<span val='"+poolId+"'>"+poolName+"</span><span val='"+areaId+"'>"+areaName+"</span>");
        	$("#planFucn").hide();	
        });
        // 拆分输入区域（子网数量和IP数量）
        $("#subnetNum").find("input").focus(function(){
            $(this).val("");
        }).blur(function(){
            if(!!$(this).val()){
            	var subnetIp=$("#inputIp").val();
            	subnetIp=subnetIp.substr(0, subnetIp.indexOf('/'));
            	var splitMbits=$split.getSplitMbitsBySubNum($("#ipCount").val(),$(this).val());
            	getSubnetData(subnetIp,$("#ipCount").val(),splitMbits); 
            	
            }
        }).keyup(function(event){
             if(event.keyCode == 13){
            	var subnetIp=$("#inputIp").val();
             	subnetIp=subnetIp.substr(0, subnetIp.indexOf('/'));
             	var splitMbits=$split.getSplitMbitsBySubNum($("#ipCount").val(),$(this).val());
            	getSubnetData(subnetIp,$("#ipCount").val(),splitMbits);  
             }     
        });
        $("#ipNum").find("input").focus(function(){
        	$(this).val("")
        }).blur(function(){
        	var subnetIp=$("#inputIp").val();
         	subnetIp=subnetIp.substr(0, subnetIp.indexOf('/'));
        	var splitMbits=$split.getSplitMbitsByIpNum($("#ipCount").val(),$(this).val());
        	getSubnetData(subnetIp,$("#ipCount").val(),splitMbits);
        }).keyup(function(event){
        	if(event.keyCode == 13){
        	var subnetIp=$("#inputIp").val();
         	subnetIp=subnetIp.substr(0, subnetIp.indexOf('/'));
         	var splitMbits=$split.getSplitMbitsByIpNum($("#ipCount").val(),$(this).val());
        	getSubnetData(subnetIp,$("#ipCount").val(),splitMbits);
        	}
        });
       // 拆分后子网段数据
        function getSubnetData(subnetBeginIp, subnetIpCount,splitMaskBit){
            var jsonObj=$split.getSplitSubnet(subnetBeginIp, subnetIpCount,splitMaskBit),
               $dataTable=$("#ipms-splitTable");
            if(!!jsonObj){
               var $data=[],
                   domCheckbox="<td align='center'><label class='aui-label'><input type='checkbox' class='aui-checkbox'></label></td>";
                   $.each(jsonObj,function(idx,obj){
                	   splitMask=obj.maskIp;
                   $data.push("<tr>"+domCheckbox+"<td>"+obj.beginIp+"</td><td>"+obj.endIp+"</td><td><a href='javascript:;'>规划</a></td></tr>");
               });
               $dataTable.find("tbody").html($data.join(""));
               tableEvent($dataTable);
               $dataTable.show();
            }else{
            	$dataTable.find("tbody").html("");
            	$dataTable.hide();
            }
        }
        function tableEvent($dataTable){
            var left=0,top=0,$planFucn=$("#planFucn");
            $dataTable.delegate("a", "click", function(event){
                    left = event.pageX||event.clientX-document.body.scroolLeft;
                    top = event.pageY||event.clientY-document.body.scrollTOp;
                    $planFucn.attr("style","position:absolute;left:"+left-100+"px;top:"+top+"px;");
                    $planFucn.show();
                    $curPlanIp=$(this);
            });
            $dataTable.delegate("input[type='checkbox']", "click", function(event){
            	var tagname=$(this).parent().parent()[0].tagName.toLowerCase();
            	if(tagname=="th"){
            		if($(this).prop("checked")){
            			$(this).prop("checked",true);
                        $dataTable.find("input[type='checkbox']").prop("checked",true);
            		}else{
            			$(this).prop("checked",false);
                        $dataTable.find("input[type='checkbox']").prop("checked",false);
            		}
            	}else{
            		if($(this).prop("checked")){
            			$(this).prop("checked",true);
            			
            		}else{
            			$(this).prop("checked",false);
            		}
            	}
            });
        }
        
        $("#form-ipplan-submit").click(function() {
        	var saveDataAry=[],$dataTable=$("#ipms-splitTable"),$dataTablebody=$dataTable.find("tbody");
        	if(!$dataTable.is(":visible")){
        		alert("请选择拆分方式拆分IP");
        	}else{
        		$.each($dataTablebody.find("input[type='checkbox']"),function(idx,obj){
        			var $tdList=$(this).parents("tr").children('td'),
        			    $spanList=$tdList.eq(3).find("span"),poolId="-9",cityId="-1";
        			
        			if($spanList.length>0){
        				poolId=$($spanList[0]).attr("val")||"-9";
        				cityId=$($spanList[1]).attr("val")||"-1";
        			}
        			//TODO 待增加网段合并功能
        			/* if($(this).prop("checked")){
        				saveDataAry.push({subnetPid:$("input[name='subnetId']").val(),beginIp:$tdList.eq(1).text(),netmask:splitMask,endIp:$tdList.eq(2).text(),poolId:poolId,cityId:cityId});
        			} */
        			saveDataAry.push({subnetPid:$("input[name='subnetId']").val(),beginIp:$tdList.eq(1).text(),netmask:splitMask,endIp:$tdList.eq(2).text(),poolId:poolId,cityId:cityId});
        		});
        	}

        	if(saveDataAry.length>0){
        		var url=_g_const.ctx + '/subnet/plan/splitSubnet';
        		$.ajax({
        	         type:'POST',
        	         url:url,
        	         dataType:"json",      
        	         contentType:"application/json",
        	         data:$.toJSON(saveDataAry),
        	         success:function(data){
        	        	 if(data){
                             $(".fancybox-overlay",parent.document).trigger("click");
                         }
        	         }
        	    });
        	}

        });

        $("#form-ipplan-cancle").click(function() {
            $(".fancybox-overlay",parent.document).trigger("click");
        });
        
  });
</script>
<script type="text/javascript" src="<c:url value="/static/modules/ipsplit.js"/>"></script>