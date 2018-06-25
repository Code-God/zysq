/**
 * 报名活动
 */
function signUp(partyId){
		$.ajax({
			dataType : 'text',
			type : 'get',
			url : 'party_signUp.Q?partyId='+partyId,
			success : function(data) {
				if(data == 'true'){
					cwindow('报名成功！');
					$("#sulink").html("<font color='green'>已报名</font>");
					$("#sulink").attr("href","###");
				}else{
					errMsg('报名失败！请确认您的联系方式是否填写。');
				}
			}
		});
}

