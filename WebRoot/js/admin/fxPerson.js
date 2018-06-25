//分销客页面专用JS
//通过树往下加载分销商机构
//从分销商的角度查询各级分销客
function loadTree(datas){
	$('#myTree').jstree({ 'core' : {
		'multiple' : false,//不允许多选
	    'data' : datas
	} }).on('changed.jstree', function (e, data) {
	    var snode = data.instance.get_node(data.selected[0]);
			loadFxPersonInfo(snode);
		//}
	 });
}
		
//点击树节点后触发的事件
function loadFxPersonInfo(snode){
	var name = snode.text;
	var id = snode.id;
	//alert(name + "-" + snode.original.orgCode);
	
	//显示表格
	$("#tableDiv").show();
	
	//------------------加载用户列表,支持分页-------------------------
	loadFxPersonsByOrg(1, id);			
}

// 根据组织机构ID加载分销客列表，ajax分页
function loadFxPersonsByOrg(page, orgId) {
	// 替换掉列表内容，显示为： 正在努力加载中....
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	var keyword = $("#keyword").val();
	
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_fxPersonList.Q",
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
				pageBarDiv += "[<a href='###' onclick=\"loadFxPersonsByOrg(" + prePage + ","+ orgId +")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"loadFxPersonsByOrg(" + i + ","+ orgId +")\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"loadFxPersonsByOrg(" + nextPage + ","+ orgId +")\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='loadFxPersonsByOrg(this.options[selectedIndex].value, "+ orgId +")'>";
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
					resumeList += "<TD>" + zb.openId.substring(0,3) + "...<a class=\"btn btn-success\" href=\"javascript:void(0)\" onclick=\"art.dialog('"+ zb.openId +"')\">Show</a></TD>";
					resumeList += "<TD>" + zb.email + "</TD>";
					resumeList += "<TD>" + zb.city + "</TD>";
					resumeList += "<TD>" + zb.province + "</TD>";
					resumeList += "<TD>" + zb.nickName + "</TD>";
					resumeList += "<TD>" + zb.orgname + "</TD>";
					resumeList += "<TD>" + zb.telephone + "</TD>";
					resumeList += "<TD>" + zb.statusStr + "</TD>";
					resumeList += "<TD>" + zb.yj/100 + "元</TD>";//该分销客所得佣金
					resumeList += "<TD><a class=\"btn btn-danger \" href=\"javascript:void(0)\" onclick='showOrders("+ zb.id +")'>查看</a></TD>";//该分销客所得佣金
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


//查看分销客订单
function showOrders(uid){
	 window.location.href = CONFIG.context + "/admin/orders_manager.Q?personId=" + uid;
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