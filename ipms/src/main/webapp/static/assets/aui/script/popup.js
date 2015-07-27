define(["jquery", "underscore", "fancybox"], function($, _) {
	return {
		_resizeInterval : "",
		_options : {
			padding : '10px',
			modal : false,
			centerOnScroll : true,
			overlayShow : true,
			openEffect : 'elastic',
			titlePosition : 'over',
			closeBtn : true,
			title : "",
			autoScale : true,
			tpl : {
				closeBtn : '<a title="关闭" class="aui-popup-close" href="javascript:;"><i class="icon-remove"></i></a>',
				wrap : '<div class="fancybox-wrap aui-popup-wrap" tabIndex="-1"><div class="fancybox-skin"><div class="fancybox-outer"><div class="fancybox-inner"></div></div></div></div>',
				error : '<p class="fancybox-error">页面加载失败!请稍候重试!</p>'
			},
			helpers : {
				overlay : {
					closeClick : true
				},
				title : {
					type : 'inside',
					position : 'top'
				}
			}
		},
		_getOptions : function(title, url, opts, callback) {
			var _opt = {};
            var self=this;
			var _options = this._options;
			_.extend(_opt, _options);
			_.extend(_opt, opts);
			_opt['title'] ="<div class='aui-popup-title'>"+title+"</div>";
			_opt['href'] = url;
			_opt['afterClose'] = function() {
				//self._clearResizeInterval();
				if ($.isFunction(callback)) {
					callback.call();
				}
			};
			return _opt;
		},
		/**
		_setResizeInterval : function() {
            var self=this;
			$(window).resize(function() {
						self._clearResizeInterval();
					});
			_resizeInterval = window.setInterval(function() {
				if ($('.fancybox-inner').children().length > 0) {
					if ($('.fancybox-inner').children("iframe").length <= 0) {
						$('.fancybox-inner').height("");
						$('.fancybox-inner').css({
									"overflow-x" : "hidden",
									"overflow-y" : "auto"
								});
						var windowHeight = $(window).height();
						var top = $('.fancybox-wrap').offset().top;
						var openDivHeight = $('.fancybox-wrap').height();
						if (windowHeight > openDivHeight) {
							$('.fancybox-wrap').css({
										top : (windowHeight - openDivHeight)
												/ 2
									});
						} else {
							$('.fancybox-inner').height(windowHeight - 150);
							$('.fancybox-wrap').css({
										top : 30
									});
							$('.fancybox-inner').scrollTop(5);
							var scrollTop = $('.fancybox-inner').scrollTop();
							if (scrollTop > 0) {
								var openDivWidth = $('.fancybox-inner').width();
								var contentWidth = 0;
								if ($('.fancybox-inner').children().length > 0) {
									contentWidth = $($('.fancybox-inner')
											.children()[0]).width();
								}
								if ((openDivWidth - contentWidth) < 35) {
									$('.fancybox-inner')
											.width($('.fancybox-inner').width()
													+ 16);
								}
								self._clearResizeInterval();
							}
						}
					}
				}
			}, 1000);
		},
		_clearResizeInterval : function() {
			window.clearInterval(_resizeInterval);
		},
		**/
		openFrame : function(title, url, opts, callback) {
			var opts = this._getOptions(title, url, opts, callback);
			opts['type'] = 'iframe';
			$.fancybox.open(opts);
			//this._setResizeInterval();
		},
		openDiv : function(title, url, opts, callback) {
			var opts = this._getOptions(title, "#" + url, opts, callback);
			opts['type'] = 'inline';
			$.fancybox.open(opts);
			//this._setResizeInterval();
		},
		openAjax : function(title, url, opts, callback) {
			var opts = this._getOptions(title, url, opts, callback);
			opts['type'] = 'ajax';
			$.fancybox.open(opts);
			//this._setResizeInterval();
		},
		close : function() {
			$.fancybox.close();
		}

	};
});