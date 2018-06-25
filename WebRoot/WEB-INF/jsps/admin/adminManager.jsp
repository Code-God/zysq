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

<script type="text/javascript">
	function resetForm(){
		$("#username").val("");
		$("#rolename").val("");
	}
	function updateAdmin(username){
		var url = "<%=request.getContextPath()%>/admin/admin_inputAdmin.Q";
		if(username){
			url+="?username="+username;
		}
		window.location.href = url;
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
						url:"<%=request.getContextPath()%>/admin/admin_delAdmin.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/admin_manager.Q";
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
						url:"<%=request.getContextPath()%>/admin/admin_disableAccount.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/admin_manager.Q";
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
						url:"<%=request.getContextPath()%>/admin/admin_enableAccount.Q",
						data:{"adminIds":adminIds},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								var url = "<%=request.getContextPath()%>/admin/admin_manager.Q";
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
	
	
	//--------------------------------- 弹出窗口时的方法  --------------------
	//审核用户
	function authUser(uid){
		//需要指定角色
		art.dialog.open(CONFIG.context +'/admin/auth/authConfirmRole.jsp', {
		    title: '确认角色',
		    width : '80%',
		    height: 400,
		    // 在open()方法中，init会等待iframe加载完毕后执行
		    init: function () {
		    },
		    ok: function () {
		    	// alert(art.dialog.data("role") );
		    	// alert(art.dialog.data("id") );
		    	if(art.dialog.data("text") != "未分组"){
			    	if(art.dialog.data("id") == undefined || art.dialog.data("role") == undefined){
			    		art.dialog("请选择所属分组和角色！");
			    		return false;
			    	}
		    	}
		    	//到后台更新
	    		auditUser(uid);
		    },
		    cancel: true
		});
	}

function auditUser(uid){
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/admin/fx_auditUser.Q",
		data : "uid="+uid+"&orgId=" + art.dialog.data("id") + "&role=" + art.dialog.data("role"),
		dataType : "json",
		success : function(data) {// 回调
			 if(data.result == 'ok'){
			 	art.dialog("操作成功！");
			 	window.location.reload();
			 }else{
			 	art.dialog("处理失败！");
			 }
		}
	});
}


function checkUser(obj){
	var id = obj.value.split("|")[0];
	var name = obj.value.split("|")[1];
	//alert(id + "|" + name);
	$("#selectedUserId").val(id);
	$("#selectedUserName").val(name);
}
</script>
</head>
<body>
<s:if test='#request.pop != "1"'>
	<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
	<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
</s:if>
<s:else>
<!-- 当此页面作为弹出页面时，选中的记录的值 -->
<input type="hidden" id="selectedUserId" value="">
<input type="hidden" id="selectedUserName" value="">
</s:else>
<input type="hidden" id="tab" value="m7,msub71"/>
<!-- 如果是弹出框，隐藏头部和左侧 -->
<div id="content" <s:if test='#request.pop == "1"'>style='margin-left:0'</s:if>>
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/admin_manager.Q" method="post" id="adminQueryForm">
            <div class="controls controls-row">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">用户名:</label>
	            <input name="username" id="username" type="text" class="span2" style="margin-left:5px;margin-right:35px" value="${ username}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">角色：</label>
	            <input type="hidden" name="pop" value="${pop }">
	            <input name="rolename" id="rolename" type="text" class="span2" style="margin-left:5px;margin-right:35px" value="${rolename }">
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>管理员列表</h5>
           		<s:if test='#request.pop != "1"'>
		            <button class="label label-info btn btn-primary btn-mini" onclick="updateAdmin();">新增</button>
		         <!-- <button class="label label-info btn btn-primary btn-mini">导入</button> -->
		            <button class="label label-info btn btn-primary btn-mini" onclick="delAdmin();">删除</button>
		            <button class="label label-info btn btn-primary btn-mini" onclick="disableAccount();">禁用</button>
		            <button class="label label-info btn btn-primary btn-mini" onclick="enableAccount();">启用</button>
		         <!--   <button class="label label-info btn btn-primary btn-mini">导出</button> -->
        	 	</s:if>
         </div>
         <div class="widget-content nopadding" id="adminTableId">
            <%@include file="adminList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
