define(["jquery", "underscore",'moment',"jquery-ui","aui/core",'lib/arale/calendar'], function(jQuery,_,moment) {
	var Calendar=require('lib/arale/calendar');
	(function($, undefined) {
		//初始化日历组件
		var calendar=null;
		function getCalendar(){
			if(calendar==null){
				calendar=new Calendar({startDay:1});
			}
			return calendar;
		}
		$.widget("aui.datesel",$.aui.core, {
			options : {
				width : 120,        //维度选择框宽度
				seltype:'D',//D为日选择,M为月选择,W为周选择
				placeholder : '请选择',//默认未选择时显示文字
				val:null, //默认选中日期
				dval:null,//为日选取时的默认日期
				mval:null,//为月选取时的默认日期
				wval:null,//为周选取时的默认日期
				valFormat:null,
				txtFormat:null,
				onchange : $.noop //选择改变时事件
			},
			_default:{
				'D':{valFormat:"YYYYMMDD",txtFormat:"YYYY-MM-DD"},
				'M':{valFormat:"YYYYMM",txtFormat:"YYYY年MM月"},
				'W':{valFormat:"YYYYMMDD",txtFormat:"YYYYMMDD"}
			},
			_const : {
				cls : 'aui-datesel'
			},
			_create : function() {
				this._initOptions();
				this.options.seltype=this._getSelType();
				this.calendar=getCalendar();
				$(this.element).uniqueId().width(this.options.width).addClass(this._const.cls);
				this.id=$(this.element).attr('id');
				this._buildDateselInput();
				this._initInput();
				this._handleEvent();
			},
			_getSelType:function(val){
				var seltype=val||this.options.seltype;
				seltype==='M'||seltype==='W'||(seltype='D');
				return seltype;
			},
			_getValFormat:function(){
				return this.options.valFormat||this._default[this._getSelType()].valFormat;
			},
			_getTxtFormat:function(){
				return this.options.txtFormat||this._default[this._getSelType()].txtFormat;
			},
			_validVal:function(seltype,val){
				var valFormat=this._default[seltype].valFormat;
				if(this._getSelType()===seltype){
					valFormat=this._getValFormat();
				}
				var date=moment(val,valFormat);
				if(!date.isValid()){
					date=moment();
					if(seltype==='M'){
						date=date.add("months",-1);
					}else if(seltype==='W'){
						date=date.add("days",-6);
					}else{
						date=date.add("days",-1);
					}
				}
				if(seltype==='W'){
					// 将date格式化
					var date_format = date.format('YYYYMMDD');
					// 用date_format创建一个与date日期相同的临时moment对象
					var date_temp = new moment(date_format,'YYYYMMDD');
					// date的日期，即只有DD部分
					var dateDay = date.date() ;
					// date是本周的星期天是几号，只有DD部分。date_temp.day(0)为date_temp所在周的第0天，即星期天，1为星期一，依次。。。
					var tempDay = date_temp.day(0).date();
					// moment.js默认每周从星期天开始，即day(0), 而此处将星期一作为每周每一天，故要做此转换。
					// dateDay 与 tempDay 相等，说明date是星期天
					if(dateDay == tempDay){
						// 如果date星期天，则减一天，使date为星期六，然后用date.day(1),将date转换成本周的星期一。
						date.subtract("days",1);
					}
					// 将所有日期转换成所在周的第一天（星期一）返回
					dateVal = date.day(1);
				}				
				return date;
			},
			_initInput:function(){
				var initVal={
					'D':this._validVal('D',this.options.val||this.options.dval),
					'M':this._validVal('M',this.options.val||this.options.mval),
					'W':this._validVal('W',this.options.val||this.options.wval)
				};
				var curVal=initVal[this.options.seltype];
				$(this.element).data('vals',initVal);
				this.select(curVal);
			},
			/**创建日期选取input区域**/
			_buildDateselInput:function(){
				this.$input=$('<a class="'+this._const.cls+'-txt" href="javascript:;"></a>').appendTo($(this.element));
			},			
			/***************************************** 事件处理BEGIN ***************************************/
			/**事件处理**/
			_handleEvent:function(){
				var self=this;
				$(this.element).delegate("."+this._const.cls+'-txt','click',function(){
					var inputId=self.calendar['inputId'];
					if(!self.calendar.isVisible()){
						self.show();
					}else{
						if(inputId!=self.id){
							self.show();
							return false;
						}else{
							self.hide();
						}
					}
				});
			},
			_setOption: function( key, value ) {
				this._super( key, value );
				if ( key === "seltype" ) {
					var seltype=this._getSelType(value);
					var vals=$(this.element).data('vals');
					this.select(vals[seltype]);
				}
			},
			/***************************************** 事件处理END ***************************************/
			
			/***************************************** 对外开放公共方法BEGIN ***************************************/
			show:function(){
				this.calendar.clearSelectEvent();
				this.calendar['inputId']=this.id;
				var self=this;
				var seltype=this._getSelType();
				var selEvent="select"+((seltype==='M')?"Month":"Date");
				var valFormat=this._getValFormat();
				var txtFormat=this._getTxtFormat();
				var val=$(this.element).data('val');
				this.calendar.on(selEvent,function(date){
					if(self._getSelType()==='W'){
						date=date.day(1);
					}
					var _val=date.format(valFormat);
					var _txt=date.format(txtFormat);
					if(self._getSelType()==='W'){
						_txt+=" ~ "+date.add("days",6).format(txtFormat);
						// date.add("days",6) 会使date值加6，此处恢复date值
						date.subtract("days",6);
					}
					self.$input.text(_txt);
					if($(self.element).data('val')!=_val){
						self.options.onchange.call(null,_val);
					}
					var vals=$(self.element).data('vals');
					vals[seltype]=date;
					$(self.element).attr({val:_val,txt:_txt}).data('val',_val).data('vals',vals);
					self.hide();	
				});				
				this.calendar.setSelType(seltype);
				this.calendar.select(moment(val,valFormat));
				this.calendar.show($(this.element));
				_.delay(function(){$('html').one('click', $.proxy(self.hide,self));},100);
			},
			hide:function(){
				$('html').off('click',$.proxy(this.hide,this));
				this.calendar.hide();
			},
			/**获取选中项对象**/
			getObject:function(){
				return {
					val:$(this.element).attr('val'),
					txt:$(this.element).attr('txt')
				};
			},
			/**获取选中值**/
			getValue:function(){
				return getObject()['val'];
			},
			/**获取选中文本**/
			getTxt:function(){
				return getObject()['txt'];
			},
			/**清除选中**/
			reset:function(){},
			/**根据值选中维度项**/
			select:function(val){
				var seltype=this.options.seltype;
				var date=this._validVal(seltype,val);
				var valFormat=this.options.valFormat||this._default[seltype].valFormat;
				var txtFormat=this.options.txtFormat||this._default[seltype].txtFormat;
				var dateVal=date.format(valFormat);
				var dateTxt=date.format(txtFormat);
				if(seltype==='W'){
					dateTxt+=" ~ "+date.add("days",6).format(txtFormat);
					// date.add("days",6) 会使date值加6，此处恢复date值
					date.subtract("days",6);
				}
				var vals=$(this.element).data('vals')||{};
				vals[seltype]=date;
				var selVal=$(this.element).data('val');
				$(this.element).data('vals',vals).data('val',dateVal).attr({txt:dateTxt,val:dateVal});
				this.$input.text(dateTxt);
				if(selVal!=dateVal){
					this.options.onchange.call(null,dateVal);
				}
			}
			/***************************************** 对外开放公共方法END ***************************************/
		});
	})(jQuery);	
});