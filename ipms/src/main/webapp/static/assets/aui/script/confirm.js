define(["jquery"], function(jQuery) {
    (function($) {
        $.confirm = function(params) {
            if (!!$('.aui-modal').length) {
                return false;
            }
            var options = $.extend({
                width: 430,
                type:'confirm', // confirm||alert
                title:'',
                message:'',
                confirm:$.noop,
                cancle:$.noop
            },params);
            var buttonDom = '<button class="aui-btn blue">确认</button>';
            if(options.type=='confirm'){
                buttonDom=buttonDom+'<button class="aui-btn gray">取消</button>';
            }
            var markup = [
                '<div class="aui-confirm">',
                '<div class="aui-confirm-overlay"></div>',
                '<div class="aui-confirm-box">',
                '<h1 class="title">',options.title,'</h1>',
                '<div class="content">',options.message,'</div>',
                '<div class="footer">',
                buttonDom,
                '</div></div></div>'
            ].join('');
            
            $(markup).hide().appendTo('body').fadeIn();
            
            $(".footer").delegate("button", "click", function(){
                if($(this).hasClass("blue")){
                    $.confirm.hide();
                    options.confirm.call();
                }else{
                    $.confirm.hide();
                    options.cancle.call();
                }
            });
        }
        
        $.confirm.hide = function() {
            $('.aui-confirm').fadeOut(function() {
                $(this).remove();
            });
        }

    })(jQuery);
});