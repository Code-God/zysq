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
	function toUserList(){
		window.location.href = "<%=request.getContextPath()%>/admin/user_manager.Q";
	}
	
	 function subExchange(){
		 var consumepoints=$("#consumepoints").val();
		 if($.trim(consumepoints) == ""){
			 alert("请输入要兑换的积分");
			 return false;
		 }
		$("#userForm").submit();
		
		 
	 }
	
	function check(){
		var points=parseInt($("#points").val());
		var consumepoints=parseInt($("#consumepoints").val());
		if($("#consumepoints").val()==""){
			alert("请输入要兑换的积分");
			 return false;
		}
		if(consumepoints>points || consumepoints<0){
			alert("您要兑换的积不够或者不能为负数");
			$("#consumepoints").val("0");
			return false;
			
		}
		$("#surpluspoints").val(points-consumepoints);
		
		
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
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>积分信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/user_saveUpatePoints.Q" method="post" class="form-horizontal" id="userForm">
          	<input type="hidden" name="userid" id="userid" value="${user.id}">
          	<input type="hidden" name="openid" id="openid" value="${user.openId}">
            <div class="control-group">
              <label class="control-label">昵称</label>
              <div class="controls">
                <input type="text" id="nickName" class="span5" name="user.nickName" value="${user.nickName }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">总积分</label>
              <div class="controls">
                <input type="text" class="span5" name="user.points" value="${user.points}"  id="points" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">兑换积分</label>
              <div class="controls">
                <input type="text" class="span5" name="consumepoints"  id="consumepoints" onblur="check();" >&nbsp;&nbsp;
              </div>
            </div>
            
              <div class="control-group">
              <label class="control-label">剩余积分</label>
              <div class="controls">
                <input type="text" class="span5" name="surpluspoints"  id="surpluspoints" readonly="readonly" />&nbsp;&nbsp;
              </div>
            </div>
  
            <div class="form-actions">
             <button type="button" class="btn btn-success" onclick="subExchange();">兑换</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toUserList();">取消</button>
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
