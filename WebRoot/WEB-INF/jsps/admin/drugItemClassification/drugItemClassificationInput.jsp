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
<script type="text/javascript">
	function checkSubmitForm(){
		var cname = $("#classificationName").val();
	
		if($.trim(cname) == ""){
			$("#nameSpan").show();
			return;
		}else{
			$("#nameSpan").hide();
		}

		$("#itemClassForm").submit();
		 
	}
	function toItemClassList(){
		window.location.href = "<%=request.getContextPath()%>/admin/itemClass_itemClassManager.Q";
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
          <form action="<%=request.getContextPath()%>/admin/itemClass_saveItemClass.Q" method="post" class="form-horizontal" id="itemClassForm">
            <input type="hidden" id="classificationId" class="span5" name="classification.id" value="${classification.id}"  />
            <div class="control-group">
              <label class="control-label">名称 :</label>
              <div class="controls">
                <input type="text" id="classificationName" class="span5" name="classification.classificationName" value="${classification.classificationName}"  />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="nameSpan">名称不能为空！</span>
              </div>
  			</div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkSubmitForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toItemClassList();">返回</button>
            </div>
          </form>       
        </div>
      </div>
      </div>
      </div>
      
</div>

<script src="<%=contextPath %>/mm/js/bootstrap.min.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.uniform.js"></script> 
<script src="<%=contextPath %>/mm/js/select2.min.js"></script> 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
