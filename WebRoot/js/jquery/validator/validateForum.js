/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "ForumForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	$("#ptitle").formValidator({
				onshow : '请输入栏目名称',
				onfocus : '请输入栏目名称',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :50,
				onerror : "栏目名称： 1-50字符！"
			});
	$("#pcontent").formValidator({
				onshow : '请输入栏目描述',
				onfocus : '请输入栏目描述',
				oncorrect : '合法'
			}).inputValidator({
				min : 5,
				max :2000,
				onerror : "栏目描述： 1-500字符！"
			});
	
});