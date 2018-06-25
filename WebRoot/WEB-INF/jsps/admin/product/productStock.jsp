<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
		<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<script type="text/javascript">
			function editStock(obj){
				var el = $(obj).parent().prevAll().eq(0).children('input[name="stock"]');
				if(el.val()=='--'){
					el.val(0);
				}
				el.attr("readonly",false);
				$(obj).hide().nextAll().show();
			}
			function canel(obj){
				$(obj).hide().prev().hide().prev().show().parent().prevAll().eq(0).children('input[name="stock"]').attr("readonly",true).val($(obj).parent().prevAll().eq(0).children('input[name="raw"]').val());
			}
			function saveStock(obj,cityCode, prdCode){
				var stock = $(obj).parent().prevAll().eq(0).children('input[name="stock"]').val();
				$.ajax({
					url:'<%=request.getContextPath()%>/admin/products_editStock.Q',
					type:'get',
					dataType:'json',
					sync:false,
					data:{'cityCode':cityCode,'prdCode':prdCode,'stock':stock},
					success:function(data){
						if(data.result=='success'){
							$(obj).parent().prevAll().eq(0).children('input[name="raw"]').val($(obj).parent().prevAll().eq(0).children('input[name="stock"]').val());
							art.dialog.tips("修改成功","1");
						}else{
							alert("修改失败，参数异常");
						}
					},
					error:function(data){
						alert("系统异常，修改失败");
					}
				});
				$(obj).hide().next().hide().end().prev().show().parent().prevAll().eq(0).children('input[name="stock"]').attr("readonly",true)
			}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m1,msub11"/>
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i> </span>
						<h5>
							库存列表
						</h5>
					</div>
					<div class="widget-content nopadding" id="stockTableId">
						<%@include file="stockList.jsp"%>
					</div>
				</div>
			</div>
			
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
