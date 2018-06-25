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
		<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">
		function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
	}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m10,msub102"/>
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
						<form action="<%=request.getContextPath() %>/admin/report_financeReport.Q" method="post" id="reportForm">
							<div class="controls controls-row">
							<label class="span1" for="inputSuccess" style="margin-top:5px">统计时间:</label>
	            
	            <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${startTime}" class="wdateinput" />
		                  	&nbsp;至&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="wdateinput" />
	            &nbsp; &nbsp;
								&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn btn-success">
									查询
								</button>
								 <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
							</div>
						</form>
					</div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-bar-chart"></i> </span>
						<h5>
							订单统计报表
						</h5>
					</div>
					<div class="controls-row">
						<div id="ordersNumReportDiv">
							<script type="text/javascript"> 
								$(function(){
									var chartType = "Column3D";
									var width1 = $("#ordersNumReportDiv").width();
				                  	var myChart1 = new FusionCharts(chartType, "ordersNumReport", width1, "400", "0", "1");
				                  	myChart1.setTransparent(true);
								    myChart1.setDataXML("${numXmlData}");
								    myChart1.render("ordersNumReportDiv");
								    
								     var width2 = $("#ordersMoneyReportDiv").width();
								    var myChart2 = new FusionCharts(chartType, "ordersMoneyReport", width2, "400", "0", "1");
				                  	myChart2.setTransparent(true);
								    myChart2.setDataXML("${moneyXmlData}");
								    myChart2.render("ordersMoneyReportDiv");
								});
			            	</script>
						</div>
					</div>
					<div class="controls-row">
						<div id="ordersMoneyReportDiv">
							
						</div>
					</div>
				</div>
						
				</div>
			</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
