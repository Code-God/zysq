<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=request.getContextPath()%>/js/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/js/artDialog4.1.7/skins/default.css" />

		<script src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script> 
		<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/artDialog.js"></script> 
		<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/js/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript">
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onDblClick: onDblClickFuc,
					onClick: onClickFuc
				}
			};

			$(function(){
				$.ajax({
					url:"<%=request.getContextPath()%>/admin/city_getCityTreeData.Q",
					async:false,
					dataType:'json',
					success:function(data){
						$.fn.zTree.init($("#treeDemo"),setting,data);
					}
				});
			});
			
			// 选中后赋值控件的id
			var fid = art.dialog.data("fieldId");
			
			/**
			 * 双击直接选中
			 */
			function onDblClickFuc(event, treeId, treeNode, clickFlag){
				if (fid) {
					//$(window.parent.document).find("#"+fid).val(treeNode.id);
					$(window.parent.document).find("#"+fid).val(treeNode.code);
					$(window.parent.document).find("#_"+fid+"_").val(treeNode.name);
				}
				art.dialog.close();
			}
			
			/**
			 * 单击赋值，需要点击确定按钮
			 */
			function onClickFuc(event, treeId, treeNode, clickFlag){
				$("#code").val(treeNode.code);
				$("#id").val(treeNode.id);
				$("#name").val(treeNode.name);
			}

			/**
			 * 点击确定按钮
			 */
			function confirmInput(){
				var code = $("#code").val();
				var name = $("#name").val();
				if(fid && code!="" && name!=""){
					$(window.parent.document).find("#"+fid).val(code);
					$(window.parent.document).find("#_"+fid+"_").val(name);
					art.dialog.close();
				}else{
					art.dialog({content:"请选择所在城市",ok:true,okVal:"确定"});
				}
			}

			/**
			 * 清空
			 */
			function resetInput(){
				$(window.parent.document).find("#"+fid).val("");
				$(window.parent.document).find("#_"+fid+"_").val("");
				art.dialog.close();
			}

			/**
			 * 关闭
			 */
			function closeWin(){
				art.dialog.close();
			}
		
	</script>
	<style type="text/css">
	.div-tree-body{height:460px; overflow:auto; border-top:#c3daf9 1px solid; border-right: #c3daf9 1px solid;border-bottom:#c3daf9 1px solid; border-left:#c3daf9 1px solid;}
	.div-button{TEXT-ALIGN: center;MARGIN-top: 5px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto; clear:both;width:auto; height:28px;}
	
	</style>
</head>
<body style="margin:4px;">
	<div class="zTreeDemoBackground left">
		<div class="div-tree-body">
		<ul id="treeDemo" class="ztree">
		</ul>
		</div>
		<br/>
		<div class="bn_ceng" style="TEXT-ALIGN: center;">
		 	<input type="hidden" value="" id="code">
		 	<input type="hidden" value="" id="id">
		 	<input type="hidden" value="" id="name">
		 	<input name="" type="button" onclick="confirmInput();" value="确 定" />&nbsp;&nbsp;
		    <input name="" type="button"  onclick="resetInput();" value="清 除" />&nbsp;&nbsp;
		    <input name="" type="button"  onclick="closeWin();" value="关 闭" />
		    <h5 style="color: red"></h5>
		</div>
	</div>
</body>
</html>