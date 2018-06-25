<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
		<script src="<%=request.getContextPath() %>/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/header.jsp"%>
		<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/navigate.jsp"%>
		<!----页面导航------>
		<div class="linknav">
			<div class="breadcrumb">
				<h1>
					搜索结果为空
				</h1>
			</div>
		</div>
		<!--列表内容-->
		<div class="wf_content scontent" id="container">
			<div class="grid_c2a">
				<div class="main-wrap2">
					<div class="searchTip">
						<h2>
							很抱歉，没有找到与“${keyword }”相关的商品。
						</h2>
						<h3>
							建议您：
						</h3>
						<ol>
							<li>
								看看输入的文字是否有误
							</li>
							<li>
								请调整关键词，如“水果“等
							</li>
						</ol>
					</div>
				</div>

			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() { 
				 $(".mod_cate").hover(function(){
			  		$(this).toggleClass("mod_cate_on");
			    });
			});
		</script>
		<!----footerStart------>
		<%@include file="/WEB-INF/jsps/foot.jsp"%>
	</body>
</html>

