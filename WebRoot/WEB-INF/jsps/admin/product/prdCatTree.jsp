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
				onClick: onClickFuc
			}
		};

			$(function(){
				$.ajax({
					url:"<%=request.getContextPath()%>/admin/products_getPrdCatTreeData.Q",
					async:false,
					dataType:'json',
					success:function(data){
						$.fn.zTree.init($("#treeDemo"),setting,data);
					}
				
				});
				
			})
			function onClickFuc(event, treeId, treeNode, clickFlag){
				if(!treeNode.children){
					$("#code").val(treeNode.code);
					$("#catId").val(treeNode.id);
					$("#name").val(treeNode.name);
				}
			}
			function confirmInput(){
				var code = $("#code").val();
				var name = $("#name").val();
				if(code!="" && name!=""){
					$(window.parent.document).find("#prdCatCode").val(code);
					$(window.parent.document).find("#prdCatName").val(name);
					//加载规格
					loadPrdSpecList($("#catId").val());
					//art.dialog.close();
				}else{
					art.dialog({content:"请选择类型",ok:true,okVal:"确定"});
				}
				
			}
			function resetInput(){
				$(window.parent.document).find("#prdCatName").val("");
				$(window.parent.document).find("#prdCatCode").val("");
				art.dialog.close();
			}
			
			
		//加载规格
		function loadPrdSpecList(catId){
			var specList = $(window.parent.document).find("#prdSpecList");
				//加载产品规格信息
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/admin/fx_loadPrdSpec.Q",
					data: {
						prdCatId: catId
					},
					dataType : "json",
					success : function(data) {
						if(data.result == "ok"){
							specList.empty();
							//show the result
							var str = "";
							for(var n=0; n<data.datas.length; n ++){
								str = "<option value='"+ data.datas[n].id +"'>"+ data.datas[n].specName +"</option>";
								specList.append(str);
							}
						}else if(data.result == "null"){
							//now there is no spec datas in db
							specList.html("<h5>此分类暂未设置规格，请到分类管理菜单新增</h5>");
						}else{
							//there was an exception
							specList.html("<h5>加载失败...</h5>");
						}
						art.dialog.close();
					}
				});
		}

			
			
	</script>
</head>
<body>
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree">
		</ul>
		<br/>
		 <div class="bn_ceng">
		 	<input type="hidden" value="" id="code">
		 	<input type="hidden" value="" id="catId">
		 	<input type="hidden" value="" id="name">
		 	<input name="" type="button" onclick="confirmInput();" value="确 定" />&nbsp;&nbsp;
		     <input name="" type="button"  onclick="resetInput();" value="清 除" />
		     <h5 style="color: red">* 只允许选择子分类</h5>
		 </div>
	</div>
</body>
</html>