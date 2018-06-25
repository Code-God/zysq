//城市IP定位
$(function () {
	if ($("#j_city_name").html().trim() === "") {
		var code = "131";
		$.ajax({
			url:"http://api.map.baidu.com/location/ip?ak=2nyuYgS5n6o1u7AsWVqoIS12&callback=?", 
			dataType:"json",
			async:false,
			type:"get", 
			success:function (data, stat) {
				var status = data.status;
				if (status == 0) {
					code = data.content.address_detail.city_code;
				}
				loadarea(code);
			}, error:function (data, stat) {
				loadarea(code);
			}
		});
	}else{
		if(typeof getStock === 'function'){
			getStock();
		}
	}
	$.getJSON("/cars/public/area_all.Q", function (data) {
		var html = "";
		for (var i = 0; i < data.length; i++) {
			var city = data[i];
			var line = "<a href=\"javascript:current(" + city.code + ");\" title=\"\u524d\u5f80" + city.name + "\">" + city.name + "</a>";
			html += line;
		}
		$(".mod_storage_city").html(html);
	});
	
	$("#j_city_con").hover(function () {
		$(this).toggleClass("mod_city_con_on");
	});
});

function loadarea(code){
	$.ajax({
		url:"/cars/public/area_current.Q",
		data:{cityCode:code},
		dataType:"json",
		async:false,
		type:"get", 
		success:function (data, status) {
			$("#j_city_name").html(data.name);
			if(typeof getStock === 'function'){
				getStock();
			}
		}, error:function (data, status) {
		
		}
	});
}

function current(code) {
	$.getJSON("/cars/public/area_current.Q", {cityCode:code}, function (data) {
		window.location.reload();
	});
}

