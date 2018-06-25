<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title><%=Version.getInstance().getSystemTitle() %>-购物车</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cart.css">
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
	<script src="<%=request.getContextPath() %>/js/norefresh.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/jsps/header.jsp"%>
	<div class="wf_header">
		<div class="grid_c1">
			<div class="mod_logo">
				<h1>
					<a href="<%=request.getContextPath() %>/public/index.Q"><img src="<%=request.getContextPath() %>/images/logo.jpg" alt="商城"> </a>
				</h1>
			</div>
			<div class="flow_step_no3 flow_cart">
				<div class="flow_step">
					<ol class="cols3">
						<li class="step_1">
							1.我的购物车
						</li>
						<li class="step_2">
							2.填写核对订单
						</li>
						<li class="step_3">
							3.订单提交成功
						</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	
	<div class="log-wrap">
	  <div class="grid_c1">
	    <div class="fg-msg">
	      <i class="fg-success"></i>
	      <div class="fg-cont">
	         <p class="fg-cont-t"><span class="b mrm">订单提交成功 订单号：${orderNo }</span></p>
	         <a href="javascript:;" class="lang-btn lang-btn-huge lang-btn-fixed-big mtm">
	           <span class="lang-btn-content" onclick="alert('系统支付中，请稍等...');">立即支付</span>
	         </a>
	         <a href="<%=request.getContextPath() %>/private/account_ordersMgt.Q" class="lang-btn lang-cancel mlm mtm">查看订单</a>
	      </div>
	    </div>
	  </div>
	</div>
	
	<jsp:include page="/WEB-INF/jsps/foot.jsp"></jsp:include>
	
</body>
</html>
