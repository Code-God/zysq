$(document).ready(function() {
	$.formValidator.initConfig({
				formid : "addWorkGroupFormId",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					$("#addWorkGroupSubmitButton").attr("disabled", true);
					return true;
				}
			});
	$("#workGroupNameId").formValidator({
				onshow : '请输入工作组名称',
				onfocus : '工作组名称为必填项',
				oncorrect : '合法'
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else if ($.trim(val).length <= 30 && $.trim(val).length >= 1) {
				return true;
			} else {
				return '工作组名称输入错误，长度在1到30之间';
			}
		}
	});
	$("#workGroupMemoId").formValidator({
				onshow : '请输入工作组描述',
				onfocus : '工作组描述为必填项',
				oncorrect : '合法'
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val))) {
				return  "不能包含<，&，>，\'，\"，\\";
			} else if ($.trim(val).length <= 255 && $.trim(val).length >=1) {
				return true;
			} else {
				return '工作组描述输入错误，长度在1到255之间';
			}
		}
	});
});