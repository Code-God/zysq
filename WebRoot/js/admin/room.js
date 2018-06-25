
function initBookRoomList(page){
		var cls = "";
		//替换掉列表内容，显示为： 正在努力加载中....
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/hotel_getBookList.Q",
			data : "page=" + page + "&limit=10",
			dataType : "json",
			success : function(data) {
				if(data.result == 'fail'){
					alert("对不起，数据获取失败！");
					$("#dataList").html("");
					return;
				}else if(data.result == "null"){
					$("#dataList").html("<tr><td colspan=10>暂时没有记录。</td></tr>");
					$("#pjCount").html("0");
					return;
				}
				if(data.datas.length == 0){
					$("#dataList").html("<li>暂时没有记录。</li>");
					$("#pageBar").remove();
				}else{
					
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
				pageBarDiv += "[<a href='###' onclick=\"initBookRoomList(" + prePage + ")\">前一页</a>]";
				if (totalPage >= 1) {
					for (var i = 1; i <= totalPage; i++) {
						if (curPage == i) {
							pageBarDiv += "<b>" + i + "</b>";
						} else {
							pageBarDiv += "<a href='###' onclick=\"initBookRoomList(" + i + ")\" >" + i + "</a>";
						}
					}
				}
				pageBarDiv += "[<a href='###' onclick=\"initBookRoomList(" + nextPage + ")\">后一页</a>]";
				pageBarDiv += "<select style='width:90px' onchange='initBookRoomList(this.options[selectedIndex].value)'>";
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
					
					/*
					 <th>序号</th>
	                  <th>预约者</th>
	                  <th>电话</th>
	                  <th>预约房间</th>
	                  <th>数量</th>
	                  <th>开始时间</th>
	                  <th>结束时间</th>
	                  <th>提交日期</th>
	                  <th>操作</th>
					*/
					var html = "";
					//第二步：加载列表当页数据
					for(var j=0; j< data.datas.length; j++){
						var zb = data.datas[j];
						html += "<tr>";
						html += "<td>"+ (j+1) +"</td>";
						html += "<td>"+ zb.bookUser +"</td>";
						html += "<td>"+ zb.telephone +"</td>";
						html += "<td>"+ zb.prdName +"</td>";
						html += "<td>"+ zb.booknum +"</td>";
						html += "<td><font color=\"#696969\">"+ zb.bookDate +"</font></td>";
						html += "<td>"+ zb.arriveTime +"</td>";
						html += "<td>"+ zb.submitDate +"</td>";
						html += "<td id='status"+ zb.id +"'>"+ zb.bstatusStr +"</td>";
						if(zb.bstatus == 1){
							html += "<td><button onclick=\"cancelBook("+ zb.id +",\'status"+ zb.id +"\')\" class=\"btn btn-success\">取消</button></td>";
						}else{
							html += "<td>无</td>";
						}
						html += "</tr>";
					}
					$("#dataList").append(html);
				}
			}
		});
	}
	
	//取消预订
	function cancelBook(id, statusTd){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/hotel_cancelBook.Q",
			data : "id=" + id,
			dataType : "json",
			success : function(data) {
				if(data.msg == "ok"){
					art.dialog.tips("操作成功！", 1.5);
					$("#" + statusTd).html("<font color=red>已取消</font>");
				}else{
					art.dialog.tips("操作失败！", 1.5);
				}
			}
		});
	}
	
	function getMorePj(prdId){
		var np = parseInt($("#nextPage").val());
		var tp = parseInt($("#totalPage").val());
		if( np > tp){
			art.dialog("已经没有了。");
			return;
		}else{
			initPj(np, prdId);
		}
	}
