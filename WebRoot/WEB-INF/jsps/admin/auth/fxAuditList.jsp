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
		<script type="text/javascript">
		
	// 根据组织机构ID加载成员列表，ajax分页
	function initData(page) {
		// 替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");
		
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_loadFxAuditList.Q",
			data : "page=" + page + "&limit=20", // 初始化显示第一页，每页50条
			dataType : "json",
			success : function(data) {
				if (data != "fail" && data != 'null') {
					$("#dataList").html("");
	
					var list = data;
					// 表头: <th>序号</th> <th>分销商名称</th> <th>联系方式</th> <th>申请日期</th> <th>操作</th>
					var resumeList = "";
					// 第二步：加载列表当页数据
					for (var j = 0; j < list.length; j++) {
						var zb = list[j];
						resumeList += "<TR>";
						resumeList += "<TD>" + (j+1) + "</TD>";
						resumeList += "<TD>" + zb.parentName + "</TD>";
						resumeList += "<TD>" + zb.orgname + "</TD>";
						resumeList += "<TD>" + zb.orgCode + "</TD>";
						resumeList += "<TD>" + zb.telephone + "</TD>";
						resumeList += "<TD>" + zb.applyDate + "</TD>";
						resumeList += "<TD>";
						resumeList += "<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"auditPass("+ zb.id +");\">通过</button>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"delAudit("+ zb.id +");\">删除</button>";
						resumeList += "</TD>";
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
	
	//删除待审核记录
	function delAudit(orgId){
		art.dialog({
		    content: '确定要删除此记录吗？',
		    okVal:'确定',
		    ok: function () {
		    	 $.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/fx_delAudit.Q",
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
	
	
</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m2,msub22" />
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
								待审核分销商列表
							</h5>
							</div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>上级分销商</th>
				                  <th>分销商名称</th>
				                  <th>分销商编号</th>
				                  <th>联系方式</th>
				                  <th>申请日期</th>
				                  <th>操作</th>
				                </tr>
				              </thead>
				              <tbody id="dataList">
				                 
				              </tbody>
				            </table>
				            <div id="pageBar">
				            </div>
				          </div>
				        </div>
				</div>
			</div>

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
