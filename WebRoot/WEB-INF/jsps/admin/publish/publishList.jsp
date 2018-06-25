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
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/admin.js"></SCRIPT>
		<script type="text/javascript">
			    
	//初始化简历列表，ajax分页
	function initData(page){
		//替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<img src='<%=request.getContextPath()%>/images/loading.gif'> ");
		
		var keyword = $("#keyword").val();
		var t = "1";
		var model = "1";
		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_getShowCase.Q",
			data: "page="+ page +"&limit=20&t=1&keyword="+keyword + "&model=" + model, //初始化显示第一页，每页20条
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
					pageBarDiv += "<select style='width:50px' onchange='initData(this.options[selectedIndex].value)'>";
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
					var resumeList = "";
					 
					//第二步：加载列表当页数据
					for(var j=0; j< list.length; j++){
						var zb = list[j];
						var openId = zb.openId;
						var ss= "";
						resumeList += "<TR>";
						resumeList += "<TD>"+ (j+1) +"</TD>";
						resumeList += "<TD>"+ zb.title +"</TD>";
						resumeList += "<TD>"+ zb.authorName +"</TD>";
						resumeList += "<TD>"+ zb.createDate +"</TD>";
						if(zb.columns != null){
							resumeList += "<TD>"+ zb.columns.title +"</TD>";
						}else{
							resumeList += "<TD>&nbsp;</TD>";
						}
						resumeList += "<TD>";
						resumeList += "<a href='<%=request.getContextPath()%>/admin/admin_editCase.Q?t="+ t +"&id="+ zb.id  +"&model="+ model +"'>编辑</a>&nbsp;&nbsp;";
						resumeList += "<a href='###' onclick='delCase("+ zb.id +")'>删除</a>";
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
	 
	 //新增内容
	   function addCase(model){
			window.location.href="<%=request.getContextPath()%>/admin/admin_gotoAddCase.Q?t=1&model=" + model;
	   }
	   
	   
	   function delCase(id){
	   		if(window.confirm('确定要删除此记录吗？')){
	   			$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/admin/admin_delCase.Q?ids="+id,
					dataType : "text",
					success : function(data) {// 回调
						 if(data == 'ok'){
						 	art.dialog("删除成功！");
						 	window.location.reload();
						 }
					}
				});
	   		}
	   }
	   
		
		</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m4,msub42" />
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
								微站模块文章发布列表
							</h5>
						</div>
						<div class="widget-title">
							按标题关键字
							<input type="text" id="keyword" name="keyword">
							<button class="btn btn-success" onclick="initData(1)">查询</button>
							<button class="btn btn-success" onclick="addCase(1)">发布</button>
						</div>
						<div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>标题</th>
								  <th>作者</th>
								  <th>创建时间</th>
								  <th>所属模块</th>
				                  <th>操作</th>
				                </tr>
				              </thead>
				              <tbody id="dataList">
				                 
				              </tbody>
				            </table>
				            <div id="pageBar" class="widget-title">
													            
				            </div>
				          </div>
				        </div>
				</div>
			</div>

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
