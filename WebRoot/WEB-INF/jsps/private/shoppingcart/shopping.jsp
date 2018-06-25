<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title><%=Version.getInstance().getSystemTitle() %>-购物车</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cart.css">
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/jsps/header.jsp"%>
	<div class="wf_header">
		<div class="grid_c1">
			<div class="mod_logo">
				<h1>
					<a href="<%=request.getContextPath() %>/public/index.Q"><img src="<%=request.getContextPath() %>/images/logo.jpg" alt="商城"> </a>
				</h1>
			</div>
			<div class="flow_step_no1 flow_cart">
				<div class="flow_step">
					<ol class="cols3">
						<li class="step_1">
							1.我的购物车
						</li>
						<li class="step_2">
							2.填写核对订单
						</li>
						<li class="step_3">
							3.订单提交成功
						</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${!empty list}">
	<div class="cart_content" id="cart_content">
		<div class="grid_c1">
			<div class="goods_list" id="goods_list">
				<div class="goods_list_inner">
					<div class="shop_mod_hd">
						<h3 class="shop_tit">
							商城
						</h3>
					</div>
					<div class="shop_mod_bd">
						<table id="shopping" class="goods_tab">
							<tr class="goods_tr4">
								<th class="goods_td1" scope="col">商品信息</th>
				                <th class="goods_td2" scope="col"></th>
				                <th class="goods_td3" scope="col">单价</th>
				                <th class="goods_td4" scope="col">数量</th>
				                <th class="goods_td5" scope="col">优惠</th>
				                <th class="goods_td5" scope="col">金额</th>
				                <th class="goods_td6" scope="col">操作</th>
							</tr>
							<c:forEach items="${list }" var="x">
								<tr class="goods_tr3 product" prdCode="${x.prdCode }" stock="${x.product.stock }">
									<td>
										<a href="<%=request.getContextPath() %>/public/details.Q?id=${x.product.id }">
										<img src="${imgPath }${x.product.prdCode }_small.jpg" width="100px" height="100px" >
										</a>
									</td>
									<td class="text_l">
										<a href="<%=request.getContextPath() %>/public/details.Q?id=${x.product.id }">${x.product.name }</a>
									</td>
									<td class="">
										￥<span id="price_${x.prdCode }">${x.product.prdPrice }</span>
										<input type="hidden" id="disprice_${x.prdCode }" value="${x.product.prdDisPrice }">
									</td>
									<td class="">
										<div class="pAmount">
											<span>
												<input type="text" id="num_${x.prdCode }" class="text" value="${x.count }" autocomplete="off" readonly/>
											</span>
											<span>
												<a href="javascript:;" id="add_${x.prdCode }" class="p-add">+</a>
												<a href="javascript:;" id="reduce_${x.prdCode }" class="p-reduce disable">-</a>
											</span>
										</div>
									</td>
									<td class="red">
										￥<span class="disSum" id="disSum_${x.prdCode }">${x.disMoney }</span>
									</td>
									<td class="">
										￥<span class="sum" id="sum_${x.prdCode }">${x.sumMoney }</span>
									</td>
									<td class="">
										<span class="clearb yunfei add_favorite">
										<c:if test="${x.product.favourite == false}"><a class="safe_a" onclick="addToFavourite(${x.id }, '${x.product.prdCode }', this);" href="javascript:void(0);">暂存收藏夹</a></c:if>
										<c:if test="${x.product.favourite == true}"><a class="safe_a" href="javascript:void(0);">已收藏</a></c:if>
										<div class="pwindow">
					                        <div class="cm">
					                          <div class="hd"><span class="getit">收藏成功</span><span class="showOther"><a href="<%=request.getContextPath() %>/private/account_favoriteMgt.Q">查看收藏夹</a></span></div>
					                          <div class="pclose" onclick="closeFav(this);"></div>
					                        </div>
					                      </div>
										</span><span class="clearb yunfei"><a class="safe_a" href="<%=request.getContextPath() %>/private/shopping_delete.Q?prdCode=${x.prdCode }">删除</a>
										</span>
									</td>
								</tr>
								</c:forEach>
						</table>
					</div>
				</div>
			</div>
			<div class="cart_foot clearfix" id="cart_foot">
				<div class="cart_foot_l">
					<a href="<%=request.getContextPath() %>/public/shoping_deleteAll.Q"><i></i>清空购物车</a>
				</div>
				<div class="cart_foot_r">
					<div class="cart_foot_li">
						<div class="cart_foot_lable">
							商品总价：
						</div>
						<div class="cart_foot_cnt">
							<span class="mod_price"><i>¥</i><span id="totalPrice">0</span>
							</span>
						</div>
					</div>
					<div class="cart_foot_li">
						<div class="cart_foot_lable">
							促销优惠：
						</div>
						<div class="cart_foot_cnt">
							-
							<span class="mod_price red"><i>¥</i><span id="totalDisPrice">0</span>
							</span>
						</div>
					</div>
					<div class="cart_foot_price">
						<span class="c_tx3">商品总价（不含运费）：</span>
						<strong class="final_price mod_price c_tx1" id="totalPay_tips"><i>¥</i><span
							id="totalPay">0</span> </strong>
					</div>
					<div class="clearfix">
						<a id="confirmBtn" class="ct_clearing_btn" href="<%=request.getContextPath() %>/private/shopping_confirm.Q">去结算</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
	<c:if test="${empty list}">
	<div class="cart_content" id="cart_content_empty">
	  <div class="grid_c1">
	    <div class="goods_empty" id="guangguang_mod">
	      <div class="goods_empty_inner">
	        <h3>您的购物袋还是空的，赶紧挑选商品吧！</h3>
	        <p><a class="btn_1" href="<%=request.getContextPath() %>/public/index.Q">挑选商品</a></p>
	      </div>
	    </div>
	  </div>
	</div>
	</c:if>
	<jsp:include page="/WEB-INF/jsps/foot.jsp"></jsp:include>
</body>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/artDialog4.1.7/skins/default.css" />
<script src="<%=request.getContextPath() %>/js/public/shopping.js"></script>
<script src="<%=request.getContextPath() %>/js/artDialog4.1.7/artDialog.js"></script>
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
		} );
		
		// 初始化总计
		total();
		
		// 购物车初始事件绑定
		$('#shopping .product').each(function (i, o) {
			initBind(o);
		});
});

function closeFav(obj){
	$(obj).parents("div .pwindow").hide();
	window.location.reload();
}

function addToFavourite(cid, prdCode, obj){
	$.ajax({
		url : '<%=request.getContextPath()%>/private/shopping_addFav.Q',
		type : 'get',
		data : {'cid':cid,'prdCode':prdCode},
		dataType : 'json',
		success : function(data){
			if(data && data.result == "success"){
				alert("已移至收藏夹");
				window.location.reload();
				//$(obj).next().show();
				//art.dialog({
				    //content: '收藏成功',
				    //cancelVal:'关闭',
				    //lock:true,
				    //cancel: function(){window.location.reload();}
				//});
				
			}
		},
		error : function(data){
		
		}
	});
	
}
</script>
</html>
