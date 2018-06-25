/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "SendMsgForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	// 可不填，任意字符串，长度范围0-10
	$("#msgContent").formValidator({
				onshow : '请输入消息内容',
				onfocus : '请输入消息内容~',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :1000,
				onerror : "不说点什么嘛?"
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else {
				return true;
			}
		}
	});
	
});