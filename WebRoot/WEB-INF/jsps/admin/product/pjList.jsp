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
		<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<script type="text/javascript">
		
	// 根据组织机构ID加载成员列表，ajax分页
	function initData(page) {
		// 替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");
		
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_loadPjList.Q",
			data : "page=" + page + "&limit=20", // 初始化显示第一页，每页50条
			dataType : "json",
			success : function(data) {
				if (data.datas.length > 0) {
					$("#dataList").html("");
	
					var list = data.datas;
					// 表头: <th>序号</th> <th>分销商名称</th> <th>联系方式</th> <th>申请日期</th> <th>操作</th>
					var resumeList = "";
					
					$("#pageBar").html("");
				var page =  data ;
				var nextPage = (parseInt(page.page) + 1) >= page.totalPage ? page.totalPage : (parseInt(page.page) + 1);
				var curPage = page.page;
				var prePage = (page.page - 1) <= 1 ? "1" : (page.page - 1);
				var totalPage = page.totalPage;
				var pageBarDiv = "";
				// 分页样式。。。。
				pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"
						+ curPage + "】页,总共【<b>" + page.total + "</b>】条记录 ";
				pageBarDiv += "[<a href='###' onclick=\"initData(" + prePage + ")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b style='color:red'>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"initData(" + i + ")\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"initData(" + nextPage + ")\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='initData(this.options[selectedIndex].value)'>";
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
					
					// 第二步：加载列表当页数据
					for (var j = 0; j < list.length; j++) {
						var zb = list[j];
						resumeList += "<TR>";
						/*
						resumeList += "<TD><input type='checkbox'  value='"+ zb.id +"' onclick='set'></TD>";
						*/
						resumeList += "<TD>" + (j+1) + "</TD>";
						resumeList += "<TD>"+ zb.prdName +"</TD>";
						resumeList += "<TD>" + zb.content + "元</TD>";
						resumeList += "<TD>" + zb.score + "</TD>";
						resumeList += "<TD>" + zb.userName + "</TD>";
						resumeList += "<TD>" + zb.pjDate.substring(0,19) + "</TD>";
						resumeList += "<TD>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"delPj("+ zb.id +");\">删除</button>";
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
	
					$("#dataList").html(resumeList);
				} else {
					$("#dataList")
							.html("暂时没有数据。");
				}
			}
		});
	}
	
	//通过审核
	function auditPass(orgId){
			art.dialog({
			    content: '确定要审核通过吗？',
			    okVal:'确定',
			    ok: function () {
			    	 $.ajax({
						type : "POST",
						url : CONFIG.context + "/admin/fx_passAudit.Q",
						data : "orgId=" + orgId,
						dataType : "json",
						success : function(data) {
							if(data.result == "ok"){
								//刷新列表
								initData(1);
							}else{
								art.dialog("对不起，操作失败！");
							}
						}
					});
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
			});
	}
	
	
	//删除 记录
	function delPj(id){
		art.dialog({
		    content: '确定要删除此记录吗？',
		    okVal:'确定',
		    ok: function () {
		    	 $.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/admin_delPj.Q",
					data : "pjId=" + id,
					dataType : "text",
					success : function(data) {
						if(data == "ok"){
							//刷新列表
							initData(1);
						}else{
							art.dialog("对不起，操作失败！");
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true //为true等价于function(){}
		});
	}
	
</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m3,msub35" />
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
								评价管理
							</h5>
							<span class="icon"> &nbsp;</span>
							<span class="icon">  <i class="icon-hand-right"></i> </span>
							<span style="line-height: 35px;margin-left: 50px;display: none;">
								<button class="btn btn-danger" type="button" onclick="hidePj()">标记为隐藏</button>
							</span>
						</div>
						 
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>产品名称</th>
				                  <th>评价内容</th>
				                  <th>评分</th>
				                  <th>评价者</th>
				                  <th>评价时间</th>
				                  <th>操作</th>
				                </tr>
				              </thead>
				              <tbody id="dataList">
				                 
				              </tbody>
				            </table>
				             <div class="form-actions" id="pageBar">
				            </div>
				          </div>
				        </div>
				</div>
			</div>

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
