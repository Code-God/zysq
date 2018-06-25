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
	function checkRoleForm(){
		var rolename = $("#roleName").val();
		if(!rolename){
			$("#errorDiv").show();
			return;
		}
		var selectCheckbox=$("input[type=checkbox][name=actionIds]:checked");
		if(selectCheckbox.length<1){
			$("#errorMsg").html("至少选择一个权限");
			$("#errorDiv").show();
			return;
		}
		var roleid = $("#roleId").val();
		if(roleid){
			$("#roleForm").submit();
		}else{
			$.ajax({
				url:"<%=request.getContextPath()%>/admin/sec_isExitRole.Q",
				data:{"roleName":rolename},
				dataType:'text',
				success:function(data){
					if("ok"==data){
						$("#roleForm").submit();
					}else{
						$("#errorMsg").html("该角色名已存在！");
						$("#errorDiv").show();
						return;
					}
				}
			})
		}
		
		
		
	}
	function selectAll(cunrretObj){
		var selectCheckbox=$("input[type=checkbox][name=actionIds]");
		//alert(cunrretObj.checked);
		selectCheckbox.each(function(){
			$(this).attr("checked",cunrretObj.checked);
			$(this).parent().toggleClass("checked");
		});
	}
	function goback(){
		window.history.go(-1);
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
	      <s:if test="role.id==0">
            <h5>新增角色</h5>
           </s:if>
           <s:else>
            <h5>编辑角色</h5>
           </s:else>
          </div>
           <div class="widget-content nopadding">
            <div class="alert alert-error" style="display:none" id="errorDiv">
	              <button class="close" data-dismiss="alert">×</button>
	              <strong id="errorMsg">角色名称必填!</strong>  
	         </div>
          	 <form action="<%=request.getContextPath()%>/admin/sec_saveRole.Q" method="post" class="form-horizontal" id="roleForm">
          	 	<input id="roleId" type="hidden" name="role.id" value="${role.id }">
	            <div class="control-group">
	              <label class="control-label">角色名称 :</label>
	              <div class="controls">
	                <input type="text" class="span11" name="role.roleName" id="roleName" value="${role.roleName }" <s:if test="#request.role.id!=null && #request.role.id>0">readonly=readonly</s:if> />
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">描述 :</label>
	              <div class="controls">
	                <textarea class="span11" name="role.roleDescription">${role.roleDescription }</textarea>
	              </div>
	            </div>
	            <div class="widget-title"> 
	            <label>
                  <input type="checkbox" name="pemissionAll" id="pemissionAll" onclick="selectAll(this);"/>
	            	<h5>全选</h5>
                </label>
	            </div>
	            <s:iterator value="#request.allPerms" var="perm">
	             <s:if test="#perm.parentPermission==null">
		          <div class="widget-content updates">
		            <div class="new-update clearfix">
		              <div class="update-done">
		              	<input type="checkbox" name="actionIds" value='<s:property value="actionId"/>' <s:if test="#perm.ck==1">checked</s:if> />&nbsp;&nbsp;
		              	<strong><s:property value="permissionName"/> </strong> 
		              	<span><s:property value="permissionDescription"/></span> 
		              </div>
		            </div>
		             <s:iterator value="#request.allSubPerms" var="subperm">
		             <s:if test="#perm.actionId==#subperm.parentPermission.actionId">
		             <div class="new-update clearfix" style="margin-left: 25px">
		              <div class="update-done">
		              	<input type="checkbox" name="actionIds" value='<s:property value="actionId"/>' <s:if test="#subperm.ck==1">checked</s:if> />&nbsp;&nbsp;
		              	<strong><s:property value="permissionName"/> </strong> 
		              	<span><s:property value="permissionDescription"/></span> 
		              </div>
		            </div>
		            </s:if>
		            </s:iterator>
		          </div>
		          </s:if> 
		      	</s:iterator>
	            
	            <div class="form-actions">
	              <button type="button" class="btn btn-success" onclick="checkRoleForm();">保存</button>&nbsp;
	              <button type="button" class="btn btn-success" onclick="goback();">返回</button>
	            </div>
             </form>
            </div>
            
	      	
	    </div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsps/admin//common/adminFooter.jsp" />
</body>
</html>
