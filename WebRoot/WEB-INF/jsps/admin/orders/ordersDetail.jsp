<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function toProductsList(){
		window.location.href = "<%=request.getContextPath()%>/admin/orders_manager.Q";
	}
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m5"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>订单详情</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
            <div class="control-group">
              <label class="control-label">订单编号 :</label>
              <div class="controls">
                <s:property value="orders.orderNo"/>
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">交易号 :</label>
              <div class="controls">
                <s:property value="orders.transactionId"/> 
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">支付用户 :</label>
              <div class="controls">
                 <s:property value="orders.user.nickName"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">支付金额 :</label>
              <div class="controls" >
                <font color=red> <s:property value="orders.fee"/></font> 元
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">下单时间 :</label>
              <div class="controls">
              <s:date name="orders.odate" format="yyyy-MM-dd hh:mm:ss" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">支付时间 :</label>
              <div class="controls">
               <s:date name="orders.chargeDate" format="yyyy-MM-dd hh:mm:ss" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">订单状态 :</label>
              <div class="controls">
              <s:if test="orders.status==0">
              	未支付
              </s:if>
              <s:elseif test="orders.status==1">
              已支付
              </s:elseif>
               <s:elseif test="orders.status==2">
              已发货
              </s:elseif>
               <s:elseif test="orders.status==3">
              已完成
              </s:elseif>
               <s:elseif test="orders.status==9">
              已取消
              </s:elseif>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">运费 :</label>
              <div class="controls">
               <s:property value="orders.transFee"/> 
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">收货人姓名 :</label>
              <div class="controls">
               <s:property value="orders.address"/> 
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">收货地址 :</label>
              <div class="controls">
               <s:property value="orders.addressee"/> 
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">收货人电话 :</label>
              <div class="controls">
               <s:property value="orders.phone"/> 
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">订单详情 :</label>
              <div class="controls">
               <s:iterator value="#request.ods" var="od">
               	<a href='<%=request.getContextPath()%>/admin/products_detail.Q?prdCode=<s:property value="prdCode"/>' target="_blank">
               	<img src='<%=request.getContextPath()%>/private/UploadImages/<s:property value="prdImage"/>' width="64" height="56"/></a><br/>
               	<s:property value="prodName"/>
               	(<s:property value="prdCount"/>)&nbsp;&nbsp;
               </s:iterator>
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
