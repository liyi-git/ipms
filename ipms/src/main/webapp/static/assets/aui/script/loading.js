define(["jquery", "underscore", "jquery-ui","blockUI"], function($,_) {
	return function(){
		var _options={
			css:{
				border: 'none',
				width:'150px',
				cursor:'auto',
				'-webkit-border-radius': '5px', 
	            '-moz-border-radius': '5px'
	       	},
	       	fadeIn:0,
	       	overlayCSS:  { 
	        	backgroundColor: '#000', 
	        	opacity:0.6
	    	}
		};
		function _getOptions(_opt){
			var $loadingDiv=$('#aui-loading-page');
			if(!$loadingDiv.length){
				$loadingDiv=$('<div id="aui-loading-page" class="aui-loading-page"><span>加载中 ......</span></div>').appendTo('body');
			}
			_options['message']=$loadingDiv;
			return _options;
		}
		return {
			show:function(selector,opt){
				var $selector=(selector instanceof $)?selector:$(selector);
				var _opt=_getOptions(opt);
				if(selector==null){
					$.blockUI(_opt);
				}else if($selector.length){
					$selector.block(_opt);
				}
			},
			hide:function(selector,opt){
				var $selector=(selector instanceof $)?selector:$(selector);
				var _opt=_getOptions(opt);
				if(selector==null){
					$.unblockUI(_opt);
				}else if($selector.length){
					$selector.unblock(_opt);
				}				
			}
		};
	}();
});