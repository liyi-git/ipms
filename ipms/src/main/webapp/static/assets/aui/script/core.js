define([ "jquery", "underscore", "jquery-ui"], function(jQuery, _) {
	(function($, undefined) {
		$.widget("aui.core", {
			/** 属性定义* */
			options : {},
			_initOptions : function() {
				var attrs = this.element.get(0).attributes, self = this;
				var propAry = [];
				for ( var prop in this.options) {
					propAry.push(prop);
				}
				$.each(attrs, function(idx, attr) {
					if (attr.specified) {
						var val = attr.nodeValue;
						if (val === 'true')
							val = true;
						if (val === 'false')
							val = false;
						if ($.inArray(attr.nodeName, propAry) != -1) {
							self.options[attr.nodeName] = val;
						}
					}
				});
			},
			isStaticContainer : function($ele) {
				if ($ele.get(0) == document.body)
					return true;
				while ($ele.parent().get(0) != document.body) {
					var pos = $ele.parent().css('position');
					if (pos == 'relative' || pos == 'fixed') {
						return false;
					}
					$ele = $ele.parent();
				}
				return true;
			}
		});
	})(jQuery);
});