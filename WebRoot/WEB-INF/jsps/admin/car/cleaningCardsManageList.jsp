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
		
		//查询参数构造
		var params = "";
		if($("#couponCode").val() != ""){
			params = ("&cardCode="+$("#cardCode").val());
		}
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/car_cleaningCardsList.Q",
			data : "page=" + page + "&limit=20" + params, // 初始化显示第一页，每页50条
			dataType : "json",
			success : function(data) {
				if (data.datas && data.datas.length > 0) {
					$("#dataList").html("");
	
					var list = data.datas;
					var resumeList = "";
					
					$("#pageBar").html("");
					
					buildPagingBar(data);
					// 第二步：加载列表当页数据
					for (var j = 0; j < list.length; j++) {
						var zb = list[j];
						resumeList += "<TR>";
						resumeList += "<TD>" + (j+1) + "</TD>";
						resumeList += "<TD>" + zb.cardCode + "</TD>";
						resumeList += "<TD>" + zb.generateDate.substring(0, 19) + "</TD>";
						resumeList += "<TD>" + zb.expireDate.substring(0, 19) + "</TD>";
						resumeList += "<TD>" + zb.cardTypeStr + "</TD>";
						resumeList += "<TD>" + zb.totalPoints + "</TD>";
						resumeList += "<TD>" + zb.leftPoints + "</TD>";
						resumeList += "<TD>" + zb.statusStr + "</TD>";
						resumeList += "<TD>";
						if(zb.status == 0){
							resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"consume("+ zb.id +");\">核销</button>";
						}else if(zb.status == 1){
							resumeList += "已用完";
						}
							resumeList += "&nbsp;&nbsp;<button class=\"label label-success btn btn-primary btn-mini\" onclick=\"cleaningLog('"+ zb.cardCode +"');\">洗车记录</button>";
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
	
	function buildPagingBar(data){
		var paging =  data ;
		var nextPage = (parseInt(paging.page) + 1) >= paging.totalPage
				? paging.totalPage
				: (parseInt(paging.page) + 1);
		var curPage = paging.page;
		var prePage = (paging.page - 1) <= 1 ? "1" : (paging.page - 1);
		var totalPage = paging.totalPage;
		var pageBarDiv = "";
		// 分页样式。。。。
		pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"
				+ curPage + "】页,总共【<b>" + paging.total + "</b>】条记录 ";
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
	}
	 
	
	//核销 记录
	function consume(id){
		art.dialog({
		    content: '确定要核销一次洗车记录吗？',
		    okVal:'确定',
		    ok: function () {
		    	 $.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/car_consumeCleaning.Q",
					data : "dbId=" + id,
					dataType : "json",
					success : function(data) {
						if(data.msg == "ok"){
							//刷新列表
							initData(1);
							art.dialog.tips("操作成功！");
						}else if(data.msg == "null"){
							art.dialog("对不起，该卡已经用完，无法核销！");
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
	
 
	
	function resetForm(){
		$("#cardCode").val("");
	}
	
	
	function cleaningLog(cardCode){
		window.location.href = "<%=request.getContextPath()%>/admin/car_cleaningLogList.Q?cardCode="+cardCode;
	}
	 
</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m1,msub11" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
					<div class="widget-box">
						<div class="widget-content">
							<div class="controls controls-row">
									<label class="span2" for="inputSuccess" style="margin-top: 5px">
										洗车卡编号:
									</label>
									<input type="text" id="cardCode" placeholder="支持模糊查询..">
									<button type="button" onclick="initData(1)" class="btn btn-success">
										查询
									</button>
									&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-primary"
										onclick="resetForm();">
										重置
									</button>
								</div>
						</div>
					</div>
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i> </span>
							<h5>
								洗车卡管理
							</h5>
							<span class="icon"> &nbsp;</span>
							<span class="icon">  <i class="icon-hand-right"></i> </span>
						</div>
						 
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>洗车卡编号</th>
				                  <th>起始日期</th>
				                  <th>有效期</th>
				                  <th>类型</th>
				                  <th>总次数</th>
				                  <th>剩余次数</th>
				                  <th>当前状态</th>
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

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
