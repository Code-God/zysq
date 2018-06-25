 

// 根据组织机构ID加载分销客列表，ajax分页
function initData(page) {
	// 替换掉列表内容，显示为： 正在努力加载中....
	$("#dataList").html("<tr height=30px><td>正在加载中...请稍后</td></tr>");

	$.ajax({
		type : "POST",
		url : CONFIG.context + "/admin/fx_allCashApplyRecord.Q",
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
				//  <th>序号</th><th>提现类型</th><th>提现金额</th><th>申请日期</th>  <th>申请人</th><th>操作</th>
				var resumeList = "";
				// 第二步：加载列表当页数据
				for (var j = 0; j < list.length; j++) {
					var zb = list[j];
					resumeList += "<TR class=\"rsmTr\">";
					resumeList += "<TD>" + (j+1) + "</TD>";
					if(zb.atype == 1){
						resumeList += "<TD><font color=blue>分销商</font></TD>";
					}else{
						resumeList += "<TD><font color=green>分销客</font></TD>";
					}
					resumeList += "<TD>" + zb.applyFee/100 + " 元</TD>";
					resumeList += "<TD>" + zb.applyDate + "</TD>";
					resumeList += "<TD>" + zb.nickName  + "</TD>";
					resumeList += "<TD>" + zb.flagStr  + "</TD>";
					resumeList += "<TD>";
					if(zb.flag == 0){
						resumeList += "<a class=\"btn btn-danger \" href=\"javascript:void(0)\" onclick='hasDone("+ zb.id +")'>确认放款</a>";//该分销客所得佣金
					}
					resumeList += "</TD>";
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
function hasDone(id){
	if(window.confirm("确定已经放款了吗？")){
		$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_cashApplyDone.Q",
			data: "id=" + id,
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					art.dialog("已处理！");
				}else{
					art.dialog("处理失败！");
				}
			}
		});
	}
}
 