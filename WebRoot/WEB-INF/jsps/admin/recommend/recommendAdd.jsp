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
		<script type="text/javascript">
			function backToLisk(){
				window.location.href="<%=request.getContextPath()%>/admin/recommend_list.Q";
			}
			$(function(){
				$("#prdCode").focus(function(){
					$(this).parents("div.control-group").removeClass("error");
					$(this).next().hide();
				});
			});
			function checkForm(){
				if($("#prdCode").val().trim() === ''){
					$("#prdCode").parents("div.control-group").addClass("error");
					$("#prdCode").next().show().html("请输入商品编码");
					return false;
				}else{
					var invaild = false;
					var prdCode = $("#prdCode").val();
					var type = $("#recommend").val();
					$.ajax({
						url:'<%=request.getContextPath()%>/admin/recommend_check.Q',
						type:'get',
						async:false,
						data:{'prdCode':prdCode,'type':type},
						dataType:'json',
						success:function(data){
							if(data){
								if(data.result == "success"){
									invaild = true;
								}else{
									$("#prdCode").parents("div.control-group").addClass("error");
									$("#prdCode").next().show().html(data.msg);
								}
							}
						},
						error:function(data){
						
						}
					});
					return invaild;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m1,msub13"/>

		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home"
						class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i> </span>
							<h5>
								配置特惠新品
							</h5>
						</div>
						<div class="widget-content nopadding">
							<form action="<%=request.getContextPath()%>/admin/recommend_add.Q" method="post" class="form-horizontal">
								<div class="control-group">
									<label class="control-label">
										推荐类型
									</label>
									<div class="controls">
										<select name="recommend" id="recommend" style="width: 579px;">
											<option value="1">
												新品推荐
											</option>
											<option value="2">
												本周特惠
											</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">
										商品编码
									</label>
									<div class="controls">
										<input type="text" class="span5" name="prdCode" id="prdCode" placeholder="商品编码" />
										<span class="help-inline" style="display:none;"></span>
									</div>
								</div>
								<div class="form-actions">
									<button type="submit" class="btn btn-success" onclick="return checkForm()" id="saveButton">
										保存
									</button>
									&nbsp;&nbsp;
									<button type="button" class="btn btn-success" onclick="backToLisk();">
										返回
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

	</body>
</html>
