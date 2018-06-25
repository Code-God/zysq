<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
<script type="text/javascript">
		 //初始化简历列表，ajax分页
	function initList(page){
		//替换掉列表内容，显示为： 正在努力加载中....
		$("#dataList").html("<img src='<%=request.getContextPath()%>/images/loading.gif'> ");
		
		//alert(name+"|" + school + "|" + sex);
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_getKeyWords.Q",
			data: "page="+ page +"&limit=20", //初始化显示第一页，每页20条
			dataType : "text",
			success : function(data) {
				if (data != "fail" && data != 'null') {
					$("#dataList").html("");
					$("#pageBar").html("");
					var page = eval("(" + data + ")");
					var nextPage =  (parseInt(page.page) + 1) >= page.totalPage ? page.totalPage :(parseInt(page.page) + 1);
					var curPage = page.page;
					var prePage = (page.page - 1 <= 1) ? "1" : (page.page - 1);
					var totalPage = page.totalPage;
					var pageBarDiv = "";
					//分页样式。。。。
					pageBarDiv += "<div style=\"LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right\">当前第【"+ curPage +"】页,总共【<b>"+ page.total +"</b>】条记录 ";
					pageBarDiv += "[<a href='###' onclick=\"initList("+ prePage +")\">前一页</a>]";
					if(totalPage >= 1){
						for(var i=1; i <= totalPage; i++){
							if(curPage == i){
								pageBarDiv += "<b>"+ i +"</b>";
							}else{
								pageBarDiv += "<a href='###' onclick=\"initList("+ i +")\" >"+ i +"</a>";
							}
						}
					}
					pageBarDiv += "[<a href='###' onclick=\"initList("+ nextPage +")\">后一页</a>]";
					pageBarDiv += "<select onchange='initList(this.options[selectedIndex].value)'>";
					if(totalPage >= 1){
						for(var i=1; i<=totalPage; i++){
							if(curPage == i){
								pageBarDiv += "<option value='"+ i +"' selected='selected'>第"+ i +"页</option>";
							}else{
								pageBarDiv += "<option value='"+ i +"'>第"+ i +"页</option>";
							}
						}
					}
					pageBarDiv += "</select>";
					pageBarDiv += "</div>";
					$("#pageBar").html(pageBarDiv);
					
					var list = page.datas;
					//表头
					var resumeList = "<tr style='thead'><TD width=50px>序号</TD><TD width=50px>规则名</TD><TD>关键字</TD><TD width=60px>回复类型</TD><TD>回复内容</TD><TD width=100px><INPUT onclick=selectallbox() type=checkbox id=checkAll name=checkAll>操作</TD></TR>";
					 
					//第二步：加载列表当页数据
					for(var j=0; j< list.length; j++){
						var zb = list[j];
						var openId = zb.openId;
						var url = "<a href='"+ zb.respContent +"' target='_blank'>"+zb.respContent+"</a>";
						var type = "";
						var respHtml = "";
						if(zb.respType=="news"){
							type = "<font color=red>图文</font>";
							if(zb.respContent.indexOf("{") == 0){
								//处理图文显示
								var r = eval("("+ zb.respContent + ")");
								//<div class='tw'><div><img src='' width="58px" height="60px"/></div><div>adfadfadf</div></div>
								respHtml = "<div class='tw'><div><a href='"+ r.twUrl +"' target='_blank'><img src='"+r.picUrl + "' width=\"58px\" height=\"60px\"/></a></div><div style='color:gray'>"+ r.twdesc.substring(0,10) +"</div></div>";
							}else{
								respHtml = zb.respContent;
							}
						}else if(zb.respType=="multi"){
							type = "<font color=orange>多图文</font>";
							if(zb.respContent.indexOf("[{") == 0){
								//处理多图文显示
								var tws = eval("("+ zb.respContent + ")");
								for(var i=0; i< tws.length; i++){
									respHtml += "<div class='tw'><div><a href='"+ tws[i].twUrl +"' target='_blank'><img src='"+tws[i].picUrl + "' width=\"58px\" height=\"60px\"/></a></div><div  style='color:gray'>"+ tws[i].twdesc.substring(0,10) +"</div></div>";
								}
							}else{
								respHtml = zb.respContent;
							}
							//<div class='tw'><div><img src='' width="58px" height="60px"/></div><div>adfadfadf</div></div>
							
						}else{
							type = "<font color=gray>文本</font>";
							respHtml = zb.respContent;
						}
						
						
						resumeList += "<TR class=\"rsmTr\">";
						resumeList += "<TD>"+ (j+1) +"</TD>";
						resumeList += "<TD>"+ zb.ruleName +"</TD>";
						resumeList += "<TD><font color=red>"+ zb.kw +"</font></TD>";
						resumeList += "<TD>"+ type +"</TD>";
						resumeList += "<TD>"+ respHtml +"</TD>";
						resumeList += "<TD>";
						resumeList += "&nbsp;&nbsp;<a href='###' onclick='del("+ zb.id +")'>删除</a>";
						resumeList += "&nbsp;&nbsp;<a href='#001' onclick='edit(\""+ zb.id +"\")'>编辑</a>";
						resumeList += "</TD>";
						resumeList += "</TR>";
					}
					
					$("#dataList").html(resumeList);
				}else{
					$("#dataList").html("数据加载失败。请<a href='###' onclick='initList(1)'>刷新</a>重试。");
				}
			}
		});
	}
	
	var ct = 2;
	/**
	 * 增加图文
	 */
	function addMulti(title, desc, picUrl, url){
		var tw = "<div style=\"margin: 5px\" id='tw"+ ct +"'>";
			tw += "<hr/>";
			tw += "图文标题：<input type=\"text\" value=\""+title+"\" size=\"50\" id=\"title\" name=\"title\"><span class=\"org\">*</span>&nbsp;<br>";
			tw += "图文描述：<input type=\"text\" value=\""+desc+"\" size=\"50\" id=\"desc\" name=\"desc\"><span class=\"org\">*</span>&nbsp;<br>";
			tw += "图片地址：<input type=\"text\" value=\""+picUrl+"\" size=\"50\" id=\"picUrl\" name=\"picUrl\"><span class=\"org\">*</span>&nbsp;<br>";
			tw += "图文地址：<input type=\"text\" value=\""+url+"\" size=\"50\" id=\"twUrl\" name=\"twUrl\"><span class=\"org\">* 请填写素材库里的文章URL</span>&nbsp;<br>";
			tw += "<input type='button' value='删除' onclick='delTw(\"tw"+ ct +"\")' />";
			tw += "</div>";
		$("#forNews").append(tw);
		ct ++;
	}
	//删除此图文
	function delTw(id){
		$("#"+id).remove();		
	}
	
	
	
	function showDiv(msgType){
		clearForm();
		if(msgType == 'news'){//图文
			$("#forNews").show();
			$("#textTr").hide();
			$("#addTW").hide();
		}else if(msgType == 'multi'){//多图文
			$("#addTW").show();
			$("#textTr").hide();
			$("#forNews").show();
		}else{//文本
			$("#forNews").hide();
			$("#textTr").show();
			$("#addTW").hide();
		}
	}
	
	
	
	/**
	 * 编辑
	 */
	function edit(id){
		//根据ID ajax查找数据，并填充到页面
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/admin/admin_getRule.Q",
			data: "kid="+ id,
			dataType : "json",
			success : function(data) {
				var r = eval(data);
				showDiv(r.respType);
				$("#id").val(id);
				$("#ruleName").val(r.ruleName);
				$("#keywords").val(r.kw);
				$("#respType").attr("value", r.respType);
				
				if(r.respType == "text"){//文本
					$("#respContent").val(r.respContent);
				}else if(r.respType == "news"){
					//先清空多图文数据区域
					$("#forNews").empty();
					var tw = eval("("+r.respContent + ")");
					addMulti(tw.title,tw.twdesc,tw.picUrl,tw.twUrl);
				}else if(r.respType == "multi"){//多图文
					var tws = eval("("+r.respContent + ")");
					//先清空多图文数据区域
					$("#forNews").empty();
					//把多图文遍历出来显示在页面...
					for(var n=0; n< tws.length; n++){
						addMulti(tws[n].title, tws[n].twdesc, tws[n].picUrl, tws[n].twUrl);
					}
				}
				$("#op").val("edit");
				$("#submitId").val("保存");
			}
		});
	}
	
	function del(id){
		art.dialog({
			id:'delC',
		    content: '确定要删除吗？',
		    ok: function () { 
		    	$("#id").val(id);
		    	$("#op").val("del");//删除操作
		    	$("#form").submit();
		    },
		    cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
		});
	}
	
	
	//清除表单
	function clearForm(){
		$("#ruleName").val("");
		$("#keywords").val("");
		//$("#respType").attr("value", "text");
		$("#respContent").val("");
		$("#forNews").empty();
		ct = 1;
		//增加一行空的
		addMulti('','','','','');
		
		$("#id").val("");
		$("#op").val("new");
		$("#submitId").val("新增");
	}
	
	</script>
</head>
<body  onload="initList(1)">
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m8,msub82"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>关键字回复设置</h5>
        </div>
        <div class="widget-content nopadding">
           <!-- start -->
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15  >
					</TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD height=2 style="color: red">
									模块说明：<br>
										由于开启了开发模式， 微信后台的关键字回复界面无法继续使用，而以接口形式提供给第三方开发；<br>
										此模块实现了和微信官方一样的功能，支持关键字回复，回复方式支持以下三种：<br>
									1）文本推送回复<br>
									2）单图文推送回复<br>
									3）多图文推送回复<br>
									<hr>
								</TD>
							</TR>
						</TABLE>
						<form action="<%=request.getContextPath() %>/admin/admin_saveOrUpdateKeyWords.Q"   method="POST" id="form">
							<table width="780" border="0" align="center" cellSpacing=0 cellPadding=5 class="resTable">
								<tr>
									<td colspan="4" class="thead" style="background: #98FB98">
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<font color='#006400'>▋&nbsp;</font>规则名：
										<input type="text" name="ruleName" id="ruleName">
										<span class='org'>*</span>&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<font color='#006400'>▋&nbsp;</font>关键字（请用"|"分割不同的关键字，<font color=red>禁止换行</font>，如：“hi|你好|在吗？|请问”）：<hr>
										<textarea rows="5" cols="120" name="keywords" id="keywords" style="overflow: auto;width: 80%"></textarea>
										<span class='org'>*</span>&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<font color='#006400'>▋&nbsp;</font>回复类型：
										<select name="respType" id="respType" onchange="showDiv(this.value)">
											<option value="news" >
												图文
											</option>
											<option value="multi" >
												多图文
											</option>
											<option value="text" selected="selected">
												文本
											</option>
										</select>
										<span class='org'>*</span>&nbsp;
									</td>
								</tr>
								<tr id="textTr">
									<td colspan="4">
										<font color='#006400'>▋&nbsp;</font>请填写自动回复的文本内容：
										<textarea rows="3" cols="120" name="respContent" id="respContent" style="overflow: auto"></textarea>
										<span class='org'>*</span>&nbsp;
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<input type="button" id="addTW" value="新增" onclick="addMulti('','','','')" style="display: none;">
										<br/>
										<div id="forNews" style="display: none; border: solid 1px #ccc;">
											<div id="tw1" style="margin: 5px">
												图文标题：<input type="text" id="title" name="title" size="50"><span class='org'>*</span>&nbsp;<br/>
												图文描述：<input type="text" id="desc" name="desc" size="50"><span class='org'>*</span>&nbsp;<br/>
												图片地址：<input type="text" id="picUrl" name="picUrl" size="50"><span class='org'>*</span>&nbsp;<br/>
												图文地址：<input type="text" id="twUrl" name="twUrl" size="50"><span class='org'>* 请填写素材库里的文章URL</span>&nbsp;<br/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="4" align="center">
										<input type="hidden" name="op" id="op" value="new">
										<input type="hidden" name="id" id="id" value="0">
										<button class="btn btn-primary" id="submitId" type="submit">新增</button>
										<button class="btn btn-danger" id="clear" type="button" onclick="clearForm()">清空</button>
										&nbsp;<br/>
										<c:if test="${info != null}">
											<font color="red">${info}</font>
										</c:if>
									</td>
								</tr>
							</table>
						</form>
						
						<font color='#006400'>▋&nbsp;</font>当前已有的关键字回复规则：
						<TABLE id=grid
							style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
							cellSpacing=1 cellPadding=2 rules=all border=0>
							<TBODY id="dataList">
								<TR style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
									<TD>
										序号
									</TD>
									<TD>
										关键字
									</TD>
									<TD>
										回复类型
									</TD>
									<TD>
										回复内容
									</TD>
									<TD>
										<INPUT onclick=selectallbox(); type=checkbox name=checkAll>
										操作
									</TD>
								</TR>
								<TR class="rsmTr">
									<TD>
										1
									</TD>
									<TD>
										<font color=red> 红茶|论道红茶|红茶坊|工夫茶|竹叶青红茶|竹派红茶</font>
									</TD>
									<TD>
										图文
									</TD>
									<TD>
										<div class='tw'><div><img src='' width="58px" height="60px"/></div><div>adfadfadf</div></div>
									</TD>
									<TD>
										修改  
										
										删除
									</TD>
								</TR>
							</TBODY>
						</TABLE>
						<SPAN id="pageBar">
						</SPAN>	
					</TD>
					<TD width=15  >
					</TD>
				</TR>
			</TBODY>
		</TABLE>

           <!-- end -->
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
