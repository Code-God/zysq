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

<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="${ctx}/js/common/city.js"></script>
<style type="text/css">
.select2-container .select2-choice {
margin-left: 5px;
}
</style>

<script type="text/javascript">
	$(function(){
		//初始化下拉框选项
		var sex = '${sex}';
		if (sex == '') {
			$("#sex").select2("val", ""); 
		}else{
			$("#sex").select2("val", sex); 
		}
		var userType = '${userType}';
		if (userType == '') {
			$("#userType").select2("val", ""); 
		} else {
			$("#userType").select2("val", userType);
			if (userType == '3') {
				changeUserType();
			}
		}
		var auditStatus = '${auditStatus}';
		if (auditStatus == '') {
			$("#auditStatus").select2("val", ""); 
		}else{
			$("#auditStatus").select2("val", auditStatus); 
		}
		

		// 初始化行政区划
	    var cs = [];
	    cs.push('<option value="" selected>请选择</option>');
	    $.each(cities, function(idx){
	    	cs.push('<option value="'+ idx +'">'+ idx +'</option>');
	    });
	    $("#province").empty();
	    $("#province").append(cs.join(""));
	    
        var province = '${province}';
		var city = '${city}';
		if (province == '') {
			$("#province").select2("val", ""); 
		}else{
			$("#province").select2("val", province); 
			
			var c2 = [];
 		    c2.push('<option value="" selected>请选择</option>');
        	var cs2 = cities[province];
 		    $.each(cs2, function(idx){
 		    	c2.push('<option value="'+ cs2[idx] +'">'+ cs2[idx] +'</option>');
 		    });
        	$("#city").empty();
		    $("#city").append(c2.join(""));
		}
		
		if (city == '') {
			$("#city").select2("val", ""); 
		}else{
			$("#city").select2("val", city); 
		}

	    // 弹窗关闭事件
	    $(".close_box").click(function(){
	        $("#mask").hide();
	        $(".box").hide();
			$(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
	    });
	    
	    // 行政区划选择事件，级联改变所在城市
	    $("#province").change(function(){
	    	var val = $(this).val(); 
        	var c2 = [];
 		    c2.push('<option value="" selected>请选择</option>');
        	$("#city").empty();
	        if (val != '') {
	        	var cs2 = cities[val];
	 		    $.each(cs2, function(idx){
	 		    	c2.push('<option value="'+ cs2[idx] +'">'+ cs2[idx] +'</option>');
	 		    });
	 		    
			    $("#city").append(c2.join(""));
	        }
	        $("#city").select2("val", "");
	    });
		
	});
	
	function selectCity() {
		var url = "<%=request.getContextPath()%>/admin/city_selectCityTree.Q?_d="+ (new Date().getTime());
		art.dialog.data("fieldId", "city");
		art.dialog.open(url, {
		    title: '选择所在城市',
		    width : 430,
		    height: 540,
		    init: function () {
		    },
		    ok: false,
		    cancel: false
		});
	}
	
	function resetForm(){
		$("#realName").val("");
		$("#ageStart").val("");
		$("#ageEnd").val("");
		$("#mobilePhone").val("");
		$("#city").val("");
		$("#_city_").val("");
		$("#address").val("");
		$("select").select2("val", "");
		changeUserType();
	}
	function delAdmin(){
		var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/user_deleteByIds.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/user_manager.Q";
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
	function disableAccount(){
		var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户禁用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			 art.dialog({
			    content: '你确定要禁用选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/user_disableAccount.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/user_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '禁用失败！'
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
		function enableAccount(){
			var selectCheckbox=$("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一名用户启用',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var adminIds = "";
			selectCheckbox.each(function(i){
			   adminIds+=$(this).val()+",";
			 });
			 if(adminIds){
			 	adminIds = adminIds.substring(0,adminIds.length-1);
			 }
			art.dialog({
			    content: '你确定要启用选中的用户吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/user_enableAccount.Q",
						data:{"ids":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								var url = "<%=request.getContextPath()%>/admin/user_manager.Q";
								window.location.href=url;
							}else{
								art.dialog({
									time: 2,
									content: '启用失败！'
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
		
	function changeUserType() {
		var userType = $("#userType").val();
		if (userType == 3) {
			$("#auditStatusLabelTd").show();
			$("#auditStatusTd").show();
		}else{
			$("#auditStatusLabelTd").hide();
			$("#auditStatusTd").hide();
		}
		$("#auditStatus").select2("val", "");
	}
	
	function updateStatus(){
		var selectCheckbox = $("input[type=checkbox][name=adminIds]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条用户记录',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var userIds = [];
			selectCheckbox.each(function(i){
			   userIds.push($(this).val());
			});
			var url = "<%=request.getContextPath()%>/admin/user_setAuditStatus.Q?userIds="+ userIds.join(",") +"&_d="+ (new Date().getTime());
			art.dialog.data("userIds", userIds.join(","));
			art.dialog.open(url, {
				id:'setAuditArtDialogId',
			    title: '设置选中用户的审核状态',
			    width : 400,
			    height: 300,
			    init: function () {
			    },
			    ok: false,
			    cancel: false
			});
		}
	}
	//用户兑换积分
	function getUser(id){
		window.location.href="<%=request.getContextPath()%>/admin/user_getUserPoints.Q?userid="+id;
	}
	//用户获取积分详细
	function getpointsDetail(openid){
		window.location.href="<%=request.getContextPath()%>/admin/user_pointsObtainManager.Q?openid="+openid;
	}
	
	
	
	
	
	
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m6"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/user_manager.Q" method="post" id="userQueryForm">
          	<table width="100%">
          		<tr>
          			<td width="10%" align="right">真实姓名:</td>
          			<td width="23%" align="left">
          				<input name="realName" id="realName" type="text" class="span2" style="margin-left:5px;margin-right:35px;width:152px" value="${ realName}">
          			</td>
          			<td width="10%" align="right">性别:</td>
          			<td width="23%" align="left">
          				 <select name="sex" id="sex" style="margin-left:5px;margin-right:35px;width:170px">
			            	<option value="" selected>请选择</option>
			             	<option value="1" <s:if test="#request.sex==1">selected</s:if>>男</option>
		                  	<option value="2" <s:if test="#request.sex==2">selected</s:if>>女</option>
			             </select>
          			</td>
          			<td width="10%" align="right">年龄:</td>
          			<td width="23%" align="left">
          				<input name="ageStart" id="ageStart" type="text" class="span2" style="width:59px;margin-left:5px;" value="${ageStart}">
		          		  至
		             	<input name="ageEnd" id="ageEnd" type="text" class="span2" style="width:59px;" value="${ageEnd}">
          			</td>
          		</tr>
          		<tr>
          			<td width="10%" align="right">联系电话:</td>
          			<td width="23%" align="left">
          				<input name="mobilePhone" id="mobilePhone" type="text" class="span2" style="margin-left:5px;margin-right:35px;width:152px" value="${mobilePhone}">
          			</td>
          			<td width="10%" align="right">所在城市:</td>
          			<td width="23%" align="left">
          				<select class="in_text"  name="province" id="province" style="margin-left:5px;margin-right:35px;width:85px">
                        </select>
                        <select class="in_text" name="city" id="city" style="margin-right:35px;width:85px">
                        </select>
          				<%-- <input name="city" id="city" type="hidden" class="span2" value="${city}">
          				<input name="_city_" id="_city_" type="text" class="span2" style="margin-left:5px;margin-right:35px;width:152px" value="${_city_}" onclick="selectCity();"> --%>
          			</td>
          			<td width="10%" align="right">通讯地址:</td>
          			<td width="23%" align="left">
          				<input name="address" id="address" type="text" class="span2" style="margin-left:5px;margin-right:35px;width:152px" value="${address}">
          			</td>
          		</tr>
          		<tr>
          			<td width="10%" align="right">用户类型:</td>
          			<td width="23%" align="left">
          				 <select name="userType" id="userType" style="margin-left:5px;margin-right:35px;width:170px" onchange="changeUserType();">
			            	<option value=""  <s:if test="#request.userType==''">selected</s:if>>请选择</option>
		                  	<option value="1" <s:if test="#request.userType==1">selected</s:if>>医生</option>
		                  	<option value="2" <s:if test="#request.userType==2">selected</s:if>>护士</option><!-- 
			             	<option value="3" <s:if test="#request.userType==3">selected</s:if>>患者</option> -->
		                  	<option value="9" <s:if test="#request.userType==9">selected</s:if>>其他</option>
			             </select>
          			</td>
          			<td width="10%" align="right" id="auditStatusLabelTd" style="display:none;">患者状态:</td>
          			<td width="23%" align="left" id="auditStatusTd" style="display:none;">
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
          			<td colspan="2" align="center">
          				<div class="controls controls-row">
				            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
				            <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
				        </div>
          			</td>
          		</tr>
          		
          	</table>
           
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>用户列表</h5>
            
            <button class="label label-info btn btn-primary btn-mini" onclick="delAdmin();">删除</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="disableAccount();">禁用</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="enableAccount();">启用</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="updateStatus();">修改审核状态</button>
         <!--   <button class="label label-info btn btn-primary btn-mini">导出</button> -->
         </div>
         <div class="widget-content nopadding" id="userTableId">
            <%@include file="userList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
