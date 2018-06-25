//获得数据
function loadWxMenu(){
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_loadWxMenu.Q",
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
			$("#isroot").val("0");
			$("#info3").hide();
			$("#addOrg").hide();
			//增加总经销的层
			$("#info33").hide();
			loadNodeInfo(snode);
		//}
	 });
}
		
//点击树节点后触发的事件
function loadNodeInfo(snode){
	var name = snode.text;
	var id = snode.id;
	//alert(name + "-" + snode.original.orgCode);
	$("#info1").show();
	$("#info2").hide();
	
	//设置当前组织ID
	$("#orgId").val(id);
	$("#currentNodeName").val(name);
	
	$("#orgName").html("<font color=red>"+name + "</font>");
}
         
		
//添加子节点
function addSubOrg(){
	var op = $("#op").val();
	if(op == "u"){//是更新，调用更新方法
		modifyNode();
		return;
	}
	var pid = $("#orgId").val();
	var name = $("#newOrgName").val();
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_addSubCat.Q",
		data : "pid=" + pid + "&name=" + name,
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("添加成功！", 1.5);
				//隐藏层
				$("#addOrg").hide();
				$("#newOrgName").val("");
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}else{
				art.dialog.tips("更新失败！", 1.5);
			}				
		}
	});

}

function showAdd(){
	$("#newOrgName").val("");
	$("#addOrg").show();
	$("#info3").fadeOut("fast");
	
	$("#op").val("a");//新增
}
function cancelAdd(){
	$("#addOrg").hide();
	//当前是根节点
	if($("#isroot").val() == "1"){
		$("#info3").fadeIn("fast");
	}
}
//删除节点
function deleteNode(){
	if($("#orgId").val() == 0){
		art.dialog("对不起，请选择要删除的节点");
		return;
	}
	if(window.confirm("真的要删除该节点吗？请确保该分类下没有子分类且没有属于该分类的商品！")){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_delCat.Q",
			data : "orgId=" + $("#orgId").val(),
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog.tips("删除成功！", 1.5);
					//刷新树
					$.jstree.destroy();
					loadPrdCat();
				}else{
					art.dialog.tips("删除失败, 该分类下有子分类或者已有商品关联到该分类！", 3);
				}				
			}
		});
	}
}

function showModify(){
	//显示修改的当前节点名称
	$("#newOrgName").val($("#currentNodeName").val());
	$("#op").val("u");//更新
	$("#addOrg").show();
}


function modifyNode(){
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_modifyCat.Q",
		data : "orgId=" + $("#orgId").val() + "&orgName=" + $("#newOrgName").val(),
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("操作成功！", 1.5);
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}else{
				art.dialog.tips("操作失败！", 1.5);
			}				
		}
	});
}

//新增一级菜单
function addCompany(flag){
	if(flag == 1){//显示层
		$("#info33").show();
		return;
	}

	if($.trim($("#topCatName").val()) == ""){
		art.dialog.tips("分类名称必填！", 1.5);
		return;
	}
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_AddTopPrdcat.Q",
		data : {
			"topCatName" : $("#topCatName").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("操作成功！", 1.5);
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}else{
				art.dialog.tips("操作失败！", 1.5);
			}				
		}
	});
}

		