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
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
<SCRIPT language=javascript>
         
		function resetForm(){
			$("#classificationName").val("");	
		}
		function addItem(){
			window.location.href="<%=request.getContextPath()%>	/admin/itemClass_inputItemClass.Q";
		}
		function delItem(){
			var selectCheckbox=$("input[type=checkbox][name=itemclassid]:checked");
			if(selectCheckbox.length<1){
				art.dialog({
				    content: '请至少选择一条项目类型信息删除',
				    okVal:'确定',
				    ok: true
				});
			}else{
				var itemIds = "";
				selectCheckbox.each(function(i){
				   itemIds+=$(this).val()+",";
				 });
				 if(itemIds){
				 	itemIds = itemIds.substring(0,itemIds.length-1);
				 }	
				art.dialog({
				    content: '你确定要删除选中的项目类型信息吗？',
				    okVal:'确定',
				    ok: function () {
				    	$.ajax({
							url:"<%=request.getContextPath()%>/admin/itemClass_delDiseaseItem.Q",
							data:{"itemIds":itemIds},
							dataType:'text',
							success:function(data){
								if("ok"==data){
									window.location.href="<%=request.getContextPath()%>/admin/itemClass_itemClassManager.Q";
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
		function updateItemClass(id){			
 			var url = "<%=request.getContextPath()%>/admin/itemClass_inputItemClass.Q";
 			if(id){
 				url+="?id="+id;
 			}
 			window.location.href = url;
		}
</SCRIPT>
</head>
<body onload="">
	<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
	<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
	<input type="hidden" id="tab" value="m3,msub31" />
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="<%=request.getContextPath()%>/admin/admin_index.Q"
					title="Go to Home" class="tip-bottom"><i class="icon-home"></i>
					Home</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="widget-box">
				<div class="widget-box">
					<div class="widget-content">
						<form action="<%=request.getContextPath()%>/admin/itemClass_itemClassManager.Q"
							method="post" id="itemClassQueryForm">
							<div class="controls controls-row">
								<label class="span1" for="inputSuccess" style="margin-top:5px">项目名称:</label>
								<input name="classificationName" id="classificationName" type="text"
									class="span2" style="margin-left:5px;margin-right:35px"
									value="${classificationName}">

								<button type="submit" class="btn btn-success">查询</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-primary"
									onclick="resetForm();">重置</button>
							</div>
						</form>
					</div>
					<br />
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>项目类型列表</h5>					
								
	           		<s:if test='#request.pop != "1"'>
			            <button class="label label-info btn btn-primary btn-mini" onclick="addItem();">新增</button>
			            <button class="label label-info btn btn-primary btn-mini" onclick="delItem();">删除</button>
	        	 	</s:if>							
					</div>
						 <div class="widget-content nopadding" id="itemClassTableId">
	          					  <%@include file="drugItemClassificationList.jsp"%>
	     				 </div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
