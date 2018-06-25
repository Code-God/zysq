<%@page import="model.bo.drug.DrugDiseaseItem"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta charset="utf-8">
    <title>更多项目</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=5,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/page.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
    
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css">
    <script type="text/javascript">
       var activejsure=1;
    </script>
</head>

<body>

    <div class="main">
        <!---->
        <section class="explain">
            <span class="col-3"></span>
            <p class="col-9">生命诚可贵，新药价更高。新药、打折药、免费药，尽在找药神器。如需报名或者推荐，请点击下面的项目！</p>
        </section>
        <!---->
            <c:forEach items="${maplist}" var="map">
            
	        <div class="panel">
	            <h1>${map.key}</h1>
	            <!---->
	            <div class="panel_body minority">
	           	<c:forEach items="${map.value}" var="disease" begin="0" end="2" >
	                <a href="<%=request.getContextPath()%>/drug/index_getDetailed.Q?id=${disease.id}"" class="panel_list">
	                    <strong>${disease.diseaseName}</strong>
	                    <p>${disease.diseaseProfile}</p>
	                </a>
	            </c:forEach>
	            </div>
	            
	            <!---->
	            <!---->
	            <div class="panel_body most">
	           	<c:forEach items="${map.value}" var="disease" >
	                <a href="<%=request.getContextPath()%>/drug/index_getDetailed.Q?id=${disease.id}"" class="panel_list">
	                    <strong>${disease.diseaseName}</strong>
	                    <p>${disease.diseaseProfile}</p>
	                </a>
	            </c:forEach>
	            </div>
	            
	            <!---->
	            <c:if test="${map.value.size()>3}">
            <span class="open iconfont icon-arrowll-copy"></span>
            <span class="retract iconfont icon-shangjiantou"></span>
            </c:if>
	            <!---->
	        </div>
            <!---->
            <!---->
            </c:forEach>
        <!---->
    </div>
    <!---->
   <%@include file="footer.jsp" %>
    <!---->
</body>

</html>
<script>
</script>
