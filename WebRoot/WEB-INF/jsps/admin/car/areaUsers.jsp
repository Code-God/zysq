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
		<script type="text/javascript">
		// 根据组织机构ID加载分销客列表，ajax分页
		function loadUsers(page) {
			// 替换掉列表内容，显示为： 正在努力加载中....
			$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

			var keyword = $("#keyword").val();
			
			$.ajax({
				type : "POST",
				url : CONFIG.context + "/admin/car_areaUserList.Q",
				data : "page=" + page + "&limit=20", // 初始化显示第一页，每页50条
				dataType : "json",
				success : function(data) {
					if (data != "fail" && data != 'null') {
					
						$("#dataList").html("");
						$("#pageBar").html("");
						var page =  data ;
						var nextPage = (parseInt(page.page) + 1) >= page.totalPage
								? page.totalPage
								: (parseInt(page.page) + 1);
						var curPage = page.page;
						var prePage = (page.page - 1) <= 1 ? "1" : (page.page - 1);
						var totalPage = page.totalPage;
						var pageBarDiv = "";
						// 分页样式。。。。
						pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"
								+ curPage + "】页,总共【<b>" + page.total + "</b>】条记录 ";
						pageBarDiv += "[<a href='###' onclick=\"loadUsers(" + prePage + ")\">前一页</a>]";
						if (totalPage >= 1) {
							for (var i = 1; i <= totalPage; i++) {
								if (curPage == i) {
									pageBarDiv += "<b>" + i + "</b>";
								} else {
									pageBarDiv += "<a href='###' onclick=\"loadUsers(" + i + ")\" >" + i + "</a>";
								}
							}
						}
						pageBarDiv += "[<a href='###' onclick=\"loadUsers(" + nextPage + ")\">后一页</a>]";
						pageBarDiv += "<select style='width:90px' onchange='loadUsers(this.options[selectedIndex].value)'>";
						if (totalPage >= 1) {
							for (var i = 1; i <= totalPage; i++) {
								if (curPage == i) {
									pageBarDiv += "<option value='" + i + "' selected='selected'>第" + i + "页</option>";
								} else {
									pageBarDiv += "<option value='" + i + "'>第" + i	+ "页</option>";
								}
							}
						}
						pageBarDiv += "</select>";
						pageBarDiv += "</div>";
						$("#pageBar").html(pageBarDiv);

						var list = page.datas;
						// 表头:地市	县	工作单位	姓名	员工号	身份证号	岗位名称	联系电话
						//<table class="table table-bordered">
		              /*
		              <thead>
		                <tr>
		                  <th>Site</th>
		                  <th>Visits</th>
		                </tr>
		              </thead>
		            */
						var resumeList = "";
						// 第二步：加载列表当页数据
						for (var j = 0; j < list.length; j++) {
							var zb = list[j];
							resumeList += "<TR class=\"rsmTr\">";
							resumeList += "<TD>" + (j+1) + "</TD>";
							resumeList += "<TD>" + zb.openId.substring(0,3) + "...<a class=\"btn btn-success\" href=\"javascript:void(0)\" onclick=\"art.dialog('"+ zb.openId +"')\">Show</a></TD>";
							resumeList += "<TD>" + zb.email + "</TD>";
							resumeList += "<TD>" + zb.city + "</TD>";
							resumeList += "<TD>" + zb.province + "</TD>";
							resumeList += "<TD>" + zb.nickName + "</TD>";
							resumeList += "<TD>" + zb.telephone + "</TD>";
							resumeList += "<TD>" + zb.statusStr + "</TD>";
							resumeList += "<TD>" + zb.orderFee/100 + "元</TD>";//该分销客所得佣金
							/*
							resumeList += "<TD><a class=\"btn btn-danger \" href=\"javascript:void(0)\" onclick='showOrders("+ zb.id +")'>查看</a></TD>";//该分销客所得佣金
							resumeList += "<TD>" + zb.lastLoginStr + "</TD>";
							*/
							resumeList += "</TR>";
						}

						$("#dataList").html(resumeList);
					} else {
						$("#dataList")
								.html("数据加载失败。请<a href='###' onclick='initData(1)'>刷新</a>重试。");
					}
				}
			});
		}

		</script>
	</head>
	<body onload="loadUsers(1)">
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
					<!-- 右侧区域 -->
					<div class="span12">
						<div id="tableDiv" >
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>本区域【<s:property value="#request.area"/>】关注用户列表</h5>
						         </div>
								 <div class="widget-content ">
						          	 	<table class="table table-bordered">
						          	 		<thead>
								                <tr>
								                 <th>序号</th><th>OpenID</th><th>邮件</th> 
								                 <th>城市</th><th>县</th> 
								                 <th>姓名</th><th>电话</th>
								                 <th>状态</th><th>订单总额</th>
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
