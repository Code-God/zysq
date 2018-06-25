<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>我的找药神器二维码</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/user.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
</head>

<body>
    <div class="main">
        <!---->
        <!---->
        <div class="qr_code">
                    <ul>
                        <li><h1>我的推荐二维码</h1></li>
                    </ul>
                    <figure>
                   		 <!--<img src="<%=request.getContextPath()%>/weixin/drug/images/qr_code.png" width="90%">-->
                   		 	  <img src="${qr_code}" width="60%">
                   		 
                    </figure>
                    <ul>
                        <li style="text-align:left">1.出示二维码给患者或家属</li>
                        <li style="text-align:left">2.患者或家属扫描二维码自行报名参加临床研究项目</li>
                    </ul>
                <div class="clear"></div>

        </div>
        <!---->
    </div>
</body>

</html>
<script>
</script>