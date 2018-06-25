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
			$(function(){
				$("#type").change(function(){
					if($(this).val() == 1){
						$("#monthDiv").hide();
					}else{
						$("#monthDiv").show();
					}
				});
			});
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m10,msub103"/>
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
						<form action="<%=request.getContextPath() %>/admin/report_productSales.Q" method="post" id="reportForm">
							<div class="controls controls-row">
								<label class="span1 m-wrap" for="inputSuccess"
									style="margin-top: 5px">
									报表类型:
								</label>
								<select name="type" id="type" class="span1 m-wrap"
									style="margin-left: 5px; margin-right: 35px; width: 156px">
									<option value="1" <c:if test="${type==1 }"> selected</c:if>>
										年报表
									</option>
									<option value="2" <c:if test="${type==2 }"> selected</c:if>>
										月报表
									</option>
								</select>
								<label class="span1 m-wrap" for="inputSuccess"
									style="margin-top: 5px">
									年份:
								</label>
								<select name="year" id="year" class="span1 m-wrap"
									style="margin-left: 5px; margin-right: 35px; width: 156px">
									<c:forEach var="num" begin="2014" end="2050">
										<option value="${num }" <c:if test="${year==num }"> selected</c:if>>
											${num }年
										</option>
									</c:forEach>
								</select>
								<div id="monthDiv" <c:if test="${type == 1 }">style="display:none;"</c:if>>
								<label class="span1 m-wrap" for="inputSuccess"
									style="margin-top: 5px">
									月份:
								</label>
								<select name="month" id="month" class="span1 m-wrap"
									style="margin-left: 5px; margin-right: 35px; width: 156px">
									<c:forEach var="num" begin="1" end="12">
										<option value="${num }" <c:if test="${month==num }"> selected</c:if>>
											${num }月
										</option>
									</c:forEach>
								</select></div>
								&nbsp;&nbsp;&nbsp;
								<button type="submit" class="btn btn-success">
									查询
								</button>
							</div>
						</form>
					</div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-bar-chart"></i> </span>
						<h5>
							商品统计报表
						</h5>
					</div>
					<div class="controls-row">
						<div id="saleReportDiv">
							<script type="text/javascript"> 
								$(function(){
									var width = $("#saleReportDiv").width();
				                  	var myChart = new FusionCharts("Bar2D", "saleReport", width, "600", "0", "1");
				                  	myChart.setTransparent(true);
								    myChart.setDataXML("${xmlData}");
								    myChart.render("saleReportDiv");
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
