<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script> 
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/artDialog.js?skin=opera"></script> 
<style type="text/css">
.select2-container .select2-choice {
margin-left: 5px;
}
</style>
<script type="text/javascript">
	function saveAudit() {
		var userIds = $("#userIds").val();
		var auditStatus = $("#auditStatus").val();
		if (auditStatus == '') {
			art.dialog("请选择审核状态！");
			return;
		}
		$.ajax({
			url:"<%=request.getContextPath()%>/admin/user_saveAuditStatus.Q",
			data:{
				"userIds":userIds,
				"auditStatus": auditStatus
			},
			dataType:'text',
			success:function(data){
				if("ok" == data){
					art.dialog({
					    content: '保存成功！',
					    okVal:'确定',
					    ok: function () {
							//window.parent.location.reload(true);
					    	closeWin();
					    },
					    cancel: false
					});
				}else{
					art.dialog("保存失败！");
				}
		    }
		});
	}
	
	/**
	 * 关闭
	 */
	function closeWin(){
		top.art.dialog({id:'setAuditArtDialogId'}).close();
	}
</script>
</head>
<body>
	<input type="hidden" id="userIds" value="${userIds }">
   <div class="widget-content">
     	<table width="100%">
     		<tr>
     			<td width="20%" align="right" style="height:100px;font-size:12px">审核状态:</td>
     			<td align="left" id="auditStatusTd">
     			<select name="auditStatus" id="auditStatus" style="margin-left:15px;margin-right:35px;width:170px">
	          		<option value="" <s:if test="#request.auditStatus==''">selected</s:if>>请选择</option>
	               	<option value="0" <s:if test="#request.auditStatus==0">selected</s:if>>待审核</option>
	               	<option value="11" <s:if test="#request.auditStatus==11">selected</s:if>>病史审核通过</option>
	               	<option value="12" <s:if test="#request.auditStatus==12">selected</s:if>>病史审核未通过</option>
	               	<option value="21" <s:if test="#request.auditStatus==21">selected</s:if>>联系中通过</option>
	               	<option value="22" <s:if test="#request.auditStatus==22">selected</s:if>>联系中未通过</option>
		           	<option value="31" <s:if test="#request.auditStatus==31">selected</s:if>>医院筛选通过</option>
		           	<option value="32" <s:if test="#request.auditStatus==32">selected</s:if>>医院筛选未通过</option>
	               	<option value="41" <s:if test="#request.auditStatus==41">selected</s:if>>审核通过</option>
	               	<option value="42" <s:if test="#request.auditStatus==42">selected</s:if>>审核未通过</option>
	           </select>
     			</td>
     		</tr>
     		<tr>
     			<td colspan="2" align="center" valign="middle" style="height:60px;">
     				<div class="controls controls-row">
			           <button type="submit" class="btn btn-success" onclick="saveAudit();">确 定</button>&nbsp;&nbsp;&nbsp;
			           <button type="button" class="btn btn-primary" onclick="closeWin();">取 消</button>
			       </div>
     			</td>
     		</tr>
     		
     	</table>
   </div>

</body>
</html>
