$(document).ready(function () {
	$(".v-show").click(function() {
	    if ($(this).children(".s-more").hasClass('hide')) {
	        $(this).children(".s-more").removeClass('hide');
	        $(this).children(".s-less").addClass('hide');
	    } else {
	        $(this).children(".s-more").addClass('hide');
	        $(this).children(".s-less").removeClass('hide');
	    }
	});
	$(".mod_cate").hover(function() {
	    $(this).toggleClass("mod_cate_on");
	});
	$("#j_city_con").hover(function() {
	    $(this).toggleClass("mod_city_con_on");
	});
	$("#category_container .mod_cate_bd").slide({
	    type: "menu",
	    titCell: ".mod_cate_li",
	    targetCell: ".mod_subcate",
	    delayTime: 0,
	    triggerTime: 10,
	    defaultPlay: false,
	    returnDefault: true
	});
	
	// 初始化分页控件
	$.jqPaginator('#pagination', {
		totalPages: 100,
	    visiblePages: 10,
	    currentPage: 1,
        wrapper:'<ul class="pagination"></ul>',
        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>'
    });
	
	$.getJSON(url, funSu);
	
	// sort css update
	$(".sort li").click(function () {
		var css = "up";
		// 重复点排序为逆序
		if ($(this).hasClass("curr")) {
			if ($(this).children().hasClass("up")) {
				css = "down";
			}
		}
		// 恢复默认css
		var oldTxt = $(".sort .curr").text();
		$(".sort .curr").html("<a>" + oldTxt + "</a>");
		// 更新当前排序方式css
		var txt = $(this).first().text();
		$(this).html("<div class='" + css + "'><a>" + txt + "<b></b></a></div>");
		// 当前排序
		$(".sort li").removeClass("curr");
		$(this).addClass("curr");
		var pageUrl = url + "&sort=" + $(this).attr("sort");
		if (css == "down") {
			pageUrl += "&isDesc=true";
		}
		$.getJSON(pageUrl, funSu);
	});
	
});

// 页面初始化加载
var funSu = function (page) {
	var html = "";
		// 获取HTML模版，替换填充数据
	var temp = $("#proListTemp").html();
	$.each(page.data, function (index, obj) {
			// 复制模版 替换数据
		var sb = temp;
		sb = sb.replace(new RegExp("#id", "gm"), obj.id);
		sb = sb.replace(new RegExp("#name", "gm"), obj.name);
		sb = sb.replace(new RegExp("#picUrl2", "gm"), obj.picUrl2);
		sb = sb.replace(new RegExp("#prdDisPrice", "gm"), obj.prdDisPrice.toFixed(2));
		sb = sb.replace(new RegExp("#prdPrice", "gm"), obj.prdPrice.toFixed(2));
		sb = sb.replace(new RegExp("#prdStandard", "gm"), obj.prdStandard);
		sb = sb.replace(new RegExp("#prdCode", "gm"), obj.prdCode);
		sb = sb.replace(new RegExp("#productStock", "gm"), obj.stock);
		html += sb;
	});
	$("#proList").html(html);
		// 初始化特效
	initCssEvent();
		// 初始化分页点击
	initPageClick(page);
		// +-初始事件绑定
	$("#proList .product").each(function (i, o) {
		initBind(o);
		var code = $(o).attr("prdCode");
		$("#addShop_" + code).bind("click", function () {
			$("#prdForm_" + code).submit();
		});
	});
};

// 商品特效初始化
var initCssEvent = function () {
	//$(".catlist .catitem:first").addClass("curr");
	$(".catitem b").click(function () {
		if ($(this).parents(".catitem").hasClass("curr")) {
			$(this).parents(".catitem").removeClass("curr");
		} else {
			$(this).parents(".catitem").addClass("curr").siblings().removeClass("curr");
		}
	});
	$(".list-all li").hover(function () {
		$(this).toggleClass("curr");
	});
};

// 初始化分页组件
var initPageClick = function (page) {
		// 改变参数
	$("#pagination").jqPaginator("option", {totalCounts:page.totalCounts, totalPages:page.totalPageCount, currentPage:page.currPageNo });
	// 绑定点击事件
	$(".pagination li").not(".disabled").click(function () {
		var currPageNo = $(this).attr("jp-data");
		var pageUrl = url + "&currPageNo=" + currPageNo;
		$.getJSON(pageUrl, funSu);
	});
};

