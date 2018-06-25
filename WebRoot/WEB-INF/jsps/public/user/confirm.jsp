<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/header1.jsp"%>
		<div class="log-wrap">
			<div class="grid_c1" <c:if test="${flag!='active' }">style="display: none;"</c:if>>
				<!-- <div class="fg-msg" id="active">
					<i class="fg-mail clo"></i>
					<div class="fg-cont">
						<p class="fg-cont-t">
							<span class="b mrm">${email }</span>
							<br>
							欢迎您加入到商城，请登录邮箱激活您的帐号
						</p>
						<p class="fg-cont-t b">
						</p>
						<a href="<%=request.getContextPath() %>/public/index.Q" class="lang-btn lang-btn-huge lang-btn-fixed-big mtm">
							<span class="lang-btn-content">返回首页</span> </a>
						<a href="javascript:;" id="sendagin"
							class="lang-btn lang-cancel mlm mtm">重新发送</a>
					</div>
				</div> -->
				<div class="fg-msg" id="success">
					<i class="fg-mail true"></i>
					<div class="fg-cont">
						<p class="fg-cont-t">
							电子邮箱验证成功
						</p>
						<p class="fg-cont-t b">
							您的邮箱
							<span style="color: #4ec05e;">${email }</span>${msg }
						</p>
						<a href="" class="lang-btn lang-btn-huge lang-btn-fixed-big mtm">
							<span class="lang-btn-content">进入我的设置</span> </a>
						<a href="<%=request.getContextPath() %>/public/index.Q" id="sendagin"
							class="lang-btn lang-cancel mlm mtm">返回首页>></a>
					</div>
				</div>
				<!-- <div class="fg-msg" <c:if test="${flag!='failed' }">style="display: none;"</c:if> id="failed">
					<i class="fg-mail warm"></i>
					<div class="fg-cont">
						<p class="fg-cont-t">
							<span class="b mrm">${email }</span>
							<br>
							${msg }
						</p>
						<p class="fg-cont-t b">
						</p>
						<a href="" class="lang-btn lang-btn-huge lang-btn-fixed-big mtm">
							<span class="lang-btn-content">重新发送</span> </a>
						<a href="<%=request.getContextPath() %>/public/index.Q" id="sendagin"
							class="lang-btn lang-cancel mlm mtm">返回首页>></a>
					</div>
				</div> -->
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

