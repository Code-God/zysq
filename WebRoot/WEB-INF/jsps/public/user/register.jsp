<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
		<script type="text/javascript">
			function check(){
				if(!$("#email").val().trim()){
					$("#emailwarning").html("请输入邮箱地址");
					$(".reg_warning,.reg_warning1").hide();
					$("#emailwarning").show();
					return false;
				}
				var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
				if(!reg.test($("#email").val())){
					$("#emailwarning").html("您输入的邮箱地址不合法");
					$(".reg_warning,.reg_warning1").hide();
					$("#emailwarning").show();
					return false;
				}
				if(!$("#nickName").val().trim()){
					$("#nickwarning").html("请输入昵称");
					$(".reg_warning,.reg_warning1").hide();
					$("#nickwarning").show();
					return false;
				}
				if(!$("#password").val().trim()){
					$("#pwdwarning").html("请输入密码");
					$(".reg_warning,.reg_warning1").hide();
					$("#pwdwarning").show();
					return false;
				}
				if(!$("#password1").val().trim()){
					$("#pwdwarning1").html("请再次输入密码");
					$(".reg_warning,.reg_warning1").hide();
					$("#pwdwarning1").show();
					return false;
				}
				if($("#password1").val() != $("#password").val()){
					$("#pwdwarning1").html("两次输入的密码不一致");
					$(".reg_warning,.reg_warning1").hide();
					$("#pwdwarning1").show();
					return false;
				}
				if(!$("#verifyCode").val().trim()){
					$("#codewarning").html("请输入验证码");
					$(".reg_warning,.reg_warning1").hide();
					$("#codewarning").show();
					return false;
				}
				if(!$("#xieyi").is(":checked")){
					return false;
				}
				return true;
			}
			
			function reloadValidateCode(){
				var src = $("#authcodeImage").attr("src");
				$("#authcodeImage").attr("src",src+"&_" + new Date());
			}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/header1.jsp"%>
		<div class="Register" id="Register">
			<div class="grid_c1">
				<div class="Register_con">
					<div class="tab">
						<ul class="tabs">
							<li>
								<a href="#">用户注册</a>
							</li>
						</ul>
						<p class="login_r">
							已有账号，
							<a href="<%=request.getContextPath() %>/public/GoLogin.Q">立即登陆>></a>
						</p>
						<!-- / tabs -->
						<div class="tab_content">
							<div class="tabs_item">
								<div class="main_l">
									<form action="<%=request.getContextPath() %>/public/regist_regist.Q" method="POST">
										<div class="ubd">
											<div class="ubd_text">
												<span class="im_col">*</span>Email：
											</div>
											<input name="user.email" value="${email }" class="ubd_input" id="email"/>
											<div class="reg_warning" id="emailwarning">${emailMsg }</div>
											<div style="clear: both"></div>
										</div>
										<div class="ubd">
											<div class="ubd_text">
												<span class="im_col">*</span>昵称：
											</div>
											<input name="user.nickName" value="${nickName }" class="ubd_input" id="nickName"/>
											<div class="reg_warning" id="nickwarning">${nickMsg }</div>
											<div style="clear: both"></div>
										</div>
										<div class="ubd">
											<div class="ubd_text">
												<span class="im_col">*</span>登陆密码：
											</div>
											<input name="user.password" type="password" value="" class="ubd_input" id="password"/>
											<div class="reg_warning" id="pwdwarning">
											</div>
											<div style="clear: both"></div>
										</div>
										<div class="ubd">
											<div class="ubd_text">
												<span class="im_col">*</span>确认密码：
											</div>
											<input name="password" type="password" value="" class="ubd_input" id="password1"/>
											<div class="reg_warning" id="pwdwarning1">
											</div>
											<div style="clear: both"></div>
										</div>
										<div class="register">
											<div class="ubd_text">
												验证码：
											</div>
											<input type="text" name="verifyCode" id="verifyCode" value="" maxlength="4"/>
											<img src="<%=request.getContextPath() %>/getCodeImage?flag=regist" id="authcodeImage" title="点击刷新" onclick="reloadValidateCode()">
											<div class="reg_warning1" id="codewarning">${vcodemsg }</div>
										</div>
										<p class="frm">
											<input name="" type="checkbox" value="" id="xieyi" />
											<span>我已阅读<a>《商城服务协议》</a>
											</span>
										</p>
										<div class="deng">
											<input type="submit" value="立即注册" onclick="return check();"/>
										</div>
									</form>
								</div>
								<div class="main_r">
									<img src="<%=request.getContextPath() %>/images/login_r.jpg">
								</div>
								<div class="clear"></div>
							</div>
							<!-- / tabs_item -->
						</div>
						<!-- / tab_content -->
					</div>
				</div>
			</div>
		</div>
		<!----footerStart------>
		<%@include file="/WEB-INF/jsps/foot.jsp"%>
	</body>
	<script>
$(document).ready(function() {
		$('.tab ul.tabs').addClass('active').find('> li:eq(0)').addClass('current');
		
		$('.tab ul.tabs li a').click(function (g) { 
			var tab = $(this).closest('.tab'), 
				index = $(this).closest('li').index();
			tab.find('ul.tabs > li').removeClass('current');
			$(this).closest('li').addClass('current');
			tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
			tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
			g.preventDefault();
		} );
});
</script>
</html>

