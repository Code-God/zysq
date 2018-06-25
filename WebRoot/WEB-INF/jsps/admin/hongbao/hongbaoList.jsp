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
			url : CONFIG.context + "/admin/fx_loadHongBaoList.Q",
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
						if(zb.thetype == 0){//显示红包面额
							resumeList += "<TD><font color=red>红包</font></TD>";
							resumeList += "<TD>" + zb.hbvalue + "元</TD>";
						}else{//显示优惠券折扣
							resumeList += "<TD><font color=blue>优惠券</font></TD>";
							resumeList += "<TD>" + zb.deduct.toFixed(1) + "</TD>";
						}
						resumeList += "<TD>" + zb.uuid + "</TD>";
						resumeList += "<TD>" + zb.createDate + "</TD>";
						resumeList += "<TD>" + zb.expireDate + "</TD>";
						resumeList += "<TD>" + zb.num + "</TD>";
						resumeList += "<TD>" + zb.numused + "</TD>";
						resumeList += "<TD>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-info btn btn-primary btn-mini\" onclick=\"delHongbao("+ zb.id +");\">删除</button>";
						resumeList += "&nbsp;&nbsp;<button class=\"label label-success btn btn-primary btn-mini\" onclick=\"createLink("+ zb.id +",'"+ zb.uuid +"',"+ zb.orgId +");\">生成推广链接</button>";
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
	function delHongbao(id){
		art.dialog({
		    content: '确定要删除此记录吗？',
		    okVal:'确定',
		    ok: function () {
		    	 $.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/fx_delHongbao.Q",
					data : "hbId=" + id,
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
	
	function showAddH(){
		$("#hongbaoDiv").show();
		$("#couponDiv").hide();
	}
	function showAddC(){
		$("#hongbaoDiv").hide();
		$("#couponDiv").show();
	}
	
	
	function addHongbao(thetype){
		var param;
		if(thetype == 0){
		 	param = {
				"thetype":thetype,
				"hbvalue": $("#hbvalue").val(),
				"num":$("#num").val(),
				"expireDate": $("#expireDateH").val()
			};
		}else{
			//优惠券
			param = {
				"thetype":thetype,
				"deduct": $("#deduct").val(),
				"expireDate": $("#expireDateC").val()
			};
		}
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_addHongbao.Q",
			data : param,
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					$("#hongbaoDiv").hide();
					$("#couponDiv").hide();
					//刷新列表
					initData(1);
				}else{
					art.dialog("对不起，操作失败！");
				}
			}
		});
	}
	
	//创建推广链接，可以放到微信菜单上
	function createLink(hbid, uuid, orgId){
		//alert(hbid + "-" + uuid + "--" + orgId);
		var param = {
			"uuid": uuid,
			"hbid": hbid,
			"orgId":orgId
		};
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_createHongBaoLink.Q",
			data : param,
			dataType : "json",
			success : function(data) {
				if(data.msg == "ok"){
					art.dialog("推广链接（可链接到公众号菜单或转发到朋友圈）：<hr>"+data.result);
				}else if(data.msg == "appId"){
					art.dialog("对不起，请先到分销商管理模块设置该公众号的APPID、appSecret等参数！");
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
		<input type="hidden" id="tab" value="m3,msub33" />
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
								卡券（红包、优惠券）管理
							</h5>
							<span class="icon"> &nbsp;</span>
							<span class="icon">  <i class="icon-hand-right"></i> </span>
							<span style="line-height: 35px;margin-left: 50px">
								<button class="btn btn-danger" type="button" onclick="showAddH()">新增红包</button>
								<button class="btn btn-info" type="button" onclick="showAddC()">新增优惠券</button>
							</span>
						</div>
						<div  id="hongbaoDiv" style="display: none;margin: 20px">
							<!-- 红包 -->
								■ 类型： <font color=red>红包</font> &nbsp;&nbsp;&nbsp;&nbsp;
								■ 面额：<input  type="text"  id="hbvalue" value="0" style="width: 50px">元&nbsp;&nbsp;&nbsp;&nbsp;
								■ 数量：<input type="text" id="num"  value="0" style="width: 50px">个&nbsp;&nbsp;&nbsp;&nbsp;
								■ 过期时间：<input type="text" id="expireDateH" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" id="expireDate" class="wdateinput" ><br>
								<button onclick="addHongbao(0)" class="btn btn-inverse">确定</button>
								<button onclick="$('#hongbaoDiv').hide()" class="btn btn-inverse">取消</button>
						</div>
						<div  id="couponDiv" style="display: none;margin: 20px">
							<!-- 优惠券 -->
								■ 类型：<font color="blue">优惠券</font> &nbsp;&nbsp;&nbsp;&nbsp;
								■ 折扣：<input type="text" id="deduct" value="0.15" style="width: 50px"> &nbsp;&nbsp;&nbsp;&nbsp;
								■ 过期时间：<input type="text" id="expireDateC" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}'})" id="expireDate" class="wdateinput" ><br>
								<button onclick="addHongbao(1)" class="btn btn-inverse">确定</button>
								<button onclick="$('#couponDiv').hide()" class="btn btn-inverse">取消</button>
						</div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>类型</th>
				                  <th>面额/折扣</th>
				                  <th>批次编号</th>
				                  <th>创建时间</th>
				                  <th>到期时间</th>
				                  <th>发放数量</th>
				                  <th>已使用数量</th>
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
