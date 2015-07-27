require([ 'jquery', 'module/main', 'aui/tabs' ], function($, main) {
	$(document).ready(function() {
		$('#pool-view-tab').tabs();
		$('#pool-breadcrumb').delegate('a', 'click', function() {
			var val = $(this).attr('val');
			var url = _g_const.ctx;
			if (val === '-9') {
				url += "/pool/show";
			} else if (val && val.substr(0, 2) === 'S_') {
				url += "/subnet/" + val + "/show";
			} else {
				url += "/pool/" + val + "/show";
			}
			main.loadPage(url, {}, 'CLOSEST', $(this));
		});
	});
});