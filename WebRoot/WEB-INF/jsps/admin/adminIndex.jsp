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
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
  <div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
  </div>
<!--End-breadcrumbs-->

<!--Chart-box-->    
    <div class="row-fluid" style="background:#ffffff;">
      <div class="widget-box"  style="background:url('<%=request.getContextPath()%>/images/h_bg.jpg') no-repeat center #ffffff;height:800px" >
         
      </div>
    </div>
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

<!--end-Footer-part-->



</body>
</html>
