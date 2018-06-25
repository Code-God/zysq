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
<script src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script> 

<script type="text/javascript">
	function checkForm(){
		$('form').submit();
	}
	function testMail(){
		$("#testButton").attr("disabled",true);
		var wait = art.dialog({
			lock: true,
			opacity: 0,
		    content: '正在测试，请稍后...'
		});
		$.post('<%=request.getContextPath()%>/admin/system_testMail.Q',$('form').serialize(),function(data){
			if(data && data.result){
				wait.close();
				$("#testButton").attr("disabled",false);
				art.dialog.tips("测试成功","1");
			}
		},'json');
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub93"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>邮件服务器设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath() %>/admin/system_saveMail.Q" method="post" class="form-horizontal">
          	<input type="hidden" name="emailConfig.id" id="mailConfigId" value="${mailConfig.id }">
            <div class="control-group">
              <label class="control-label">Email地址</label>
              <div class="controls">
              	<input type="text" class="span11" name="emailConfig.userName" value="${mailConfig.userName }" id="userName"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">SMTP服务器</label>
              <div class="controls">
              	<input type="text" class="span11" name="emailConfig.smtpAddress" value="${mailConfig.smtpAddress }" id="smtpAddress"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">端口</label>
              <div class="controls">
              	<input type="text" class="span11" name="emailConfig.port" value="${mailConfig.port }" id="port"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">密码</label>
              <div class="controls">
              	<input type="password" class="span11" name="emailConfig.password" value="${mailConfig.password }" id="password"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">主题前缀</label>
              <div class="controls">
              	<input type="text" class="span11" name="emailConfig.subjectPrefix" value="${mailConfig.subjectPrefix }" id="subjectPrefix"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="testMail()" id="testButton">测试</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
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
