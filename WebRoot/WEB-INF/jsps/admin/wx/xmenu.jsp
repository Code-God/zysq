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
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/xmenu.js"></SCRIPT>
		<script type="text/javascript">
		</script>
	</head>
	<body onload="loadMenu()">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m8,msub81"/>

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
						    <!-- 如果不是系统管理员，不显示此按钮 -->
						    <div class="widget-title" style="text-align: center;margin: 5px">
								<button class="btn btn-primary" onclick="addTopMenu(1)">新增一级菜单</button>
								<button class="btn btn-danger" onclick="publish2Wx()">同步到微信平台</button>
							</div>
							<div class="widget-title">
								<span class="icon"> <i class="icon-file"></i> </span>
								<h5>
									微信公众号自定义菜单
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
							<button class="btn btn-success"  onclick="showAdd()">新增子菜单</button>
							<button class="btn btn-success"  onclick="showModify()">修改节点</button>
							<button class="btn btn-success"  onclick="deleteNode()">删除节点</button>
							<div id="addOrg" style="display: none;">
								<hr>
								菜单名称：<input type="text" id="curMenuName"><br>
								菜单类型：
								<br><input type="radio" id="cmType_1" value="0" name="cmType" checked="checked" checked="checked"  onclick="changeTypeSub(this)">无（作为父菜单,注意,二级菜单不能选择此项;一级菜单,没有子菜单的也不能选择此项）
								<br><input type="radio" value="click" name="cmType" onclick="changeTypeSub(this)">click(此类型一般需要定制开发)
								<br><input type="radio" value="view" name="cmType"  onclick="changeTypeSub(this)">view（此类型较常用，直接填写URL即可）
								<!-- 
								<input type="radio" value="scancode_push" name="type">
								<input type="radio" value="scancode_waitmsg" name="type">
								 -->
								<br>
								<div id="cmparam" style="display: none;">
								key：<input type="text" id="curKey" ><br>
								url：<input type="text" id="curUrl" disabled="disabled" style="width: 70%"><br>
								</div>
								<input type="hidden" id="op" value="a">
								<input type="hidden" id="dbId" value="">
								<button class="btn btn-inverse" onclick="addSubOrg()">确定</button>
								<button class="btn btn-inverse" onclick="cancelAddSub()">取消</button>
							</div>
							<hr>
							
						</div>
						<!-- 一级菜单-->
						<div id="info33" style="display: none;"><!-- topOrgName, telephone, email -->
							<div class="widget-box">
								<div class="widget-title"> <span class="icon"> <i class="icon-arrow-right"></i> </span>
						            <h5>一级菜单</h5>
						         </div>
								 <div class="widget-content ">
						          	<ul>
						                <li>
											菜单名称：<input type="text" id="menuName"><br>
											菜单类型：
											<br><input type="radio" value="0" name="mType" checked="checked" checked="checked"  onclick="changeType(this)">无（作为父菜单）
											<br><input type="radio" value="click" name="mType" onclick="changeType(this)">click(此类型一般需要定制开发)
											<br><input type="radio" value="view" name="mType"  onclick="changeType(this)">view（此类型较常用，直接填写URL即可）
											<!-- 
											<input type="radio" value="scancode_push" name="type">
											<input type="radio" value="scancode_waitmsg" name="type">
											 -->
											<br>
											<div id="mparam" style="display: none;">
											key：<input type="text" id="key" ><br>
											url：<input type="text" id="url" disabled="disabled" style="width: 70%"><br>
											</div>
											<button class="btn btn-inverse" onclick="addTopMenu(2)">确定</button>
											<button class="btn btn-inverse" onclick="cancelAdd()">取消</button>
						                </li>
						             </ul>
						          </div>
							</div>
						</div>
						<div id="info3" style="display: none;">
							<button class="btn btn-success"  onclick="showAdd()">新增子节点</button>
						</div>
					</div>
				</div>
			<input type="hidden" id="currentNodeName" value="">
			<input type="hidden" id="orgId" value="<s:property value="#session.adminOrg.id"/>">
			</div>
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
			<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
	</body>
</html>
