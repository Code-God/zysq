<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
   
   //单独更新某个参数
   function doUpdateParam(key){
   		var val = $("#" + key).val();
   		//<%=request.getContextPath()%>/system_updateParam.Q
   		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/system_updateParam.Q",
			data : "key=" + key + "&cvalue=" + val,
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog.tips("更新成功！");
				}else{
					art.dialog.tips("更新失败！");
				}
			}
		});
   }
   
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub90"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>系统参数设置</h5>
        </div>
        <div class="widget-content nopadding">
           		<table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th width="15%">参数</th>
	                  <th width="70%">值</th>
	                  <th>操作</th>
	                </tr>
	              </thead>
	              <tbody>
		              <c:forEach items="${list}" var="cp">
		                <tr>
		                  <td><span class="label label-success">${cp.cdesc }</span></td>
		                  <td>
		                  	<input type="text" value="${cp.cvalue}" name="${cp.ckey}" id="${cp.ckey}" class="span12">
		                  </td>
		                  <td><button class="btn btn-info" type="button" onclick="doUpdateParam('${cp.ckey}')">更新&nbsp;</button></td>
		                </tr>
	                 </c:forEach>
	              </tbody>
	            </table>
          <c:if test="${success==1}">
          <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="javascript:void(0);">×</a>
              <h4 class="alert-heading">保存成功!</h4><br>
          </div>
          </c:if>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
