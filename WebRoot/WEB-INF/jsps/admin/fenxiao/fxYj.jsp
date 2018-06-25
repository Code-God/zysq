<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title><%=Version.getInstance().getSystemTitle()%></title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<script type="text/javascript">
		//分销客提现
		function cashApply(){
			art.dialog.open(CONFIG.context + '/weixin/cashApply.jsp', {
			    title: '请输入提现金额（单位：元），只能是10的整数倍。',
				// 在open()方法中，init会等待iframe加载完毕后执行
				init: function () {
			    	 
			    },
			    ok: function () {
			    	var iframe = this.iframe.contentWindow;
			    	//用户输入的提现费用
			    	var fee = iframe.document.getElementById('applyFee').value;
			    	if(fee%10 != 0){
			    		art.dialog.tips("请输入10的整数倍。", 2);
			    		return false;
			    	}
			    	if(parseInt(fee) > parseInt($("#yj1").html())){
			    		art.dialog.tips("余额不足", 2);
			    		return false;
			    	}
			    	//提交提现申请
			    	$.ajax({
						type : "POST",
						url : CONFIG.context + "/admin/fx_submitCashApply.Q",
						data : "applyFee="+fee,
						dataType : "json",
						success : function(data) {
							if(data.result == 'nofee'){// 
								art.dialog("余额不足！");
							}else if(data.result == 'ok'){
								art.dialog("申请已经提交，请等待处理！");
								window.location.reload();
							}else{
								art.dialog("系统出了点问题，请稍后重试！");
							}
						}
					});
			       	return true;
			    },
			    cancel: true
			});
		}
		</script>
	</head>
	<body >
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m2,msub24" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid span6">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i> </span>
							<h5>
								我的佣金
							</h5>
							</div>
				          <div class="widget-content ">
				             <div class="todo">
				              <ul>
				                <li class="clearfix">
				                  <div class="txt"> 分销订单数：  <span class="date badge badge-important" >${yj.orderSize } </span> 个  </div>
				                  <div class="pull-right"> 
				                  <a class="btn btn-info"   href="<%=request.getContextPath()%>/admin/orders_manager.Q">查看详情</a>
				                  </div>
				                </li>
				                <li class="clearfix">
				                  <div class="txt"> 佣金比例： <span class="date badge badge-info">${yj.commission } %</span> </div>
				                   <div class="pull-right"> 
				                  </div>
				                </li>
				                <li class="clearfix">
				                  <div class="txt"> 累计佣金： <span class="date badge badge-warning">${yj.yjfee/100 }</span> 元</div>
				                   <div class="pull-right"> 
				                  </div>
				                </li>
				                <li class="clearfix">
				                  <div class="txt"> 可提佣金： <span class="date badge badge-success">${yj.avfee/100 }</span> 元</div>
				                   <div class="pull-right"> 
				                   	<button onclick="cashApply()" class="btn btn-danger">我要提现</button>
				                  </div>
				                </li>
				                 
				              </ul>
            				</div>
				        </div>
				</div>
			</div>

			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
