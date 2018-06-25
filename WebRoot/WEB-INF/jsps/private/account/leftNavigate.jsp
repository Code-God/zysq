<%@ page language="java" pageEncoding="UTF-8"%>
	<!--左侧开始-->
	<div class="order_l">
      <dl class="order_dl">
      	<dt class="order_dt">会员中心</dt>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_userInfo.Q">个人资料</a>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_addrMgt.Q">收货地址</a>
         <!-- <a class="order_a">积分管理</a>-->
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_toMdfPassWord.Q">密码管理</a>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_couponMgt.Q">优惠券</a>
        <dt class="order_dt">交易管理</dt>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_ordersMgt.Q">我的订单</a>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_commentsMgt.Q">商品评价</a>
        <a class="order_a" href="<%=request.getContextPath()%>/private/account_favoriteMgt.Q">我的收藏</a>
        <!--<a class="order_a" href="<%=request.getContextPath()%>/private/account_returnProductsMgt.Q">退换货服务</a>
      --></dl>
    </div>
    <!--左侧结束-->
