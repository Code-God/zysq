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
<script type="text/javascript" src="js/comment.js"></script>
<script type="text/javascript">
	function saveComments(){
		var StarNum = $("#StarNum").val();
		if(0==StarNum){
			$("#errorMsg").html("请给评价打分");
			return;
		}
		var content = $("#content").val();
		if(!content){
			$("#errorMsg").html("请填写评价内容");
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
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">商品评论</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
     <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">商品评论</span>
       </h3>
       <div class="clearb"></div>
       <table class="order_tab">
          <tr class="order_tr1">
            <th class="order_td2" scope="col">商品</th>
            <th class="order_td5" scope="col">金额</th>
            <th class="order_td7" scope="col">下单时间</th>
          </tr>
          
          <tr class="order_tr2">
            <td class="order_td1">
              <div class="p-img fl"><a target="_blank" href='<%=request.getContextPath()%>/public/details.Q?code=<s:property value="#request.od.prdCode"/>'><img height="80" width="100" src='${imgServer }<s:property value="#request.od.prdCode"/>_small.jpg'></a></div>
              <div class="p-name fl">${od.prodName }</div>
            </td>
            <td class="order_td2">${od.price }</td>
            <td class="order_td3"><s:date name="#request.od.buyDate" format="yyyy-MM-dd hh:mm:ss" /> </td>
          </tr>
          
        </table>
        <div class="quiz">
            <h3>我要评论</h3>
            <div class="quiz_content">
                <form action="<%=request.getContextPath()%>/private/account_saveComments.Q" id="myform" method="post">
                <input name="comments.stars" id="StarNum" type="hidden" value="0">
                 <input name="comments.prdCode" id="prdCode" type="hidden" value="${od.prdCode }">
                  <input name="comments.orderDetailId" id="detailId" type="hidden" value="${od.id }">
                   <input name="comments.prdName" id="prdName" type="hidden" value="${od.prodName }">
                    <div class="goods-comm">
                        <div class="goods-comm-stars">
                            <span class="star_l">满意度：</span>
                            <div id="rate-comm-1" class="rate-comm"></div>
                        </div>
                    </div>
        
                    <div class="l_text">
                        <label class="m_flo">内  容：</label>
                        <textarea name="comments.content" id="content" class="text"></textarea>
                        
                    </div>
                    
                    <button class="btn_1" type="button" style="margin-left: 65px;padding: 3px 20px;" onclick="saveComments();">发表评论</button>
	                </form>
                    </div>
            </div>
        </div>
    </div>
    <!--右侧结束-->
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
