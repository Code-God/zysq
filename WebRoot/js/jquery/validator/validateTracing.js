/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "TracingRecordForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	// 可不填，任意字符串，长度范围0-10
	$("#cContent").formValidator({
				onshow : '请输入沟通内容1,5-1000字',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :1000,
				onerror : "沟通内容必填."
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