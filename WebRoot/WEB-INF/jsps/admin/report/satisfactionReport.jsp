<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
		<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<script src="<%=request.getContextPath() %>/js/fusionchart/FusionCharts.js"></script>
		<script src="<%=request.getContextPath() %>/js/fusionchart/FusionCharts.HC.js"></script>
		<script src="<%=request.getContextPath() %>/js/fusionchart/FusionCharts.HC.Charts.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m10,msub104"/>
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home"
						class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-content">
					</div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-bar-chart"></i> </span>
						<h5>
							满意度统计报表
						</h5>
					</div>
					<div class="controls-row">
						<div id="satisfactionReportDiv">
							<script type="text/javascript"> 
								$(function(){
									var width = $("#satisfactionReportDiv").width();
									var chartType = "MSColumn3D";
				                  	var myChart = new FusionCharts(chartType, "satisfactionReport", width, "500", "0", "1");
				                  	myChart.setTransparent(true);
								    myChart.setDataXML("${xmlData}");
								    myChart.render("satisfactionReportDiv");
								});
			            	</script>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
