<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jstree/themes/default/style.min.css" />
	</head>
	<body onload="loadDaili()">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m2,msub26" />

		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span3">
						<div class="widget-box">
						<button class="btn btn-primary" onclick="addDaili()">新增代理商</button>
							<div class="widget-title">
								<span class="icon"> <i class="icon-file"></i> </span>
								<h5>
									代理商列表
								</h5>
							</div>
							<div class="widget-content nopadding">
								 <div id="myTree"></div>
							</div>
						</div>

					</div>
					<!-- 右侧区域 -->
					<div class="span9">
							<hr>
							<!-- 此处显示基本信息 -->
							<div class="widget-box" id="orderInfo" style="display: none;">
					          <div class="widget-title"> <span class="icon"> <i class="icon-hand-right"></i></span>
					          <h5>
									订单列表
								</h5>
					          </div>
					          <div class="widget-content notify-ui" id="orderList">
					          	<!-- 该代理商的订单显示在这里 -->
					          	<table class="table table-bordered">
				          	 		<thead>
						                <tr>
						                 <th>序号</th><th>订单ID</th><th>支付用户</th><th>下单时间</th> 
						                 <th>订单金额</th><th>订单状态</th>
						                </tr>
						              </thead>
									<TBODY id="dataList">
										 
									</TBODY>
								</table>
								<SPAN id="pageBar">
								</SPAN>
					          	
					          </div>
					        </div>
					        <div id="tips">
								<h1 style="color: #00008B">请点击左侧的代理商名称查看。</h1>
							</div>
						</div>	
					</div>
				</div>
			<input type="hidden" id="orgId" value="0">
			<input type="hidden" id="currentNodeName" value="">
			<input type="hidden" id="isroot" value="">
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
			<script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script> 
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
	    <script src="<%=request.getContextPath() %>/js/jstree/jstree.min.js"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/dailiManage.js"></SCRIPT>
	</body>
</html>
