function doNext(step){
	if(step == 2){
		$("#citySpan").hide();
		$("#provinceSpan").hide();
		var city = $("#city").val();
		var province = $("#province").val();
		if(province == "0"){
			$("#provinceSpan").show();
			return;
		}
		if(city == "0"){
			$("#citySpan").show();
			return;
		}
		switchDiv("step2", "step1");
	}else if(step == 3){
		switchDiv("step3", "step2");
	}else if(step == 4){
		//直接提交表单
		$("#theForm").submit();
		//switchDiv("step4", "step3");
	}
}

function switchDiv(showId, hideId){
	$("#" + hideId).fadeOut(500, function(){
		$("#"+showId).fadeIn("fast");
	});
}


//提交核保
function hebao(){
	
	$.ajax({
		url:CONFIG.context + "/private/pri_submitCarInfo.Q",
		data:{"recommend":recommend,"prdCatCode":prdCatCode},
		dataType:'text',
		success:function(data){
			if(data.msg == "ok"){
				switchDiv("step4", "step3");
			}else{
				art.dialog.tips("提交失败，请稍后再试。");
			}
		}
	});
	
}

$('.switch-btn-wrap').on('click', function() {
    var $target = $(this);
    if($target.hasClass('switch-btn-sel')) {
        $target.removeClass('switch-btn-sel');
        $("#guohu").val(0);
    } else {
        $target.addClass('switch-btn-sel');
        $("#guohu").val(1);
    }
});