<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title><%=Version.getInstance().getSystemTitle()%></title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
				<script type="text/javascript" src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js"></script>
		<SCRIPT language=javascript>
         
	//初始化简历列表，ajax分页
	function initData(page){
		//替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<img src='<%=request.getContextPath()%>/images/loading.gif'> ");
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var stat = $("#stat").val();
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_exchangeList.Q",
			data: "page="+ page +"&limit=20&startDate="+startDate+"&endDate="+endDate+"&stat="+stat, //初始化显示第一页，每页20条
			dataType : "text",
			success : function(data) {
				if (data != "fail" && data != 'null') {
					$("#dataList").html("");
					$("#pageBar").html("");
					var page = eval("(" + data + ")");
					var nextPage =  (parseInt(page.page) + 1) >= page.totalPage ? page.totalPage :(parseInt(page.page) + 1);
					var curPage = page.page;
					var prePage = (page.page - 1 <= 1) ? "1" : (page.page - 1);
					var totalPage = page.totalPage;
					var pageBarDiv = "";
					//分页样式。。。。
					pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"+ curPage +"】页,总共【<b>"+ page.total +"</b>】条记录 ";
					pageBarDiv += "[<a href='###' onclick=\"initData("+ prePage +")\">前一页</a>]";
					if(totalPage >= 1){
						for(var i=1; i <= totalPage; i++){
							if(curPage == i){
								pageBarDiv += "<b>"+ i +"</b>";
							}else{
								pageBarDiv += "<a href='###' onclick=\"initData("+ i +")\" >"+ i +"</a>";
							}
						}
					}
					pageBarDiv += "[<a href='###' onclick=\"initData("+ nextPage +")\">后一页</a>]";
					pageBarDiv += "<select onchange='initData(this.options[selectedIndex].value)'>";
					if(totalPage >= 1){
						for(var i=1; i<=totalPage; i++){
							if(curPage == i){
								pageBarDiv += "<option value='"+ i +"' selected='selected'>第"+ i +"页</option>";
							}else{
								pageBarDiv += "<option value='"+ i +"'>第"+ i +"页</option>";
							}
						}
					}
					pageBarDiv += "</select>";
					pageBarDiv += "</div>";
					$("#pageBar").html(pageBarDiv);
					
					var list = page.datas;
					//表头
					var resumeList = "<tr style='thead'><TD>序号</TD><TD>礼品名称</TD><TD>兑换积分</TD><TD>兑换人</TD><TD>兑换时间</TD><TD>标记时间</TD><td>操作</TD></TR>";
					 
					//第二步：加载列表当页数据
					for(var j=0; j< list.length; j++){
						var zb = list[j];
						var openId = zb.openId;
						var ss= "";
						resumeList += "<TR>";
						resumeList += "<TD>"+ (j+1) +"</TD>";
						resumeList += "<TD>"+ zb.giftName +"</TD>";
						resumeList += "<TD>"+ zb.score +"</TD>";
						resumeList += "<TD>"+ zb.operator +"</TD>";
						resumeList += "<TD>"+ zb.opdate +"</TD>";
						resumeList += "<TD>"+ zb.markdate +"</TD>";
						resumeList += "<TD>";
						//resumeList += "<a href='<%=request.getContextPath()%>/admin.do?m=editCase&t="+ t +"&id="+ zb.id  +"'>编辑</a>";
						if(stat == 0){
							resumeList += "<a href='###' onclick='dh("+ zb.id +")'>标记为已兑换</a>";
						}
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
					
					$("#dataList").html(resumeList);
				}else{
					$("#dataList").html("数据加载失败。请<a href='###' onclick='initData(1)'>刷新</a>重试。");
				}
			}
		});
	}
	 
	   
	   function dh(id){
	   		if(window.confirm('确定标记为已兑换吗？')){
	   			$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/admin/admin_dh.Q?id="+id,
					dataType : "text",
					success : function(data) {// 回调
						 if(data == 'ok'){
						 	art.dialog("标记成功！");
						 	window.location.reload();
						 }
					}
				});
	   		}
	   }
	   
    </SCRIPT>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m3,msub32" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i> </span>
							<h5>
								积分兑换管理
							</h5>
							</div>
				          <div class="widget-content nopadding">
				             <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
				<TBODY>
					<TR>
						<TD width=15>
						</TD>
						<TD width="100%" background="<%=request.getContextPath() %>/index/YHChannelApply.files/new_020.jpg" height=20></TD>
						<TD width=15>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
				<TBODY>
					<TR>
						<TD width=15 background="<%=request.getContextPath() %>/index/YHChannelApply.files/new_022.jpg">
						</TD>
						<TD vAlign=top width="100%" bgColor=#ffffff>
							<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
								<TR>
									<TD class=manageHead>
										当前位置：后台 > <span id="navName">兑换管理</span>
									</TD>
								</TR>
								<TR>
									<TD height=2></TD>
								</TR>
							</TABLE>
							<TABLE CLASS="table table-bordered table-striped">
								<TBODY>
									<TR>
										<TD height=25>
											<FORM id=form1 name=form1 action="/hr/admin.do?m=exportExcel&t=r" method="post">
											<TABLE cellSpacing=0 cellPadding=2 border=0>
														<TBODY>
															<TR>
																<TD>
																	过滤条件：
																</TD>
																<TD>
																	按日期：
																	从 <input type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',vel:'d244_1',onpicked:checkdate,oncleared:checkdate})" id="startDate" name="startDate">
																	到 <input type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',vel:'d244_1',onpicked:checkdate,oncleared:checkdate})" id="endDate" name="endDate">
																</TD>
																<td> 是否兑换：</td>
																<TD>
																	 <select id="stat" name="stat" style="width: 100px">
																		 <option value="0" selected="selected">否</option>
																		 <option value="1">是</option>
																	 </select>
																</TD>
																 
																<TD>
																	<button class="btn btn-success" type="button" onclick="initData(1)"> 筛选 </button>
																</TD>
															</TR>
														</TBODY>
													</TABLE>
															</FORM>
												</TD>
											</TR>
											<TR>
												<TD>
													<TABLE id=grid class="table table-bordered table-striped">
														<TBODY id="dataList">
															 
															 
														</TBODY>
													</TABLE>
												</TD>
											</TR>
											<TR>
												<TD align=right height=25>
													<INPUT id=boxListValue type=hidden name=boxListValue>
												</TD>
											</TR>
											<TR>
												<TD>
													<SPAN id="pageBar">
													</SPAN>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
								</TD>
								<TD width=15 background="<%=request.getContextPath() %>/index/YHChannelApply.files/new_023.jpg">
								</TD>
							</TR>
						</TBODY>
					</TABLE>
					<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
						<TBODY>
							<TR>
								<TD width=15>
								</TD>
								<TD align=middle width="100%" background="<%=request.getContextPath() %>/index/YHChannelApply.files/new_025.jpg" height=15></TD>
								<TD width=15>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				          </div>
				        </div>
				</div>
			</div>

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
