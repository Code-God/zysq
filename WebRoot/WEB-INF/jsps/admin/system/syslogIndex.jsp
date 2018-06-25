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
<script src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="<%=request.getContextPath() %>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#operater").val("");
		$("#module").val("");
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub94"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath() %>/admin/syslog_index.Q" method="post" id="syslogForm">
            <div class="controls controls-row">
             <label class="span1" for="inputSuccess" style="margin-top:5px">操作人:</label>
	            <input name="operater" id="operater" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ operater}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">模块:</label>
	            <select name="module" id="module" class="span2" style="margin-left:5px;margin-right:35px;width:96px">
	            	 <option value="">请选择</option>
	             	<option value="商品管理" <s:if test="#request.module=='商品管理'">selected</s:if> >商品管理</option>
                  	<option value="分类管理" <s:if test="#request.module=='分类管理'">selected</s:if>>分类管理</option>
                  	<option value="特惠新品" <s:if test="#request.module=='特惠新品'">selected</s:if> >特惠新品</option>
                  	<option value="广告维护" <s:if test="#request.module=='广告维护'">selected</s:if>>广告维护</option>
                  	<option value="评价管理" <s:if test="#request.module=='评价管理'">selected</s:if>>评价管理</option>
                  	<option value="订单管理" <s:if test="#request.module=='订单管理'">selected</s:if>>订单管理</option>
                  	<option value="会员管理" <s:if test="#request.module=='会员管理'">selected</s:if>>会员管理</option>
                  	<option value="权限管理" <s:if test="#request.module=='权限管理'">selected</s:if>>权限管理</option>
                  	<option value="区域管理" <s:if test="#request.module=='区域管理'">selected</s:if>>区域管理</option>
                  	<option value="后台登录" <s:if test="#request.module=='后台登录'">selected</s:if>>后台登录</option>
                  	<option value="系统管理" <s:if test="#request.module=='系统管理'">selected</s:if>>系统管理</option>
	             </select>
	            <label class="span1" for="inputSuccess" style="margin-top:5px">操作时间:</label>
	            
	            <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${startTime}" class="wdateinput" readonly />
		                  	&nbsp;至&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="wdateinput" readonly/>
	            &nbsp; &nbsp;
	           
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>系统日志列表</h5>
         </div>
         <div class="widget-content nopadding" id="syslogTableId">
            <%@include file="syslogList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
