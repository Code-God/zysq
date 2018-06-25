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
	function resetForm(){
		$("#username").val("");
		$("#rolename").val("");
	}
	//$(function(){
	//	$("#addRoleBut").bind("click", function(){
	//	  window.location.href="<%=request.getContextPath()%>/admin/sec_addRole.Q";
	//	});
	//})
	function addRole(){
		window.location.href="<%=request.getContextPath()%>/admin/sec_addRole.Q";
	}
	
	function modRole(roleId){
		window.location.href="<%=request.getContextPath()%>/admin/sec_addRole.Q?roleId=" + roleId;
	}
	
	function delRole(roleId,roleName){
		$.ajax({
					url:"<%=request.getContextPath()%>/admin/sec_checkRoleUser.Q",
					data:{"roleName":roleName},
					dataType:'text',
					success:function(data1){
						if("ok"==data1){
							var dialog = art.dialog({
							    title: '提示',
							    content: '确定要删除该角色吗！',
							    okVal:'确定',
							    ok: function(){
									  $.ajax({
										url:"<%=request.getContextPath()%>/admin/sec_delRole.Q",
										data:{"roleId":roleId},
										dataType:'text',
										success:function(data2){
											if("succ"==data2){
												window.location.href="<%=request.getContextPath()%>/admin/sec_roleManager.Q";
											}else{
												art.dialog({
													time: 2,
												    content: '删除失败！'
												});
											}
										}
									 })
							   	  	return true;
							   },
							   cancelVal: '取消',
					    	   cancel: true
							});
						}else{
							art.dialog({
								time: 3,
							    content: '该角色下还有用户，不能删除！'
							});
						}
					}
		})
		
		
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub72"/>

<div id="content">
	<div id="content-header">
	    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
	</div>
	<div class="container-fluid">
	      <div class="widget-box">
	      <div class="widget-title"> <span class="icon"> <i class="icon-move"></i> </span>
            <h5>角色列表</h5>
            <button id="addRoleBut" class="label label-info btn btn-primary btn-mini" onclick="addRole();">新增</button>
          </div>
	      	<s:iterator value="#request.allRoles" var="role">
	          <div class="widget-content nopadding updates">
	            <div class="new-update clearfix"><i class="icon-ok-sign"></i>
	              <div class="update-done">
	              	<a href="<%=request.getContextPath()%>/admin/sec_addRole.Q?roleId=<s:property value="id"/>" title="点击查看或编辑"><strong><s:property value="roleName"/> </strong></a> 
	              	<span><s:property value="roleDescription"/></span> 
	              </div>
	               <s:if test="#role.deletable==true">
						<div class="update-date" style="width: 100px">
							<span class="update-day">
								<a class="label btn btn-danger btn-mini" href="#" onclick="delRole('${id }','${roleName }');">删除</a>
								<a class="label btn btn-danger btn-mini" href="#" onclick="modRole('${id }','${roleName }');">修改</a>
							</span>
						</div>
					</s:if>
	            </div>
	          </div>
	      	</s:iterator>
	      
	      </div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
