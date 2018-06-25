$(document).ready(function() {
	$.formValidator.initConfig({
				formid : "klSubjectForm",
				wideword : false,
				onerror : function(msg) {
					return false;
				},
				onsuccess : function() {
					return true;
				}
			});
	$("#subjectName").formValidator({
				onshow : '请输入专题名称',
				onfocus : '专题名称为必填项',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max : 10,
				onerrormin : "不能为空",
				onerrormax : "专题名称长度最大为10"
			}).functionValidator({
				fun : notNull
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val))
					|| /\s{2}/.test($.trim(val)) || /^\s/.test($.trim(val))
					|| /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else {
				return true;
			}
		}
	});
})