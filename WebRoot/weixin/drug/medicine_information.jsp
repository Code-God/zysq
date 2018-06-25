<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>推荐得积分</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=5,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/page.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/iconfont/iconfont.css">
    <script src="${ctx}/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="${ctx}/weixin/drug/js/html5shiv.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css">
    <script>
    var activejsure = 1;
    $(document).ready(function () {

        $(".transition").css("height", $(document).height());
        $(".transition").css("width", $(document).width());

    });
    </script>
</head>

<body class="transition">
        <!---->
     <img src="${qr_code}" width="60%">
        <!---->
            <ul class="Continue">
                <li>
                	<form action="${ctx}/drug/user_patientInfo.Q" method="post" id="theForm" class="form-horizontal">
                	<input type="hidden" name="singupType" value="${singupType}">
        			<input type="hidden" name="diseaseId" value="${diseaseId}">
                    <button type="submit" class="patient_submit">继续推荐</button>
                    </form>
                </li>
            </ul>
    <!---->
    <%@include file="footer.jsp"%>
    <!---->
</body>

</html>
<script>
</script>