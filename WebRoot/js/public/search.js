$(document).ready(function () { 
	// 失焦
	$('#searchTxt').blur(function(){
		setTimeout(function(){
			$('#shelper').css('display','none')
		} ,300);
	});
	// 获焦
	$('#searchTxt').focus(function(){
		var pageUrl = "public/list_getKeyWord.Q";
		$.getJSON(pageUrl, funShelper);
	});
	
	var lastTm;
	$('#searchTxt').bind('input propertychange', function() {
		// 执行锁
		var key = $(this).val();
		var tm = setTimeout(function(){
   			$('#shelper').css('display','none');
			var pageUrl = "public/list_getKeyWord.Q";
			pageUrl += "?key=" + key;
			console.log(pageUrl);
			$.getJSON(pageUrl, funShelper);
		} ,500);
		// clear last timeout
		clearTimeout(lastTm);
		// save new timeout
		lastTm = tm;
	});
});

// 搜索建议内容绑定
var funShelper = function(list){
	var html = ""; 
	var sb = "";
	$.each(list, function(index, obj) {
		sb += "<li>" + obj.keyword + "</li>"
	});
	$("#shelper").html(sb);
	// 显示搜索建议框
	$('#shelper').css('display','block');
	$("#shelper li").click(function(){
		$('#searchTxt').val($(this).text());
		$('#shelper').css('display','none');
	});
}