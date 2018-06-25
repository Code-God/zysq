<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%
	String contextPath = request.getContextPath();
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>

<link rel="stylesheet" href="<%=contextPath %>/mm/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/colorpicker.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/datepicker.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/uniform.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/select2.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/matrix-style.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/matrix-media.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/bootstrap-wysihtml5.css" />
<link href="<%=contextPath %>/mm/font-awesome/css/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="<%=contextPath %>/mm/js/jquery.min.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.ui.custom.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap.min.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap-colorpicker.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap-datepicker.js"></script> 
<script src="<%=contextPath %>/mm/js/masked.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.uniform.js"></script> 
<script src="<%=contextPath %>/mm/js/select2.min.js"></script> 
<script src="<%=contextPath %>/mm/js/matrix.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.dataTables.min.js"></script> 
<script src="<%=contextPath %>/mm/js/matrix.tables.js"></script> 
<script src="<%=contextPath %>/mm/js/wysihtml5-0.3.0.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.peity.min.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap-wysihtml5.js"></script> 

<script type="text/javascript">
	$(function(){
		$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	});
</script>

