define(["jquery", "underscore", "jquery-ui", "blockUI","aui/core"], function(jQuery,_) {
	(function($, undefined) {
		$.widget("aui.toggle", $.aui.core,{
			/**属性定义**/
			options : {
				val:null,
				items:null,
				showcount:5,
				cascade:null,
				onclick : $.noop,
				onchange : $.noop
			},
			_getItems:function(){
				var items=this.options.items||[];
				if(!items.length){
					$(this.element).find('>ul >li').each(function(){
						var $li=$(this);
						items.push({val:$li.attr('val'),txt:$li.text(),idx:$li.index()});
					});
				}
				return items;
			},
			/**常量定义**/
			_const:{
				cls:'aui-toggle-'
			},
			/**组件初始化创建**/
			_create : function() {
				var $ele = $(this.element),_opt=this.options,_cls=this._const.cls, self = this;
				$ele.css('position','relative');
				this._initOptions();
				var liAry=[];
				var tpl=_.template("<li val='{{=val}}' idx='{{=idx}}'><a href='javascript:;'>{{=txt}}</a></li>");
				$.each(this._getItems(),function(){
					liAry.push(tpl(this));
				});
				var $lis=$(liAry.join(""));
				$lis.eq(0).addClass(_cls+'item-left');
				$lis.filter(':gt('+(_opt.showcount-1)+')').hide();
				var $curselli=$lis.filter('[val="'+_opt.val+'"]');
				var $ul=$('>ul',$ele).empty().append($lis).show();
				/**大于显示数量时，需显示更多下拉项**/
				if($lis.length>_opt.showcount){
					var parm;
					var tpl_more=_.template("<li val='{{=val}}' idx='{{=idx}}' class='{{=cls}}'><a href='javascript:;'>{{=txt}}<span class='aui-icon'></span></a></li>");
					if($curselli.index()>(_opt.showcount-1)){
						this.$more=$(tpl_more({val:$curselli.attr('val'),idx:$curselli.attr('idx'),txt:$curselli.text(),cls:$curselli.attr('class')}));
						$curselli=this.$more;
					}else{
						this.$more=$(tpl_more({val:'more',txt:'更多',cls:'',idx:''}));
					}
					this.$more.addClass(_cls+'item-more '+_cls+'item-right').appendTo($ul);
					this._initMorePanel();
				}else{
					$lis.last().addClass(_cls+'item-right');
				}
				this._handelEvent();
				$curselli.trigger('click');
			},
			_initMorePanel:function(){
				this.$morePanel=$('<ul class="'+this._const.cls+'morepanel">').css('top',this.$more.outerHeight()).appendTo(this.element);
			},
			_showMore:function(left){
				var $lis=$('ul.aui-toggle > li:hidden',this.element),self=this;
				var tpl_more_li=_.template("<li val='{{=val}}' idx='{{=idx}}'><a href='javascript:;'>{{=txt}}</a></li>");
				this.$morePanel.empty();
				$lis.each(function(){
					var $li=$(this);
					self.$morePanel.append(tpl_more_li({val:$li.attr('val'),txt:$li.text(),idx:$li.attr('idx')}));				
				});
				this.$morePanel.css('left',left).show();
			},
			_setSelectValue:function(result){
				this.curVal=result.val;
				$(this.element).attr('val',result.val).attr('txt',result.txt).attr('idx',result.idx);
			},
			/**事件处理**/
			_handelEvent : function() {
				var _opt=this.options,_self=this;
				$(this.element).delegate('ul.aui-toggle >li','click',function(e){
					var cls=_self._const.cls+'item-cursel';
					var _$li=$(this);
					var _result={val:_$li.attr('val'),txt:_$li.text(),idx:_$li.attr('idx')};
					if(_result.val=='more')return;
					_$li.siblings().removeClass(cls).end().addClass(cls);
					if(!_self.curVal||_self.curVal!=_result.val){
						_self._setSelectValue(_result);
						_self.options.onchange.call(null,e,_result,_self);
					}
					_self.options.onclick.call(null,e,_result,_self);
				});
				if(!_.isUndefined(this.$more)){
					this.$more.hover(function(){
						_self._show=false;
						_self._showMore($(this).position().left);
					},function(){
						_.delay(function(obj){
							if(!obj._show){
								obj.$morePanel.hide();
							}
						},100,_self);
					});
					this.$morePanel.delegate('li','click',function(e){
						var $li=$(this);
						var a=_self.$more.attr('val',$li.attr('val')).attr('idx',$li.attr('idx')).find('a');
						a.html(a.html().replace(a.text(),$li.text()));
						_self.$more.trigger('click');
						_self.$morePanel.hide();
					}).mouseleave(function(){
						_self.$morePanel.hide();
					}).mouseenter(function(){
						_self._show=true;
					});
				}
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
			toggle:function(val){
				var $lis=$(this.element).find('ul.aui-toggle >li');
				if(!val){
					var val=this.curVal;
					this.curVal=null;
				}
				var $li=$lis.filter('[val="'+val+'"]');
				if($li.is(":hidden")){
					var a=this.$more.attr('val',$li.attr('val')).attr('idx',$li.attr('idx')).find('a');
					a.html(a.html().replace(a.text(),$li.eq(0).text()));
					this.$more.trigger('click');					
				}else{
					$li.trigger('click');
				}
			},
			_destroy :function(){
				$(this.element).find('*').unbind();
			}
		});
	})(jQuery);	
});