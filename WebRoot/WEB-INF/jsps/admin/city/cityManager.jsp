<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script type="text/javascript">
	function resetForm(){
		$("#name").val("");
		$("#support").val("");
	}
	
	function setSupport(id, support){
		
		var message = "确认取消对该城市的支持？";
		if(support){
			message = "确定设置对该城市的支持？";
		}
		if(confirm(message)){
			$.ajax({
				url:'<%=request.getContextPath()%>/admin/city_setSupport.Q',
				type:'get',
				data:'support='+support+'&id='+id,
				dataType:'json',
				success:function(data){
					if(data.result=='success'){
						alert("设置成功");
						queryForm();
					}else{
						alert("系统异常");
					}
				},
				error:function(data){
					alert("系统异常");
					queryForm();
				}
			});
		}
	}
	function setHot(id, hot){
		var message = "确认取消该热门城市？";
		if(hot){
			message = "确定设置该城市为热门城市？";
		}
		if(confirm(message)){
			$.ajax({
				url:'<%=request.getContextPath()%>/admin/city_setHot.Q',
				type:'get',
				data:'hot='+hot+'&id='+id,
				dataType:'json',
				success:function(data){
					if(data.result=='success'){
						alert("设置成功");
						queryForm();
					}else{
						alert("系统异常");
					}
				},
				error:function(data){
					alert("系统异常");
					queryForm();
				}
			});
		}
	}
	
	function queryForm(){
		$('form').submit();
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m8"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/city_index.Q" method="post" id="cityQueryForm">
            <div class="controls controls-row">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">城市名称:</label>
	            <input name="name" id="name" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ name}">
	             <label class="span1" for="inputSuccess" style="margin-top:5px">配送支持:</label>
	             <select name="support" id="support" style="margin-left:5px;margin-right:35px;width:156px">
	            	 <option value="">请选择</option>
	             	<option value="true" <s:if test="#request.support==true">selected</s:if> >支持</option>
                  	<option value="false" <s:if test="#request.support==false">selected</s:if>>不支持</option>
	             </select>
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>城市列表</h5>
         </div>
         <div class="widget-content nopadding" id="cityTableId">
            <%@include file="cityList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
