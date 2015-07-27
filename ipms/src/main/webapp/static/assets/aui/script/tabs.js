define(["jquery", "underscore", "jquery-ui","aui/loading","aui/core"], function(jQuery,_,ui,loading) {
	(function($, undefined) {
		$.widget("aui.tabs",$.aui.core, {
			/**属性定义**/
			options : {
				val:null,
				'trigger-type':'click',
				onchange:$.noop,
				onbeforeload:$.noop,
				onload:$.noop
			},
			_getItems:function(){
				var items=this.options.items||[];
				if(!items.length){
					$(this.element).find('>ul >li').each(function(){
						var $li=$(this);
						items.push({val:$li.attr('val'),url:$li.attr('url'),txt:$li.text(),idx:$li.index(),target:$li.attr('target')});
					});
				}
				return items;
			},
			/**常量定义**/
			_const:{
				cls:'aui-tab-'
			},
			/**组件初始化创建**/
			_create : function() {
				var $ele = $(this.element),_opt=this.options,_cls=this._const.cls, self = this;
				$ele.css('position','relative');
				this._initOptions();
				var liAry=[];
				var tpl=_.template("<li class='"+_cls+"item' url='{{=url}}' target='{{=target}}' idx='{{=idx}}' val='{{=val}}'><a href='javascript:;'><span>{{=txt}}</span></a></li>");
				_opt.targets=_opt.targets||[];
				$.each(this._getItems(),function(){
					liAry.push(tpl(this));
					$('#'+this.target).hide();
					_opt.targets.push(this.target);
				});
				delete this.options.items;
				_opt.targets=_.uniq(_opt.targets);
				var $lis=$(liAry.join(""));
				//this.curTabid=$ele.attr('val');
				var $ul=$('>ul',$ele).empty().append($lis).show();
				//this._loadMorePanel($ul);
				this._handelEvent();
				if($lis.filter('[val="'+_opt.val+'"]').length > 0 && !$lis.filter('[val="'+_opt.val+'"]').is(":hidden")){
					$lis.filter('[val="'+_opt.val+'"]').trigger('click');
				}else{
					$('#'+this.element.attr("id") + "-morePanel > li").filter('[val="'+_opt.val+'"]').trigger('click');
				}
			},
			_loadMorePanel:function($ul){
				$('ul[id='+panelId+'][class='+this._const.cls+'morepanel]').remove();
				var _self = this;
				var ulWidth = $ul.width() - 50;
				var allLi = $('li',$ul);
				var showLiWidth = null;
				var appendLi = "";
				var tpl_more = _.template("<li class='"+this._const.cls+"item' idx='{{=idx}}' val='{{=val}}'><a href='javascript:;'><span>{{=txt}}</span></a></li>");
				var tpl_showMore = _.template("<li class='"+this._const.cls+"item' showMore='show' idx='{{=idx}}' target='{{=target}}' val='{{=val}}'><a href='javascript:;'><span>{{=txt}}</span></a></li>");
				var tabMore = "";
				var showMore = "";
				for(var i = 0;i < allLi.length;i++){
					if((ulWidth - showLiWidth) > $(allLi[i]).outerWidth()){
						showLiWidth += $(allLi[i]).outerWidth();
					}else{
						if(i - 1 >= 0){
							$(allLi[i - 1]).hide();
						}
						if(i - 2 >= 0){
							$(allLi[i - 2]).hide();
						}
						$(allLi[i]).hide();
					}
				}
				if($('li:hidden',$ul).length > 0){
					var panelId = this.element.attr("id") + "-morePanel";
					tabMore = $(tpl_more({val:'more',txt:'更多',idx:''}));
					$ul.append(tabMore);
					this.$morePanel=$('<ul id='+panelId+' class="'+this._const.cls+'morepanel">');
					var tpl=_.template("<li url='{{=url}}' target='{{=target}}' idx='{{=idx}}' val='{{=val}}'><a href='javascript:;' title='{{=txt}}'><span>{{=txt}}</span></a></li>");
					var $lis = $('li:hidden',$ul);
					$lis.each(function(){
						var $li=$(this);
						_self.$morePanel.append(tpl({val:$li.attr('val'),txt:$li.text(),idx:$li.attr('idx'),url:$li.attr('url'),target:$li.attr('target')}));
					});
					$("#"+panelId).remove();
					this.$morePanel.appendTo(document.body);
					tabMore.hover(function(){
						_self._show=false;
						var offTop = tabMore.offset().top;
						var offLeft = tabMore.offset().left;
						_self.$morePanel.css('top',tabMore.outerHeight() + offTop);
						if(offLeft > _self.$morePanel.width()){
							_self.$morePanel.css('left',offLeft - _self.$morePanel.width() + tabMore.outerWidth()).show();
						}else{
							_self.$morePanel.css('left',offLeft).show();
						}
					},function(){
						_.delay(function(obj){
							if(!obj._show){
								obj.$morePanel.hide();
							}
						},100,_self);
					});
					this.$morePanel.delegate('li','click',function(e){
						var $li=$(this);
						if($('li[showMore=\'show\']',$ul).length <= 0){
							showMore = $(tpl_showMore({val:$li.attr('val'),idx:$li.attr('idx'),url:$li.attr('url'),target:$li.attr('target'),txt:$('a span',$li).text()}));
							showMore.insertBefore($(tabMore,$ul));
						}
						var a=showMore.attr('val',$li.attr('val')).attr('idx',$li.attr('idx')).attr('url',$li.attr('url')).attr('target',$li.attr('target')).find('a');
						a.html(a.html().replace(a.text(),$li.text()));
						showMore.trigger('click');
						_self.$morePanel.hide();
					}).mouseleave(function(){
						_self.$morePanel.hide();
					}).mouseenter(function(){
						_self._show=true;
					});
				}
			},
			_loadTab:function($li,$div){
				var url=$li.attr('url'),_opt=this.options,_obj={parms:{}},tabId=$li.attr('val');
				$div.empty();
				loading.show($div);
				_opt.onbeforeload.call(_obj,tabId,url);
				$.ajax({
					   type: "POST",dataType:"html",
					   url: url,data: _obj.parms,
					   success: function(data){
					   		_.delay(function(){
					   			loading.hide($div);
					   			$div.css('height','').html(data);
					   			_opt.onload.call(null,tabId,url);
					   		},200);
					   },
					   error:function(xmlhttprequest, textStatus, errorThrown){},
					   statusCode:{
					   		404:function(){$div.html('请求资源不存在!');},
					   		500:function(){$div.html('系统发生错误,请联系管理员!');}
					   },
					   complete:function(){
						   	if(typeof $($li).attr('showmore') == "undefined"){
						   		$li.data('init',1);
						   	}
				   			//loading.hide($div);
					   }
				});
			},
			select:function(tabId,isReload){
				var $lis=$(this.element).find('li.aui-tab-item');
				if(!tabId){
					var tabId=this.curTabid;
					this.curTabid=null;
				}
				var $li=$lis.filter('[val="'+tabId+'"]');
				if(isReload){
					$('#'+$li.attr('target')).empty();
					$li.removeData('init');
				}
				$li.trigger('click');
			},
			reload:function(tabId){	
				this.select(tabId,true);
			},
			getVal : function() {
				return this.getObj()['val'];
			},
			getTxt:function(){
				return this.getObj()['txt'];
			},
			getIndex:function(){
				return this.getObj()['idx'];
			},
			getObj:function(){
				return {
					val:$(this.element).attr("val"),
					txt:$(this.element).attr("txt"),
					idx:$(this.element).attr('idx')
				};
			},		
			/**事件处理**/
			_handelEvent : function() {
				var _opt=this.options,_self=this,evt='click';
				if(_opt['trigger-type']=='over'){
					evt='mouseenter';
				}
				$(this.element).delegate('ul.aui-tab-panel >li[val!=more][idx!=\'\']',evt,function(e){
					var cls=_self._const.cls+'item-cursel',$li=$(this);
					$li.siblings().removeClass(cls).end().addClass(cls);
					if(_opt.targets.length>1){
						$.each(_opt.targets,function(){$('#'+this).hide();});
					}
					var $div=$('#'+$li.attr('target'));
					if($div.height()<10){
						$div.css('min-height','450px');	
					}
					$div.show();
					var tabId=$li.attr('val');
					if(!_self.curTabid||_self.curTabid!=tabId){
						_self.element.attr('val',tabId).attr('txt',$li.text()).attr('idx',$li.attr('idx'));
						_self._trigger("onchange",e,{
							val:tabId,
							txt:$li.text(),
							idx:$li.attr('idx')
						});
						_self.curTabid=tabId;
					}
					if(!_.isEmpty($li.attr('url'))&&($li.data('init')!=1||_opt.targets.length==1)){
						if(_opt.targets.length == 1){
							_self._loadTab($li,$div);
						}else{
							if($('#'+$li.attr('target')).children().length == 0){
								_self._loadTab($li,$div);
							}
						}
					}
				});
			}
		});
	})(jQuery);	
});