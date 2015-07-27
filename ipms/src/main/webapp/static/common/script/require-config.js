var _baseUrl = "";
if (!!window['_g_const']) {
	_baseUrl = _g_const.ctx + '/static/assets';
} else {
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(0).lastIndexOf('/') + 1);
	_baseUrl = projectName + '/static/assets';
}
require.config({
	// 开发专用，阻止浏览器缓存
	urlArgs : 'v=' + Date.now(),
	baseUrl : _baseUrl,
	paths : {
		'enums' : _g_const.ctx + '/dic/enums',
		'module' : _g_const.ctx + '/static/modules',
		'archivecity' : _g_const.ctx + '/dic/archivecity',
		/** *********************jquery库**************************** */
		'jquery' : 'jquery/1.11.3/jquery',
		/** *********************第三方JS库**************************** */
		'underscore' : 'underscore/1.8.3/underscore',
		'moment' : 'moment/2.10.3/moment',
		'scrollMonitor' : 'scrollMonitor/1.0/scrollMonitor',
		'echart-core' : 'echart/2.2.5/src/echarts',
		'echart-chart' : 'echart/2.2.5/src/chart',
		/** *********************jquery插件************************* */
		'jquery-ui' : 'jquery/plugins/ui/1.11.4/jquery-ui',
		'jqGrid' : 'jquery/plugins/jqGrid/4.8.2/jquery.jqGrid',
		'json' : 'jquery/plugins/json/2.5.1/jquery.json',
		'jqw' : 'jquery/plugins/jqwidgets/3.6.0',
		'ztree' : 'jquery/plugins/ztree/3.5.17/jquery.ztree',
		'blockUI' : 'jquery/plugins/blockUI/2.70/jquery.blockUI-debug',
		'fancybox' : 'jquery/plugins/fancybox/2.1.5/jquery.fancybox-debug',
		'mousewheel' : 'jquery/plugins/mousewheel/3.1.12/jquery.mousewheel-debug',
		/** *********************自定义组件**************************** */
		'aui' : 'aui/script'
	},
	map : {
		'*' : {
			'jquery' : 'jquery-private',
			'css' : 'require/css'
		},
		'jquery-private' : {
			'jquery' : 'jquery'
		}
	},
	shim : {
		'ztree' : [ 'jquery', 'css!ztree.css' ],
		'fancybox' : [ 'css!fancybox.css' ],
		'jquery-ui' : [ 'jquery', 'css!jquery-ui.css' ],
		'echart-chart/pie' : [ 'echart-core' ],
		'echart-chart/bar' : [ 'echart-core' ],
		'echart-chart/line' : [ 'echart-core' ],
		'echart-chart/gauge' : [ 'echart-core' ],
		'echart-chart/radar' : [ 'echart-core' ],
		'jqGrid' : [ 'jquery-ui', 'css!jqGrid.css' ],
		'jqw/jqxcore' : [ 'jquery', 'css!jqw/styles/jqx.base.css', 'css!jqw/styles/jqx.metro.css' ],
		'jqw/jqxmaskedinput' : [ 'jqxcore' ],
		'jqw/jqxbuttons' : [ 'jqxcore' ],
		'jqw/jqxcheckbox' : [ 'jqxcore' ],
		'jqw/jqxmenu' : [ 'jqxcore' ],
		'jqw/jqxdata' : [ 'jqxcore' ],
		'jqw/jqxscrollbar' : [ 'jqw/jqxbuttons' ],
		'jqw/jqxlistbox' : [ 'jqw/jqxscrollbar' ],
		'jqw/jqxdropdownlist' : [ 'jqw/jqxlistbox' ],
		'jqw/jqxdata.export' : [ 'jqw/jqxdata' ],
		'jqw/jqxdatatable' : [ 'jqw/jqxdata', 'jqw/jqxdropdownlist' ],
		'jqw/jqxgrid.pager' : [ 'jqw/jqxgrid' ],
		'jqw/jqxgrid.sort' : [ 'jqw/jqxgrid' ],
		'jqw/jqxgrid.edit' : [ 'jqw/jqxgrid' ],
		'jqw/jqxgrid.export' : [ 'jqw/jqxdata.export', 'jqw/jqxgrid' ],
		'jqw/jqxgrid.selection' : [ 'jqw/jqxgrid' ],
		'jqw/jqxgrid.columnsresize' : [ 'jqw/jqxgrid' ],
		'jqw/jqxgrid' : {
			deps : [ 'jqw/jqxdata', 'jqw/jqxdropdownlist' ],
			exports : 'jQuery'
		},
		'jqw/jqxtreegrid' : [ 'jqw/jqxdata', 'jqw/jqxdatatable' ]
	}
});

// 避免jquery全局变量污染
define('jquery-private', [ 'jquery' ], function($) {
	$(document).ajaxError(function(e, xhr, opts) {
		switch (xhr.status) {
		case (500):
			alert("服务器系统内部错误,错误信息:[" + xhr.textStatus + "]");
			break;
		case (401):
			alert("登录超时,请重新登录！");
			location.href=_g_const.ctx+"/login";
			break;
		case (403):
			alert("无操作权限，请联系管理员！");
			break;
		case (408):
			alert("请求超时！");
			break;
		case (404):
			alert("请求资源不存在！"+opts.url);
			break;
		default:
			alert("未知错误,请联系管理员！错误信息：[" + xhr.textStatus + "]");
		}
	});
	return $.noConflict(true);
});
// 用于设置jqwidget组件全局主题样式
define('jqxcore', [ 'jqw/jqxcore' ], function($) {
	$.jqx.theme = 'metro';
	return $;
});
// 用于简化组件引入依赖模块
define('table', [ 'jqw/jqxdatatable' ]);
define('xgrid', [ 'jqw/jqxgrid', 'jqw/jqxgrid.pager','jqw/jqxgrid.edit', 'jqw/jqxcheckbox','jqw/jqxgrid.selection', 'jqw/jqxgrid.columnsresize' ]);
define('treegrid', [ 'jqw/jqxtreegrid' ]);