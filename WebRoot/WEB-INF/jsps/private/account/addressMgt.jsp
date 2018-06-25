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
  	 <a class="order_a1" href="">会员中心</a>&gt; <a class="order_a2" href="">地址管理</a>
  </div>
</div>
<div class="order_center">
	<!--左侧开始-->
	<%@include file="/WEB-INF/jsps/private/account/leftNavigate.jsp"%>
    <!--左侧结束-->
    <!--右侧开始-->
    <div class="order_r">
       <h3 class="order_h3">
       	  <span class="myorder">地址管理</span>
       </h3>
       <div class="clearb"></div>
       <s:if test="#request.addrlist.size>0">
         <table class="order_tab">
          <tr class="order_tr4">
            <th class="wid_12" scope="col">地址类型</th>
            <th class="wid_20" scope="col">具体地址</th>
            <th class="wid_12" scope="col">邮政编码</th>
            <th class="wid_12" scope="col">收件人</th>
            <th class="wid_12" scope="col">联系电话</th>
            <th class="wid_30" scope="col">操作</th>
          </tr>
          
                <s:iterator value="#request.addrlist" var="addr">
                 <tr class="order_tr3">
                  <td class=""><s:property value="alias"/> </td>
                   <td class=""><s:property value="detailAddr"/></td>
                   <td class=""><s:property value="zipcode"/></td>
                  <td class=""><s:property value="username"/></td>
                  <td class=""><s:property value="phone"/></td>
                  <td class="">
                  	<s:if test="#addr.isDefault!=1">
                   <button type="button" class="btn_1" onclick='setDefault(<s:property value="id"/>);'>设为默认</button>
                   </s:if>
                   <button type="button" class="btn_1" onclick='modifyAddr(<s:property value="id"/>);'>修改</button><button type="button" class="btn_1" onclick='delAddr(<s:property value="id"/>);'>删除</button>
                  </td>
                </tr>
               </s:iterator>
      </table>
       </s:if>
		<div class="add_address"><button type="button" class="btn_2" onclick="addAddr();">+  新增地址</button></div>
      <div class="address_edit" id="address_edit" style="display:none">
      <form action="<%=request.getContextPath()%>/private/account_addAddr.Q" id="myform" method="post">
      <input name="addr.id" value="" id="addrId" type="hidden">
       <input name="addr.userid" value="" id="userid" type="hidden">
        <ul class="list_info">
          <li><span class="tit">地址标注:</span> <input type="text" name="addr.alias" class="input_text" value="" id="alias"> <span class="nor" id="aliasSpan">例如家里、公司</span></li>
          <li><span class="tit"><span class="strong">*</span>详细地址:</span> <input type="text" name="addr.detailAddr" class="input_text" value="" id="detailAddr"><span class="nor" id="detailAddrSpan" style="color:red"></span></li>
          <li><span class="tit"><span class="strong"> </span>邮政编码:</span> <input type="text" name="addr.zipcode" class="input_text" value="" id="zipcode"></li>
          <li><span class="tit"><span class="strong">*</span>收&nbsp;&nbsp;货&nbsp;&nbsp;人:</span> <input type="text" name="addr.username" class="input_text" value="" id="username"><span class="nor" id="usernameSpan" style="color:red"></span></li>
          <li><span class="tit"><span class="strong">*</span>手&nbsp;&nbsp;机:</span> <input type="text" name="addr.phone" class="input_text" value="" id="phone"> <span class="nor" id="phoneSpan" style="color:red"></span></li>
          <li>
            <div class="wrap_btn">
            	<button type="button" class="btn_2" onclick="submitForm();">保存</button>
            </div>
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
