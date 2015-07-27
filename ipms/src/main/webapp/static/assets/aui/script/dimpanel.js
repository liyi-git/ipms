define(["jquery", "underscore","jquery-ui","aui/core",'aui/dimsel','aui/toggle','aui/datesel'], function(jQuery, _) {
	(function($, undefined) {
		$.widget("aui.dimpanel",$.aui.core, {
			options : {
				trigger:null,   //维度组件onchange触发query
				onquery:$.noop, //点击查询按钮时
				oninit:$.noop,   //维度panel初始化时
				customId:''		//自定义查询按钮id
			},
			_const : {
				cls : 'aui-dimpanel'
			},			
			_create:function(){
				this._initOptions();
				var self=this;
				//初始化日期选择组件
				$('.aui-datesel',this.element).datesel();
				//初始化维度切换组件
				$('.aui-toggle-div',this.element).toggle({
					onchange:function(e,result,obj){
						if(obj&&obj.options&&obj.options.cascade){
							var dateCmp=$('.aui-datesel[name="'+obj.options.cascade+'"]');
							dateCmp.datesel('option',{seltype:result['val']});
						}
					}
				});
				//初始化维度选取组件
				var $container=$(this.element).siblings('.'+this._const.cls+'-pop');
				$('.aui-dimsel',this.element).each(function(){
					var isSearch = $(this).attr("issearch");
					// issearch 属性不为true时，调用dimsel组件初始化
					if(isSearch != 'true'){
						$(this).dimsel({
							parentNode:$container
						});
					}
				});
				
				//查询按钮事件处理
				$('#'+this._const.cls+'-query',this.element).bind('click',function(){
					self.options.onquery.call(null,self);
				});
				
				//自定义查询按钮事件处理
				if($('#'+this.options.customId).length > 0){
					$('#'+this.options.customId).bind('click',function(){
						self.options.onquery.call(null,self);
					});
				}
				this.options.oninit.call(null,this);	
			},
			//获取所有的查询参数
			_getAllParams:function(){
				var params=[];
//				$('.'+this._const.cls+'-item',this.element).filter(':visible').each(function(){
				//将隐藏过滤取消，用于品牌隐藏能拿到-1值
				$('.'+this._const.cls+'-item',this.element).each(function(){
					var name=$(this).attr('name');
					var label=$('>label',this).text()||$(this).attr('label');
					var $dim=$('.aui-toggle-div,.aui-dimsel,.aui-datesel',this).eq(0);
					var val=$dim.attr('val')||'-1';
					val=$.trim(val).length==0?'-1':val;
					params.push({label:label,name:name,val:val,txt:$dim.attr('txt')||''});
				});
				return params;
			},
			//获取所有查询条件对象，返回JSON对象格式
			_getJsonParams:function(isVal){
				var result={};
				$.each(this._getAllParams(),function(){
					result[this.name]=isVal?this.val:this.txt;
				});
				return result;
			},			
			/***************************************** 对外开放公共方法Begin ***************************************/
						
			//获取所有查询条件值,返回JSON对象格式,如：{optime:"201310",city:"999"}
			getJsonParams:function(){
				return this._getJsonParams(true);
			},
			//获取所有查询条件文本,返回JSON对象 如：{optime:"2013年10月",city:"全省"}
			getJsonTxtParams:function(){
				return this._getJsonParams(false);
			},
			//获取所有查询条件值,返回URL拼接的字符串 如：optime=201310&city=999
			getUrlParams:function(){
				return $.param(this.getJsonParams());
			},
			//获取所有查询条件文本,返回URL拼接的字符串,如：optime=2013年10月&city=全省
			getUrlTxtParams:function(){
				return $.param(this.getJsonTxtParams());
			},
			//获取所有查询条件文本描述,返回字符串,如：分析类型[日分析],日期[2013年10月],地市[全省]
			getTextParams:function(){
				var result=[];
				$.each(this._getAllParams(),function(){
					result.push(this.label+"["+this.txt+"]");
				});
				return result.join(",");
			},
			//返回Form表单,将所有查询条件放入隐藏域,传入参数为表单提交的action URL
			getForm:function(action){
				var id='#FROM_'+this.element.attr('id'),$form=$(id);
				if(!$form.length){
					$form=$('<form style="display:none;" id="'+id+'"/>').appendTo(this.element);
				}
				$.each(this._getAllParams(),function(){
					var $input=$form.find('[name="'+this.name+'"]');
					if(!$input.length){
						$input=$('<input type="hidden" name="'+this.name+'"/>').appendTo($form);
					}
					$input.val(this.val);
				});
				$form.attr('action',action||'').attr('method','POST');
				return $form.get(0);
			},
			//将查询参数作为Form表单提交,传入参数为表单提交的action URL
			submit:function(action){
				this.getForm(action).submit();
			},
			//重设维度面板中的维度组件为初始状态
			reset:function(){
				$('.aui-dimsel',this.element).dimSel('clear');
				var form=this.getForm();
				if(form){
					$('input:hidden',form).remove();
				}
			},
			//按维度名称，获取该维度组件
			getDim:function(name){
			},
			//按维度名称，获取该维度当前选中值
			getVal:function(name){
			},
			//按维度名称，控制该维度控件隐藏；隐藏控件，将获取不到选取条件；
			hideDim:function(name){
			},
			//按维度名称，控制该维度控件显示
			showDim:function(name){
			},
			//按维度名称、维度值,选中该维度控件的该维度值
			select:function(name,val){
			}
			/***************************************** 对外开放公共方法END ***************************************/
		});
	})(jQuery);	
});