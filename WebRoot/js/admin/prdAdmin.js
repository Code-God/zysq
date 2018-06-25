//分销客页面专用JS
//通过树往下加载分销商机构
//从分销商的角度查询各级分销客
function loadTree(datas){
	$('#myTree').jstree({ 'core' : {
		'multiple' : false,//不允许多选
	    'data' : datas
	} }).on('changed.jstree', function (e, data) {
	    var snode = data.instance.get_node(data.selected[0]);
			loadPrdList(snode);
		//}
	 });
}
		
//点击树节点后触发的事件
function loadPrdList(snode){
	var name = snode.text;
	var id = snode.id;
	var orgCode = snode.original.orgCode;
	//alert(name + "-" + snode.original.orgCode);
	
	//显示表格
	$("#tableDiv").show();
	
	//------------------加载用户列表,支持分页-------------------------
	loadFxPersonsByOrg(1, orgCode);			
}

// 根据组织机构ID加载商品列表，ajax分页
function loadFxPersonsByOrg(page, orgCode) {
	// 替换掉列表内容，显示为： 正在努力加载中....
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	var keyword = $("#keyword").val();
	
	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_productListByOrg.Q",
		data : "page=" + page + "&limit=10&orgCode=" + orgCode, // 初始化显示第一页，每页50条
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
				pageBarDiv += "[<a href='###' onclick=\"loadFxPersonsByOrg(" + prePage + ",'"+ orgCode +"')\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"loadFxPersonsByOrg(" + i + ",'"+ orgCode +"')\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"loadFxPersonsByOrg(" + nextPage + ",'"+ orgCode +"')\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='loadFxPersonsByOrg(this.options[selectedIndex].value, '"+ orgCode +"')'>";
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
               <th>商品名</th>
               <th>商品编码</th>
               <th>商品规格</th>
               <th>商品价格</th>
               <th>折扣价</th>
               <th>所属分类</th>
               <th>发布日期</th>
               <th>评价数</th>
            */
				var resumeList = "";
				// 第二步：加载列表当页数据
				for (var j = 0; j < list.length; j++) {
					var zb = list[j];
					resumeList += "<TR class=\"rsmTr\">";
					resumeList += "<TD>" + (j+1) + "</TD>";
					resumeList += "<TD>" + zb.name +"</TD>";
					resumeList += "<TD>" + zb.prdCode + "</TD>";
					resumeList += "<TD>" + zb.prdStandard + "</TD>";
					resumeList += "<TD>" + zb.prdPrice + "</TD>";
					
					//全网销量
					resumeList += "<TD><span id=\"sctxt_"+ zb.id +"\" style=\"color:red\">"+ zb.prdSaleCount +"</span>";
                  	resumeList +=  "<button onclick=\"showScModDiv("+ zb.id +")\" class=\"label label-info btn btn-primary btn-mini\">修改</button>";
                  	resumeList +=  "<div id=\"prdSCDiv_"+ zb.id +"\" style=\"display: none;\">";
                  	resumeList +=  "<input type=\"text\" id=\"sc_"+ zb.id +"\" style=\"width: 40px\"><br>";
                  	resumeList +=  "<button onclick=\"modCount("+ zb.id +")\" class=\"btn-success btn-mini\">确定</button>";
                  	resumeList +=  "<button onclick=\"hideDiv("+ zb.id +")\" class=\"btn-success btn-mini\">取消</button>";
                  	resumeList +=	"</div></TD>";
					
					resumeList += "<TD>" + zb.prdDisPrice + "</TD>";
					resumeList += "<TD>" + zb.prdCatName + "</TD>";
					resumeList += "<TD>" + zb.createDateStr + "</TD>";
					resumeList += "<TD><font color=red>" + zb.pjcount + "</font></TD>";
					resumeList += "<TD><button class=\"btn btn-info\" type='button' onclick='generatePj("+ zb.id +")'>生成评价</button></TD>";
					resumeList += "</TR>";
				}

				$("#dataList").html(resumeList);
			} else {
				$("#dataList").html("数据加载失败。请<a href='###' onclick='initData(1)'>刷新</a>重试。");
			}
		}
	});
}


//更新销量
	function modCount(prdId){
		$.ajax({
			url:CONFIG.context + "/admin/products_modSaleCount.Q",
			data:{
				"prdId":prdId,
				"saleCount":$("#sc_" + prdId).val()
			},
			dataType:'text',
			success:function(data){
				if("ok"==data){
					//修改销量文本
					$("#sctxt_" + prdId).html($("#sc_" + prdId).val());
					art.dialog({
						time: 2,
						content: '更新销量成功！'
					});
					hideDiv(prdId);
				}else{
					art.dialog({
						time: 2,
						content: '删除失败！'
					});
				}
		    }
		});
	}
	
	//显示修改层
	function showScModDiv(prdId){
		$("#prdSCDiv_"+prdId).fadeIn("fast");
	}
	//隐藏
	function hideDiv(prdId){
		$("#prdSCDiv_"+prdId).fadeOut("fast");
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
		
//生成评价弹出框
function generatePj(prdId){
	art.dialog.open(CONFIG.context +'/admin/admin_generatePjInput.Q', {
	    title: '填写评价数量',
	    width : '30%',
	    height: 200,
	    // 在open()方法中，init会等待iframe加载完毕后执行
	    init: function () {
	    },
	    ok: function () {
	    	var iframe = this.iframe.contentWindow;
	    	var pjCount = iframe.document.getElementById("pjCount").value;
	    	//alert( "--" + prdId);
    		$.ajax({
				type : "POST",
				url : CONFIG.context +"/admin/admin_generatePj.Q?prdId=" + prdId + "&pjCount=" + pjCount,
				dataType : "json",
				success : function(data) {
					art.dialog("已成功生成" + pjCount + "条评论！");
					return true;
				}
			});
	    },
	    cancel: true
	});
}	