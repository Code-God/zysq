<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="<%=contextPath %>/js/common/city.js"></script> 
<script type="text/javascript">
	function checkAdminForm(){
		var username = $("#username").val();
		var roleNames = $("#roleNames").val();
		var phone = $("#phone").val();
		//var city = $("#city").val();
		//var province = $("#province").val();
		//在创建门店管理员时，需要指定地址，这个地址在发布优惠券时需要列举出来
		<c:if test="${hideAddress == 'n' }">
		var company = $("#company").val();
		var address = $("#address").val();
		/*
		if(city == "0"){
			$("#citySpan").show();
			return;
		}
		if(province == "0"){
			$("#provinceSpan").show();
			return;
		}
		*/
		if($.trim(company) == ""){
			$("#companySpan").show();
			return;
		}
		if($.trim(address) == ""){
			$("#addressSpan").show();
			return;
		}
		</c:if>
		if(!username){
			$("#loginNameSpan").show();
			return;
		}
		if(!roleNames){
			$("#roleSpan").show();
			return;
		}
		if(phone){
			if(phone.length>11){
				$("#phoneSpan").show();
				return;
			}
		}
		$("#roleNames4").val(roleNames);
		//var adminId = $("#adminId").val();
		//if(adminId){
		$("#saveButton").attr("disabled", "disabled");
		$("#adminForm").submit();
		 
	}
	function toAdminList(){
		window.location.href = "<%=request.getContextPath()%>/admin/admin_manager.Q";
	}
</script>


</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub71"/>
  <s:bean name="com.wfsc.actions.common.SelAction" id="selMap"></s:bean>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/admin_saveAdmin.Q" method="post" class="form-horizontal" id="adminForm">
          	<input type="hidden" name="admin.id" id="adminId" value="${admin.id }">
          	<input type="hidden" name="roleNames" id="roleNames4" value="">
            <div class="control-group">
              <label class="control-label">用户名 :</label>
              <div class="controls">
                <input type="text" id="username" class="span5" name="admin.username" value="${admin.username }" <s:if test="#request.admin.id!=null && #request.admin.id>0">readonly=readonly</s:if> />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">角色 ：</label>
              <div class="controls">
                <select  style="width:579px" id="roleNames">
                	<s:iterator value="#request.roles" var="role">
                	<option value="${role.id}" <s:if test="#role.ck==1">selected</s:if> >${role.roleName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="roleSpan">请至少选择一个角色！</span>
              </div>
            </div>
             <c:if test="${hideAddress == 'n' }">
	             <div class="control-group">
	              <label class="control-label">门店名称 :</label>
	              <div class="controls">
	                <input type="text" class="span5" name="admin.company" value="${admin.company }" id="company"/>&nbsp;&nbsp;
	                <span style="color: #F00;display:none" id="companySpan">请填写门店名称！</span>
	              </div>
	            </div>
	             <div class="control-group">
	              <label class="control-label">门店地址 :</label>
	              <div class="controls">
	                <input type="text" class="span5" name="admin.address" value="${admin.address }" id="address"/>&nbsp;&nbsp;
	                <span style="color: #F00;display:none" id="addressSpan">请填写门店地址！</span>
	              </div>
            </div>
             </c:if>
             
           <div class="control-group">
              <label class="control-label">手机 :</label>
              <div class="controls">
                <input type="text" class="span5" name="admin.phone" value="${admin.phone }" id="phone"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="phoneSpan">手机长度不能大于11位！</span>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">邮箱 :</label>
              <div class="controls">
                <input type="text" class="span5" name="admin.email" value="${admin.email }"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkAdminForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toAdminList();">返回</button>
               <span style="color: #F00;display:none; font-size: 20pt" id="loginNameSpan">用户名不能为空！</span>
            </div>
          </form>
          <s:if test="#request.admin.id==null || #request.admin.id<1">
          <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="#">×</a>
              <h4 class="alert-heading">注意!</h4><br>
             保存后用户状态默认为启用状态，密码默认初始为11111111，需要改密码请前往修改密码。
          </div>
          </s:if> 
        </div>
      </div>
      </div>
      </div>
      
</div>
<script src="<%=contextPath %>/mm/js/jquery.min.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap.min.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.uniform.js"></script> 
<script src="<%=contextPath %>/mm/js/select2.min.js"></script> 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
