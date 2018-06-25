<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%
	response.setHeader("pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>访问出错</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/weixin/css/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/weixin/css/font.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/weixin/css/idangerous.swiper.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/weixin/css/jquery.mmenu.all.css" />
</head>
<body onload="init()">
<!-- 头部菜单 -->
<jsp:include page="/weixin/top.jsp"/> 
<div class="content">
  <div style="text-align:center; font-size:2em; padding:5em 0">
		您取消了授权。（此授权链接来自官方微信，是安全并值得信赖的。）
	</div>
	  <div class="more"><button class="button3" type="button" onclick="window.location.href='<%=request.getContextPath() %>/weixin/index.jsp'">返回首页</button></div>
</div>
<script type="text/javascript">
function init(){
	if (typeof WeixinJSBridge == "undefined"){
	    if( document.addEventListener ){
	        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    }else if (document.attachEvent){
	        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	}else{
	    onBridgeReady();
	}
}


function onBridgeReady(){
 	WeixinJSBridge.call('hideOptionMenu');
}

</script>
</body>
</html>