 

// 根据组织机构ID加载分销客列表，ajax分页
function initData(page) {
	// 替换掉列表内容，显示为： 正在努力加载中....
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_getCashApplyRecord.Q",
		data : "page=" + page + "&limit=20", // 初始化显示第一页，每页50条
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
				pageBarDiv += "[<a href='###' onclick=\"initData(" + prePage +  ")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"initData(" + i +  ")\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"initData(" + nextPage  +")\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='initData(this.options[selectedIndex].value )'>";
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
				//  <th>序号</th><th>提现金额</th><th>提现日期</th>  <th>状态</th><th>操作</th>
				var resumeList = "";
				// 第二步：加载列表当页数据
				for (var j = 0; j < list.length; j++) {
					var zb = list[j];
					resumeList += "<TR class=\"rsmTr\">";
					resumeList += "<TD>" + (j+1) + "</TD>";
					resumeList += "<TD>" + zb.applyFee/100 + " 元</TD>";
					resumeList += "<TD>" + zb.applyDate + "</TD>";
					resumeList += "<TD>" + zb.flagStr + "</TD>";
					if(zb.flag == 0){//处理中的可以取消
						resumeList += "<TD><a class=\"btn btn-danger \" href=\"javascript:void(0)\" onclick='cancelApply("+ zb.id +")'>取消</a></TD>";//该分销客所得佣金
					}else{
						resumeList += "<TD><a class=\"btn btn-danger \" href=\"javascript:void(0)\"  style='background:#ccc'>无法取消</a></TD>";//该分销客所得佣金
					}
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

//取消提现
function cancelApply(id){
	if(window.confirm("确定要取消提现吗？")){
		$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_cancelApply.Q",
			data: "id=" + id,
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog("取消成功！");
					initData(1);
				}else{
					art.dialog("取消失败，可能管理员已处理或系统故障！");
				}
			}
		});
	}
}
 