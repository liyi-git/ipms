require([ 'jquery', 'module/main', 'ztree', 'aui/tabs' ], function($, main) {
	var URL_getIPTree = _g_const.ctx + "/pool/getPoolTreeFor";
	var URL_showPoolView = _g_const.ctx + "/pool/showPoolView/";
	$(document).ready(function() {
		var pooltree;
		// 地址池树Tab页
		$('#pool-viewby').tabs({
			onchange : function(e, obj) {
				if (pooltree) {
					pooltree.destroy();
					pooltree.setting.async.url = URL_getIPTree + obj.val;
					pooltree = $.fn.zTree.init($("#poolTree"), pooltree.setting);
				} else {
					pooltree = initIPPoolTree();
				}
			}
		});
		loadPoolView(_g_const.ctx + "/pool/show/");
	});
	// 初始化生成地址池树
	function initIPPoolTree() {
		var setting = {
			data : {
				keep : {
					parent : false
				},
				key : {
					name : "NAME"
				},
				simpleData : {
					enable : true,
					idKey : "ID",
					pIdKey : "PID",
					rootPId : "-9"
				}
			},
			async : {
				enable : true,
				url : URL_getIPTree + $('#pool-viewby').attr('val')
			},
			callback : {
				onClick : function(e, treeId, node) {
					clickTreeNode(treeId, node);
				}
			}
		};
		return $.fn.zTree.init($("#poolTree"), setting);
	}
	function clickTreeNode(treeId, node) {
		var url = _g_const.ctx + "/";
		if (node == '-9') {
			url += "pool/show"
		}else if(node.TYPE == 'S'){
			url += "subnet/" + node.ID + "/show";
		}else{
			url+="pool/"+node.TYPE+"_"+node.ID+"/show";
		}
		loadPoolView(url);
	}
	function loadPoolView(url) {
		main.loadPage(url, {}, "ASSIGN", $('#pool-view-panel'));
	}
});