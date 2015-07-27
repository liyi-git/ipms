;
define([ 'jquery', 'underscore', 'aui/loading' ], function($, _, loading) {
	"use strict";
	function loadPage(url, dataParms, target, $selector) {
		var $pageContainer;
		// 在页面内容容器加载（菜单、内容两栏布局）
		if (!target || target === 'MAIN') {
			$pageContainer = $('.page-container').first();
		}
		// 替换整个页面
		if (target && target === 'TOP') {
			$pageContainer = $(document.body);
		}
		// 根据指定容器加载
		if (target && target === 'ASSIGN') {
			if ($selector && $selector instanceof $) {
				$pageContainer = $selector;
			}
		}
		// 获取距离最近的容器加载
		if (target && target === 'CLOSEST') {
			if ($selector && $selector instanceof $) {
				$pageContainer = $selector.closest('.page-container');
			}
		}
		if (!$pageContainer || $pageContainer.length == 0) {
			$pageContainer = $(document.body);
		}
		$pageContainer.empty();
		loading.show($pageContainer);
		$.ajax({
			type : "POST",
			async : true,
			dataType : "html",
			data : dataParms,
			url : url,
			success : function(data) {
				$pageContainer.html(data);
				loading.hide($pageContainer);
			},
			error : function(xmlhttprequest, textStatus, errorThrown) {
				$pageContainer.html(xmlhttprequest.responseText);
				loading.hide($pageContainer);
			}
		});
	}
	//加载圆形占比图表
	function loadCircleChart(echart, data,divId) {
		var labelTop = {
			normal : {
				label : {
					show : true,
					position : 'center',
					formatter : '{b}',
					textStyle : {
						baseline : 'bottom'
					}
				},
				labelLine : {
					show : false
				}
			}
		};
		var labelBottom = {
			normal : {
				color : '#ccc',
				label : {
					show : true,
					position : 'center'
				},
				labelLine : {
					show : false
				}
			},
			emphasis : {
				color : 'rgba(0,0,0,0)'
			}
		};
		var labelFromatter = {
			normal : {
				label : {
					formatter : function(params) {
						return new Number(100 - params.value).toFixed(2) + '%'
					},
					textStyle : {
						baseline : 'top'
					}
				},
				color : function(_obj) {
					return [ '#61a9dc', '#f0a0ef', '#a9ca2c' ][_obj['seriesIndex']];
				}
			},
		};
		var option = {
			series : [ {
				type : 'pie',
				center : [ '20%', '50%' ],
				radius : [ 35, 50 ],
				itemStyle : labelFromatter,
				data : [ {
					name : '测试',
					value : new Number(100 - data.usePercent).toFixed(2),
					itemStyle : labelBottom
				}, {
					name : '已使用',
					value : new Number(data.usePercent).toFixed(2),
					itemStyle : labelTop
				} ]
			}, {
				type : 'pie',
				center : [ '50%', '50%' ],
				radius : [ 35, 50 ],
				itemStyle : labelFromatter,
				data : [ {
					name : 'other',
					value : new Number(100 - data.keepPercent).toFixed(2),
					itemStyle : labelBottom
				}, {
					name : '已预留',
					value : new Number(data.keepPercent).toFixed(2),
					itemStyle : labelTop
				} ]
			}, {
				type : 'pie',
				center : [ '80%', '50%' ],
				radius : [ 35, 50 ],
				itemStyle : labelFromatter,
				data : [ {
					name : 'other',
					value : new Number(100 - data.freePercent).toFixed(2),
					itemStyle : labelBottom
				}, {
					name : '空闲',
					value : new Number(data.freePercent).toFixed(2),
					itemStyle : labelTop
				} ]
			} ]
		};
		echart.init($(divId).get(0)).setOption(option);
	};
	return {
		loadPage:loadPage,
		loadCircleChart:loadCircleChart
	}
})