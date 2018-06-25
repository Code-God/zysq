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
<title>用来挂接的几个入口</title>
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
  	<li> <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8275aa5fa86fb7c6&redirect_uri=http%3A%2F%2Flocalhost:88%2Ffenxiao%2Fpublic%2Fwx_goPage.Q&response_type=code&scope=snsapi_base&state=fxApply,000#wechat_redirect">经销商申请入口</a>
  	<li> <a href="">微商城入口</a>
	  
  </div>
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