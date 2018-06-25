/**
 * 运维人员验证 修改密码验证
 */

$(document).ready(function() {

	$.formValidator.initConfig({
				formid : "registerForm",
				wideword : false,
				onerror : function(msg) {
				},
				onsuccess : function() {
					return true;
				}
			});
	// 用户登录时输入的用户名，全局唯一，可输入英文、数字、下划线，长度范围1-20
	$("#username").formValidator({
				onshow : '请输入用户名',
				onfocus : '用户名不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max : 20,
				onerror : '长度范围1到20'
			}).regexValidator({
				regexp : "^[0-9|a-zA-Z|_]{1,20}$",
				onerror : "只能输入字母，数字，下划线"
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

	// 用户旧密码 字符串 4-8
	$("#useroldPassWord").formValidator({
				onshow : '请输入旧密码',
				onfocus : '旧密码不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max : 20,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "密码两边不能有空格！"
				},
				onerror : "密码必填, 长度为8-20个字符！"
			});
	// 用户新密码 字符串 4-8
	$("#usernewPassWord").formValidator({
				onshow : '请输入新密码',
				onfocus : '新密码不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max : 20,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "密码两边不能有空格！"
				},
				onerror : "密码必填, 长度为8-20个字符！"
			}).compareValidator({
				desid : "useroldPassWord",
				operateor : "!=",
				onerror : "输入错误，新密码和旧密码相同！"
			});
	// 用户新密码确认 字符串 4-8
	$("#usernewPassWordConfirm").formValidator({
				onshow : '请输入确认密码',
				onfocus : '请输入确认密码',
				oncorrect : '合法'
			}).inputValidator({
				min : 8,
				max : 20,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "密码两边不能有空格！"
				},
				onerror : "密码必填, 长度为8-20个字符！"
			}).compareValidator({
				desid : "usernewPassWord",
				operateor : "=",
				onerror : "请确认,两次密码不一致"
			});

	// 用户的姓名，必填，长度范围2-20，可重复
	$("#userrealName").formValidator({
				onshow : '请输入姓名',
				onfocus : '姓名不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 2,
				max : 20,
				onerror : '姓名不能为空，长度为2到20'
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			}else{
			return true;
			}
		}
	});
	// 用户的姓名，必填，长度范围2-20，可重复
	$("#nickName").formValidator({
				onshow : '请输入昵称',
				onfocus : '昵称不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 2,
				max : 20,
				onerror : '昵称不能为空，且长度必须为2到20之间'
			}).functionValidator({
			fun : function(val, elem) {
				if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
						|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
					return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
				}else{
					return true;
				}
			}
		});
	// 用户的工号，长度范围0-30
	$("#useremployeeNo").formValidator({
				onshow : '请输入工号',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max : 30,
				onerror : '工号输入错误，最大长度为30'
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else  {
				return true;
			} 
		}
	});

	// 上网账号 用户的上网唯一账号，字符串，长度范围0-30
	$("#internetAccount").formValidator({
				onshow : '请输入上网账号',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max : 30,
				onerror : '上网账号输入错误，最大长度为30'
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else{
				return true;
			} 
		}
	});

	// 工作组
	$("#selectWorkGroup").formValidator({
				onshow : '请选择工作组',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				onerror : '合法'
			});

	// 角色
	$("#multipleSelectRoleId").formValidator({
				onshow : '请选择角色',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).functionValidator({
				fun : function(val, elem){
					if(elem.options.length<1){
						return "角色不能为空，请选择角色！";
					}else{
						return true;
					}
				}
	});
	$("#multipleSelectRoleId").bind("mouseout", function(){
		$("#multipleSelectRoleId").blur();
	});
	$("#allSelectRoleId").bind("mouseout", function(){
		$("#multipleSelectRoleId").blur();
	});
	
	// 部门
	$("#selectuserdept").formValidator({
				onshow : '请选择部门',
				onfocus : '部门不能为空',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				onerror : '部门不能为空'
			});
	// 长度范围0-255
	$("#usertaskNotifier").formValidator({
				onshow : '请输入技能描述',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max : 255,
				onerror : '输入错误，长度范围为0到255'
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

	$("#mail").formValidator({
				onshow : '请输入Email',
				onfocus : '请填写邮箱,如jack@abc.com',
				oncorrect : '合法'
			}).inputValidator({
				min : 1,
				max :50,
				onerror : "请填写邮箱,如jack@abc.com"
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else {
				return true;
			}
		}
	}).regexValidator({
		regexp : "^(\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+)?$",
		onerror : "邮箱格式错误！"
	});

	// 用户的电子邮件，字符串格式，非必填，符合邮件地址格式，长度范围为6-50，全局唯一
	// 必填，长度范围1-50
	$("#telephone").formValidator({
				onshow : '请输入手机号码',
				onfocus : '该项为必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 5,
				max :50,
				onerror : "请输入正确的手机号."
			}).functionValidator({
		fun : function(val, elem) {
			if (/[<|&|>|'|"|\\]+/.test($.trim(val)) || /\s{2}/.test($.trim(val))
					|| /^\s/.test($.trim(val)) || /\s$/.test($.trim(val))) {
				return "不能包含&，\\，|，<，>，\'，\"，连续的2个空格";
			} else  {
				return true;
			}
		}
	});

	// 可不填，格式不做校验，用户自己填，长度范围0-20
	$("#userfax").formValidator({
				onshow : '请输入传真',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "传真输入错误，最大长度为50"
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


	// 可不填，格式不做校验，用户自己填，长度范围0-20
	$("#useroffice").formValidator({
				onshow : '请输入固定电话',
				onfocus : '该项为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "固定电话输入错误，最大长度为50"
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
	
	$("#imgCodeStr").formValidator({
				onshow : '请输入验证码',
				onfocus : '请输入验证码',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "请输入验证码"
			}).functionValidator({
		fun : function(val, elem) {
			if (val == "") {
				return "请输入验证码";
			} else {
				return true;
			} 
		}
	});
	$("#idCard").formValidator({
				onshow : '请输入身份证',
				onfocus : '请输入身份证',
				oncorrect : '合法'
			}).inputValidator({
				min : 16,
				max :520,
				onerror : "请输入正确的省份证号码."
			}).regexValidator({
				regexp : "idcard",
				datatype : "enum",
				onerror : "身份证不正确"
			});
	
	

	// 英文或数字，长度范围0-50
	$("#msn").formValidator({
				onshow : "请输入MSN",
				onfocus : "MSN为非必填项",
				oncorrect : "合法"
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "MSN输入错误，最大长度50"
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
	// 英文或数字，长度范围0-50
	$("#qq").formValidator({
				onshow : "请输入QQ号",
				onfocus : "QQ为非必填项",
				oncorrect : "合法"
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "MSN输入错误，最大长度50"
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

	// 可不填，任意字符串，长度范围0-10
	$("#usercountry").formValidator({
				onshow : '请输入国家名称',
				onfocus : '国家名称为非必须输入项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max :10,
				onerror : "国家名称输入错误，最大长度为10"
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

	// 可不填，任意字符串，长度范围0-50
	$("#useraddress").formValidator({
				onshow : '请输入地址',
				onfocus : '地址为非必填项',
				oncorrect : '合法'
			}).inputValidator({
				min : 0,
				max :50,
				onerror : "地址输入错误，最大长度为50"
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

	// 可不填，数字格式，比如200122，长度为5-20
	$("#userzipcode").formValidator({
				onshow : "请输入邮编",
				onfocus : "邮编为非必填项",
				oncorrect : "合法"
			}).inputValidator({
				min : 0,
				max :20,
				onerror : "邮编输入错误，最大长度为20"
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

	// 必须选择
	$("#city").formValidator({
				onshow : '请选择所在地区',
				onfocus : '地区为必选项',
				oncorrect : '合法'
			}).functionValidator({
				fun : function(val, elem) {
					if(val == "0"){
						return "请选择您所在的地区。";
					}else{
						return true;
					}
				}
	});
	// 必须选择
	$("#diploma").formValidator({
				onshow : '请选择您的学历',
				onfocus : '学历为必选',
				oncorrect : '合法'
			}).functionValidator({
				fun : function(val, elem) {
					if(val == "0"){
						return "请选择您的学历。";
					}else{
						return true;
					}
				}
	});
	// 必须选择
	$("#salary").formValidator({
				onshow : '请选择您的月薪',
				onfocus : '月薪为必选',
				oncorrect : '合法'
			}).functionValidator({
				fun : function(val, elem) {
					if(val == "0"){
						return "请选择月薪。";
					}else{
						return true;
					}
				}
	});

	// 必填,生日
	$("#birthday").formValidator({
				onshow : '请输入生日',
				onfocus : '生日为必填项',
				oncorrect : '合法'
			}).functionValidator({
				fun : function(val, elem) {
					if(val == ""){
						return "请选择生日。";
					}else{
						return true;
					}
				}
	});
	
	
	
});




function checkNick(val){
	UserService.nickNameExists(val, function(data){
		if(data){
		   $("#nickNameTip").html('昵称已经存在,请更换昵称.');
		   $("#nickNameTip").removeClass();
		   $("#nickNameTip").addClass("onError");
		   $("input[type='submit']").attr("disabled",true);
		}else{
			$("#nickNameTip").html("");
			$("#nickNameTip").removeClass();
		    $("#nickNameTip").addClass("onCorrect");
		    $("input[type='submit']").attr("disabled",false);
		}
	});
}


function checkMail(val){
	UserService.emailExists(val, function(data){
		if(data){
		   $("#mailTip").html('该邮箱已被注册.');
		   $("#mailTip").removeClass();
		   $("#mailTip").addClass("onError");
		   $("input[type='submit']").attr("disabled",true);
		}else{
			$("#mailTip").html("");
			$("#mailTip").removeClass();
		    $("#mailTip").addClass("onCorrect");
		    $("input[type='submit']").attr("disabled",false);
		}
	});
}


function checkTel(val){
	if(!/^\d{11}$/.test($.trim(val))){
		$("#telTip").html("请输入正确的手机号码。");
		$("#telTip").css("height","26px");
		$("#telTip").removeClass();
	    $("#telTip").addClass("onError");
	    $("input[type='submit']").attr("disabled",true);
	}else{
		$("#telTip").html("");
		$("#telTip").css("height","0px");
		$("#telTip").removeClass();
	    $("#telTip").addClass("onCorrect");
	    $("input[type='submit']").attr("disabled",false);
	}
}




