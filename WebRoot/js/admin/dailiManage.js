//获得数据
function loadDaili(){
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_loadDali.Q",
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
	   	//alert(snode.parent);
	   	if(snode.parent == '#'){
			$("#isroot").val("1");//是根节点的标记，有些内容，只有根节点才显示，比如一级分类图片
		}else{
			$("#isroot").val("0");
		}
		//加载该节点相关信息
		$("#baseInfo").show();
		var id = snode.id;
		loadDailiOrders(1, id);
	 });
}

  
function hideDiv(id){
	$("#"+id).fadeOut("fast");
}

// 加载代理商所负责区域的订单
function loadDailiOrders(page, adminId) {
	// 替换掉列表内容，显示为： 正在努力加载中....
//	$("#dataList").html("<img src='"+ CONFIG.context +"/images/loading.gif'> ");
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_orderList.Q",
		data : "page=" + page + "&limit=20&adminId=" + adminId, // 初始化显示第一页，每页50条
		dataType : "json",
		success : function(data) {
			$("#orderInfo").show();
			$("#tips").hide();
			if (data.total > 0) {
				
				$("#dataList").html("");
				$("#pageBar").html("");
				var page =  data ;
				var nextPage = (parseInt(page.page) + 1) >= page.totalPage
						? page.totalPage
						: (parseInt(page.page) + 1);
				var curPage = page.page;
				var prePage = (page.page - 1) <= 1 ? "1" : (page.page - 1);
				var totalPage = page.totalPage;
				var pageBarDiv = "";
				// 分页样式。。。。
				pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"
						+ curPage + "】页,总共【<b>" + page.total + "</b>】条记录 ";
				pageBarDiv += "[<a href='###' onclick=\"loadUsersByOrg(" + prePage + ","+ orgId +")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"loadUsersByOrg(" + i + ","+ orgId +")\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"loadUsersByOrg(" + nextPage + ","+ orgId +")\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='loadUsersByOrg(this.options[selectedIndex].value, "+ orgId +")'>";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<option value='" + i + "' selected='selected'>第" + i + "页</option>";
						} else {
							pageBarDiv += "<option value='" + i + "'>第" + i	+ "页</option>";
						}
					}
				}
				pageBarDiv += "</select>";
				pageBarDiv += "</div>";
				$("#pageBar").html(pageBarDiv);

				var list = page.datas;
				// 表头:地市	县	工作单位	姓名	员工号	身份证号	岗位名称	联系电话
              /*
             <th>序号</th><th>订单ID</th><th>支付用户</th><th>下单时间</th> 
						                 <th>订单金额</th><th>订单状态</th>
            */
				var resumeList = "";
				// 第二步：加载列表当页数据
				for (var j = 0; j < list.length; j++) {
					var zb = list[j];
					resumeList += "<TR class=\"rsmTr\">";
					resumeList += "<TD>" + (j+1) + "</TD>";
					resumeList += "<TD>" + zb.orderNo + "</TD>";
					resumeList += "<TD>" + zb.user.nickName +  "</TD>";
					resumeList += "<TD>" + zb.odateStr + "</TD>";
					resumeList += "<TD>" + zb.feePrice/100 + " 元</TD>";
					resumeList += "<TD>" + zb.statusStr + "</TD>";
					resumeList += "</TR>";
				}

				$("#dataList").html(resumeList);
			} else {
				$("#dataList").html("<tr><td colspan=6>暂时没有数据...</td></tr>");
			}
		}
	});
}
         
		
//添加子节点
function addSubOrg(){
	$("#isroot").val("0");
	var op = $("#op").val();
	if(op == "u"){//是更新，调用更新方法
		modifyNode();
		return;
	}
	//alert($("#catpic").css('display'));
	
	if($("#catpic").css('display') != "none" && $("#filePath").val() == ""){
		art.dialog("请选择分类图片。");
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
				
				doAjaxUpload();
				
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}else{
				art.dialog.tips("更新失败！", 1.5);
			}				
		}
	});
}

//上传图片,如果是新增，提示用户选择图片，如果是修改，用户没选择新的图片，就不上传
function doAjaxUpload(){
	var op = $("#op").val();
	var catId = $("#orgId").val();
	//alert(catId);
	if(op == "u" && $("#isroot").val() == "1"){
		if($("#filePath").val() != ""){
			fileupload(catId, "filePath");
		}
	}else{
		fileupload(catId, "filePath");
	}
}


//新增子分类
function showAdd(){
	$("#isroot").val("0");
	$("#newOrgName").val("");
	$("#addOrg").show();
	//$("#info3").fadeOut("fast");
	//不显示图片选择
	$("#catpic").hide();
	
	$("#op").val("a");//新增
}
function cancelAdd(){
	$("#addOrg").hide();
	//当前是根节点
	if($("#isroot").val() == "1"){
		//$("#info3").fadeIn("fast");
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
	
	//只有根节点，才显示图片
	if($("#isroot").val() == "1"){
		$("#catpic").show();
	}else{
		$("#catpic").hide();
	}
	
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
				
				doAjaxUpload();
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}else{
				art.dialog.tips("操作失败！", 1.5);
			}				
		}
	});
}
 

function addDaili(){
	window.location.href = CONFIG.contex + "/admin/admin_manager.Q";
}


function fileupload(catId, fileEleId){
	if($("#" + fileEleId).val()=="" && $("#op").val() == "a" && $("#catpic").css('display') != "none"){
		alert("上传文件不能为空!");
		return false;
	}
	//alert("catId=="+catId);
	$.ajaxFileUpload({
			url:CONFIG.context + "/fileLoad?catId=" + catId,
			secureuri:false,
			fileElementId: fileEleId,
			dataType: 'text/xml',
			success: function (data) {
				//alert("success");
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			},error: function (data, status, e){
				//alert("fail");
				//刷新树
				$.jstree.destroy();
				loadPrdCat();
			}
		}
	);
}

		