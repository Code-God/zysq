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
</head>
<body>
<div id="content" style="margin-left: 0px">
<div id="content-header">
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <form  class="form-horizontal">
        <div class="widget-content nopadding">
            <div class="control-group">
              <label class="control-label">评价数量</label>
              <div class="controls">
              	<input type="text" class="span11" name="pjCount" value="20" id="pjCount"/>&nbsp;&nbsp;
              </div>
            </div>
        </div>
        </form>
      </div>
      </div>
      </div>
      
</div>
</body>
</html>
