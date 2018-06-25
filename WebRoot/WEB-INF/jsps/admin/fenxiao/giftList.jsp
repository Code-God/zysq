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
		<SCRIPT language=javascript>
         
	//初始化列表，ajax分页  显示当前月话题
	function initData(page){
		//替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<img src='<%=request.getContextPath()%>/images/loading.gif'> ");
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_getGifts.Q",
			dataType : "json",
			success : function(data) {
				if(data.result == 'empty'){
					$("#dataList").html("暂时没有记录。请<a href='###' onclick='initData(1)'>刷新</a>重试。");
				}else{
					$("#dataList").html("");
					//表头
					var resumeList = "";
					 
					//第二步：加载列表当页数据
					for(var j=0; j< data.length; j++){
						var zb = data[j];
						var ss= "";
						resumeList += "<TR class=\"rsmTr\">";
						resumeList += "<TD>"+ (j+1) +"</TD>";
						resumeList += "<TD><img src='<%=request.getContextPath()%>/"+ zb.giftPic +"?r="+Math.random()+"' width=100 height=100/></TD>";
						resumeList += "<TD>"+ zb.giftName +"</TD>";
						resumeList += "<TD>"+ zb.giftDescStr +"</TD>";
						resumeList += "<TD>"+ zb.score +"</TD>";
						resumeList += "<TD>";
						resumeList += "<a href='<%=request.getContextPath()%>/admin/admin_editGif.Q?id="+ zb.id  +"'>编辑</a>&nbsp;";
						resumeList += "<a href='###' onclick='delGift("+ zb.id +")'>删除</a>";
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
					
					$("#dataList").html(resumeList);
				}
			}
		});
	}
	 
	 //新增内容
	   function addGift(){
//			window.location.href="<%=request.getContextPath()%>/admin/addGift.jsp";
			window.location.href="<%=request.getContextPath()%>/admin/admin_inputGift.Q";
	   }
	   
	   function delGift(id){
	   		if(window.confirm('确定要删除此记录吗？')){
	   			$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/admin/admin_delGift.Q?ids="+id,
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
	   
	   function exchangeList(){
	   		window.location.href="<%=request.getContextPath()%>/admin/exchangeList.jsp";
	   }
	   
    </SCRIPT>
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m3,msub31" />
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
								积分商城管理 
							</h5>
							<button type="button" class="btn btn-success" onclick="addGift()"> 新增 </button>
							</div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>图片</th>
				                  <th>礼品名称</th>
				                  <th>礼品描述</th>
				                  <th>兑换积分</th>
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
