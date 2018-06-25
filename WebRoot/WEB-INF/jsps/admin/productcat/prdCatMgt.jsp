<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
	    <script src="<%=request.getContextPath() %>/js/jquery/jquery.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/js/jstree/themes/default/style.min.css" />
	    <script src="<%=request.getContextPath() %>/js/jstree/jstree.min.js"></script>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/prdCat.js"></SCRIPT>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/ajaxfileupload.js"></SCRIPT>
	</head>
	<body onload="loadPrdCat()">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m1,msub12" />

		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span4">
						<div class="widget-box">
						<button class="btn btn-primary" onclick="addCompany(1)">新增一级分类</button>
							<div class="widget-title">
								<span class="icon"> <i class="icon-file"></i> </span>
								<h5>
									商品分类
								</h5>
							</div>
							<div class="widget-content nopadding">
								 <div id="myTree"></div>
							</div>
						</div>

					</div>
					<!-- 右侧区域 -->
					<div class="span8">
					
						<div id="info1" style="display: none; ">
							<button class="btn btn-success"  onclick="showAdd()">新增子分类</button>
							<button class="btn btn-success"  onclick="showModify()">修改分类</button>
							<button class="btn btn-success"  id="delBut" onclick="deleteNode()">删除分类</button>
							<div id="addOrg" style="display: none;">
								<hr>
								分类名称：<input type="text" id="newOrgName" >
								<!-- 如果是一级分类，需要显示图片 -->
								<div id='catpic'>
								分类图片：<input type="file" name="filePath" id="filePath">
								<img src='' id="catpicUrl" width="100" height="80"><br>
								<font color=red>推荐图片尺寸：300*155</font>
								</div>
								<!-- 修改和添加，都显示此div，通过op的值后台判断是新增还是更新 -->
								<input type="hidden" id="op" value="a">
								<button class="btn btn-inverse" onclick="addSubOrg()">确定</button>
								<button class="btn btn-inverse" onclick="cancelAdd()">取消</button>
							</div>
							<hr>
							<!-- 此处显示规格信息 -->
							<div id="addPrdSpec" style="display: none;">
								<div class="widget-box">
									<div class="widget-title"><h5>新增规格</h5></div>
									<div class="widget-content notify-ui" >
										<input type="text" value="" id="newPrdSpec" >
										<button class="btn btn-inverse" onclick="addPrdSpec(2)">确定</button>
										<button class="btn btn-inverse" onclick="hideDiv('addPrdSpec')">取消</button>
									</div>
								</div>
							</div>
							<div class="widget-box">
					          <div class="widget-title"> <span class="icon"> <i class="icon-hand-right"></i> </span>
					            <h5>产品规格</h5><button class="btn btn-inverse" onclick="addPrdSpec(1)">增加规格</button>
					          </div>
					          <div class="widget-content notify-ui" id="specList">
					          	<!-- 产品规格显示在这里 -->
					          </div>
					        </div>
					        <!-- 规格结束 -->
					        
						</div>
						<div id="info2">
							<h1 style="color: #00008B">请点击左侧的分类节点进行分类管理。</h1>
						</div>
						
						<!-- 总经销 -->
						<div id="info33" style="display: none;"><!-- topOrgName, telephone, email -->
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>新增一级分类</h5>
						         </div>
								 <div class="widget-content ">
						          	<ul>
						                <li>
											分类名称：<input type="text" id="topCatName" ><br>
											<!-- 如果是一级分类，需要显示图片 -->
											分类图片：<input type="file" name="filePathTop" id="filePathTop">
											<img sr='' id="catpicUrl" width="100" height="80"><br>
											<font color=red>推荐图片尺寸：300*155</font><br>
											<button class="btn btn-inverse" onclick="addCompany(2)">确定</button>
											<button class="btn btn-inverse" onclick="$('#info33').hide()">取消</button>
						                </li>
						             </ul>
						          </div>
							</div>
						</div>
					</div>
				</div>
			<input type="hidden" id="orgId" value="0">
			<input type="hidden" id="currentNodeName" value="">
			<input type="hidden" id="isroot" value="">
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
			
	</body>
</html>
