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
	function init(){
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_getWelcome.Q",
			dataType : "text",
			success : function(data) {
				if(data != 'fail'){
					$("#welcomeMsg").val(data);
				}else{
					$("#welcomeMsg").val("加载失败...");			
				}
			}
		});
	}
	
	function update(){
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_updateWelcome.Q",
			dataType : "json",
			data: "msg="+$("#welcomeMsg").val(),
			success : function(data) {
				if(data.msg == 'ok'){
					alert("更新成功！");
				}else{
					alert("更新失败！");
				}
			}
		});
	}
	</script>
</head>
<body  onload="init()">
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m8,msub83"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>用户关注公众号回复设置</h5>
        </div>
        <div class="widget-content nopadding">
           <!-- start -->
           		请填写用户关注公众号后的回复：<br>
				<textarea rows="150" cols="100" id="welcomeMsg" style="width: 80%;height: 250px"></textarea>
				<br>
				<button type="button" class="btn btn-info" onclick="update()">更新</button>
           <!-- end -->
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
