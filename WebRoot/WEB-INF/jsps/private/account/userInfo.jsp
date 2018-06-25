<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!doctype html>
<html>
<head>
<base href="<%=basePath %>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=Version.getInstance().getSystemTitle() %></title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/order.css">
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery.easing-1.3.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript" src="js/public/index.js"></script>
<script src="js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
<script type="text/javascript">
	function saveUser(){
		var telphone = $("#telphone").val();
		if(!telphone){
			$("#telphoneSpan").html("手机必填");
			return;
		}
		var email = $("#email").val();
		if(!email){
			$("#emailSpan").html("邮箱必填");
			return;
		}
		var nickName = $("#nickName").val();
		if(!nickName){
			$("#nickNameSpan").html("昵称必填");
			return;
		}
		$("#myform").submit();
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/header.jsp"%>
<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
<%@include file="/WEB-INF/jsps/navigate.jsp"%>
<!----页面导航------>
<!--我的订单开始-->
<div class="linknav">
  <div class="breadcrumb">
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">个人资料</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
    <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">个人资料</span>
       </h3>
       <div class="clearb"></div>
       <div class="token_process">
         <form id="myform" method="post" action="<%=request.getContextPath()%>/private/account_saveUser.Q">
         <input name="user.id" value="${user.id }" type="hidden">
           <ul>
             <li>
               <span class="process_text">昵称： </span>
               <input type="text" class="input_text" value="${user.nickName }" id="nickName" name="user.nickName"/>
                <span style="color:red" id="nickNameSpan">&nbsp;*</span>
               <div style="clear:both"></div>
             </li>
             <li>
               <span class="process_text">手机： </span>
               <input name="user.telphone" value="${user.telphone }" class="input_text" type="text" maxlength="11" id="telphone">
               <span style="color:red" id="telphoneSpan">&nbsp;* </span>
               <div style="clear:both"></div>
             </li>
             <li>
               <span class="process_text">邮箱： </span>
               <input name="user.email" value="${user.email }" class="input_text" type="text" maxlength="80" id="email">
               <span style="color:red" id="emailSpan">&nbsp;* </span>
               <div style="clear:both"></div>
             </li>
              <li>
               <span class="process_text">我的积分： </span>
               <input type="text" class="input_text" value="${user.integration }" id="integration" readonly="readonly"/>
               <div style="clear:both"></div>
             </li>
             <li>
               <span class="process_text">注册日期： </span>
               <input value='<s:date name="user.regDate" format="yyyy-MM-dd hh:mm:ss" />' class="input_text" type="text" maxlength="80" readonly="readonly">
               <div style="clear:both"></div>
             </li>
              <li>
               <span class="process_text">最后登陆时间： </span>
               <input value='<s:date name="user.lastLogin" format="yyyy-MM-dd hh:mm:ss" />' class="input_text" type="text" maxlength="80" readonly="readonly">
               <div style="clear:both"></div>
             </li>
              <li>
               <span class="process_text"> </span>
               <span style="color:red" >${msg } </span>
               <div style="clear:both"></div>
             </li>
             <li class="side_btn">
                <button type="button" class="btn_2" onclick="saveUser();">保存</button>
             </li>
           </ul>
         </form>
       </div>
    </div>
    <!--右侧结束-->
</div>
<div style="clear:both;"></div>
<!--我的订单结束-->
<!----footerStart------>
<%@include file="/WEB-INF/jsps/foot.jsp"%>
</body>
<script>
$(document).ready(function() {
     $(".order_a").click(function(){
         $(this).siblings().removeClass('curr_a');
         $(this).addClass("curr_a");
     });
	  $(".page a").click(function(){
         $(this).siblings().removeClass('current');
         $(this).addClass("current");
     });
	 $("#j_city_con").hover(function(){
	      $(this).toggleClass("mod_city_con_on");
	  });
	    $(".mod_cate").hover(function(){
		  $(this).toggleClass("mod_cate_on");
		  });
	$("#category_container .mod_cate_bd").slide({
	type:"menu",
	titCell:".mod_cate_li",
	targetCell:".mod_subcate",
	delayTime:0,
	triggerTime:10,
	defaultPlay:false,
	returnDefault:true
    });
	
	$('.tab ul.tabs').addClass('active').find('> li:eq(0)').addClass('current');
	$('.tab ul.tabs li a').click(function (g) { 
		var tab = $(this).closest('.tab'), 
			index = $(this).closest('li').index();
		
		tab.find('ul.tabs > li').removeClass('current');
		$(this).closest('li').addClass('current');
		
		tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
		tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
		
		g.preventDefault();
	} );
});
</script>
</html>
