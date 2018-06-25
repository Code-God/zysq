<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%=Version.getInstance().getSystemTitle()%></title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
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
            <h5>新增图片</h5>
          </div>
           <div class="widget-content nopadding">
          	 <form action="<%=request.getContextPath()%>/admin/banner_saveBanner.Q" method="post" class="form-horizontal" enctype="multipart/form-data">
	            <div class="control-group">
	              <label class="control-label">上传图片 :</label>
	              <div class="controls">
	                <input type="file" name="file"/>
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">链接地址 :</label>
	              <div class="controls">
	                <input class="span11" name="banner.imglink" />
	              </div>
	            </div> 
	            <div class="form-actions">
	              <button type="submit" class="btn btn-success" >保存</button>&nbsp;
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
