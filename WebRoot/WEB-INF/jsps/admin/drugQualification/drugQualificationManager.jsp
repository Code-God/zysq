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
			$("#hospital").val("");	
			$("#office").val("");	
			$("#professionalTitle").val("");	
			$("#reviewState").val("");	
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
		function updateQualification(id){			
 			var url = "<%=request.getContextPath()%>/admin/qualification_getDrugQualification.Q";
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
						<form action="<%=request.getContextPath()%>/admin/qualification_qualManager.Q"
							method="post" id="qualificationQueryForm">
							<div class="controls controls-row">
								<table style="width: 100%">
								<tr>
									<td>
										医院:
									</td>
									<td>
										<input name="hospital" id="hospital" type="text"
											class="span2" style="margin-left:5px;margin-right:35px"
											value="${hospital}">
									</td>
									<td>	
										科室:
									</td>
									<td>
										<input name="office" id="office" type="text"
											class="span2" style="margin-left:5px;margin-right:35px"
											value="${office}">
									</td>		
								</tr>
								<tr>
									<td>
										职称:
									</td>
									<td>	
										<select name="professionalTitle" id="professionalTitle" style="width:170px" onchange="test()">
											<option value="100" selected="selected">请选择</option>
											<option value="临床医师" <s:if test="#professionalTitle == '临床医师'">selected</s:if>>临床医师</option>
											<option value="护理证书" <s:if test="#professionalTitle == '护理证书'">selected</s:if>>护理证书</option>
										</select>								
									</td>	
									<td>	
										审核状态:
									</td>
									<td>	
										<select name="reviewState" id="reviewState" style="width:170px">
											<option value="100"  selected="selected" >请选择</option>
											<option value="0" <s:if test="#reviewState == '0'">selected</s:if>>未审核</option>
											<option value="1" <s:if test="#reviewState == '1'">selected</s:if>>审核通过</option>
											<option value="2" <s:if test="#reviewState == '2'">selected</s:if>>审核不通过</option>
										</select>	
									</td>								
								</tr>
								<tr>	
									<td colspan="4">							
										<button type="submit" class="btn btn-success">查询</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-primary"
											onclick="resetForm();">重置</button>
									</td>		
								</tr>		
							</table>		
							</div>
						</form>
					</div>
					<br />
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>认证列表</h5>					
								
<%-- 	           		<s:if test='#request.pop != "1"'>
			            <button class="label label-info btn btn-primary btn-mini" onclick="addItem();">新增</button>
			            <button class="label label-info btn btn-primary btn-mini" onclick="delItem();">删除</button>
	        	 	</s:if>		 --%>					
					</div>
						 <div class="widget-content nopadding" id="qualificationTableId">
	          					  <%@include file="drugQualificationList.jsp"%>
	     				 </div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
