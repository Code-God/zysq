<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
			function resetForm(){
				$("#recommend").val("");
			}
			function cancelRecommend(recommendId){
				if(confirm("确定取消吗？")){
					$.ajax({
						url : '<%=request.getContextPath()%>/admin/recommend_cancel.Q',
						type : 'get',
						data : {id:recommendId},
						dataType : 'json',
						success : function(data){
							if(data && data.result == "success"){
								alert("取消成功");
								$('form').submit();
							}
						},
						error : function(data){
							alert("取消失败");
							$('form').submit();
						}
					});
				}
			}
			
			function addRecommend(){
				window.location.href="<%=request.getContextPath()%>/admin/recommend_preAdd.Q";
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
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-content">
						<form action="<%=request.getContextPath()%>/admin/recommend_list.Q" method="post" id="recommendForm">
							<div class="controls controls-row">
								<label class="span1" for="inputSuccess" style="margin-top: 5px">
									推荐类型:
								</label>
								<select name="recommend" id="recommend" style="margin-left: 5px; margin-right: 35px; width: 156px">
									<option value="">
										请选择
									</option>
									<option value="1" <s:if test="#request.recommend==1">selected</s:if>>
										新品推荐
									</option>
									<option value="2" <s:if test="#request.recommend==2">selected</s:if>>
										本周特惠
									</option>
								</select>
								<button type="submit" class="btn btn-success">
									查询
								</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-primary" onclick="resetForm();">
									重置
								</button>
							</div>
						</form>
					</div>
					<br />
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i> </span>
						<h5>
							特惠新品列表
						</h5>
						<button class="label label-info btn btn-primary btn-mini"
							onclick="addRecommend();">
							新增
						</button>
					</div>
					<div class="widget-content nopadding" id="recommendTableId">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>
										序号
									</th>
									<th>
										商品名称
									</th>
									<th>
										商品编码
									</th>
									<th>
										推荐类型
									</th>
									<th>
										操作
									</th>
								</tr>
							</thead>
							<tbody>
								<s:if test="#request.recommends.size>0">
									<c:forEach items="${recommends}" var="recommend" varStatus="num">
										<tr>
											<td>
												${num.index + 1}
											</td>
											<td>
												${recommend.product.name}
											</td>
											<td>
												${recommend.product.prdCode}
											</td>
											<td>
												<c:choose>
							                  		<c:when test="${recommend.type == 1}">新品推荐</c:when>
							                  		<c:when test="${recommend.type == 2}">本周特惠</c:when>
							                  		<c:otherwise>错误数据</c:otherwise>
							                  	</c:choose>
											</td>
											<td>
												<button class="label label-info btn btn-primary btn-mini" onclick="cancelRecommend(${recommend.id})">
													<c:choose>
								                  		<c:when test="${recommend.type == 1}">取消推荐</c:when>
								                  		<c:when test="${recommend.type == 2}">取消特惠</c:when>
								                  	</c:choose>
												</button>
											</td>
										</tr>
									</c:forEach>
								</s:if>
								<s:else>
									<tr>
										<td colspan="5">
											<div class="alert alert-block">
												<h4 align="center" class="alert-heading">
													暂时没有符合条件的记录！
												</h4>
											</div>
										</td>
									</tr>
								</s:else>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
