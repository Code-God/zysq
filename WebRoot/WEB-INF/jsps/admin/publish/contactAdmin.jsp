<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title><%=Version.getInstance().getSystemTitle()%></title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<script type="text/javascript">
	
	function loadContatcs(orgId){
		//alert(orgId);
		load(1);
		load(2);
		load(3);
		load(4);
		load(5);
	}
	
	function load(locId){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_loadContact.Q",
			dataType : "json",
			data : {
				"locId" : locId
			},
			success : function(data) {// 回调
				if(data.msg == "ok"){
					 $("#ctitle"+locId).val(data.result.ctitle);
					 $("#areaCode"+locId).val(data.result.areaCode);
					 $("#telCode"+locId).val(data.result.telCode);
				}else if(data.msg == "fail"){
					$("#info"+locId).html("对不起，加载失败！");
				}
			}
		});
	}
	
	//更新页面上对应位置的联系方式
	function updateContacts(locId){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/admin_updateContact.Q",
			dataType : "json",
			data : {
				"locId" : locId,
				"ctitle" : $("#ctitle"+locId).val(),
				"areaCode" : $("#areaCode"+locId).val(),
				"telCode" : $("#telCode"+locId).val()
			},
			success : function(data) {// 回调
				if(data.msg == "ok"){
					 $("#info"+locId).html("更新成功！");
				}else if(data.msg == "fail"){
					$("#info"+locId).html("对不起，加载失败！");
				}
			}
		});
	}
	
</script>
	</head>
	<body onload="loadContatcs(<%=request.getAttribute("orgId")%>)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m8,msub84" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span7">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i> </span>
							<h5>
								1号区域设置
							</h5>
						</div>
						<div class="widget-content">
							 标题 ： <input type="text" style="width: 120px; height: 30px" name="ctitle1" id="ctitle1" value=" " /><br>
							 区号 ： <input type="text" style="width: 120px; height: 30px" name="areaCode1" id="areaCode1" value=" " /><br>
							 电话 ： <input type="text" style="width: 120px; height: 30px" name="telCode1" id="telCode1" value=" " /><br>
							 <button type="button" class="btn btn-success" onclick="updateContacts(1);" id="saveButton">
								更新
							</button>
							<div id="info1"  style="color: red;"></div>
						</div>
					</div>
					</div>
					<!-- 2号 -->
					<div class="span5">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i> </span>
							<h5>
								2号区域设置
							</h5>
						</div>
						<div class="widget-content">
							 标题 ： <input type="text" style="width: 120px; height: 30px" name="ctitle2" id="ctitle2" value=" " /><br>
							 区号 ： <input type="text" style="width: 120px; height: 30px" name="areaCode2" id="areaCode2" value=" " /><br>
							 电话 ： <input type="text" style="width: 120px; height: 30px" name="telCode2" id="telCode2" value=" " /><br>
							 <button type="button" class="btn btn-success" onclick="updateContacts(2);" id="saveButton">
								更新
							</button>
							<div id="info2"  style="color: red;"></div>
						</div>
					</div>
					</div>
				</div>
				
			
			</div>

			<!-- 第二排 -->
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span4">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i> </span>
							<h5>
								3号区域设置
							</h5>
						</div>
						<div class="widget-content">
							 标题 ： <input type="text" style="width: 120px; height: 30px" name="ctitle3" id="ctitle3" value=" " /><br>
							 区号 ： <input type="text" style="width: 120px; height: 30px" name="areaCode3" id="areaCode3" value=" " /><br>
							 电话 ： <input type="text" style="width: 120px; height: 30px" name="telCode3" id="telCode3" value=" " /><br>
							 <button type="button" class="btn btn-success" onclick="updateContacts(3);" id="saveButton">
								更新
							</button>
							<div id="info3" style="color: red;"></div>
						</div>
					</div>
					</div>
					<!-- 4号 -->
					<div class="span4">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"> <i class="icon-align-justify"></i> </span>
								<h5>
									4号区域设置
								</h5>
							</div>
							<div class="widget-content">
							 标题 ： <input type="text" style="width: 120px; height: 30px" name="ctitle4" id="ctitle4" value=" " /><br>
							 区号 ： <input type="text" style="width: 120px; height: 30px" name="areaCode4" id="areaCode4" value=" " /><br>
							 电话 ： <input type="text" style="width: 120px; height: 30px" name="telCode4" id="telCode4" value=" " /><br>
								 <button type="button" class="btn btn-success" onclick="updateContacts(4);" id="saveButton">
									更新
								</button>
								<div id="info4"  style="color: red;"></div>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"> <i class="icon-align-justify"></i> </span>
								<h5>
									5号区域设置
								</h5>
							</div>
							<div class="widget-content">
							 标题 ： <input type="text" style="width: 120px; height: 30px" name="ctitle5" id="ctitle5" value=" " /><br>
							 区号 ： <input type="text" style="width: 120px; height: 30px" name="areaCode5" id="areaCode5" value=" " /><br>
							 电话 ： <input type="text" style="width: 120px; height: 30px" name="telCode5" id="telCode5" value=" " /><br>
								 <button type="button" class="btn btn-success" onclick="updateContacts(5);" id="saveButton">
									更新
								</button>
								<div id="info5"  style="color: red;"></div>
							</div>
						</div>
					</div>
					 
				</div>
				
			
			</div>


		</div>
		<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

	</body>
</html>
