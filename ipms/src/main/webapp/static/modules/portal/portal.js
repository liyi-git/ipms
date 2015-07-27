require(['jquery','aui/confirm'], function($) {
	$(document).ready(function() {
		var $targetAnchor = "",
			$parentLi = "",
			$ipmsContent = $(".ipms-content"),
			loadPage = function(url, parm) {
				$.ajax({
					type: "POST",
					async: true,
					dataType: "html",
					url: url,
					data: parm,
					success: function(data) {
						$ipmsContent.html(data);
					},
					error: function(xmlhttprequest, textStatus, errorThrown) {
						$ipmsContent.html(xmlhttprequest.responseText);
					},
					complete: function() {}
				});
			};

		$("body").on("click", "ul.ipms-menu a", function() {
			var lisMenu = $(this).closest("ul.ipms-menu").children("li");
			$parentLi = $(this).parent("li");
			// dir click
			if ($(this).children("span").length > 0) {
				if ($(this).closest("li").hasClass("active")) {
					$(this).closest("li").removeClass("open").removeClass("active");
					$(this).siblings('ul.ipms-menu').slideToggle({
						duration: 200,
						progress: function() {}
					});
					return true;
				} else {
					$(lisMenu).find("ul.ipms-menu:visible").slideToggle({
						duration: 200,
						progress: function() {}
					});
					$parentLi.siblings("li").removeClass("open").removeClass("active");
					$parentLi.addClass("open").addClass("active");
					$(this).siblings('ul.ipms-menu').slideToggle({
						duration: 200,
						progress: function() {}
					});
				}
			} else {
				$parentLi.parents("li").siblings("li").find("li.active").removeClass("active")
				$parentLi.siblings("li").removeClass("active");
				$parentLi.addClass("active");

				if (typeof($parentLi.attr("url")) != "undefined" && $parentLi.attr("url").length > 0) {
					loadPage($parentLi.attr("url"), {});
				}

			}
		});

		$.each($('ul.ipms-menu a'), function() {
			if (this.href == window.location) {
				$targetAnchor = this;
				$(this).parents("ul.ipms-menu").siblings("a").trigger("click");
				$(this).trigger("click");
				return false;
			}
		});
		$("#page-header").on("click", "a.toggle-nav", function() {
			var $nav = $(".ipms-nav"),
				$activeLi = $("#ipms-sidebar").find(".open"),
				$activeCli = $("#ipms-sidebar").find("li.active");

			if (typeof($nav.attr("id")) != "undefined") {
				$ipmsContent.removeAttr("style");
				$nav.removeAttr("id");
				if ($activeCli.length > 0) {
					$($activeCli[0]).parents("ul.ipms-menu").siblings("a").trigger("click");
				}
			} else {
				$nav.attr("id", "ipms-collapse-nav");
				$ipmsContent.attr("style", "margin-left:50px;");
				if ($activeLi.length > 0) {
					$($activeLi[0]).children("a").trigger("click");
				}
				$("#ipms-sidebar").find("li.hasChild").each(function() {
					if (typeof($nav.attr("id")) != "undefined") {
						$(this).mouseover(function() {
							$(this).children("a").find("span").show();
						}).mouseout(function() {
							$(this).children("ul").removeAttr("style");
							$(this).children("a").find("span").removeAttr("style");
						});
					} else {
						return;
					}
				});
			}
		});
        
        $("#ipms-logout").click(function(){
            $.confirm({
                'title'     : '注销系统',
                   'message'   : '确定要退出系统？',
                   'confirm':function(){
                    var url = _g_const.ctx + "/logout",
                       params={};
                        $.ajax({
                            type : "post",
                            url : url,
                            data : params,
                            dataType : "html",
                            contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                            success : function(data){}
                        });
                },
                'cancle':function(){
                    
                }
            });
        });
        var firstDir=$("#ipms-sidebar").find("li.hasChild")[0],
           firstMenu=$(firstDir).children(".ipms-menu").find("a")[0];
           $(firstDir).children("a").trigger("click");
           $(firstMenu).trigger("click");
	});
})