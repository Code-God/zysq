<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><%=Version.getInstance().getSystemTitle() %>-订单确认</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cart.css">
		<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
		<style>
.order_cashin .dd_mod_order_bd {
	padding: 0 30px 20px 30px;
}

.cashin_row {
	zoom: 1;
	overflow: hidden;
	line-height: 20px;
	padding: 10px 0;
	border-bottom: 1px solid #e0e0e0;
	clear: both
}

.cashin_row_c1 {
	float: left;
	color: #666
}

.cashin_row_c2 {
	float: right
}

.cashin_row_hd {
	font-weight: 700;
	color: #666
}

.cashin_jifen_use {
	cursor: pointer;
	font-weight: 700;
	color: #666
}

.cashin_jifen_use .dd_mod_arrow {
	margin-left: 3px;
	margin-right: 5px
}

.cashin_row_bd {
	zoom: 1;
	padding-top: 5px
}

.cashin_row_bd : after {
	content: '';
	display: block;
	clear: both;
	height: 0;
	visibility: hidden
}

.cashin_stat {
	clear: both;
	padding: 15px 0px 40px
}

.cashin_stat_price {
	text-align: right;
	padding-bottom: 5px;
	font-size: 14px;
	height: 35px;
	line-height: 35px;
	border-bottom: #d3d3d3 solid 1px;
	color: #929292
}

.cashin_stat_price .co_txt1 {
	font-size: 30px;
	font-weight: 400;
	padding-left: 15px;
	font-family: arial
}

.cashin_stat_submit_wrap {
	position: relative;
	clear: both;
	margin-top: 10px;
	zoom: 1
}

.cashin_stat_submit_wrap : after {
	content: '';
	display: block;
	clear: both;
	height: 0;
	visibility: hidden
}

.cashin_stat_submit {
	float: right;
	width: 120px;
	height: 40px;
	overflow: hidden;
	background: #faa701;
	color: #fff;
	text-align: center;
	font: normal 14px/ 40px "Microsoft Yahei";
}

.cashin_stat_submit_disabled {
	background-position: -130px -30px
}

.cashin_stat_row {
	zoom: 1;
	overflow: hidden;
	padding: 7px 0;
	color: #333;
	line-height: 22px
}

.cashin_stat_row a {
	color: #1c7ada
}

.cashin_stat_row_c1 {
	float: left;
	width: 870px;
	text-align: right
}

.cashin_stat_row_c2 {
	float: right;
	width: 60px;
	text-align: center;
}

.cashin_stat_row_c2 a {
	color: #37f;
}

.cashin_stat_row_c1 strong {
	color: #666
}

.cashin_stat_row .ta_r strong {
	color: #737373
}

.co_txt1 {
	color: #E00;
}

.order_address_list .order_address_list_edit {
	padding-bottom: 20px;
	padding-left: 120px;
	background-color: #f5f5f5;
}

.order_address_info {
	float: left;
	zoom: 1;
}

.order_address_form {
	float: left;
	width: 800px;
}

.order_address_form_item {
	padding: 5px 0;
	height: 24px;
	line-height: 24px;
	zoom: 1;
}

.order_address_form .item_area {
	padding-top: 20px;
}

.order_address_form_item .form_hd {
	float: left;
	width: 75px;
}

.order_address_form_item .form_bd {
	float: left;
	width: 720px;
}

.order_address_form_item .require {
	display: inline-block;
	zoom: 1;
	width: 12px;
	color: #f1680c;
	font-family: simsun;
}

.select_area {
	z-index: 40;
	height: 20px;
	padding-bottom: 0;
}

.order_address_form .dd_mod_input_txt {
	height: 22px;
	padding: 0 5px;
	line-height: 22px;
}

.order_address_form .item_user .dd_mod_input_txt {
	width: 100px;
}
</style>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/header.jsp"%>
		<div class="wf_header">
			<div class="grid_c1">
				<div class="mod_logo">
					<h1>
						<a><img src="<%=request.getContextPath() %>/images/logo.jpg" alt="商城">
						</a>
					</h1>
				</div>
				<div class="flow_step_no2 flow_cart">
					<div class="flow_step">
						<ol class="cols3">
							<li class="step_1">
								1.我的购物车
							</li>
							<li class="step_2">
								2.确认订单并结算
							</li>
							<li class="step_3">
								3.订单提交成功
							</li>
						</ol>
					</div>
				</div>
			</div>
		</div>
		<div class="cart_content" id="cart_content">
			<div class="grid_c1">
				<div class="dd_mod_order order_address" name="address">
					<div class="dd_mod_order_hd">
						<h3 class="dd_mod_order_hd_tit">
							收货信息
						</h3>
					</div>

					<div class="dd_mod_order_bd">
						<ul class="order_address_list">
							<c:forEach items="${addrlist}" var="addr">
								<label for="i_aid${addr.id }" class="radio_desp">
									<li
										<c:if test="${addr.isDefault == 1 || fn:length(addrlist) == 1 }">class="selected"</c:if>
										username="${addr.username }" phone="${addr.phone }"
										zipcode="${addr.zipcode }" detail="${addr.detailAddr }">
										<div class="order_address_info">
											<span class="c1 radio_wrap"> <input
													id="i_aid${addr.id }" name="sel_addr_rd" class="in_sel"
													type="radio" value="${addr.id }"
													<c:if test="${addr.isDefault == 1 || fn:length(addrlist) == 1 }">checked</c:if>>
											</span>
											<span class="c2">${addr.alias }</span><span class="c3">${addr.detailAddr
												} ${addr.zipcode }，${addr.username } ${addr.phone }</span>
										</div>
									</li>
								</label>
								<script>
							          <c:if test="${addr.isDefault == 1 || fn:length(addrlist) == 1 }">
							          $(document).ready(function() {
							          	$('#sel_addr').val(${addr.id});
							          });
									  </c:if>
						         </script>
							</c:forEach>
							<form action="<%=request.getContextPath() %>/private/account_addAddr.Q" id="addform"
								method="post">
								<div class="order_address_list_edit">
									<div class="order_address_form">
										<div class="order_address_form_item item_address">
											<span class="form_hd"><span class="require"></span>地址标注：</span>
											<div class="form_bd" id="j_add_input_bd">
												<input name="addr.alias" type="text"
													class="dd_mod_input_txt dd_mod_input_txt_focus"
													id="j_add_input" placeholder="例如家里、公司" maxlength="50">
											</div>
										</div>
										<div class="order_address_form_item item_address">
											<span class="form_hd"><span class="require">*</span>详细地址：</span>
											<div class="form_bd" id="j_add_input_bd">
												<input id="detailAddr" name="addr.detailAddr" type="text"
													class="dd_mod_input_txt  dd_mod_input_txt_focus"
													id="j_add_input" maxlength="255">
												<span id="detailAddrSpan" style="color: red"></span>
											</div>
										</div>
										<div class="order_address_form_item item_address">
											<span class="form_hd"><span class="require">*</span>收&nbsp;&nbsp;货&nbsp;人：</span>
											<div class="form_bd" id="j_add_input_bd">
												<input id="username" name="addr.username" type="text"
													class="dd_mod_input_txt  dd_mod_input_txt_focus"
													id="j_add_input" maxlength="20">
												<span id="usernameSpan" style="color: red"></span>
											</div>
										</div>
										<div class="order_address_form_item item_address">
											<span class="form_hd"><span class="require">*</span>手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span>
											<div class="form_bd" id="j_add_input_bd">
												<input id="phone" name="addr.phone" type="text"
													class="dd_mod_input_txt  dd_mod_input_txt_focus"
													id="j_add_input" maxlength="11">
												<span id="phoneSpan" style="color: red"></span>
											</div>
										</div>
										<div class="order_address_form_item item_address">
											<span class="form_hd"><span class="require"></span>邮政编码：</span>
											<div class="form_bd" id="j_add_input_bd">
												<input id="zipcode" name="addr.zipcode" type="text"
													class="dd_mod_input_txt dd_mod_input_txt_focus"
													id="j_add_input" maxlength="10">
											</div>
										</div>
									</div>
									<div class="order_address_form_action">
										<button class="mod_btn" onclick="submitForm();">
											保存
										</button>
									</div>
									<input type="hidden" name="type" value="shopping_confirm" />
								</div>
							</form>
						</ul>
						<div class="order_address_other">
							<a target="_self" href="<%=request.getContextPath() %>/private/account_addrMgt.Q"
								id="new_address">管理收货地址</a>
						</div>
					</div>
				</div>
				<form action="<%=request.getContextPath() %>/private/shopping_commit.Q" method="post"
					id="shoppForm">
					<input type="hidden" id="sel_addr" name="sel_addr" value="0" />
					<div class="dd_mod_order order_payment">
						<div class="dd_mod_order_hd">
							<h3 class="dd_mod_order_hd_tit">
								支付方式
							</h3>
						</div>
						<div class="dd_mod_order_bd">
							<div class="tab">
								<ul class="tabs">
									<li>
										<a href="javascript:void(0);">支付平台</a>
									</li>
								</ul>
								<!-- / tabs -->
								<div class="tab_content">
									<div class="tabs_item">
										<ul class="list_info">
											<li>
												<label for="netbank_1" class="status_on">
													<input type="radio" name="sel_online_pay" value="19" id="netbank_19" checked>
													<img src="<%=request.getContextPath() %>/images/bank3.png" alt="支付宝">
													<b class="bank_name"></b>
												</label>
											</li>
										</ul>
									</div>
								</div>
								<!-- / tab_content -->
							</div>
						</div>
					</div>
					<div class="dd_mod_order order_cargo"
						style="padding-bottom: 0px; border-bottom: none;">
						<div class="dd_mod_order_hd">
							<h3 class="dd_mod_order_hd_tit">
								送货清单
							</h3>
							<div class="dd_mod_order_hd_extra">
								<a class="back_cart" href="<%=request.getContextPath() %>/private/shopping_list.Q">返回购物车修改</a>
							</div>
						</div>
						<div class="dd_mod_order_bd">
							<table class="goods_tab">
								<tr class="goods_tr4">
									<th class="goods_td1" scope="col">
										商品信息
									</th>
									<th class="goods_td2" scope="col"></th>
									<th class="goods_td3" scope="col">
										单价
									</th>
									<th class="goods_td4" scope="col">
										数量
									</th>
									<th class="goods_td5" scope="col">
										优惠
									</th>
									<th class="goods_td6" scope="col">
										金额
									</th>
								</tr>
								<c:set var="sum" value="0" />
								<c:forEach items="${list }" var="x">
									<tr class="goods_tr3">
										<td>
											<img src="${imgPath }${x.product.prdCode }_small.jpg"
												width="100px" height="100px">
										</td>
										<td class="text_l">
											${x.product.name }
										</td>
										<td class="red">
											￥${x.product.prdPrice }
										</td>
										<td class="">
											${x.count }
										</td>
										<td class="">
											￥${((x.product.price - x.product.disPrice) * x.count) /100}
										</td>
										<td class="">
											${x.product.disPrice * x.count /100 }
										</td>
									</tr>
									<c:set var="sum"
										value="${sum + x.product.disPrice * x.count}" />
								</c:forEach>
							</table>
						</div>
					</div>
					<div class="dd_mod_order order_cashin">
						<div class="dd_mod_order_hd">
							<h3 class="dd_mod_order_hd_tit">
								订单结算
							</h3>
						</div>
						<div class="dd_mod_order_bd">
							<div class="cashin_row">
								<div class="cashin_row_c1">
									<strong>商品合计</strong>
								</div>
								<div class="cashin_row_c2" id="pr_goodsprice" pr="${sum/100 }">
									¥${sum/100 }
								</div>
							</div>
							<div class="cashin_row">
								<div class="cashin_row_c1">
									<strong>运费合计</strong>
								</div>
								<div class="cashin_row_c2" id="pr_goodsprice" pr="0.00">
									¥0.00
								</div>
							</div>
							<div class="cashin_stat" id="summary">
								<div class="cashin_stat_price">
									总计
									<strong class="co_txt1" id="pr_totalprice" pr="${sum/100 }">¥${sum/100
										}</strong>
								</div>
								<div class="cashin_stat_row" style="display: block;">
									<div class="cashin_stat_row_c1" id="addrfinal_box">
										<strong>寄送至：</strong><span></span>
									</div>
								</div>
								<div class="cashin_stat_submit_wrap">
									<a target="_self" class="cashin_stat_submit" href="javascript:void(0);" id="btn_order" title="">提交订单</a>
								</div>
							</div>
						</div>
					</div>
				<input type="hidden" name="hash" value="${hash }" />
			</form>
			</div>
			<jsp:include page="/WEB-INF/jsps/foot.jsp"></jsp:include>

			<script>
$(document).ready(function() {

		$('.tab ul.tabs').addClass('active').find('> li:eq(0)').addClass('current');
		
		$('.tab ul.tabs li a').click(function (g) { 
			var tab = $(this).closest('.tab'), 
				index = $(this).closest('li').index();
			tab.find('ul.tabs > li').removeClass('current');
			$(this).closest('li').addClass('current');
			tab.find('.tab_content').find('div.tabs_item').not('div.tabs_item:eq(' + index + ')').slideUp();
			tab.find('.tab_content').find('div.tabs_item:eq(' + index + ')').slideDown();
			g.preventDefault();
		});
		// 地址选择
		$('.order_address_list li').not('selected').click(function(){
			$('.order_address_list li').removeClass('selected');
	      	$(this).addClass('selected');
	      	buildInfo($(this));
	      	// 获取选择的收货地址
			$('#sel_addr').val($('input[name="sel_addr_rd"]:checked').val());
		});
		
		// 初始化选择
		var select = $('.order_address_list .selected');
      	buildInfo(select);
      	
      	// 提交前检查库存
      	$('.cashin_stat_submit').click(function(){
      		if($('#sel_addr').val() == 0){
      			alert("请选择一个收货地址！");
      			return;
      		}
			var url = '<%=request.getContextPath()%>/private/shopping_checkStock.Q';
			$.getJSON(url, function(obj){
				if(obj.code == 0){
					alert(obj.name +'库存不足,请返回购物车修改后再提交订单！');
				} else {
					$('#shoppForm').submit();
				}
			});
		});
});
// 更新收货人信息
function buildInfo(select){
	if(select.attr('detail') != undefined){
		var info = select.attr('detail') + '&nbsp;&nbsp;' + select.attr('zipcode') + '<br>' + select.attr('username') + '（收件人）' + select.attr('phone');
		$('#addrfinal_box span').html(info);
	} else {
		$('#addrfinal_box span').html('(请选择地址)');
	}
}
function submitForm(){
	$("#detailAddrSpan").html('');
	$("#usernameSpan").html('');
	$("#phoneSpan").html('');
	
	var detailAddr = $("#detailAddr").val();
	if(!detailAddr){
		$('#detailAddrSpan').html(' 详细地址必填');
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
	$("#addform").submit();
}
</script>
	</body>
</html>
