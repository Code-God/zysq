function loadTree(datas){
	$('#myTree').jstree({ 'core' : {
		'multiple' : false,//不允许多选
	    'data' : datas
	} }).on('changed.jstree', function (e, data) {
	    var snode = data.instance.get_node(data.selected[0]);
	   //alert(snode.parent)
		//加载该节点相关信息
		/*
		if(snode.parent == '#'){
			$("#isroot").val("1");
			//只显示新增按钮，根节点无法删除
			$("#tableDiv").hide();
			$("#info3").show();
			$("#info1").hide();
			$("#info2").hide();
			$("#addOrg").hide();
			$("#orgId").val(snode.id);
		}else{
		*/
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
	//alert(name + "-" + snode.original.mtype);
	var mtype = snode.original.mtype;//菜单类型
	//设置隐藏域的ID
	$("#dbId").val(id);
	
	//显示表格
	$("#info1").show();
	$("#info2").hide();
	$("#addOrg").hide();
	
	//加载当前节点信息
	$("#curMenuName").val(name);
	//$("#curMtype").val(snode.original.mtype);
//	alert(snode.original.mtype);
	var radios = $("input[type=radio][name=cmType]"); 
	if(snode.original.mtype == ""){//有二级菜单
		radios.each(function(){
			if(this.value == "0"){
				$(this).prop("checked", true);
			}
		});
	}else{
		radios.each(function(){
			if(this.value == snode.original.mtype){
				$(this).prop("checked", true);
			}
		});
	}
	if(snode.original.mtype == "click"){
		$("#cmparam").show();
		$("#curKey").removeAttr("disabled");	
		$("#curUrl").prop("disabled", true);	
	}else if(snode.original.mtype == "view"){
		$("#cmparam").show();
		$("#curKey").prop("disabled", true);	
		$("#curUrl").removeAttr("disabled");	
	}else{
		$("#cmparam").hide();
	}
	
	$("#curKey").val(snode.original.key);
	$("#curUrl").val(snode.original.url);
	
}
 
         
//获得数据
function loadMenu(){
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_loadMenu.Q",
		dataType : "json",
		success : function(data) {
			loadTree(data);
		}
	});
}

 
//添加子节点
function addSubOrg(){
	var op = $("#op").val();
	if(op == "u"){//是更新，调用更新方法
		modifyNode();
		return;
	}
	//父ID
	var pid = $("#dbId").val();
	var name = $("#curMenuName").val();
	var mtype = $('input:radio[name=cmType]:checked').val();
	//alert("mtype===="+mtype);
	var key = $("#curKey").val();
	var url= $("#curUrl").val();
	
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_addSubMenu.Q",
		data : {
		//"pid=" + pid + "&name=" + name + "&mtype="+mtype + "&key="+key+"&url="+url,
			"pid": pid,
			"name": name,
			"mtype": mtype,
			"key": key,
			"url": url
		},
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("添加成功！", 1.5);
				//隐藏层
				$("#addOrg").hide();
				$("#curMenuName").val("");
				//刷新树
				$.jstree.destroy();
				loadMenu();
			}else{
				art.dialog.tips("无法新增更多的二级菜单或系统错误！", 1.5);
			}
		}
	});

}

function showAdd(){
	//清空菜单
	$("#curMenuName").val("");
	$("#curKey").val("");
	$("#curUrl").val("");
	$("#cmparam").fadeIn("fast");
	
	//禁用一个radio，子菜单里不支持
	$("#cmType_1").attr("disabled", "disabled");
	
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
function cancelAddSub(){
	$("#addOrg").hide();
}
//删除节点
function deleteNode(){
	if($("#dbId").val() == 0){
		art.dialog("对不起，请选择要删除的节点");
		return;
	}
	if(window.confirm("子节点也将一并删除！真的要删除该节点吗？")){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_delWxMenu.Q",
			data : "dbId=" + $("#dbId").val(),
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog.tips("删除成功！", 1.5);
					//刷新树
					$.jstree.destroy();
					loadMenu();
				}else{
					art.dialog.tips("删除失败！", 1.5);
				}				
			}
		});
	}
}

function showModify(){
	//显示修改的当前节点名称
	$("#subMenuName").val($("#currentNodeName").val());
	$("#op").val("u");//更新
	$("#addOrg").show();
}


function modifyNode(){
	var mtype = $('input:radio[name=cmType]:checked').val();
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_modifyWxMenu.Q",
		data : {
			"orgId" : $("#orgId").val(),
			"dbId" : $("#dbId").val(),
			"menuName" : $("#curMenuName").val(),
			"mtype" : mtype,
			"key" : $("#curKey").val(),
			"url" : $("#curUrl").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("更新成功！", 1.5);
				//刷新树
				$.jstree.destroy();
				loadMenu();
			}else{
				art.dialog.tips("操作失败！", 1.5);
			}				
		}
	});
}


function cancelAdd(){
	$('#info33').hide();
	if($('#currentNodeName').val() != ""){//说明已经选择过
		$('#info1').fadeIn('fast');
	}
}


//点击菜单类型单选触发
function changeType(radio){
	//如果选择了“无”，则表示还有二级菜单
	if(radio.value == "0"){
		$("#mparam").hide();
	}else{
		$("#mparam").show();
		if(radio.value == "click"){
			$("#key").removeAttr("disabled");
			$("#url").attr("disabled","disabled");
		}
		if(radio.value == "view"){
			$("#key").attr("disabled","disabled");
			$("#url").removeAttr("disabled");
		}
	}
}
//点击菜单类型单选触发
function changeTypeSub(radio){
	//如果选择了“无”，则表示还有二级菜单
	if(radio.value == "0"){
		$("#cmparam").hide();
	}else{
		$("#cmparam").show();
		if(radio.value == "click"){
			$("#curKey").removeAttr("disabled");
			$("#curUrl").attr("disabled","disabled");
		}
		if(radio.value == "view"){
			$("#curKey").attr("disabled","disabled");
			$("#curUrl").removeAttr("disabled");
		}
	}
}

//增加一级菜单
function addTopMenu(flag){
	if(flag == 1){
		//清空数据
		$("#menuName").val("");
		$("#key").val("");
		$("#url").val("");
		//显示层
		$("#info33").show();
		$("#info1").hide();
	}else{
		if($.trim($("#menuName").val()) == ""){
			art.dialog.tips("名称必填！", 1.5);
			return;
		}
		if($.trim($("#menuName").val()).length > 4){
			art.dialog.tips("一级菜单长度不得超过4个字符。", 1.5);
			return;
		}
		if($("#menuName").val().length > 4){
			art.dialog.tips("一级菜单最多4个汉字！", 1.5);
			return;
		}
		
		//如果按钮类型是view， 则url必填， 否则key必填
		var mtype = $('input:radio[name=mType]:checked').val();
		if(mtype != 0){
			if(mtype == "click"){//key必填
				if($.trim($("#key").val()) == ""){
					art.dialog.tips("key必填！", 1.5);
					return;
				}
			}else if(mtype == "view"){
				//url必填
				if($.trim($("#url").val()) == ""){
					art.dialog.tips("url必填！", 1.5);
					return;
				}
			}
		}
		
		//提交,增加一级菜单addCompany
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/fx_AddTopMenu.Q",
			data : {
				"menuName" : $("#menuName").val(),
				"mtype" : mtype,
				"key" : $("#key").val(),
				"url" : $("#url").val()
				},
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog.tips("操作成功！", 1.5);
					//刷新树
					$.jstree.destroy();
					loadMenu();
				} else{
					art.dialog.tips("超过一级菜单数量限制（最多三个）或系统错误！", 1.5);
				}				
			}
		});
	}
}

//发布到微信平台
function publish2Wx(){
	//alert("orgId==="+$("#orgId").val());
	if(window.confirm("同步操作将覆盖公众号已有菜单，且不可恢复， 确定同步？")){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_publish2wx.Q",
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog.tips("操作成功！", 1.5);
					//刷新树
					$.jstree.destroy();
					loadMenu();
				} else{
					art.dialog.tips("同步失败！", 1.5);
				}
			}
		});
	}
}
