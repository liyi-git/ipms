<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<div class="aui-grid-6" style="padding: 0;">
	<div class="aui-panel" style="margin: 0;">
		<div class="aui-panel-body" id="overview-panel">
			<table class="aui-prop-grid">
				<tr>
					<th>总IP数量：</th>
					<td>${TOTAL_DATA["IP_COUNT"]} 个</td>
				</tr>
				<tr>
					<th>已使用IP数量：</th>
					<td>${TOTAL_DATA["USE_COUNT"]} 个</td>
				</tr>
				<tr>
					<th>IP使用率：</th>
					<td>${TOTAL_DATA["USE_PERCENT"]}</td>
				</tr>
				<tr>
					<th>预留IP数量：</th>
					<td>${TOTAL_DATA["KEEP_COUNT"]} 个</td>
				</tr>
				<tr>
					<th>待分配IP数量：</th>
					<td>${TOTAL_DATA["WAIT_COUNT"]} 个</td>
				</tr>
				<tr>
					<th>空闲IP数量：</th>
					<td>${TOTAL_DATA["FREE_COUNT"]} 个</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="aui-grid-6" style="padding: 0;">
	<div class="aui-panel" style="margin: 0;">
		<div class="aui-panel-body" id="chart-panel" style="position: relative;"></div>
		</div>
	</div>
</div>
<div class="aui-grid-12" style="padding: 0;">
	<div class="aui-panel" style="margin: 0;">
		<div class="aui-panel-heading">
			<h3 class="aui-panel-title">地址池分配概况</h3>
		</div>
		<div class="aui-panel-body" id="subnet-overview-panel">
			<ul class="ip-stat-chart" >
			<c:set var="colorAry" value="${fn:split('0195FF,b73ce4,f9cc01,f78c00,83d95c,0395FF,b71fe4,f6cd01,f18c30,43d35c,d73fe4,e6cd31,b11c10,62de5c,b78e00,a3f95c',',')}" />
			<c:set var="idField" value="${param.idField}_ID"/>
			<c:set var="nameField" value="${param.idField}_NAME"/>
			<c:if test="${param.idField=='SUBNET'}">
				<c:set var="nameField" value="${param.idField}_DESC"/>
			</c:if>
			<c:forEach var="it" items="${DATA_LIST}" varStatus="s">
				<fmt:formatNumber pattern="#######0.0000" value="${it.IP_COUNT}" var="ipCount"/>
				<fmt:formatNumber pattern="#######0.0000" value="${TOTAL_DATA['IP_COUNT']}" var="totalCount"/>
				<c:set var="usePercent" value="${ipCount/totalCount*100}"/>
				<fmt:formatNumber pattern="#######0.00" value="${usePercent}" var="percent"/>
				<c:set var="usePercent2" value="${usePercent<1&&usePercent>0?1:usePercent}"/>
				<fmt:formatNumber value="${usePercent2-0.5}" var="percent2" maxFractionDigits="0"/>
				<c:if test="${usePercent!=0}">
					<c:if test="${fn:startsWith(it[idField],'-')}">
						<li title="${percent}%" style="width:${percent2}%;background-color:#dfdfdf;">${percent}%</li>
					</c:if>
					<c:if test="${!fn:startsWith(it[idField],'-')}">
						<li title="${percent}%" style="width:${percent2}%;background-color:#${colorAry[s.index]};">${percent}%</li>
					</c:if>
				</c:if>
			</c:forEach>
			</ul>
			<ul  class="ip-stat-legend" >
			<c:forEach var="it" items="${DATA_LIST}" varStatus="s">
				<fmt:formatNumber pattern="#######0.0000" value="${it.IP_COUNT}" var="ipCount"/>
				<c:set var="usePercent" value="${ipCount/(TOTAL_DATA['IP_COUNT'])*100}"/>
				<c:if test="${usePercent!=0}">
					<c:if test="${fn:startsWith(it[idField],'-')}">
						<li><i style="background-color:#dfdfdf;"></i>${it[nameField]}</li>
					</c:if>
					<c:if test="${!fn:startsWith(it[idField],'-')}">
						<li><i style="background-color:#${colorAry[s.index]};"></i>${it[nameField]}</li>
					</c:if>
				</c:if>	
			</c:forEach>
			</ul>
		</div>
	</div>
</div>