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
	function savePw(){
		var oldPw = $("#psw_old").val();
		if(!oldPw){
			$("#psw_old_hint").html("请填写旧密码");
			return;
		}else{
			$("#psw_old_hint").html("");
		}
		var newPw = $("#psw_new").val();
		if(!newPw){
			$("#psw_new_hint").html("请填写新密码");
			return;
		}else{
			$("#psw_new_hint").html("");
		}
		var newPwAgain = $("#newPwAgain").val();
		if(!newPwAgain){
			$("#psw_newAgain_hint").html("请再次填写新密码");
			return;
		}else{
			$("#psw_newAgain_hint").html("");
		}
		if(newPw!=newPwAgain){
			$("#psw_newAgain_hint").html("两次密码填写不一致");
			return;
		}else{
			$("#psw_newAgain_hint").html("");
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
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">密码管理</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
    <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">密码管理</span>
       </h3>
       <div class="clearb"></div>
       <div class="token_process">
         <form id="myform" method="post" action="<%=request.getContextPath()%>/private/account_mdPassWord.Q">
          <ul>
             <li>
               <span class="process_text">旧密码： </span>
               <input name="oldPw" value="" class="input_text" maxlength="16" type="password" id="psw_old">
               <span id="psw_old_hint" class="tips_info" style="">*</span>
               <div style="clear:both"></div>
             </li>
             <li>
               <span class="process_text">新密码： </span>
               <input name="newPw" value="" class="input_text" maxlength="16" type="password" id="psw_new">
                <span id="psw_new_hint" class="tips_info" style="">*</span>
               <div style="clear:both"></div>
             </li>
             <li>
               <span class="process_text">重复密码： </span>
               <input  value="" class="input_text" maxlength="16" type="password" id="newPwAgain">
               <span id="psw_newAgain_hint" class="tips_info" style="">*</span>
               <div style="clear:both"></div>
             </li>
              <li>
               <span class="process_text" style="color:red">${msg } </span>
               <div style="clear:both"></div>
             </li>
             <li class="side_btn">
                <button type="button" class="btn_2" onclick="savePw();">保存</button>
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
