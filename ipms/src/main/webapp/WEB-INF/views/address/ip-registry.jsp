<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
<div class="aui-panel aui-nomargin">
	<div class="aui-panel-heading">
		<label>IP地址：</label><span style="padding: 0 5px;font-weight: bold;font-style: italic;">${ADDRESS.addressIp}</span> <label>子网掩码：</label><span style="padding: 0 5px;font-weight: bold;font-style: italic;">${ADDRESS.netmask}</span>
	</div>
	<div class="aui-panel-body">
		<div class="aui-grid-8">
			<div class="aui-form" >
				<form name="registryForm" id="registryForm">
				<input type="hidden" name="addressId" id="addressId" value="${ADDRESS.addressId}">
				<div class="aui-form-item">
					<label class="aui-label">设备类型：</label>
					<div class="form-control">
					<div class="aui-dimsel" id="deviceType" name="deviceType" width="180" val="${ADDRESS.deviceType}">
                            <ul>
                                <c:forEach items="${exfn:getDeviceType()}" var="it">
                                    <li val="${it.deviceTypeId}">${it.deviceTypeName}</li>
                                </c:forEach>
                            </ul>
                        </div>					
					</div>
				</div>
				<div class="aui-form-item">
					<label class="aui-label">设备型号：</label>
					<div class="form-control">
						<input type="text" class="aui-input" id="deviceModel" maxlength="30" value="${ADDRESS.deviceModel}" name="deviceModel" style="width: 180px;">
					</div>
				</div>
				<div class="aui-form-item">
					<label class="aui-label">使用端口：</label>
					<div class="form-control">
						<input type="text" class="aui-input" id="usePort" maxlength="20" name="usePort" value="${ADDRESS.usePort}"  style="width: 180px;">
					</div>
				</div>
				<div class="aui-form-item">
					<label class="aui-label">到期时间：</label>
					<div class="form-control">
						<input class="aui-input" id="expireDate" name="expireDate" readonly="readonly"  value="<fmt:formatDate value="${ADDRESS.expireDate}" pattern="yyyy-MM-dd"/>"  style="cursor: pointer; background: #fff; width: 180px;">
					</div>
				</div>
				<div class="aui-form-item">
					<label class="aui-label">地址用途：</label>
					<div class="form-control">
						<textarea class="aui-input" rows="2" maxlength="50" id="addressUsage"    name="addressUsage">${ADDRESS.addressUsage}</textarea>
					</div>
				</div>
				<div class="aui-form-item" id="areaItem">
					<label class="aui-label">设备位置：</label>
					<div class="form-control">
						<textarea class="aui-input" rows="2" id="devicePosition" maxlength="50"  name="devicePosition">${ADDRESS.devicePosition}</textarea>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	<div class="aui-panel-footer">
		<div class="aui-grid-10 aui-grid-offset-4">
			<button type="button" class="aui-btn aui-btn-md" id="form-submit">保存</button>
			<button type="button" class="aui-btn aui-btn-md" id="form-cancle">取消</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	require([ 'jquery', 'underscore', "jquery-ui","aui/dimsel" ], function($, _, laydate) {
		$(document).ready(function() {
			$('#deviceType').dimsel();
			$('#expireDate').datepicker({
				showMonthAfterYear : true,
				yearSuffix : '年',
				prevText : '上一月',
				nextText : '下一月',
				monthNamesShort  : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月' ],
				dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
				dateFormat : 'yy-mm-dd',
				changeMonth : true,
				changeYear : true
			});
			$('#form-cancle').click(function(){
				$(".fancybox-overlay").trigger("click");
			});
			$('#form-submit').click(function() {
				var parm = getQueryParm();
				if (parm.deviceType == '-1') {
					alert('请选择设备类型!');
					return;
				}
				if (_.isEmpty(parm.deviceModel)) {
					alert('请输入设备型号!');
					return;
				}
				if (_.isEmpty(parm.usePort)) {
					alert('请输入使用端口!');
					return;
				}
				if (_.isEmpty(parm.expireDate)) {
					alert('请输入到期日期!');
					return;
				}
				if (_.isEmpty(parm.addressUsage)) {
					alert('请输入地址用途!');
					return;
				}
				$.ajax({
					type : 'post',
					url :  _g_const.ctx + '/address/'+$('#addressId').val()+'/saveReg',
					data : parm,
					dataType : 'json',
					contentType : "application/x-www-form-urlencoded;charset=UTF-8",
					success : function(data){
						if(data.success){
							alert('IP地址使用注册信息保存成功！');
							$(".fancybox-overlay").trigger("click");
						}else{
							alert('IP地址使用注册信息保存失败！');
						}
					},
					error : function(xmlhttprequest, textStatus, errorThrown) {
					}
				});
			});
		});
		function getQueryParm() {
			var parmObj = {};
			$('.aui-dimsel').each(function(idx, it) {
				var name = $(it).attr('name');
				if (name) {
					parmObj[name] = $(it).attr('val');
				}
			});
			$(':text,textarea').each(function(idx, it) {
				parmObj[$(it).attr('name')] = $(it).val();
			});
			return parmObj;
		}
	});
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>