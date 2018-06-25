<%@ page language="java" pageEncoding="UTF-8"%>
<s:if test="#session.orgCode != null">
	<!-- 系统超级管理员 -->
	<s:if test="#session.orgCode.length() == 1">
		<%@include file="/WEB-INF/jsps/admin/common/adminleft_sys.jsp"%>
	</s:if>
	<!-- 总公司管理员 -->
	<s:if test="#session.orgCode.length() >= 3 && #session.orgCode.length() <= 6"><!-- 兼容旧的数据，后来总销商的code都改为6位了， 之前是3位 -->
		<%@include file="/WEB-INF/jsps/admin/common/adminleft_top.jsp"%>
	</s:if>
</s:if>


<script>
	//30秒查一次
	$(document).ready(function(){
		setInterval(getMsg, 30000);
	});
	
	function getMsg(){
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/fx_getMsg.Q",
			dataType : "json",
			success : function(data) {
				 if(data.auditCnt){
				 	$("#auditMenu").html("待审核&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"label label-important\">"+ data.auditCnt +"</span>");
				 }
			}
		});
	}
</script>