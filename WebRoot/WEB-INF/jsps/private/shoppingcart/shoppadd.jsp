<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title><%=Version.getInstance().getSystemTitle() %>-添加到购物车</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cart.css">
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsps/header.jsp"%>
<div class="wf_header">
  <div class="grid_c1">
    <div class="mod_logo">
      <h1><a href="<%=request.getContextPath() %>/public/index.Q"><img src="images/logo.jpg" alt="商城"></a></h1>
    </div>
  </div>
</div>
<div class="cart_content" id="cart_content">
  <div class="grid_c1">
    <div class="success" id="">
      <div class="goods_add_inner">
        <div class="success-b">
            <h3>商品已成功加入购物车！</h3>
        </div>
        <span style="float:right;margin: -35px 80px 0 0;">
        <a href="<%=request.getContextPath() %>/private/shopping_list.Q" class="btn-pay">去结算</a>
        <a href="<%=request.getContextPath() %>/public/details.Q?code=${prdCode }" class="btn-continue">继续购物</a>
        </span>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/jsps/foot.jsp"></jsp:include>

</body>
</html>
