<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-panel aui-nomargin" style="min-width: 600px;">
    <div class="aui-panel-body">
        <div class="aui-grid-8 aui-grid-offset-2">
            <form id="form-ipplan">
                <div class="aui-form">
                    <div class="aui-form-item">
                        <label class="aui-label">IP地址段</label>
                        <div class="form-control">
                            <input type="text" class="aui-input" value="${entity.subnetDesc}" name="entity.subnetDesc" disabled="disabled">
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
                            <input type="text" class="aui-input" value="${entity.ipCount}" name="entity.ipCount" disabled="disabled">
                        </div>
                    </div>
                    <div class="aui-form-item">
                        <label class="aui-label">地址池</label>
                        <div class="form-control">
                            <div class="aui-dimsel" val="${entity.poolId}" width="250" id="selPool">
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
                            <div class="aui-dimsel" val="${entity.cityId}" id="selArea">
                                <ul>
                                	<li val="-1">暂不分配</li>
                                    <c:forEach items="${exfn:getCity()}" var="it">
                                        <li val="${it.cityId}">${it.cityName}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                
                <input type="hidden" class="aui-input" value="${entity.subnetDesc}" name="subnetDesc">
                <input type="hidden" class="aui-input" value="${entity.netmask}" name="netmask">
                <input type="hidden" class="aui-input" value="${entity.ipCount}" name="ipCount">
                
                <input type="hidden" class="aui-input" value="${entity.poolId}" name="poolId" id="inputPool">
                <input type="hidden" class="aui-input" value="${entity.cityId}" name="cityId" id="inputArea">
                <input type="hidden" class="aui-input" value="${entity.subnetId}" name="subnetId">
                <input type="hidden" class="aui-input" value="${entity.beginIp}" name="beginIp">
                <input type="hidden" class="aui-input" value="${entity.beginIpDecimal}" name="beginIpDecimal">
                <input type="hidden" class="aui-input" value="${entity.endIp}" name="endIp">
                <input type="hidden" class="aui-input" value="${entity.endIpDecimal}" name="endIpDecimal">
                <input type="hidden" class="aui-input" value="${entity.maskBits}" name="maskBits">
                <input type="hidden" class="aui-input" value="${entity.subnetPid}" name="subnetPid">
                <input type="hidden" class="aui-input" value="${entity.isIpv6}" name="isIpv6">
                <input type="hidden" class="aui-input" value="${entity.useStatus}" name="useStatus">
                <input type="hidden" class="aui-input" value="${entity.lft}" name="lft">
                <input type="hidden" class="aui-input" value="${entity.rgt}" name="rgt">
            </form>

        </div>
    </div>
    <div class="aui-panel-footer">
        <div class="aui-grid-10 aui-grid-offset-4">
            <button type="button" class="aui-btn aui-btn-md" id="form-ipplan-submit">提交</button>
            <button type="button" class="aui-btn aui-btn-md" id="form-ipplan-cancle">取消</button>
        </div>
    </div>
</div>
<!--条件选择区域 end-->

<script type="text/javascript">
    require([ 'jquery', 'aui/dimsel' ],function($) {
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
            }
            $(".aui-dimsel-items").find("li").css("minWidth","100px");
        }});
        
        
        
        $("#form-ipplan-submit").click(function() {
            
            if ($("#areaItem").is(":visible")) {
                $("#inputArea").val($("#selArea").attr("val"));
            }
            
            $("#inputPool").val($("#selPool").attr("val"));
            
            var url = _g_const.ctx + '/subnet/plan/planSubnet',
                params=$("#form-ipplan").serialize();
            $.ajax({
	                type:'POST',
	                url:url,
	                dataType:"json",      
	                contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	                data:params,
	                success:function(data){
	                    if(data){
	                        $(".fancybox-overlay",parent.document).trigger("click");
	                    }else{
	                    	alert("网段已经使用或者处于预留不能重新规划!");
	                    }
	                }
	           });

        });

        $("#form-ipplan-cancle").click(function() {
            $(".fancybox-overlay",parent.document).trigger("click");
        });

  });
</script>
