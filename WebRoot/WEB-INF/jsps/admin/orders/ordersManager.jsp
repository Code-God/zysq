<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#orderNo").val("");
		$("#status").val("");
	}
	function changeOrderState(orderNo,state){
		art.dialog({
		    content: '你确定要发货吗？',
		    okVal:'确定',
		    ok: function () {
		    	var url = "<%=request.getContextPath()%>/admin/orders_changeOrderState.Q?orderNo="+orderNo+"&orderState="+state;
				window.location.href = url;
		    },
		    cancelVal: '取消',
		    cancel: true //为true等价于function(){}
		});
		
	}
	function detail(orderNo){
		var url = "<%=request.getContextPath()%>/admin/orders_ordersDetai.Q?orderNo="+orderNo;
		window.location.href = url;
	}
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条订单删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的订单吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/orders_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/orders_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '删除失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
			});
		}
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m5,msub51"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/orders_manager.Q" method="post" id="ordersQueryForm">
            <div class="controls controls-row">
             <label class="span1" for="inputSuccess" style="margin-top:5px">订单编号:</label>
	            <input name="orderNo" id="orderNo" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ orderNo}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">订单状态:</label>
	            <select name="status" id="status" class="span2" style="margin-left:5px;margin-right:35px;width:96px">
	            	 <option value="">请选择</option>
	             	<option value="0" <s:if test="#request.status==0">selected</s:if> >未支付</option>
                  	<option value="1" <s:if test="#request.status==1">selected</s:if>>已支付</option>
                  	<option value="2" <s:if test="#request.status==2">selected</s:if> >已发货</option>
                  	<option value="3" <s:if test="#request.status==3">selected</s:if>>已完成</option>
                  	<option value="3" <s:if test="#request.status==9">selected</s:if>>已取消</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px">下单时间:</label>
	            
	            <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime}" class="wdateinput" />
		                  	&nbsp;至&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="wdateinput" />
	            &nbsp; &nbsp;
	           
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>订单列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="delByIds();">删除</button>
         </div>
         <div class="widget-content nopadding" id="ordersTableId">
            <%@include file="ordersList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
