
/**
 * 加载模块
 */
function initColumns(t){
	// 替换掉列表内容，显示为： 正在努力加载中....
	$("#dataList").html("<tr height=30px><td colspan=3>正在加载中...请稍后</td></tr>");
		
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/admin_initColumns.Q?model=" + t,
		dataType : "json",
		success : function(data) {// 回调
			 if(data.result == 'fail'){
			 	art.dialog("获取失败！");
			 }else{
			 	if(data.length == 0){
				 	$("#dataList").html("暂时没有记录！");
			 	}else{
			 		var dataHtml = "";
			 		for(var i=0; i< data.length; i++){
			 			var zb = data[i];
			 			dataHtml += "<TR>";
						dataHtml += "<TD>" + (i+1) + "</A></TD>";
						dataHtml += "<TD>" + zb.title + "</TD>";
						dataHtml += "<TD>";
						dataHtml += "&nbsp;&nbsp;<button class=\"btn btn-success\"  onclick='showEdit("+ zb.id +",\""+ zb.title +"\")'>编辑</button>";
						if((i+1) > 3){
							dataHtml += "&nbsp;&nbsp;<button class=\"btn btn-danger\" onclick='delCm("+ zb.id +")'>删除</button>";
						}
						dataHtml += "</TD>";
						dataHtml += "</TR>";
			 		}
			 		
			 		$("#dataList").html(dataHtml);
			 	}
			 }
		}
	});
}

//新增栏目
function addOrUpdateColum(){
	var op = $("#op").val(); 
	if(op == "add"){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_addColumn.Q",
			dataType : "text",
			data : {
				"title": $("#columnName").val(), 
				"model" : $("#model").val()
			},
			success : function(data) {// 回调
				if(data == "ok"){
					art.dialog("新增成功！");
					$("#columnName").val("");
					initColumns($("#model").val());
				}else{
					art.dialog("新增失败！");
				}
			}
		});
	}else{//更新
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_updateColumn.Q",
			dataType : "text",
			data : {	
				"id":$("#colid").val(),
				"title": $("#columnName").val(),
				"model" : $("#model").val()
			},
			success : function(data) {// 回调
				if(data == "ok"){
					art.dialog("更新成功！");
					$("#columnName").val("");
					initColumns($("#model").val());
				}else{
					art.dialog("更新失败！");
				}
			}
		});
	}
}


function showAdd(visible){
	if(visible == 1){
		$("#button2").val("新增");
		$("#op").val("add");
		$("#columnDiv").show();
	}else{
		$("#button2").val("更新");
		$("#op").val("update");
		$("#columnDiv").hide();
	}
}

//显示编辑层，并赋值
function showEdit(id, title){
	$("#colid").val(id);
	$("#columnName").val(title);
	
	$("#op").val("update");
	$("#button2").val("更新");
	$("#columnDiv").show();
}


function delCm(id){
	if(window.confirm("删除此栏目会将该栏目下所有文章一并删除且无法恢复，确定要删除吗？")){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin.do?m=delCm",
			dataType : "text",
			data : {	
				"id":id
			},
			success : function(data) {// 回调
				if(data == "ok"){
					art.dialog("操作成功！");
					initColumns();
				}else if(data == "param"){
					art.dialog("参数不正确！");
				}else{
					art.dialog("操作失败！");
				}
			}
		});
	}
}

function gotoImportUser(){
	window.location.href=CONFIG.context + "/admin/common/importUser.jsp";
}


function initVip(){
	$("#dataList").html("");
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin.do?m=initVip",
		dataType : "json",
		success : function(data) {// 回调
			 if(data.result == 'fail'){
			 	art.dialog("获取失败！");
			 }else{
			 	if(data.length == 0){
				 	art.dialog("暂时没有记录！");
			 	}else{
			 		var dataHtml = "<tr style='thead'><TD>序号</TD><TD>大V微信号</TD><TD>推广URL</TD><TD>目前积分</TD><TD><INPUT onclick=selectallbox(); type=checkbox id='checkAll'>操作</TD></TR>";
			 		for(var i=0; i< data.length; i++){
			 			var zb = data[i];
			 			dataHtml += "<TR class=\"rsmTr\">";
						dataHtml += "<TD>" + (i+1) + "</A></TD>";
						dataHtml += "<TD>" + zb.wxName + "</TD>";
						dataHtml += "<TD>" + zb.spUrl + "</TD>";
						dataHtml += "<TD>" + zb.score + "</TD>";
						dataHtml += "<TD>";
						dataHtml += "&nbsp;&nbsp;<INPUT class=button type=button value='编辑'  onclick='showEditVip("+ zb.id +",\""+ zb.wxName +"\")'>";
						dataHtml += "&nbsp;&nbsp;<INPUT class=button type=button value='删除'  onclick='delVip("+ zb.id +")'>";
						dataHtml += "</TD>";
						dataHtml += "</TR>";
			 		}
			 		
			 		$("#dataList").html(dataHtml);
			 	}
			 }
		}
	});

}
