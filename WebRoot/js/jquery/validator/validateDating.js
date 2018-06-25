/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "DatingForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	$("#maleId").formValidator({
				onshow : '请输入男会员ID',
				onfocus : '请输入男会员ID~',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :1000,
				onerror : "会员ID必须输入"
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
	$("#femaleId").formValidator({
				onshow : '请输入男会员ID',
				onfocus : '请输入男会员ID~',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :1000,
				onerror : "会员ID必须输入"
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
	
	
	// 约会时间必选
	$("#datingDate").formValidator({
				onshow : '请输入约会时间',
				onfocus : '约会时间不能为空',
				empty: false,
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :20,
				onerror : "约会时间必须输入"
			});
	$("#location").formValidator({
				onshow : '请输入约会地点',
				onfocus : '约会地点不能为空',
				empty: false,
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :20,
				onerror : "约会地点必须输入"
			});
	
});