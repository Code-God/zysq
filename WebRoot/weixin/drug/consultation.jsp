<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>400电话</title>
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/page.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script>
    function callphone(){    
        window.location.href = "tel:4006303390";
    }
</script>
</head>

<body onload="callphone();">
    
    <!---->
    <!---->
    <div class="main">
        <!---->
         <div class="details_info">       
             <img src="<%=request.getContextPath()%>/weixin/drug/images/contact400.jpg" width="100%">
              <!--<h1>业务咨询或有任何疑问请拨打电话4006303390。</h1>-->
        </div>
        <!---->        
    </div>
    <!---->
    <!---->
</body>

</html><script>
/////////////////////////////////////////////////////////////////////////
(function (doc, win) {
var docEl = doc.documentElement,
resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
recalc = function () {
var clientWidth = docEl.clientWidth;
if (!clientWidth) return;
docEl.style.fontSize = 16 * (clientWidth / 320) + 'px';
};

if (!doc.addEventListener) return;
win.addEventListener(resizeEvt, recalc, false);
doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
</script>