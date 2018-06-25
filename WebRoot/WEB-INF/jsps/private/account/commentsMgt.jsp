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
<style>
.order_tab tr th,.order_tab tr td{padding: 6px 0;font-weight: normal;margin: 0 auto;text-align: center;}
.order_tab tr td a,.order_tab tr td .green{color:#4ec05e; font-weight:700;}
</style>
<script type="text/javascript">
	function submitForm(){
		var detailAddr = $("#detailAddr").val();
		if(!detailAddr){
			$("#detailAddrSpan").html(" 详细地址必填");
			return;
		}
		var username = $("#username").val();
		if(!username){
			$("#usernameSpan").html(" 收货人必填");
			return;
		}
		var phone = $("#phone").val();
		if(!phone){
			$("#phoneSpan").html(" 手机必填");
			return;
		}
		$("#myform").submit();
	}
	function addAddr(){
		$("#addrId").val("");
		$("#userid").val("");
		$("#alias").val("");
		$("#detailAddr").val("");
		$("#zipcode").val("");
		$("#username").val("");
		$("#phone").val("");
		$("#address_edit").show();
	}
	function modifyAddr(id){
		$.ajax({
				url:"<%=request.getContextPath()%>/private/account_modifyAddr.Q",
				data:{"id":id},
				dataType:'json',
				success:function(addr){
					$("#addrId").val(addr.id);
					$("#userid").val(addr.userid);
					$("#alias").val(addr.alias);
					$("#detailAddr").val(addr.detailAddr);
					$("#zipcode").val(addr.zipcode);
					$("#username").val(addr.username);
					$("#phone").val(addr.phone);
					$("#address_edit").show();
				}
			})
	
	}
	
	function setDefault(id){
		$.ajax({
				url:"<%=request.getContextPath()%>/private/account_setDefault.Q",
				data:{"id":id},
				dataType:'text',
				success:function(addr){
					if(addr="sucess"){
						window.location.href='<%=request.getContextPath()%>/private/account_addrMgt.Q';
					}
					
				}
			})
	}
	
	function delFavorite(id){
		$.ajax({
				url:"<%=request.getContextPath()%>/private/account_delFavorite.Q",
				data:{"id":id},
				dataType:'text',
				success:function(addr){
					if(addr="sucess"){
						window.location.href='<%=request.getContextPath()%>/private/account_favoriteMgt.Q';
					}
					
				}
			})
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
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">商品评价</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
     <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">商品评价</span>
       </h3>
       <div class="clearb"></div>
       <div class="tab">
          <ul class="tabs">
            <li><a href="#">待评论商品</a></li>
            <li><a href="#">已评价商品</a></li>
          </ul>
          <!-- / tabs -->
          <div class="tab_content">
            <div class="tabs_item"> <table class="order_tab">
          <tr class="order_tr4">
            <th class="wid_12" scope="col">订单编号</th>
            <th class="wid_30" scope="col">商品名称</th>
            <th class="wid_12" scope="col">购买时间</th>
            <th class="wid_12" scope="col">操作</th>
          </tr>
          <s:iterator value="#request.unorderDetails" var="unorderDetail">
          <tr class="order_tr3">
            <td class=""><s:property value="orderNo"/></td>
            <td class=""><a href='<%=request.getContextPath()%>/public/details.Q?code=<s:property value="prdCode"/>' target="_blank"><s:property value="prodName"/></a></td>
            <td class=""><s:date name="buyDate" format="yyyy-MM-dd hh:mm:ss" /></td>
            <td class="">
            <span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_commentsInput.Q?detailId=<s:property value="id"/>' targer="_blank">
            我要评价</a></span>
            </td>
          </tr>
          </s:iterator>
        </table>
        <!--<div class="page">
        	<a class="disabled"><span class="pre_a"></span>上一页</a>
            <a class="current" href="#?page=2">1</a>
            <a href="#?page=2">2</a>
            <a href="#?page=3">3</a>
            ...
            <a href="#?page=462">462</a>
            <a href="#?page=2">下一页<span class="next_a"></span> </a>
        </div>-->
            </div>
            <!-- / tabs_item -->
            <div class="tabs_item"> <table class="order_tab">
          <tr class="order_tr4">
            <th class="wid_12" scope="col">订单编号</th>
            <th class="wid_30" scope="col">商品名称</th>
            <th class="wid_12" scope="col">购买时间</th>
            <th class="wid_12" scope="col">操作</th>
          </tr>
          
         <s:iterator value="#request.hasorderDetails" var="hasorderDetail">
          <tr class="order_tr3">
            <td class=""><s:property value="orderNo"/></td>
            <td class=""><a href='<%=request.getContextPath()%>/public/details.Q?code=<s:property value="prdCode"/>' target="_blank"><s:property value="prodName"/></a></td>
            <td class=""><s:date name="buyDate" format="yyyy-MM-dd hh:mm:ss" /></td>
            <td class="">
            <span class="clearb yunfei"><a class="safe_a" href='<%=request.getContextPath()%>/private/account_commentsDetail.Q?detailId=<s:property value="id"/>'>查看</a></span>
            </td>
          </tr>
          </s:iterator>
         
        </table>
       <!-- <div class="page">
        	<a class="disabled"><span class="pre_a"></span>上一页</a>
            <a class="current" href="#?page=2">1</a>
            <a href="#?page=2">2</a>
            <a href="#?page=3">3</a>
            ...
            <a href="#?page=462">462</a>
            <a href="#?page=2">下一页<span class="next_a"></span> </a>
        </div>-->
            </div>
            <!-- / tabs_item -->
          </div>
          <!-- / tab_content -->
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
