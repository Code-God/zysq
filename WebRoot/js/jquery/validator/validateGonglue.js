/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "GonglueForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});


	$("#ptitle").formValidator({
				onshow : '请输入标题',
				onfocus : '请输入标题',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :50,
				onerror : "标题不得少于1个字符且不得大于50字符！"
			});
	$("#pcontent").formValidator({
				onshow : '请输入文章内容',
				onfocus : '请输入文章内容',
				oncorrect : '合法'
			}).inputValidator({
				min : 5,
				max :100000,
				onerror : "文章字数不得少于5个字符！"
			});
	
});