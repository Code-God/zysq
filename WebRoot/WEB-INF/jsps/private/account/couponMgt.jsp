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
	
	function delAddr(id){
		$.ajax({
				url:"<%=request.getContextPath()%>/private/account_delAddr.Q",
				data:{"id":id},
				dataType:'text',
				success:function(addr){
					if(addr="sucess"){
						window.location.href='<%=request.getContextPath()%>/private/account_addrMgt.Q';
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
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">优惠券</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
    <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">优惠券</span>
       </h3>
       <div class="clearb"></div>
       <div class="tab">
          <ul class="tabs">
            <li><a href="#">未使用的优惠券</a></li>
            <li><a href="#">已使用的优惠券</a></li>
            <li><a href="#">已过期的优惠券</a></li>
          </ul>
          <!-- / tabs -->
          <div class="tab_content">
            <div class="tabs_item"> 
              <table class="order_tab">
                  <tr class="order_tr4">
                    <th class="wid_12" scope="col">优惠券代码</th>
                    <th class="wid_20" scope="col">优惠券名称</th>
                    <th class="wid_12" scope="col">优惠券金额</th>
                    <th class="wid_12" scope="col">需消费金额</th>
                    <th class="wid_22" scope="col">有效期</th>
                    <th class="wid_12" scope="col">状态</th>
                  </tr>
                   <s:iterator value="#request.unUselist" var="unuse">
                  <tr class="order_tr3">
                    <td class=""><s:property value="couponCode"/> </td>
                    <td class=""><s:property value="couponName"/></td>
                    <td class=""><span class="green"><s:property value="couponMoney"/></span></td>
                    <td class=""><s:property value="needCustomMoney"/></td>
                    <td class=""><s:property value="userfulLife"/></td>
                    <td class="">未使用</td>
                  </tr>
                 </s:iterator>
              </table>
            </div>
            <!-- / tabs_item -->
            <div class="tabs_item"> 
              <table class="order_tab">
                  <tr class="order_tr4">
                    <th class="wid_12" scope="col">优惠券代码</th>
                    <th class="wid_20" scope="col">优惠券名称</th>
                    <th class="wid_12" scope="col">优惠券金额</th>
                    <th class="wid_12" scope="col">需消费金额</th>
                    <th class="wid_22" scope="col">有效期</th>
                    <th class="wid_12" scope="col">状态</th>
                  </tr>
                   <s:iterator value="#request.hasUselist" var="hasuse">
                  <tr class="order_tr3">
                    <td class=""><s:property value="couponCode"/> </td>
                    <td class=""><s:property value="couponName"/></td>
                    <td class=""><span class="green"><s:property value="couponMoney"/></span></td>
                    <td class=""><s:property value="needCustomMoney"/></td>
                    <td class=""><s:property value="userfulLife"/></td>
                    <td class="">已使用</td>
                  </tr>
                  </s:iterator>
              </table>
            </div>
            <!-- / tabs_item -->
            <div class="tabs_item"> 
              <table class="order_tab">
                  <tr class="order_tr4">
                    <th class="wid_12" scope="col">优惠券代码</th>
                    <th class="wid_20" scope="col">优惠券名称</th>
                    <th class="wid_12" scope="col">优惠券金额</th>
                    <th class="wid_12" scope="col">需消费金额</th>
                    <th class="wid_22" scope="col">有效期</th>
                    <th class="wid_12" scope="col">状态</th>
                  </tr>
                  <s:iterator value="#request.hasCanclelist" var="hascancle">
                  <tr class="order_tr3">
                     <td class=""><s:property value="couponCode"/> </td>
                    <td class=""><s:property value="couponName"/></td>
                    <td class=""><span class="green"><s:property value="couponMoney"/></span></td>
                    <td class=""><s:property value="needCustomMoney"/></td>
                    <td class=""><s:property value="userfulLife"/></td>
                    <td class="">已过期</td>
                  </tr>
                  </s:iterator>
              </table>
            </div>
          </div>
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
