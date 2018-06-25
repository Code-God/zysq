function loadTree(datas){
	$('#myTree').jstree({ 'core' : {
		'multiple' : false,//不允许多选
	    'data' : datas
	} }).on('changed.jstree', function (e, data) {
	    var snode = data.instance.get_node(data.selected[0]);
	   //alert(snode.parent)
		//加载该节点相关信息
		if(snode.parent == '#'){
			$("#isroot").val("1");
		}else{
			$("#isroot").val("0");
		}
		/*
			//只显示新增按钮，根节点无法删除
			$("#tableDiv").hide();
			$("#info3").show();
			$("#info1").hide();
			$("#info2").hide();
			$("#addOrg").hide();
			$("#orgId").val(snode.id);
		}else{
		*/
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
//	alert($("#currentOrgCode").val());
	
	//逻辑判断：如果当前节点就是自己所在的分销商，则不允许修改负责人和微商城有效期（只有上级才能有权限），需要禁用选择和更新按钮
	if(snode.original.orgCode == $("#currentOrgCode").val()){
		$("#selCharger").hide();
		$("#updCharger").hide();
		$("#charger").attr("readOnly","readOnly");
		
		//不允许修改微商城有效期
		$("#mallDiv").hide();
		//运费不允许下级分销商修改
		$("#deliverBut").show();
	}else{
		//$("#mallDiv").show();
		$("#selCharger").show();
		$("#updCharger").show();
		
		//运费不允许下级分销商修改
		$("#deliverBut").hide();
	}
	//超级管理员可以修改运费
	if($("#currentOrgCode").val() == "0"){
		$("#deliverBut").show();
	}
	
	//显示表格
	$("#tableDiv").show();
	$("#info1").show();
	$("#info2").hide();
	$("#addOrg").hide();
	$("#wxMallUrl").hide();
	
	//设置当前组织ID
	$("#orgId").val(id);
	$("#currentNodeName").val(name);
	
	//清空微商城入口url
	$("#wxMallUrl").html("");
	
	//显示佣金
	//$("#commission").val(snode.original.commission);
	
	$("#orgName").html("<font color=red>"+name + "</font>");
	$("#charger").val("加载中...");
	//-----------加载负责人等基本信息----------------------
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_loadCharger.Q",
		data : "id=" + id,
		dataType : "json",
		success : function(data) {
			if(data.result == "fail"){
				art.dialog("对不起，操作失败。");
			}else if(data.result == null){
				$("#charger").val("无负责人");
			}else{
				$("#charger").val(data.name);
				$("#userId").val(data.id);
			}
			//显示佣金
			$("#commission").val(data.commission);
			$("#personCommission").val(data.personCommission);
			$("#appid").val(data.appid);
			$("#appsecret").val(data.appsecret);
			$("#wxID").val(data.wxID);
			//显示地区
			$("#orgCity").html(data.city);
			
			//加载关注URL
			$("#attHintUrl").val(data.attHintUrl);
			
			//多客服
			$("#kfAccount").val(data.kfAccount);
			
			$("#mallexpire").val(data.mallexpire);
			//加载分销客转发设置
			$("#shareLogo").val(data.shareLogo);
			$("#shareTitle").val(data.shareTitle);
			$("#shareDesc").val(data.shareDesc);
			$("#baoyou").val(data.baoyou/100);
			if(data.code.length <= 6){
				//加载运费
				$("#deliverFee").val(data.deleverFee/100);
			}
			
			
		}
	});
	
	//------------------加载用户列表,支持分页-------------------------
	loadUsersByOrg(1, id);			
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
					//resumeList += "<TD>" + zb.email + "</TD>";
					//resumeList += "<TD>" + zb.city + "</TD>";
					//resumeList += "<TD>" + zb.country + "</TD>";
					//resumeList += "<TD>" + zb.company + "</TD>";
					resumeList += "<TD>" + zb.nickName + "</TD>";
					resumeList += "<TD>" + (zb.sex==1 ? "男" : "女") + "</TD>";
					resumeList += "<TD>" + zb.orgname + "</TD>";
					//resumeList += "<TD>" + zb.idCard + "</TD>";
					//resumeList += "<TD>" + zb.jobtitle + "</A></TD>";
					resumeList += "<TD>" + zb.telephone + "</TD>";
					resumeList += "<TD>" + zb.statusStr + "</TD>";
					//resumeList += "<TD>" + zb.lastLoginStr + "</TD>";
					resumeList += "<TD><button onclick=\"sendBless('"+ zb.openId +"')\" class=\"btn btn-success\">发送祝福</button></TD>";
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
         
		//获得数据
		function loadOrg(){
			$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin/fx_loadOrg.Q",
				dataType : "json",
				success : function(data) {
					loadTree(data);
				}
			});
		}
		
		//从弹出框选择用户
		function selUser(){
			art.dialog.open(CONFIG.context +'/admin/admin_manager.Q?pop=1', {
			    title: '选择用户',
			    width : '80%',
			    height: 500,
			    // 在open()方法中，init会等待iframe加载完毕后执行
			    init: function () {
			    },
			    ok: function () {
			    	var iframe = this.iframe.contentWindow;
			    	var id = iframe.document.getElementById("selectedUserId").value;
			    	var name = iframe.document.getElementById("selectedUserName").value;
			    	//alert(id + "--" + name);
			    	$("#charger").val(name);
			    	//设置ID
			    	$("#userId").val(id);
			    },
			    cancel: true
			});
		}
		
		/**
		 * 调用模板消息发送给用户
		 */
		function sendBless(openId){
			$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin/fx_sendBless.Q",
				data : "openId=" + openId,
				dataType : "json",
				success : function(data) {
					if(data.result == "ok"){
						art.dialog.tips("发送成功！", 1.5);
					}else{
						art.dialog.tips("发送失败！", 1.5);
					}				
				}
			});
		}
		
		
		
		//更新负责人
		function updateCharger(){
			var chargerid = $("#userId").val();
			var orgId = $("#orgId").val();
			$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin/fx_updateCharger.Q",
				data : "chargerId=" + chargerid + "&orgId=" + orgId,
				dataType : "json",
				success : function(data) {
					if(data.result == "ok"){
						art.dialog.tips("更新成功！", 1.5);
					}else{
						art.dialog.tips("更新失败,该用户可能已经被授权为其他地区管理员！", 1.5);
					}				
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
			var pid = $("#orgId").val();
			var name = $("#newOrgName").val();
			$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin/fx_addSubOrg.Q",
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
						loadOrg();
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
			if(window.confirm("子节点也将一并删除！真的要删除该节点吗？")){
				$.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/fx_delOrg.Q",
					data : "orgId=" + $("#orgId").val(),
					dataType : "json",
					success : function(data) {
						if(data.result == "ok"){
							art.dialog.tips("删除成功！", 1.5);
							//刷新树
							$.jstree.destroy();
							loadOrg();
						}else{
							art.dialog.tips("删除失败！", 1.5);
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
				url : CONFIG.context + "/admin/fx_modifyOrg.Q",
				data : "orgId=" + $("#orgId").val() + "&orgName=" + $("#newOrgName").val(),
				dataType : "json",
				success : function(data) {
					if(data.result == "ok"){
						art.dialog.tips("操作成功！", 1.5);
						//刷新树
						$.jstree.destroy();
						loadOrg();
					}else{
						art.dialog.tips("操作失败！", 1.5);
					}				
				}
			});
		}
		
		
		function addCompany(flag){
			if(flag == 1){
				//显示层
				$("#info33").show();
				$("#info1").hide();
				$("#info2").hide();
				$("#info3").hide();
				$("#tableDiv").hide();
			}else{
				
				if($.trim($("#topOrgName").val()) == ""){
					art.dialog.tips("名称必填！", 1.5);
					return;
				}
				if($.trim($("#city").val()) == 0){
					art.dialog.tips("请选择开通城市！", 1.5);
					return;
				}
				if($.trim($("#telephone").val()) == ""){
					art.dialog.tips("电话必填！", 1.5);
					return;
				}
				//提交,增加分销总公司 topOrgName, telephone, email
				$.ajax({
					type : "POST",
					url : CONFIG.context + "/admin/fx_AddTopOrg.Q",
					data : {
						"topOrgName" : $("#topOrgName").val(),
						"city" : $("#city").val(),
						"telephone" : $("#telephone").val(),
						"email" : $("#email").val(),
						},
					dataType : "json",
					success : function(data) {
						if(data.result == "ok"){
							art.dialog.tips("操作成功！", 1.5);
							//刷新树
							$.jstree.destroy();
							loadOrg();
						}else if(data.result == "config"){
							art.dialog.tips("配置文件被篡改，无法新增！", 1.5);
						}else if(data.result == "max"){
							art.dialog("对不起，此版本最多只能增加最多<font color=red>"+ data.max +"</font>家总销商！请联系技术支持！");
						}else{
							art.dialog.tips("操作失败！", 1.5);
						}				
					}
				});
			}
		}
		
		
//更新佣金		
function updateCommission(){
	var orgId = $("#orgId").val();
	var commission = $("#commission").val();
	var personCommission = $("#personCommission").val();
	if(isNaN(commission) || isNaN(personCommission)){
		art.dialog.tips("请输入数字！");
		return;
	}
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_updateCommission.Q",
		data : "commission=" + commission + "&personCommission="+ personCommission +"&orgId=" + orgId,
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("更新成功！", 1.5);
			}else{
				art.dialog.tips("更新失败！", 1.5);
			}				
		}
	});
}

//更新分销客设置
function updateShareConfig(){
	var shareLogo = $("#shareLogo").val();
	var shareTitle = $("#shareTitle").val();
	var shareDesc = $("#shareDesc").val();
	var attHintUrl = $("#attHintUrl").val();//关注url
	//alert(escape(attHintUrl));
	if($.trim(shareLogo) =="" || $.trim(shareTitle) =="" || $.trim(shareDesc) ==""){
		art.dialog.tips("以上输入不得为空！");
		return;
	}
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_updateShareConfig.Q",
		data : "shareLogo=" + shareLogo + "&shareTitle="+ shareTitle +"&shareDesc=" + shareDesc+"&attHintUrl="+escape(attHintUrl),
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog.tips("更新成功！", 1.5);
			}else{
				art.dialog.tips("更新失败！", 1.5);
			}				
		}
	});
}

//更新微信设置：APPID APPSECRET
function updateWxconfig(){
	var orgId = $("#orgId").val();
	var appId = $.trim($("#appid").val());
	var appsecret = $.trim($("#appsecret").val());
	//原始ID
	var wxID = $("#wxID").val();
	var kfAccount = $("#kfAccount").val();
	
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_updateWxconfig.Q",
		data : "orgId=" + orgId + "&appid=" + appId + "&appsecret=" + appsecret+"&wxID=" + wxID+"&kfAccount="+kfAccount,
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog("更新成功！");
			}else{
				art.dialog("更新失败！");
			}
		}
	});
	
}

//更新运费，只有总销商有权限
function updateDeliverFee(){
	var orgId = $("#orgId").val();
	var deliverFee = $("#deliverFee").val();
	//alert(orgId);
	var mallexpire = $("#mallexpire").val();
	var baoyou = $("#baoyou").val();
	//alert(baoyou);
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_updateDeliverFee.Q",
		data : "orgId=" + orgId +"&deliverFee=" + deliverFee+"&baoyou="+baoyou,
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog("更新成功！");
			}else{
				art.dialog("更新失败！");
			}
		}
	});
	

}


//更新商城有效期 flag=0表示无限制
function updateMallExpire(flag){
	var orgId = $("#orgId").val();
	var mallexpire = $("#mallexpire").val();
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_updateMallExpire.Q",
		data : "orgId=" + orgId +"&mallexpire=" + mallexpire,
		dataType : "json",
		success : function(data) {
			if(data.result == "ok"){
				art.dialog("更新成功！");
			}else{
				art.dialog("更新失败！");
			}
		}
	});

}


//显示当前分销商的微信公众号的微商城入口URL
function showWxMallUrl(){
	if($("#wxMallUrl").html() != ""){
		$("#wxMallUrl").show();
		return;
	}
	
	if($.trim($("#appid").val())==""){
		art.dialog("请填写微信公众号信息：APPID, APPSECRET，原始ID！");
		return;
	}
	
	$("#wxMallUrl").html("正在加载中....请稍后。");	
	$("#wxMallUrl").show();
	$.ajax({
		type : "POST",
		url : CONFIG.context +"/admin/fx_showWxMallUrl.Q",
		data : "orgId=" + $("#orgId").val(),
		dataType : "text",
		success : function(data) {
			$("#wxMallUrl").html(data);	
		}
	});
}


		