/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "PwdForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	$("#oldPwd").formValidator({
				onshow : '请输入原密码',
				onfocus : '请输入原密码',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max :1000,
				onerror : "原密码必须输入且长度不得少于8！"
			});
	$("#newPwd").formValidator({
				onshow : '请输入新密码',
				onfocus : '请输入新密码',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max :1000,
				onerror : "新密码必须输入且长度不得少于8！"
			});
	$("#newPwdConfirm").formValidator({
				onshow : '请确认新密码',
				onfocus : '请确认新密码',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max :1000,
				onerror : "新密码必须输入且长度不得少于8！"
			}).functionValidator({
				fun : function(val, elem) {
					if (val != $("#newPwd").val()) {
						return "2次输入新密码不一致，请重新输入！";
					} else {
						return true;
					}
				}
			});
	
});