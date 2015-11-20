<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-panel aui-nomargin" style="max-width: 600px;">
    <div class="aui-panel-body">
        <div class="aui-grid-8 aui-grid-offset-2">
            <div class="aui-form" id="form-ipadd">
                <div class="aui-form-item">
                    <label class="aui-label">IP起始地址</label>
                    <div class="form-control">
                        <input type="text" class="aui-input" placeholder="请输入IP" id="inputIp">
                    </div>
                </div>

                <div class="aui-form-item">
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
                <div class="aui-form-item">
                    <label class="aui-label">IP结束地址</label>
                    <div class="form-control">
                        <input type="text" class="aui-input" disabled="disabled" id="lastedIp">
                    </div>
                </div>
                <div class="aui-form-item">
                    <label class="aui-label">可用IP数量</label>
                    <div class="form-control">
                        <input type="text" class="aui-input" disabled="disabled" id="usableIp">
                    </div>
                </div>
                <div class="aui-form-item">
                    <label class="aui-label">主机数量</label>
                    <div class="form-control">
                        <input type="text" class="aui-input" disabled="disabled" id="hostNum">
                    </div>
                </div>
                <div class="aui-form-item">
                    <label class="aui-label">地址池</label>
                    <div class="form-control">
                        <div class="aui-dimsel" val="-9" width="250" id="inputPool">
                            <ul>
                                <li val='-9' data-isplancity="-1">IP地址资源池</li>
                                <c:forEach items="${exfn:getIPPool()}" var="it">
                                    <li val="${it.poolId}" data-isplancity="${it.isPlanCity}">|${exfn:repeat("---",it.deep)}${it.poolName}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="aui-form-item" id="areaItem">
                    <label class="aui-label">地域</label>
                    <div class="form-control">
                        <div class="aui-dimsel" val="-1" id="inputArea">
                            <ul>
                                <c:forEach items="${exfn:getCity()}" var="it">
                                    <li val="${it.cityId}">${it.cityName}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="aui-panel-footer">
        <div class="aui-grid-10 aui-grid-offset-4">
            <button type="button" class="aui-btn aui-btn-md" id="form-ipadd-submit">提交</button>
            <button type="button" class="aui-btn aui-btn-md" id="form-ipadd-cancle">取消</button>
        </div>
    </div>
</div>
<!--条件选择区域 end-->
<script type="text/javascript">
    require([ 'jquery','aui/dimsel'],function($) {
            var $split = $ip("#form-ipadd"), params = {}, inputIp = "";
            $(".aui-dimsel").dimsel({onchange:function(obj){
                var $ul=this.getDimPanel(),
                    ipPlanCity=$ul.find(".aui-dimsel-item-cursel").attr("data-isplancity");
       
                    if(!!ipPlanCity){
                        if(obj=="-9"){
                            if($("#areaItem").is(":visible")){
                                $("#areaItem").hide();
                            }
                        }
                        if(ipPlanCity=="1"){
                            $("#areaItem").show();
                        }else{
                            $("#areaItem").hide();
                        }
                    }else{
                        if (obj != "-9") {
                            if (!!inputIp) {
                            	isExistSubnet(inputIp,$("#inputNetMask").attr("val"));
                            	// 先提示是否是超网
                            	var mask = $split.decimalToAddr($split.getMaskDecimal(parseInt($("#inputNetMask").attr("val"))));
                            	$split.isSupernetted(inputIp,mask);
                                var ipObj = $split.getObjBymBits(inputIp,
                                		$("#inputNetMask").attr("val"));
                                $("#inputIp").val(ipObj.firstIp);
                                $("#lastedIp").val(ipObj.lastIp);
                                $("#usableIp").val(ipObj.uIpNum);
                                $("#hostNum").val(ipObj.hostNum);
                            }
                        }
                    }
                   $(".aui-dimsel-items").find("li").css("minWidth","100px");
            }});
            
            $("#inputIp").focus(function() {
                $(this).val("");
                $("#lastedIp").val("");
                $("#usableIp").val("");
                $("#hostNum").val("");
                inputIp = "";
            }).blur(
                function() {
                    var ip = $.trim($(this).val());
                    if (ip && $split.isIP(ip)) {
                    	if($split.isPrivateIp(ip)){
                    		alert("请输入公有IP地址");
                    		inputIp="";
                    		$("#inputIp").val("");
                    		return true;
                    	}
                        inputIp = ip;
                        if ($("#inputNetMask").attr("val") != "-9") {
                        	isExistSubnet(inputIp,$("#inputNetMask").attr("val"));
                            var ipObj = $split.getObjBymBits(inputIp,
                                    $("#inputNetMask").attr("val"));
                            $("#inputIp").val(ipObj.firstIp);
                            $("#lastedIp").val(ipObj.lastIp);
                            $("#usableIp").val(ipObj.uIpNum);
                            $("#hostNum").val(ipObj.hostNum);
                        }
                    }
            });
            
            
            $("#form-ipadd-submit").click(function() {
                var ip = $.trim($("#inputIp").val());
                var maskbits = $("#inputNetMask").attr("val");
                if (inputIp && $split.isIP(inputIp)) {
                    params.ip = inputIp;
                } else {
                    alert("请输入正确的IP地址");
                    return false;
                }
                if (maskbits != "-9") {
                    params.maskbits = maskbits;
                } else {
                    alert("请输入正确的子网掩码");
                    return false;
                }
                if($("#areaItem").is(":visible")){
                    params.area = $("#inputArea").attr("val");
                }else{
                    params.area = "-1";
                }
                params.pool = $("#inputPool").attr("val");
                var url = _g_const.ctx + '/subnet/plan/addSubnet';
                $.ajax({
                    type:'POST',
                    url:url,
                    dataType:"json",      
                    contentType:"application/x-www-form-urlencoded;charset=UTF-8",
                    data:params,
                    success:function(data){
                        if(data){
                        	$(".fancybox-overlay",parent.document).trigger("click");
                        }
                    }
               });

            });
            
            $("#form-ipadd-cancle").click(function() {
                params = {};
                $(".fancybox-overlay",parent.document).trigger("click");
            });
            
            function isExistSubnet(subnet,maskbits){
            	var url = _g_const.ctx + '/subnet/plan/isExistSubnet';
                $.ajax({
                    type:'POST',
                    url:url,
                    dataType:"json",      
                    contentType:"application/x-www-form-urlencoded;charset=UTF-8",
                    data:{subnet:$.trim(subnet),maskbits:$.trim(maskbits)},
                    success:function(data){
                        if(data){
                        }else{
                        	alert("地址段已经存在！");
                        	inputIp = "";
                        	$("#inputIp").val("");
                        }
                    }
               });
            }  
                
    });
</script>
<script type="text/javascript" src="<c:url value="/static/modules/ipsplit.js"/>"></script>