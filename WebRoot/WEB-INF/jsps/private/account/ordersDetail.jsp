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
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/header.jsp"%>
<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
<%@include file="/WEB-INF/jsps/navigate.jsp"%>
<!----页面导航------>
<!--我的订单开始-->
<div class="linknav">
  <div class="breadcrumb"> <a class="order_a1" href="<%=request.getContextPath() %>/private/account_userInfo.Q">个人中心</a>&gt; <a class="order_a2" href="<%=request.getContextPath() %>/private/account_ordersMgt.Q">我的订单</a>&gt; <a class="order_a2">订单详情</a> </div>
</div>
<div class="order_center">
  <dl class="order_base_dl">
    <dt class="order_base_dt">订单概况</dt>
    <dd class="order_base_dd"> <span class="order_base_cue">订单编号：</span> <span class="order_base_cont">${orders.orderNo }</span> </dd>
    <dd class="order_base_dd"> <span class="order_base_cue">订单状态：</span> <span class="order_base_cont">
     <s:if test="#request.orders.status==0">
              	未支付
              </s:if>
              <s:elseif test="#request.orders.status==1">
              已支付
              </s:elseif>
               <s:elseif test="#request.orders.status==2">
              已发货
              </s:elseif>
               <s:elseif test="#request.orders.status==3">
              已完成
              </s:elseif>
               <s:elseif test="#request.orders.status==9">
              已取消
              </s:elseif>
    </span> </dd>
    <dd class="order_base_dd"> <span class="order_base_cue">订单总计：</span> <span class="order_base_cont"><b class="order_price">￥${(orders.feePrice+orders.transFeePrice)/100 }</b>(含运费￥${orders.transFeePrice/100 }) <span class="order_jia"><span class="jia_icon"></span>下单24小时内，无符合“价格保护”补偿的商品</span> </span> </dd>
    <dd class="order_base_dd"> <span class="order_base_cue">收货信息：</span> <span class="order_base_cont"><s:property value="#request.orders.addressee"/> </span> <span style="margin:0 20px;"><s:property value="#request.orders.address"/> </span> <span> <s:property value="#request.orders.phone"/> </span> </dd>
    <dd class="order_base_dd"> <span class="order_base_cue">订单备注：</span> <span class="order_base_cont">无</span> </dd>
  </dl>
  <dl class="order_base_dl" style="margin-bottom:30px;">
    <dt class="order_base_dt">订单详情</dt>
    <dd class="order_base_detail">
      <h3 class="order_base_h3">订单完整流程</h3>
      <ul class="order_base_ul">
        <li>提交订单</li>
        <li>付款</li>
        <li>商城发货</li>
        <li>确认收货</li>
        <li>评价</li>
      </ul>
      <span class="wuliu_icon"></span> </dd>
    <hr class="order_hr">
    <dd class="order_base_qingdan">
      <h3 class="order_base_h3">商品清单</h3>
      <dl class="order_qingdan_h">
        <dd class="qingdan_name">商品名称</dd>
        <dd class="qingdan_price">单价</dd>
        <dd class="qingdan_num">购买数量</dd>
        <dd class="qingdan_price">金额小计</dd>
      </dl>
       <s:iterator value="#request.ods" var="od">
      <dl class="order_qingdan_c">
        <dd class="qingdan_name"><a target="_blank" href="<%=request.getContextPath()%>/public/details.Q?code=<s:property value='prdCode'/>"> <s:property value="prodName"/></a></dd>
        <dd class="qingdan_price">￥<s:property value="price"/></dd>
        <dd class="qingdan_num"><s:property value="prdCount"/></dd>
        <dd class="qingdan_price">￥<s:property value="total"/></dd>
      </dl>
      </s:iterator>
    </dd>
    <dd class="order_totle">
      <p><span>运费金额：</span><em class="totle_cue">￥${orders.transFeePrice/100 }</em></p>
      <p><span>积分抵扣：</span><em class="totle_cue">￥0.00</em></p>
      <p><span>促销优惠：</span><em class="totle_cue">￥0.00</em></p>
      <p class="totle_p"><span>应付款总计：</span><em class="totle_cue tprice">￥${(orders.feePrice+orders.transFeePrice)/100 }</em></p>
    </dd>
  </dl>
</div>
<div class="clearb"></div>
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
