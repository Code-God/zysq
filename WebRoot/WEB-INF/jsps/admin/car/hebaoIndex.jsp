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
		
	//  ajax分页
	function initData(page) {
		// 替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");
		var param = "";
		if($("#flag").val() != 0){
			param += ("&flag=" + $("#flag").val());
		}
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/car_loadHebaoList.Q",
			data : "page=" + page + "&limit=20" + param, // 初始化显示第一页，每页50条
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
						resumeList += "<TD>" + zb.userName + "</TD>";
						resumeList += "<TD>" + zb.telephone + "</TD>";
						resumeList += "<TD>" + zb.city + "</TD>";
						resumeList += "<TD>" + zb.carSn + "</TD>";
						resumeList += "<TD>" + zb.carModel + "</TD>";
						resumeList += "<TD>" + (zb.guohu==1? "<font color=red>过户车</font>" : "非过户") + "</TD>";
						resumeList += "<TD>" + zb.submitDate + "</TD>";
						resumeList += "<TD><font color=green>" + zb.price/100 + " 元</font></TD>";
						resumeList += "<TD>" + (zb.feedbackDate=="" ? "<font color=red>未反馈</font>" : zb.feedbackDate) + "</TD>";
						resumeList += "<TD>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"delRecord("+ zb.id +");\">删除</button>";
						if(zb.flag != 1){
							resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"hebao("+ zb.id +");\">核保</button>";
						}
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"hebaoDetail("+ zb.id +");\">详情</button>";
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
	
					$("#dataList").html(resumeList);
				} else {
					var ss ="<td colspan='11'><div class=\"alert alert-block\">";
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
					url : CONFIG.context + "/admin/car_delHebaoRecord.Q",
					data : "dbId=" + id,
					dataType : "json",
					success : function(data) {
						if(data.msg == "ok"){
							//刷新列表
							initData(1);
							art.dialog({
							    icon: 'succeed',
							    content: '操作成功！'
							});
						}else{
							art.dialog({
							    icon: 'warning',
							    content: '操作失败！'
							});
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true //为true等价于function(){}
		});
	}
	
	function hebao(dbId){
		curId = dbId;
		artDialog.confirm("核保价格为：<input type='text' id='hbPrice'>元" , fillFee);
	}
	
	function hebaoDetail(dbId){
		window.location.href = CONFIG.context + "/admin/car_hebaoDetail.Q?id="+dbId;
	}
	/** hebao方法弹出框的回调函数 */
	function fillFee(){
		var price = $('#hbPrice').val();
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/car_submitHebaoPrice.Q",
			data : "dbId="+ curId +"&price=" + price,
			dataType : "json",
			success : function(data) {
				if(data.msg == "ok"){
					//刷新列表
					initData(1);
					art.dialog({
					    icon: 'succeed',
					    content: '操作成功！'
					});
				}else{
					art.dialog({
					    icon: 'warning',
					    content: '操作失败！'
					});
				}
			}
		});
	}
	 
</script>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m5,msub53" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-content">
			           <div class="control-group">
			              <label style="width: 100px;float: left;">状态：</label>
			              <div class="controls" style="width: 100px;float: left;">
			                <select id="flag" class="span2">
								<option value="0">待核保</option>
								<option value="1">已核保</option>
							</select>
			              </div>
			              <label style="width: 100px;float: left;">&nbsp;&nbsp;&nbsp;&nbsp;</label>
			               <button class="btn btn-success"  onclick="initData(1)" type="button"  >查询</button>
			            </div>
			        </div>
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i> </span>
						<h5>
							车险核保信息
						</h5>
						<span class="icon"> &nbsp;</span>
						<span class="icon">  <i class="icon-hand-right"></i> </span>
					</div>
					
			          <div class="widget-content nopadding">
			            <table class="table table-bordered table-striped">
			              <thead>
			                <tr>
			                  <th>序号</th>
			                  <th>车主姓名</th>
			                  <th>联系电话</th>
			                  <th>行驶城市</th>
			                  <th>车架号码</th>
			                  <th>车辆型号</th>
			                  <th>是否过户</th>
			                  <th>用户提交时间</th>
			                  <th>报价</th>
			                  <th>报价反馈时间</th>
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
