/**
 * 创建资产模块的验证
 */
$(document).ready(function() {
	$.formValidator.initConfig({
				formid : "mailForm",
				onerror : function(msg) {
					// alert(msg)
				},
				onsuccess : function() {
					save();
					return false;
				}
			});

	$("#address").formValidator({
				onshow : JS_I18N.mail_property_address_input,
				onfocus : JS_I18N.mail_property_address_not_null,
				oncorrect : JS_I18N.issue_legal
			}).inputValidator({
				min : 1,
				max : 255,
				onerror : JS_I18N.mail_property_address_not_null
			}).regexValidator({
		regexp : regexEnum.email,
		onerror : JS_I18N.mail_property_address_format_error
	});

	$("#port").formValidator({
				onshow : JS_I18N.mail_property_port_input,
				onfocus : JS_I18N.mail_property_port_not_null,
				oncorrect : JS_I18N.issue_legal
			}).inputValidator({
				min : 1,
				max : 65536,
				type : "number",
				onerror : JS_I18N.mail_property_port_not_null,
				onerrormax:JS_I18N.mail_property_port_max
			}).regexValidator({
				regexp : regexEnum.intege,
				onerror : JS_I18N.mail_property_port_is_number
			});

	$("#smtpServer").formValidator({
				onshow : JS_I18N.mail_property_smtpServer_input,
				onfocus : JS_I18N.mail_property_smtpServer_not_null,
				oncorrect : JS_I18N.issue_legal
			}).inputValidator({
				min : 1,
				max : 255,
				onerror : JS_I18N.mail_property_smtpServer_not_null
			}).regexValidator({
				regexp : regexEnum.internet+"|"+regexEnum.ip4,
				onerror : JS_I18N.mail_property_smtpServer_format_error
			});

	$("#user").formValidator({
				onshow : JS_I18N.mail_property_user_input,
				onfocus : JS_I18N.mail_property_user_not_null,
				oncorrect : JS_I18N.issue_legal
			}).inputValidator({
				min : 1,
				max : 30,
				onerror : JS_I18N.mail_property_user_not_null,
				onerrormax:JS_I18N.mail_property_user_max
			}).regexValidator({
				regexp : regexEnum.email,
				onerror : JS_I18N.mail_property_address_format_error
			});

	$("#password").formValidator({
				onshow : JS_I18N.mail_property_password_input,
				onfocus : JS_I18N.mail_property_password_not_null,
				oncorrect : JS_I18N.issue_legal
			}).inputValidator({
				min : 1,
				max : 30,
				onerror : JS_I18N.mail_property_password_not_null,
				onerrormin:JS_I18N.mail_property_password_min,
				onerrormax:JS_I18N.mail_property_password_max
			});

});