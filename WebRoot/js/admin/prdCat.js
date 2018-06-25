//获得数据
function loadPrdCat(){
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_loadPrdCat.Q",
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
	   	

		//alert(snode.text);
	   	//优惠券分类无法删除
	   	if(snode.text == "优惠券"){
	   		$("#delBut").hide();
	   	}else{
	   		$("#delBut").show();
	   	}
	   	
		//加载该节点相关信息
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
	
	//设置当前分类ID
	$("#orgId").val(id);
	$("#currentNodeName").val(name);
	
	//如果是根节点，设置图片路径
	if($("#isroot").val() == "1"){
		$("#catpicUrl").attr("src", CONFIG.context+"/private/category/"+snode.original.picUrl+"?r="+Math.random());
	}
	
	$("#orgName").html("<font color=red>"+name + "</font>");
	//加载规格列表
	loadPrdSpecList(id);
}


function loadPrdSpecList(catId){
	$("#specList").html("<h3>正在加载规格信息...</h3>");
		//加载产品规格信息
		$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_loadPrdSpec.Q",
			data: {
				prdCatId: catId
			},
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					//show the result
					var str = "";
					for(var n=0; n<data.datas.length; n ++){
						str +="<span class=\"label label-important\">"+ data.datas[n].specName +"</span><a class=\"tip-top\" href=\"#\" title=\"删除此规格\" alt=\"删除此规格\" onclick='delSpec("+ data.datas[n].id +")'><i class=\"icon-remove\"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					
					$("#specList").html(str);
				}else if(data.result == "null"){
					//now there is no spec datas in db
					$("#specList").html("<h5>此分类暂未设置规格，请点击“新增规格”按钮新增</h5>");
				}else{
					//there was an exception
					$("#specList").html("<h5>加载失败...</h5>");
				}
			}
		});
}



//删除规格
function delSpec(specId){
	if(isNaN(specId)){
		art.dialog("参数不正确");
		return;
	}
	$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_delPrdSpec.Q",
			data: {
				specId : specId
			},
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog("删除成功");
					loadPrdSpecList($("#orgId").val());
				}else{
					art.dialog("删除失败。");
				}
			}
		});
	
}

//增加产品 规格
//flag = 1 显示添加层
//flag = 2 插入数据库
function addPrdSpec(flag){
	if(flag == 1){
		$("#newPrdSpec").val("");
		$("#addPrdSpec").fadeIn("fast");
	}else if(flag == 2){
		//insert into db
		var catId = $("#orgId").val();
		var specName = $("#newPrdSpec").val();
		$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_addPrdSpec.Q",
			data: {
				catId: catId,
				specName : specName
			},
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog("添加成功");
					var html = "<span class=\"label label-important\">"+ specName +"</span><a class=\"tip-top\" href=\"#\" title=\"删除此规格\" alt=\"删除此规格\" onclick='delSpec("+ data.id +")'><i class=\"icon-remove\"></i></a>";
					$("#specList").append(html + "&nbsp;&nbsp;&nbsp;&nbsp;");
				}else if(data.result == "param"){
					art.dialog("参数不正确。");
				}else{
					art.dialog("添加失败。");
				}
				
			}
		});
	}
}


function hideDiv(id){
	$("#"+id).fadeOut("fast");
}

// 根据组织机构ID加载成员列表，ajax分页
function loadUsersByOrg(page, orgId) {
	// 替换掉列表内容，显示为： 正在努力加载中....
//	$("#dataList").html("<img src='"+ CONFIG.context +"/images/loading.gif'> ");
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	var keyword = $("#keyword").val();
	
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_userList.Q",
		data : "page=" + page + "&limit=20&orgId=" + orgId, // 初始化显示第一页，每页50条
		dataType : "json",
		success : function(data) {
			if (data != "fail" && data != 'null') {
			
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
				//<table class="table table-bordered">
              /*
              <thead>
                <tr>
                  <th>Site</th>
                  <th>Visits</th>
                </tr>
              </thead>
            */
				var resumeList = "";
				// 第二步：加载列表当页数据
				for (var j = 0; j < list.length; j++) {
					var zb = list[j];
					resumeList += "<TR class=\"rsmTr\">";
					resumeList += "<TD>" + (j+1) + "</TD>";
					resumeList += "<TD>" + zb.openId + "</TD>";
					resumeList += "<TD>" + zb.email + "</TD>";
					//resumeList += "<TD>" + zb.city + "</TD>";
					//resumeList += "<TD>" + zb.country + "</TD>";
					//resumeList += "<TD>" + zb.company + "</TD>";
					resumeList += "<TD>" + zb.username + "</TD>";
					resumeList += "<TD>" + zb.orgname + "</TD>";
					//resumeList += "<TD>" + zb.idCard + "</TD>";
					//resumeList += "<TD>" + zb.jobtitle + "</A></TD>";
					resumeList += "<TD>" + zb.telephone + "</TD>";
					resumeList += "<TD>" + zb.statusStr + "</TD>";
					resumeList += "<TD>" + zb.lastLoginStr + "</TD>";
					resumeList += "</TR>";
				}

				$("#dataList").html(resumeList);
			} else {
				$("#dataList")
						.html("数据加载失败。请<a href='###' onclick='initData(1)'>刷新</a>重试。");
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
				
				//设置当前的catId
				$("#orgId").val(data.cid);
				//alert("子节点ID="+ data.cid);
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

//新增一级分类
function addCompany(flag){
	if(flag == 1){//显示层
		$("#topCatName").val("");
		$("#filePathTop").val("");
		$("#info33").show();
		$("#addOrg").hide();
		return;
	}
	//以下是flag==2的情况
	if($.trim($("#topCatName").val()) == ""){
		art.dialog.tips("分类名称必填！", 1.5);
		return;
	}
	
	if($("#filePathTop").val()==""){
		alert("一级分类图片不能为空!");
		return false;
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
				
				//上传图片
				fileupload(data.id, "filePathTop");
			}else{
				art.dialog.tips("操作失败！", 1.5);
			}				
		}
	});
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

		