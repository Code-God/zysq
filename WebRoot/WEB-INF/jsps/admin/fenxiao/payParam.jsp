<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%=Version.getInstance().getSystemTitle() %></title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function saveOrUpdate(){
		if($.trim($("#mchId").val()) == "" || $.trim($("#payKey").val()) == ""){
			art.dialog("对不起，商户ID和支付秘钥不得为空！");
			return;
		}
	
		$("form").submit();
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub95" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5> 微信支付设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/fx_payParam.Q" method="post" class="form-horizontal" id="adForm" enctype="multipart/form-data">
          	<input type="hidden" name="orgId" id="orgId" value="${org.id }">
          	<input type="hidden" name="op"  value="update">
            <div class="control-group">
              <label class="control-label">商户ID</label>
              <div class="controls">
               	<input type="text" style="width: 200px;height:30px"   name="mchId" id="mchId" value="${org.mchId }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">支付秘钥</label>
              <div class="controls">
               		<input type="text" style="width: 200px;height:30px" name="payKey" id="payKey" value="${org.payKey }"/>
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">更新</button>&nbsp;&nbsp;
              <s:if test="#request.info != null">
              	<span class="label label-important">${info }</span>
              </s:if>
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
