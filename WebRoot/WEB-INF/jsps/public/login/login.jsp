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
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/login.css">
		<script type="text/javascript" src="<%=path%>/js/jquery-1.10.2.js"></script>
		<style>
			.reg_title {
				height: 50px;
				line-height: 50px;
				text-indent: 125px;
			}
			
			.reg_title font {
				color: #3fb04f;
			}
		</style>
		<script type="text/javascript">
			function reloadValidateCode(){
				var src = $("#authcodeImage").attr("src");
				$("#authcodeImage").attr("src",src+"&_" + new Date());
			}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/header1.jsp"%>
		<div class="login" id="login">
			<div class="grid_c1">
				<div class="login_con">
					<div class="login_left">
						<img src="<%=path%>/images/login_l.jpg">
					</div>
					<div class="login_right">
						<div class="login_warming">${msg }</div>
						<form action="<%=path%>/login.html" method="POST">
							<div class="ubd">
								<div class="ubd_text">
									帐号：
								</div>
								<input name="userName" value="${userName }" class="ubd_input">
								<div style="clear: both"></div>
							</div>
							<div class="ubd">
								<div class="ubd_text">
									登陆密码：
								</div>
								<input name="password" type="password"
									class="ubd_input">
								<div style="clear: both"></div>
							</div>
							<div class="register">
								<div class="ubd_text">
									验证码：
								</div>
								<input type="text" name="codeImage" value="" maxlength="4">
								<img src="<%=path%>/getCodeImage?flag=login" id="authcodeImage" title="点击刷新" onclick="reloadValidateCode()">
							</div>
							<div class="deng">
								<input type="submit" value="登录">
								<a href="" class="forget_passWord">忘记密码了？</a>
							</div>
							<p class="reg_title">
								还没有账号，
								<a class="checkTitle" href="<%=request.getContextPath() %>/public/regist_gotoreg.Q"><font
									style="font-size: 12px">立即注册>> </font>
								</a>
							</p>
						</form>
					</div>
					<div class="clear"></div>
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

