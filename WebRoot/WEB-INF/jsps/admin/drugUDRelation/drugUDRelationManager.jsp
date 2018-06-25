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
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
<SCRIPT language=javascript>
function resetForm(){
	$("#realname").val("");	
	$("#diseaseName").val("");	
	$("#auditStatus").val("");		
}
</SCRIPT>
</head>
<body onload="">
	<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
	<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
	<input type="hidden" id="tab" value="m3,msub31" />
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=request.getContextPath()%>/admin/admin_index.Q"
					title="Go to Home" class="tip-bottom"><i class="icon-home"></i>
					Home</a>
			</div>
		</div>
		
		<div class="container-fluid">
			<div class="widget-box">
				<div class="widget-box">
					<div class="widget-content">
						<form action="<%=request.getContextPath()%>/admin/drugUDRelation_relationManager.Q"
							method="post" id="relationQueryForm">
							<div class="controls controls-row">
								<table style="width: 100%">
								<tr>
									<td>
										姓名:
									</td>
									<td>
										<input name="realname" id="realname" type="text"
											class="span2" style="margin-left:5px;margin-right:35px"
											value="${realname}">
									</td>
									<td>	
										项目名称:
									</td>
									<td>
										<input name="diseaseName" id="diseaseName" type="text"
											class="span2" style="margin-left:5px;margin-right:35px"
											value="${diseaseName}">
									</td>		
								</tr>
								<tr>	
									<td>	
										审核状态:
									</td>
									<td>	
										<select name="auditStatus" id="auditStatus" style="width:170px">
											<option value="100"  selected="selected" >请选择</option>
											<option value="0" <s:if test="#auditStatus == '0'">selected</s:if>>未审核</option>
											<option value="41" <s:if test="#auditStatus == '41'">selected</s:if>>审核通过</option>
											<option value="42" <s:if test="#auditStatus == '42'">selected</s:if>>审核不通过</option>
										</select>	
									</td>								
								</tr>
								<tr>	
									<td colspan="4">							
										<button type="submit" class="btn btn-success">查询</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-primary"
											onclick="resetForm();">重置</button>
									</td>		
								</tr>		
							</table>
							</div>
						
					</div>
					</form>
					<br />
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>报名人员列表</h5>										
					</div>
						 <div class="widget-content nopadding" id="relationTableId">
	          					  <%@include file="drugUDRelationList.jsp"%>
	     				 </div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
