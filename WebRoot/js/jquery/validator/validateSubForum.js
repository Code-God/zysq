/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "subForumForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	$("#expertName").formValidator({
				onshow : '请输入专家姓名',
				onfocus : '请输入专家姓名',
				oncorrect : '合法'
			}).inputValidator({
				min : 2,
				max :10,
				onerror : "标题不得少于2个字符且不得大于10字符！"
			});
	$("#desc").formValidator({
				onshow : '请输入专家简介',
				onfocus : '请输入专家简介',
				oncorrect : '合法'
			}).inputValidator({
				min : 3,
				max : 30,
				onerror : "文章字数不得少于3个字符！"
			});
	
});