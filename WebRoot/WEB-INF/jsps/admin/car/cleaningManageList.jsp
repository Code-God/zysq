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
			url : CONFIG.context + "/admin/car_loadCleaningList.Q",
			data : "page=" + page + "&limit=20", // 初始化显示第一页，每页50条
			dataType : "json",
			success : function(data) {
				if (data.datas && data.datas.length > 0) {
					$("#dataList").html("");
	
					var list = data.datas;
					// 表头: <th>序号</th> <th>分销商名称</th> <th>联系方式</th> <th>申请日期</th> <th>操作</th>
					var resumeList = "";
					
					
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
				pageBarDiv += "[<a href='###' onclick=\"initData(" + prePage + ")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
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
						resumeList += "<TD>" + (j+1) + "</TD>";
						resumeList += "<TD>" + zb.facName + "</TD>";
						resumeList += "<TD>" + zb.serviceContent + "</TD>";
						resumeList += "<TD>" + zb.price/100 + "元</TD>";
						resumeList += "<TD>" + zb.telephone + "</TD>";
						resumeList += "<TD>" + zb.address + "</TD>";
						resumeList += "<TD>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"delRecord("+ zb.id +");\">删除</button>";
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
	
					$("#dataList").html(resumeList);
				} else {
					var ss ="<td colspan='7'><div class=\"alert alert-block\">";
					ss += "<h4 align=\"center\" class=\"alert-heading\">暂时没有符合条件的记录！</h4>";
		            ss += "</div></td>";
					$("#dataList").html(ss);
				}
			}
		});
	}
	 
	
	//删除 记录
	function delRecord(id){
		art.dialog({
		    content: '确定要删除此记录吗？',
		    okVal:'确定',
		    ok: function () {
		    	 $.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/car_delCleaning.Q",
					data : "dbId=" + id,
					dataType : "json",
					success : function(data) {
						if(data.msg == "ok"){
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
	
	function showAdd(){
		$("#rescueInfoDiv").show();
	}
	
	function addRescue(){
		var param  = {
			"facName":$("#facName").val(),
			"serviceContent": $("#serviceContent").val(),
			"price": $("#price").val(),
			"telephone": $("#telephone").val(),
			"charger": $("#charger").val(),
			"address": $("#address").val()
		};
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/car_addCleaning.Q",
			data : param,
			dataType : "json",
			success : function(data) {
				if(data.msg == "ok"){
					//刷新列表
					initData(1);
					$("#rescueInfoDiv").hide();
				}else{
					art.dialog("对不起，操作失败！");
				}
			}
		});
	}
	
	 
</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m8,msub83" />
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
								洗车信息发布
							</h5>
							<span class="icon"> &nbsp;</span>
							<span class="icon">  <i class="icon-hand-right"></i> </span>
							<span style="line-height: 35px;margin-left: 50px">
								<button class="btn btn-danger" type="button" onclick="showAdd()">新增</button>
							</span>
						</div>
						<div  id="rescueInfoDiv" style="display: none;margin: 20px">
							<div class="widget-box">
						        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
						          <h5>新增信息</h5>
						        </div>
						        <div class="widget-content">
						         <form class="form-horizontal" method="get" action="#">
						           <div class="control-group">
						              <label class="control-label">服务商名称 :</label>
						              <div class="controls">
						                <select  id="facName" style="width: 250px">
					                		<option value="0">--请选择--</option>
					                		<s:iterator var="ss" value="#request.serviceList">
					                			<option value="<s:property value="id"/>"><s:property value="company"/> </option>
					                		</s:iterator>
					                	</select> 
					                	找不到服务商？请先<a href="<%=request.getContextPath() %>/admin/admin_manager.Q" style="font-weight: bolder;color: red;font-size: 20pt">添加本地服务商</a>。
						              </div>
						            </div>
						            
						            <div class="control-group">
						              <label class="control-label">联系电话 :</label>
						              <div class="controls">
						                <input type="text" id="telephone" >
						              </div>
						            </div>
						            <div class="control-group">
						              <label class="control-label">联系人 :</label>
						              <div class="controls">
						                <input type="text" id="charger" > 
						              </div>
						            </div>
						            <div class="control-group">
						              <label class="control-label">联系地址 :</label>
						              <div class="controls">
						                <input type="text" id="address">
						              </div>
						            </div>
						            <div class="control-group">
						              <label class="control-label">费用 :</label>
						              <div class="controls">
						                	<input type="text" id="price"  value="0" style="width: 50px">元
						              </div>
						            </div>
						            <div class="control-group">
						              <label class="control-label">服务内容 :</label>
						              <div class="controls">
						                	<textarea rows="6" cols="20" style="height: 80px;width: 300px" id="serviceContent"></textarea>
						              </div>
						            </div>
						            <div class="form-actions">
						              	<button onclick="addRescue()" class="btn btn-inverse">确定</button>
										<button onclick="$('#rescueInfoDiv').hide()" class="btn btn-inverse">取消</button>						            </div>
						            </form>
						        </div>
						      </div>
								
						</div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>服务商名称</th>
				                  <th>服务内容</th>
				                  <th>费用</th>
				                  <th>联系电话</th>
				                  <th>联系地址</th>
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
