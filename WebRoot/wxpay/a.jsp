<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testpay.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function doWxPay(){
			//JS API
			var appId = "wx8275aa5fa86fb7c6";
			var time = new Date().getTime();
			var nonceStr="jiamspay";
			var package="";
			var signType="MD5";
			var paySign = "";
			
			WeixinJSBridge.invoke('getBrandWCPayRequest',{
				"appId" : appId, // 公众号名称，
				"timeStamp": ""+time,//  时间戳，自 1970 1970 年以来的秒数 年以来的秒数 年以来的秒数 年以来的秒数 
				"nonceStr" : nonceStr, // 随机串 \
				"package" : "orderId=1234-001",
				"signType" : "MD5", // 微信签名方式
				"paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" // 微信签名
				},function(res){ 
					alert(res.err_msg);
					if(res.err_msg == "get_brand_wcpay_request:ok"){
						
					}
				});
				
		}
	
	</script>
  </head>
  
  <body>
    
    价格： 10.00元
  
  <input type="button" value="现在支付" onclick="doWxPay()" style="font-size:50pt">
    
    
  </body>
</html>
