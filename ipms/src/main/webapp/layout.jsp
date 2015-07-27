<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%>
	<div id="page-header">
		<div class="logo">
			<a href="#">中国移动</a>
		</div>
		<div class="binding">
			<span class="binding-cn">MS物流资源管理系统</span>
			<span class="binding-en">Resource Managent System</span>
		</div>
		<a class="toggle-nav" title="收起菜单"></a>
		<ul class="header-func">
			<li class="userinfo">MS系统管理员</li>
		</ul>
	</div>
	<div id="page-container">
		<div class="ipms-nav">
			<ul class="ipms-menu" id="ipms-sidebar">
				<li class="haschild">
					<a href="javascript:;"> <i class="icon-th"></i>
						<span>IP资源管理</span>
					</a>
					<ul class="ipms-menu">
						<li url="">
							<a href="#ipms1" id="test">IP地址段资源管理</a>
						</li>
						<li url="">
							<a href="#ipms2">IP地址池资源管理</a>
						</li>
						<li url="">
							<a href="#ipms3">IP地址自定义查询</a>
						</li>
						<li url="">
							<a href="#ipms4">IP地址操作日志</a>
						</li>
					</ul>
				</li>
				<li class="hasChild">
					<a href="javascript:;"> <i class="icon-list-ol"></i>
						<span>ESOP调用</span>
					</a>
					<ul class="ipms-menu">
						<li url="">
							<a href="#ipms5">IDC/专线申请</a>
						</li>
					</ul>
				</li>
				<li class="hasChild">
					<a href="javascript:;">
						<i class="icon-pencil"></i>
						<span>报表统计</span>
					</a>
					<ul class="ipms-menu">
						<li url="">
							<a href="#ipms6">报表统计一</a>
						</li>
						<li url="">
							<a href="#ipms7">报表统计二</a>
						</li>
						<li url="">
							<a href="#ipms8">报表统计三</a>
						</li>
						<li url="">
							<a href="#ipms9">报表统计四</a>
						</li>
					</ul>
				</li>
				<li class="hasChild">
					<a href="javascript:;">
						<i class="icon-map-marker"></i>
						<span>配置管理</span>
					</a>
					<ul class="ipms-menu">
						<li url="">
							<a href="#ipms10">配置管理一</a>
						</li>
						<li url="">
							<a href="#ipms11">配置管理二</a>
						</li>
						<li url="">
							<a href="#ipms12">配置管理三</a>
						</li>
						<li url="">
							<a href="#ipms13">配置管理四</a>
						</li>
					</ul>
				</li>
				<li class="hasChild">
					<a href="javascript:;">
						<i class="icon-map-marker"></i>
						<span>系统管理</span>
					</a>
					<ul class="ipms-menu">
						<li url="">
							<a href="#ipms14">用户管理</a>
						</li>
						<li url="">
							<a href="#ipms15">权限管理</a>
						</li>
						<li url="">
							<a href="#ipms16">日志统计</a>
						</li>
						<li url="">
							<a href="#ipms17">备份管理</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="ipms-content">
			<!--维度选择区域 start-->
			<div class="aui-dimpanel">
				<div class="aui-dimpanel-item">
					<label>输入框：</label>
					<input type="text" placeholder="Enter Info"></div>

				<div class="aui-dimpanel-item">
					<label>日期选择：</label>
					<div class="aui-datesel">
						<a class="aui-datesel-txt" href="javascript:;">2013-11-01</a>
					</div>
				</div>
				<div class="aui-dimpanel-item">
					<label>地域选择：</label>
					<div class="aui-dimsel" style="width: 150px;">
						<a class="aui-dimsel-txt" href="javascript:;"></a>
					</div>
				</div>
				<div class="aui-dimpanel-item">
					<label>下拉列表：</label>
					<div class="aui-dimsel" style="width: 150px;">
						<a class="aui-dimsel-txt" href="javascript:;">全部品牌</a>
					</div>
				</div>
				<div class="aui-dimpanel-func">
					<button type="button" class="aui-btn aui-btn-sm">查询</button>
				</div>
			</div>
			<div class="aui-filtrate">
			     <dl  class="aui-filtrate-item">
	                  <dt>终端品牌2</dt>
	                  <dd>
	                      <span val="1">苹果</span>
	                      <span val="2">三星</span>
	                      <span val="3">魅族</span>
	                      <span val="4">小米</span>
	                  </dd>
	              </dl>
			</div>
			<!--维度选择区域 end-->

			<!--面包屑导航 start-->
			<ol class="aui-breadcrumb">
				<li>
					<a href="javascript:;">Home</a>
				</li>
				<li>
					<a href="javascript:;">Library</a>
				</li>
				<li class="active">Data</li>
			</ol>
			<!--面包屑导航 end-->

			<!--面板 start-->
			<div class="aui-panel">
				<div class="aui-panel-heading">
					<h3 class="aui-panel-title">aui-panel</h3>
					<div class="aui-panel-func">panel 操作区域</div>
				</div>
				<div class="aui-panel-body"></div>
			</div>
			<!--面板 end-->

			<!--基本表格 start-->
			<div class="aui-panel">
				<div class="aui-panel-heading">
					<h3 class="aui-panel-title">aui-table</h3>
					<div class="aui-panel-func">panel 操作区域</div>
				</div>
				<div class="aui-panel-body">
					<table class="aui-table aui-table-striped aui-table-hover  aui-table-bordered">
						<thead>
							<tr>
								<th>#</th>
								<th>Table heading</th>
								<th>Table heading</th>
								<th>Table heading</th>
								<th>Table heading</th>
								<th>Table heading</th>
								<th>Table heading</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
							</tr>
							<tr>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
							</tr>
							<tr>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
							</tr>
							<tr>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
								<td>Table cell</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!--基本表格 end-->

			<!--选项卡 start-->
			<div class="aui-panel">
				<ul class="aui-tab-panel">
					<li class="aui-tab-item aui-tab-item-cursel">
						<a href="javascript:;">
							<span>TAB选项一</span>
						</a>
					</li>
					<li class="aui-tab-item">
						<a href="javascript:;">
							<span>TAB选项二</span>
						</a>
					</li>
					<li class="aui-tab-item">
						<a href="javascript:;">
							<span>TAB选项三</span>
						</a>
					</li>
					<li class="aui-tab-item">
						<a href="javascript:;">
							<span>TAB选项四</span>
						</a>
					</li>
				</ul>
				<div class="aui-panel-body" style="height:200px;"></div>
			</div>
			<!--选项卡 end-->

			<!--选项卡 分页&按钮 start-->
			<div class="aui-panel">
				<div class="aui-panel-heading">
					<h3 class="aui-panel-title">分页&按钮</h3>
				</div>
				<div class="aui-panel-body">
					<ul class="aui-pagination">
						<li class="prev disabled">
							<a href="javascript:;">上一页</a>
						</li>
						<li class="active">
							<a href="javascript:;">1</a>
						</li>
						<li>
							<a href="javascript:;">2</a>
						</li>
						<li>
							<a href="javascript:;">3</a>
						</li>
						<li class="next">
							<a href="javascript:;">下一页</a>
						</li>
					</ul>
					<p>
						<button type="button" class="aui-btn aui-btn-xs">xs</button>
						<button type="button" disabled="disabled" class="aui-btn aui-btn-sm">small</button>
						<button type="button" class="aui-btn aui-btn-md">middle</button>
						<button type="button" class="aui-btn aui-btn-lg">large</button>
						<button class="aui-btn aui-btn-icon">
							<i class="icon-android"></i>
							android
						</button>
						<button class="aui-btn aui-btn-icon">
							<i class="icon-windows"></i>
							Briefcase
						</button>
						<button class="aui-btn aui-btn-icon">
							<i class="icon-linux"></i>
							windows
						</button>
						<button class="aui-btn aui-btn-icon">
							<i class="icon-skype"></i>
							skype
						</button>
						<button class="aui-btn aui-btn-icon">
							<i class="icon-apple"></i>
							apple
						</button>
					</p>
				</div>
			</div>
			<!--选项卡 分页&按钮 end-->

			<!--选项卡 grid and 两列布局 start-->
			<div class="aui-grid aui-panel-grid">
				<div class="aui-grid-3">
					<div class="aui-panel aui-nomargin aui-noborder">
						<ul class="aui-tab-panel">
							<li class="aui-tab-item aui-tab-item-cursel">
								<a href="javascript:;">
									<span>TAB一</span>
								</a>
							</li>
							<li class="aui-tab-item">
								<a href="javascript:;">
									<span>TAB二</span>
								</a>
							</li>
						</ul>
						<div class="aui-panel-body"></div>
					</div>
				</div>
				<div class="aui-grid-9">
					<div class="aui-panel aui-nomargin aui-noborder">
						<div class="aui-panel-body" style="height:70px;"></div>
					</div>
				</div>
			</div>
			<div class="aui-clear"></div>
			<!--选项卡 grid and 两列布局 end-->

			<!--Form start-->
			<div class="aui-panel">
				<div class="aui-panel-heading">
					<h3 class="aui-panel-title">Form</h3>
				</div>
				<div class="aui-panel-body">
					<div class="col-md-8 col-md-offset-2">
						<div class="aui-grid-8 aui-grid-offset-2"
						<div class="aui-form">
							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<input type="text" class="aui-input" placeholder="Enter Info"></div>
							</div>

							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<input type="text" class="aui-input" placeholder="Enter Info"></div>
							</div>
							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<input type="text" class="aui-input" placeholder="Enter Info"></div>
							</div>
							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<input type="text" class="aui-input" placeholder="Enter Info"></div>
							</div>
							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<input type="text" class="aui-input" placeholder="Enter Info"></div>
							</div>
							<div class="aui-form-item">
								<label class="aui-label aui-grid-4">label</label>
								<div class="aui-grid-8">
									<textarea name="" id="" class="aui-input" rows="4"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--Form end-->

		</div>
	</div>
<script type="text/javascript" src="<c:url value="/static/modules/ipsplit.js"/>"></script>
<script type="text/javascript">
	var $IP=$ip("#page-header");
	// var declimaIp=$IP.getNetDecimal("192.168.0.103","255.0.0.0");
	// var addr=$IP.decimalToAddr(declimaIp);
	// var broadcastIp=$IP.getBroadcastDec("192.168.0.103","255.255.255.0");
	//var usableinfo=$IP.getUsableAsObj("19.168.0.103","255.255.255.0");
	// console.log("网络地址-------"+"十进制："+declimaIp+"::::二进制："+addr);
	// console.log("广播地址------"+$IP.decimalToAddr(broadcastIp));
	//console.log("可用Ip数量："+usableinfo.uIpNum+" 第一个可用IP："+usableinfo.firstIp+" 最后一个可用IP："+usableinfo.lastIp+" 主机数量："+usableinfo.hostNum);
	//var test=$IP.getObjBySubnetNum("192.168.0.103","27");
	//console.log(test);
	//var test=$IP.getObjByHostNum("168.195.0.0","700");
	// /console.log(test);
	var test=$IP.getObjByIpNum("192.168.0.3","20");
	//console.log(test);
</script>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>