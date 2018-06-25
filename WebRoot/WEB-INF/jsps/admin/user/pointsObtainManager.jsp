<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="${ctx}/js/common/city.js"></script>
<style type="text/css">
.select2-container .select2-choice {
margin-left: 5px;
}
</style>
<script type="text/javascript">
	
	//获取积分切换
	function obtainManager(){
		var openid=$("#openid").val();
		window.location.href="<%=request.getContextPath()%>/admin/user_pointsObtainManager.Q?openid="+openid;
	}
	//兑换积分切换
	function exchangeManager(){
		var openid=$("#openid").val();
		window.location.href="<%=request.getContextPath()%>/admin/user_pointsExchangeManager.Q?openid="+openid;
		
	}
//返回
	
	function toUserList(){
		window.location.href="<%=request.getContextPath()%>/admin/user_manager.Q";
	}
</script>


	
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m6"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/user_pointsObtainManager.Q" method="post" id="userQueryForm">
         	<input type="hidden" id="openid" name="openid" value="${openid}">
          	<table width="100%">
          		<tr>
          			<td colspan="2" align="left">
          				<div class="controls controls-row">
				            <a class="btn btn-success" onclick="obtainManager()">获得积分记录</a>&nbsp;&nbsp;&nbsp;
				            <a  class="btn" onclick="exchangeManager();">兑现积分记录</a>&nbsp;&nbsp;&nbsp;
				            <a  class="btn btn-primary" onclick="toUserList();">返回</a>
				        </div>
          			</td>
          		</tr>
          		
          	</table>
           
          </form>
        </div>
        <br/>
         <div class="widget-content nopadding" id="userTableId">
            <%@include file="pointsObtainList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
