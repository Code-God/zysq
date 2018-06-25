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
	    <script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jstree/themes/default/style.min.css" />
	    <script src="<%=request.getContextPath() %>/js/jstree/jstree.min.js"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/fxPerson.js"></SCRIPT>
		<script type="text/javascript">
		</script>
	</head>
	<body onload="loadOrg()">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m2,msub23" />

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
							<div class="widget-title">
								<span class="icon"> <i class="icon-file"></i> </span>
								<h5>
									分销客分级总览
								</h5>
							</div>
							<div class="widget-content nopadding">
								 <div id="myTree"></div>
							</div>
						</div>

					</div>
					<!-- 右侧区域 -->
					<div class="span9">
						<div id="tableDiv" >
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>分销客列表</h5>
						         </div>
								 <div class="widget-content ">
						          	 	<table class="table table-bordered">
						          	 		<thead>
								                <tr>
								                 <th>序号</th><th>OpenID</th><th>邮件</th> 
								                 <th>城市</th><th>县</th> 
								                 <th>姓名</th><th>上级</th><th>电话</th>
								                 <th>状态</th><th>佣金</th><th>订单</th><th>最后登录</th>
								                </tr>
								              </thead>
											<TBODY id="dataList">
												 
											</TBODY>
										</table>
										<SPAN id="pageBar">
										</SPAN>
						          </div>
							</div>
						</div>
					</div>
				</div>

			<input type="hidden" id="isroot" value="">
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
			<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
	</body>
</html>
