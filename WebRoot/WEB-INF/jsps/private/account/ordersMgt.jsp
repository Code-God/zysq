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
	function searchOrder(){
		var status = document.getElementById("orderstatus").value;
		window.location.href='<%=request.getContextPath()%>/private/account_ordersMgt.Q?status='+status;
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
  	 <a class="order_a1" href="<%=request.getContextPath() %>/private/account_userInfo.Q">个人中心</a>&gt; <a class="order_a2">我的订单
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
 <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">我的订单</span>
          <span class="order_choose">
          	<select id="orderstatus" name="status">
          		 <option value="" <s:if test="#request.status==0">selected="selected"</s:if> >请选择</option>
                <option value="0" <s:if test="#request.status==0">selected="selected"</s:if> >未支付</option>
                <option value="1" <s:if test="#request.status==1">selected="selected"</s:if> >已支付</option>
                <option value="2" <s:if test="#request.status==2">selected="selected"</s:if> >已发货</option>
                <option value="3" <s:if test="#request.status==3">selected="selected"</s:if> >已完成</option>
                <option value="9" <s:if test="#request.status==9">selected="selected"</s:if> >已取消</option>
            </select>
            <input type="button" value="搜索" onclick="searchOrder();">
          </span>
       </h3>
       <div class="clearb"></div>
       <table class="order_tab">
          <tr class="order_tr1">
            <th class="order_td1" scope="col">订单号</th>
            <th class="order_td2" scope="col">商品</th>
            <th class="order_td5" scope="col">总金额</th>
             <th class="order_td5" scope="col">运费</th>
            <th class="order_td7" scope="col">下单时间</th>
            <th class="order_td5" scope="col">状态</th>
            <th class="order_td8" scope="col">操作</th>
          </tr>
           <s:iterator value="#request.ordersList" var="order">
          <tr class="order_tr2">
            <td class="order_td1"><s:property value="orderNo"/></td>
            <td class="order_td2">
            <s:iterator value="#order.ordersDetail" var="detail">
           	 	<a target="_blank" href='<%=request.getContextPath()%>/public/details.Q?code=<s:property value="prdCode"/>'><img src='${imgServer }<s:property value="prdImage"/>'></a>
           	 	<!--<span class="clearb yunfei"><a href='<%=request.getContextPath()%>/public/details.Q?id=<s:property value="prdId"/>'>申请退货</a></span> -->
           </s:iterator>
             </td>
            <td class="order_td5"><s:property value="fee"/>
            </td>
            <td class="order_td5"><s:property value="transFee"/>
            </td>
            <td class="order_td7"><s:date name="odate" format="yyyy-MM-dd HH:mm:ss" />
            </td>
             <td class="order_td5">
             		<s:if test="#order.status==0">未支付</s:if>
                  	<s:elseif test="#order.status==1"><font color=green>已支付</font></s:elseif>
                  	<s:elseif test="#order.status==2">已发货</s:elseif>
                  	<s:elseif test="#order.status==3">已完成</s:elseif>
                  	<s:elseif test="#order.status==9"><font color=red>已取消</font></s:elseif>
            </td>
            <td class="order_td8">
            <span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_ordersDetai.Q?orderNo=<s:property value="orderNo"/>'>订单详情</a></span>
            <s:if test="#order.status==0">
            	<span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/payment_payment.Q?orderNo=<s:property value="orderNo"/>'>立即支付</a></span>
                <span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_changeOrderState.Q?orderNo=<s:property value="orderNo"/>&orderState=9'>取消订单</a></span>
            </s:if>
            <s:if test="#order.status==2">
            	<span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_changeOrderState.Q?orderNo=<s:property value="orderNo"/>&orderState=3'>确认收货</a></span>
            </s:if>
            <s:if test="#order.status==3">
            	<span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_commentsMgt.Q'>去评论</a></span>
            </s:if>
            </td>
          </tr>
         </s:iterator>
        </table>
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
