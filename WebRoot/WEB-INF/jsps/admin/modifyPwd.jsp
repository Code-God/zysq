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
	function checkPswdForm(){
		var pswd1 = $("#pswd1").val();
		var pswd2 = $("#pswd2").val();
		if(!pswd1 || !pswd2){
			return;		
		}
		if(pswd1!=pswd2){
			$("#confirmPswdSpan").show();
			return;
		}
		$("#pswdForm").submit();
	}
	function goback(){
		window.history.go(-1);
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<div id="content">
<div class="container-fluid">
   <hr>
  <div class="row-fluid">
    <div class="span6">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>修改密码</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/admin_savePwd.Q" method="post" class="form-horizontal" id="pswdForm">
          
            <div class="control-group">
              <label class="control-label">新密码 :</label>
              <div class="controls">
                <input type="password" class="span5" id="pswd1"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">确认密码 :</label>
              <div class="controls">
                <input type="password" class="span5" name="newPwd" id="pswd2"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="confirmPswdSpan">两次密码输入不一致！</span>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkPswdForm();">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="goback();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      </div>
      
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
