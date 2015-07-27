define(["jquery", "underscore","jquery-ui","aui/core"], function(jQuery,_) {
	(function($, undefined) {
		$.widget("aui.dimsel",$.aui.core, {
			options : {
				name:'',
				width : 150,        //维度选择框宽度
				itemwidth : 'auto',  //维度选择项宽度,设置为auto时,宽度和维度选取框文字展示区域大小一致
				cols : 1,         //选择弹出层展示列数
				rows : 5,         //选择弹出层最大展示行数
				showtips : false,  //是否展示提示信息
				tip:'',
				multisel : false,  //是否多选
				suggest : false,  //是否支持输入建议
				drill : false,   //钻取
				placeholder : '请选择',//默认未选择时显示文字
				showall:false,//是否默认添加全部
				alltxt:'全部',
				allval:'-1',
				val:null,//默认选中值
				ajax : false,     //是否异步获取数据
				url : '', //异步获取数据时的URL
				params:null,//异步url参数
				asyndrill:false,//异步钻取
				onchange : $.noop, //选择改变时事件
				parentNode: $(document.body)
			},
			_const : {
				scrollwidth : 18,//纵向滚动条宽度
				cls : 'aui-dimsel',
				drillTitle:"点击钻取到下一级"
			},
			_create : function() {
				this._initOptions();
				var self=this;
				$(this.element).width(this.options.width).attr({val:'-1',txt:''}).addClass(this._const.cls+(this.options.multisel==true?(" "+this._const.cls+"-multi"):""));	
				//创建维度选取input
				this._buildDimselInput();
				//创建维度选择弹出层div
				this._buildDimselPanel(function(){
					//self._resetDimPanel();
					self._handleEvent();
					/**如果存入参数有默认选中值，则选中**/
					if(self.options.val){
						var val=self.options.val||"-1";
						self.select(val);
					}
				});
				//创建提示信息显示区
				if(this.options.showtips==true){
					this.$tips=$('<div class='+this._const.cls+'-tips>').text(this.options.tip).appendTo(this.$dimpanel);
				}
			},
			/***************************************** 私有函数BEGIN ***************************************/
			/**创建维度选取input区域**/
			_buildDimselInput:function(){
				//如果是可通过suggest提示输入,增加输入框
				var $a=$('<a class="'+this._const.cls+'-txt" href="javascript:;"></a>');
				if (this.options.suggest) {
					this.$input = $('<input type="text"/>').appendTo($a);
				}else{
					this.$input=$a;
				}
				this._setInput();
				$(this.element).append($a);
			},
			/**设置维度选择框文本和值**/
			_setInput:function(val,txt){
				var tagName=this.$input.get(0).tagName;
				if(_.isUndefined(val)||val==''){
					txt=this.options.placeholder;
				}
				if(tagName=='INPUT'){
					this.$input.val(txt);
				}else{
					this.$input.text(txt);
				}
			},
			/**创建维度选择项弹出层panel,支持三种数据源**/
			_buildDimselPanel:function(callback){
				//如果是异步获取数据
				var self=this;
				if(this.options.ajax&&this.options.url!=''){
					$.getJSON(this.options.url,this.options.params,function(data){
						self._buildDimItem(data);
						callback.call(self);					
					});
				//如果是外部传入的JSON对象数组
				}else if($.isArray(this.options.ds)){
					this._buildDimItem(this.options.ds);
					callback.call(this);
					delete this.options.ds;
				//否则读取DOM结构中的UL
				}else{
					this._buildDimItem($(">ul",this.element));
					callback.call(this);
				}
			},
			/**根据数据对象递归创建UL**/
			_buildULLoop:function(data){
				var ary=[],children=data['children'],self=this;
				ary.push("<li val='"+data['ID']+"'>"+data['TXT']);
				if(_.isArray(children)){
					ary.push('<ul>');
					$.each(children,function(idx){
						ary.push(self._buildULLoop(children[idx]));
					});
					ary.push('</ul>');
				}
				return ary.push('</li>');
			},
			/**创建维度项**/
			_buildDimItem:function(data){
				var tpl='<li val="{{=val}}">{{=txt}}</li>';
				var _cls=this._const.cls+'-panel'+(this.options.cols>1?(' '+this._const.cls+'-panel-cols'):'');
				this.$dimpanel=$('<div class="'+_cls+'">');
				var $ul=$("<ul class='"+this._const.cls+"-items'>").attr('main',1);	
				//如果是dom元素
				if((data instanceof $)){
					if(_.isElement(data[0])){
						if(this.options.drill==true){
							var navli=data.children(':eq(0)');
							this._addDrillNav({val:navli.attr('val'),txt:this._getLiTxt(navli)});
							$ul.append($('>ul',navli).children().find('ul').hide().end());
						}else{
							$ul.append(data.children());
						}
						data.remove();
					}
				//如果不是dom元素
				}else if(data){
					//如果是钻取，应该返回对象
					if(this.options.drill==true){
						if(!$.isPlainObject(data)&&console)console.error('钻取维度,数据源必须为对象!');
						var array=this._buildULLoop(data);
						$ul.append(array.join(""));
					//如果不是钻取，应该返回数组
					}else{
						if(!$.isArray(data)&&console)console.error('非钻取维度,数据源必须为数组!');
						for(var i=0;i<data.length;i++){
							$ul.append(_.template(tpl,{val:data[i]['ID'],txt:data[i]['TXT']}));
						}
					}
				}
				/**添加全部选项**/
				if(this.options.showall&&!this.options.drill){
					var firstli=$('>li',$ul).first();
					var allli='<li val="'+this.options.allval+'">'+this.options.alltxt+'</li>';
					if(firstli.length){
						$(allli).insertBefore(firstli);
					}else{
						$ul.append(allli);
					}
				}
				this.$dimpanel.uniqueId().append($ul).appendTo(this.options.parentNode).width($(this.element).width());
				this._reLayoutDimItems($ul);
			},
			/**重设导航栏**/
			_resetDrillNav:function($target){
				var $navUL=$('.'+this._const.cls+'-nav',this.$dimpanel);
				if(!$target)$target=$('>li',$navUL).eq(0);
				$target.nextAll().not('.'+this._const.cls+'-nav-selectAll').remove();
				$('.'+this._const.cls+'-nav-selectAll',$navUL).attr({val:$target.attr('val')}).text('选择'+$target.text());
			},
			/**重设弹出层**/
			_resetDimItemsPanel:function(){
				var $ul=$('.'+this._const.cls+'-items',this.$dimpanel);
				$ul.filter('[main!=1]').empty().hide();
				$ul.filter('[main=1]').show();
			},
			/**添加钻取导航项**/
			_addDrillNav:function(nav){
				var $navUL=$('.'+this._const.cls+'-nav',this.$dimpanel);
				var cursel_cls=this._const.cls+'-nav-cursel';
				if(!$navUL.length){
					$navUL=$('<ul class="'+this._const.cls+'-nav"/>');
					this.$dimpanel.append($navUL);
				}
				$navUL.children().removeClass(cursel_cls);
				/**添加修改选择全部链接**/
				var $selAllLi=$('.'+this._const.cls+'-nav-selectAll',$navUL);
				if(!$selAllLi.length){
					$selAllLi=$("<li class='"+this._const.cls+"-nav-selectAll' val='"+nav['val']+"'>选择"+nav['txt']+"</li>").appendTo($navUL);
				}else{
					$selAllLi.attr({val:nav['val']}).text("选择"+nav['txt']);
				}
				/**添加导航项**/
				$('<li class="'+cursel_cls+'" val="'+nav['val']+'">'+nav['txt']+'</li>').insertBefore($selAllLi);
			},
			/**创建钻取维度面板**/
			_buildDrillPanel:function($lis){
				var $drillUL=$('.'+this._const.cls+'-items',this.$dimpanel).hide().filter('[main=-1]');
				if(!$drillUL.length){
					var $ul=$('.'+this._const.cls+'-items',this.$dimpanel);
					var $drillUL=$ul.clone().attr('main',-1);
				}
				$drillUL.empty().append($lis.clone()).insertAfter($ul);
				this._reLayoutDimItems($drillUL);
				return $drillUL;
			},
			/**根据行列设置,重新计算维度项布局**/
			_reLayoutDimItems:function($ul){
				var opt=this.options,self=this,$lis=$('>li',$ul);
				/**显示弹出层,计算布局**/
				$ul.show();
				if(!$lis.length)return;
				var isHidden=this.$dimpanel.is(":hidden");
				if(isHidden){
					this.$dimpanel.css('z-index',-9999).show();
				}
				/**如果维度项数量小于列数设置项,将列数设置为维度项数量**/
				var _cols=($lis.length<opt.cols&&this.options.drill!=true)?$lis.length:opt.cols;
				/**计算共需展示几行**/
				var row_count=Math.ceil($lis.length/_cols);
				var _rows=row_count>opt.rows?opt.rows:row_count;
				/**滚动条宽度**/
				var scroll_w=row_count>opt.rows?this._const.scrollwidth:0;
				/**计算维度选取框宽度**/
				var input_w=$('.'+this._const.cls+'-txt',this.element).width();
				/**计算div的padding**/
				var panel_w_padding=this.$dimpanel.outerWidth()-this.$dimpanel.width();
				/**计算ul的padding**/
				var ul_w_padding=$ul.outerWidth()-$ul.width();
				var ul_h_padding=$ul.outerHeight()-$ul.height();
				/**计算li的padding**/
				var li_w_padding=$lis.eq(0).outerWidth()-$lis.eq(0).width();
				/**设定维度项宽度**/
				if(opt.itemwidth=='auto'){
					$lis.width(input_w);
				}else{
					$lis.width(opt.itemwidth);
				}
				/**计算li的宽高**/
				var li_w=$lis.eq(0).outerWidth();
				var li_h=$lis.eq(0).outerHeight();
				$lis.each(function(idx,it){
					/**设置维度项提示信息**/
					var title,$it=$(it);
					if($it.find('ul').length){
						title=self._getLiTxt($it);
						/**多层钻取维度时，增加钻取图标**/
						$it.append('<a class="'+self._const.cls+'-item-drill" title="'+self._const.drillTitle+'"></a>');
					}else{
						title=$it.text();
					}
					$it.attr('title',title);
					/**多列布局时,最左侧的列增加去掉线条的样式**/
					if(_cols>1&&!opt.multisel&&idx%_cols==0){
						$it.addClass(self._const.cls+"-item-noline");
					}
				});
				/**设定ul高度**/
				$ul.css({height:_rows*li_h});
				/**设定弹出层宽度,隐藏弹出层**/
				var panel_w=li_w*_cols+ul_w_padding+scroll_w;
				var _w=$(this.element).width()-panel_w_padding;
				panel_w=(panel_w<_w)?_w:panel_w;
				if(isHidden){
					this.$dimpanel.hide();
				}
				this.$dimpanel.css({width:panel_w,'z-index':800});
				$ul.css('overflow-y',(row_count>opt.rows)?'scroll':'hidden');
			},
			/**获取维度项文本**/
			_getLiTxt:function($li){
				var txt;
				if($li.find('ul').length){
					txt=$li[0].childNodes[0].nodeValue;
				}else{
					txt=$li.text();
				}
				return txt;
			},
			/**获取维度选择框title提示信息**/
			_getDisTitle:function(selTxts){
				var disTitles="当前选择 : ",disTxts='';
				if(this.options.drill){
					var navtxt='';
					$('.'+this._const.cls+'-nav >li:gt(0)',this.$dimpanel).not(':last').each(function(idx){
						navtxt+=$(this).text()+"->";
					});
					for(var i=0;i<selTxts.length;i++){
						selTxts[i]=navtxt+selTxts[i];
					}
				}			
				if(this.options.multisel){
					disTitles+=selTxts.length+"项  [ "+selTxts.join(' , ')+" ]";
				}else{
					disTitles+=selTxts.join(" , ");
				}
				return disTitles;

			},
			/**获取当前选中项**/
			_selectDim:function($ul){
				var selectedItems=[],isChange=false,self=this;
				this.selectedVal=this.selectedVal||[];
				if(!$ul){
					$ul=$('.'+this._const.cls+'-items',this.$dimpanel).filter('[main=1]');
				}
				/**获取弹出层中被选中的维度值**/
				var cls_cursel=this._const.cls+'-item-cursel';
				$('>li.'+cls_cursel,$ul).each(function(){
					selectedItems.push({
						val:$(this).attr('val'),
						txt:self._getLiTxt($(this))
					});
				});
				if(selectedItems.length==0&&this.options.drill){
					var $firstNav=$('.'+this._const.cls+'-nav>li',this.$dimpanel).first();
					selectedItems.push({
						val:$firstNav.attr('val'),
						txt:$firstNav.text()
					});
				}
				var selVals=_.pluck(selectedItems,'val');
				var selTxts=_.pluck(selectedItems,'txt');
				/**判断是否和上次选中项一致,触发onchange**/
				if(this.selectedVal.length==selectedItems.length){
					var curVals=_.pluck(this.selectedVal,'val');
					var difVals=_.difference(curVals,selVals);
					isChange=(difVals.length>0);
				}else{
					isChange=true;
				}
				this.selectedVal=selectedItems;
				var disVals=selVals.join(','),disTxts=selTxts.join(',');
				this.element.attr({
					val:disVals==''?'-1':disVals,
					txt:disTxts,
					title:this._getDisTitle(selTxts)
				});
				/**设置维度选择框文本**/
				this._setInput(disVals,disTxts);
				if(isChange&&_.isFunction(this.options.onchange)){
					this.options.onchange.call(this,disVals||-1);
				}		
			},
			/***************************************** 私有函数END ***************************************/
			
			/***************************************** 事件处理BEGIN ***************************************/
			/**维度项点击事件处理**/
			_itemEventHandler:function($target){
				var cls_cursel=this._const.cls+'-item-cursel';
				var $ul=$target.parent('ul');
				if(this.options.multisel!=true){
					$target.addClass(cls_cursel);
					$target.siblings().removeClass(cls_cursel);
					this._selectDim($ul);
					this.hidePanel();
				}else{
					$target.toggleClass(cls_cursel);
					this._selectDim($ul);
				}
			},
			/**钻取导航点击事件处理**/
			_drillNavEventHandler:function($target){
				if($target.index()==0){
					/**如果点击第一级导航,移除钻取层，展示主层**/
					this._resetDimItemsPanel();
					this._resetDrillNav();
				}else if($target.hasClass(this._const.cls+'-nav-selectAll')){
					this.select($target.attr('val'));
					this.hidePanel();
				}else{
					/**如果点击非一级导航，生成钻取层展示，隐藏主层**/
					var $mainPanel=$('.'+this._const.cls+'-items[main]',this.$dimpanel);
					var $li=$('li[val="'+$target.attr('val')+'"]',$mainPanel);
					this._buildDrillPanel($('>ul>li',$li));
					/**重设导航栏**/
					this._resetDrillNav($target.addClass(this._const.cls+'-nav-cursel'));
				}
			},
			/**维度项钻取点击事件处理**/
			_itemDrillEventHandler:function($target){
				var $li=$target.parent();
				var txt=this._getLiTxt($li);
				this._addDrillNav({val:$li.attr('val'),txt:txt});
				this._buildDrillPanel($('>ul>li',$li));
			},
			/**事件处理**/
			_handleEvent:function(){
				var self=this;
				$(this.element).delegate("."+this._const.cls+'-txt','click',function(){
					/**点击维度选取框**/
					if(self.$dimpanel.is(':hidden')){
						self.showPanel();
					}else{
						self.hidePanel();
					}
				});
				this.$dimpanel.delegate("."+this._const.cls+'-items > li','click',function(){
					/**维度项事件绑定**/
					self._itemEventHandler($(this));
					return false;
				}).delegate("."+this._const.cls+'-items > li','mouseenter',function(){
					if(self.options.drill&&self.$tips){
						if($('>ul',this).length){
							self.$tips.text('点击文字选中,点击右侧箭头可钻取至下一级');
						}else{
							self.$tips.text('无子项,不能钻取');
						}
					}
				}).delegate("."+this._const.cls+'-items > li','mouseleave',function(){
					if(self.$tips){
						self.$tips.text(self.options.tip);
					}
				}).delegate("."+this._const.cls+"-nav",'click',function(){
					/**层级钻取维度导航栏**/
					return false;
				}).delegate('a.'+this._const.cls+"-item-drill",'click',function(){
					/**点击钻取图标**/
					self._itemDrillEventHandler($(this));
					return false;
				}).delegate('.'+this._const.cls+'-nav >li','click',function(){
					/**点击钻取导航**/
					self._drillNavEventHandler($(this));
					return false;
				}).click(function(){
					return false;
				});
			},
			/***************************************** 事件处理END ***************************************/
			
			/***************************************** 对外开放公共方法BEGIN ***************************************/
			/**获取维度弹出层对象**/
			getDimPanel:function(){
				return this.$dimpanel;
			},
			/**显示维度弹出层**/
			showPanel:function(){
				var self=this,$ele=$(this.element);
				var isstatic=this.isStaticContainer(this.options.parentNode);
				var $pos=(isstatic)?$pos=$ele.offset():$pos=$ele.position();
				var h=$ele.outerHeight(!isstatic);
				this.$dimpanel.css({left:$pos.left,top:$pos.top+h+4}).slideDown('fast');
				_.delay(function(){
					$('html').one('click', $.proxy(self.hidePanel,self));
				},100);
			},
			/**隐藏维度弹出层**/
			hidePanel:function(){
				$('html').off('click',$.proxy(this.hidePanel,this));
				this.$dimpanel.slideUp('fast');
			},
			/**获取选中项对象**/
			getObject:function(){
				var result=this.selectedVal||[];
				if(!this.options.multisel){
					return result.length==0?null:result[0];
				}
				return result;
			},
			/**获取选中值**/
			getValue:function(){
				if(!this.selectedVal||!this.selectedVal.length){return -1;}
				return _.pluck(this.selectedVal,'val').join(',');
			},
			/**获取选中文本**/
			getTxt:function(){
				if(!this.selectedVal||!this.selectedVal.length){return '';}
				return _.pluck(this.selectedVal,'txt').join(',');
			},
			/**清除选中**/
			reset:function(){
				var cls_cursel=this._const.cls+'-item-cursel';
				var $ul=$('.'+this._const.cls+'-items',this.$dimpanel);
				$('>li',$ul).removeClass(cls_cursel);
				if(this.options.drill){
					this._resetDrillNav();
					this._resetDimItemsPanel();
				}
				this._selectDim();
			},
			/**根据值选中维度项**/
			select:function(vals){
				var $ul=$('.'+this._const.cls+'-items',this.$dimpanel);
				var cls_cursel=this._const.cls+'-item-cursel';
				if(!vals)return;
				var valAry=vals.split(',');
				/**如果是单选，需要清除已选择项**/
				if(!this.options.multisel){
					$('>li',$ul).removeClass(cls_cursel);
					valAry=[valAry[0]];
				}
				for(var i=0;i<valAry.length;i++){
					valAry[i]='[val="'+valAry[i]+'"]';
				}
				/**如果是钻取维度,先查找弹出层是否有该维值,如果没有,需创建钻取面板**/
				if(this.options.drill){
					var $li=$('>li',$ul.filter('[main=1]')).filter(valAry.join(','));
					this._resetDrillNav();
					this._resetDimItemsPanel();
					/**如果维度选取层有该维度值,则直接选中**/
					if($li.length){
						this._selectDim($li.addClass(cls_cursel).parent('ul'));
					}else{
						/**如果维度选取层无该维度值,则查找该值，创建钻取层**/
						$li=$('>li li',$ul).filter(valAry.join(',')).eq(0);
						if($li.length){
							var $parentLi=$li.parentsUntil('ul.aui-dimsel-items','li');
							for(var i=$parentLi.length-1;i>=0;i--){
								this._addDrillNav({val:$parentLi.eq(i).attr('val'),txt:this._getLiTxt($parentLi.eq(i))});
							}
							var $drillUL=this._buildDrillPanel($li.siblings().andSelf());
							$drillUL.find('>li').filter(valAry.join(',')).addClass(cls_cursel);
							this._selectDim($drillUL);
						}else{
							this._selectDim();
						}
					}
				}else{
					$('>li',$ul).filter(valAry.join(',')).addClass(cls_cursel);
					this._selectDim($ul);
				}
			},
			reload:function(data){
				var $ul=$('>ul',this.$dimpanel),domstr="";
				this.reset();
				$ul.empty();
				/**添加全部选项**/
				if(this.options.showall&&!this.options.drill){
					domstr+='<li val="'+this.options.allval+'">'+this.options.alltxt+'</li>';
				}
				for(var it in data){
					domstr+="<li val='"+it+"'>"+data[it]+"</li>";
				}
				$ul.append(domstr);
				this._reLayoutDimItems($ul);
			}
			/***************************************** 对外开放公共方法END ***************************************/
		});
	})(jQuery);	
});