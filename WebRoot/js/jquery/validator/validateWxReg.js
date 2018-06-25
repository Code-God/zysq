/**
 * 微信人员验证 修改密码验证
 */

$(document).ready(function() {
	$.formValidator.initConfig({
				formid : "wxRegForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});
	// 用户登录时输入的用户名，全局唯一，可输入英文、数字、下划线，长度范围1-20
	$("#name").formValidator({
				onshow : '请输入用户名',
				onfocus : '用户名不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max : 20,
				onerror : '长度范围1到20'
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

	 
	// 用户的工号，长度范围0-30
	$("#birth").formValidator({
				onshow : '请输入出生日期',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max : 8,
				onerror : '出生日期输入错误'
			}).regexValidator({
//				regexp : "^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",
				regexp : "^19[0-9][0-9]\\d{4}$",
				onerror : "你输入的生日格式不正确"
			});

	$("#height").formValidator({
				onshow : '请输入身高',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 3,
				max : 3,
				onerror : '请输入正确的身高'
			}).regexValidator({
				regexp : "^(1|2)[0-9][0-9]$",
				onerror : "你输入的身高格式不正确"
			});
			
	$("#telephone").formValidator({
				onshow : '请输入手机号码',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max : 13,
				onerror : '请输入正确的电话号码'
			});
	
	$("#area").formValidator({
				onshow : '请输入所在区域',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 2,
				max : 5,
				onerror : '请输入所在区域'
			});
	
});

