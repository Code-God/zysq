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
	function checkForm(){
		$('form').submit();
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub92"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>SEO设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath() %>/admin/system_saveSeo.Q" method="post" class="form-horizontal">
          	<input type="hidden" name="seoConfig.id" id="seoConfigId" value="${seoConfig.id }">
            <div class="control-group">
              <label class="control-label">标题</label>
              <div class="controls">
              	<input type="text" class="span11" name="seoConfig.title" value="${seoConfig.title }" id="title"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">关键字</label>
              <div class="controls">
              	<input type="text" class="span11" name="seoConfig.keywords" value="${seoConfig.keywords }" id="keywords"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">描述</label>
              <div class="controls">
              	<input type="text" class="span11" name="seoConfig.description" value="${seoConfig.description }" id="description"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
            </div>
          </form>
          <c:if test="${success==1}">
          <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="javascript:void(0);">×</a>
              <h4 class="alert-heading">保存成功!</h4><br>
          </div>
          </c:if>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
