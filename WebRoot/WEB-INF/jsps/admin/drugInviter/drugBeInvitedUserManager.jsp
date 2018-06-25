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
	$("#nickname").val("");	
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
					<!-- <div class="widget-content"> -->
						<form action="<%=request.getContextPath()%>/admin/drugUDRelation_getBeInvitedManager.Q"
							method="post" id="beInvitedQueryForm">
						
						</form>
					<!-- <br /> -->
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>邀请人列表</h5>										
					</div>
						 <div class="widget-content nopadding" id="beinvitedId">
	          					  <%@include file="drugBeInvitedUserList.jsp"%>
	     				 </div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
