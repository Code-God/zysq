<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD id=Head1>
		<TITLE>选择角色</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<META HTTP-EQUIV="pragma" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="Cache-Control" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="expires" CONTENT="-1">
	    <script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
   	    <link rel="stylesheet" href="<%=request.getContextPath() %>/js/jstree/themes/default/style.min.css" />
   	    <SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
	    <script src="<%=request.getContextPath() %>/js/jstree/jstree.min.js"></script>
		<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
		<script type="text/javascript">
		
		function setV(sel){
			art.dialog.data("role", sel.value);
		}
		
		//获得数据
		function loadOrg(){
			//清除数据
			art.dialog.removeData("role");
			art.dialog.removeData("text");
			art.dialog.removeData("id");
		
			$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin.do?m=loadOrg",
				dataType : "json",
				success : function(data) {
					loadTree(data);
				}
			});
		}
		
		
		function loadTree(datas){
			$('#myTree').jstree({ 'core' : {
				'multiple' : false,//不允许多选
			    'data' : datas
			} }).on('changed.jstree', function (e, data) {
			    var snode = data.instance.get_node(data.selected[0]);
			   //alert(snode.parent)
				//加载该节点相关信息
				if(snode.parent == '#'){
					 
				}else{
					art.dialog.data("id", snode.id);
					art.dialog.data("text", snode.text);
					if(snode.text == "未分组"){
						$("#role").hide();
					}
				}
			 });
		}
		
		
		</script>
		
		<style>
		.wid-50{width:50%;display:inline-block; float: left; height: 50px; overflow: visible;}
		</style>
	</HEAD>
	<BODY onload="loadOrg()">
		<div>
			<div class="wid-50">
				<br>请选择所属分组或区域： 
				<div id="myTree"></div>
			</div>
			<div class="wid-50">
				请选择角色：<select id="role" onchange="setV(this)" >
									<option selected="selected">--请选择--</option>
									<option value="Account Manager">Account Manager</option>
									<option value="Direct">Direct</option>
									<option value="Manager">Manager</option>
									<option value="Sales">Sales</option>
								</select>
			</div>
		</div>
	</BODY>
</HTML>
