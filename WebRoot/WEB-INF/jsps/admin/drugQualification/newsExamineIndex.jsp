<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%=Version.getInstance().getSystemTitle()%></title>
<meta charset="UTF-8" />
<meta name="referrer" content="never">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/calendar/default.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/calendar/default.date.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/calendar/default.time.css"/>
<style>
	.datepicker{
		border-radius:5px!important;
	}
</style>
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/calendar/picker.js"></SCRIPT>
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/calendar/picker.date.js"></SCRIPT>
<SCRIPT language=javascript
	src="<%=request.getContextPath()%>/js/calendar/picker.time.js"></SCRIPT>
<SCRIPT language=javascript>
         
		function resetForm(){
			$("#hospital").val("");	
			$("#office").val("");	
			$("#professionalTitle").val("");	
			$("#reviewState").val("");	
			$("#startTransDate").val("");	
			$("#endTransDate").val("");	
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
 			var url = "<%=request.getContextPath()%>/admin/qualification_getNewsExamine.Q";
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
						<form action="<%=request.getContextPath()%>/admin/qualification_imageWordsManager.Q"
							method="post" id="qualificationQueryForm">
							<div class="controls controls-row">
								<table style="">
								<tr>
									<td>开始日期:</td>
									<td><input type="text" value="${startTransDate}" name="startTransDate" id="startTransDate" class="span2 datepicker"/></td>
                                    <td>结束日期:</td>
									<td><input type="text" value="${endTransDate}" name="endTransDate" id="endTransDate" class="span2 datepicker"/></td>
								</tr>
								<tr>
								    <td>
										发送方名称:
									</td>
									<td>
										<input name="hospital" id="hospital" type="text"
											class="span2" style="margin-left:5px;margin-right:35px;border-radius:5px"
											value="${hospital}">
									</td>
									<td>	
										审核状态:
									</td>
									<td>	
										<select name="reviewState" id="reviewState" style="width:170px;border-radius:5px">
											<option value="100"  selected="selected" >请选择</option>
											<option value="0" <s:if test="#reviewState == 0">selected</s:if>>未审核</option>
											<option value="1" <s:if test="#reviewState == 1">selected</s:if>>审核通过</option>
											<option value="2" <s:if test="#reviewState == 2">selected</s:if>>审核不通过</option>
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
	          					  <%@include file="newsExamineList.jsp"%>
	     				 </div>
					</div>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
		<script>
	
		$('.datepicker').pickadate({
			  weekdaysShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			  monthsFull: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			  format: 'yyyy-mm-dd 00:00'
			})
		</script>
</body>
</html>
