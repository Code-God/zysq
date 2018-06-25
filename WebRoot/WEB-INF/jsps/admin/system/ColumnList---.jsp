<%@page language="java" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD id=Head1>
		<TITLE>用户列表</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="<%=request.getContextPath()%>/index/YHChannelApply.files/Style.css" type=text/css rel=stylesheet>
		<LINK href="<%=request.getContextPath()%>/index/YHChannelApply.files/Manage.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jQuery1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<META HTTP-EQUIV="pragma" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="Cache-Control" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="expires" CONTENT="-1">
		<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/common/config.js"></SCRIPT>
		<SCRIPT language=javascript src="<%=request.getContextPath()%>/js/admin/admin.js"></SCRIPT>
		<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
	</HEAD>
	<BODY onload="initColumns('<%=request.getParameter("t") %>')">
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15>
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_019.jpg" border=0>
					</TD>
					<TD width="100%" background="<%=request.getContextPath()%>/index/YHChannelApply.files/new_020.jpg" height=20></TD>
					<TD width=15>
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_021.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15 background="<%=request.getContextPath()%>/index/YHChannelApply.files/new_022.jpg">
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_022.jpg" border=0>
					</TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>
									<%if("1".equals(request.getParameter("t"))){ %>
									当前位置：系统管理 > 邮政资讯模块管理
									<%}else{ %>
									当前位置：系统管理 >在线学习模块管理
									<%} %>
								</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<TABLE borderColor=#cccccc cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
							<TBODY>
								<TR>
									<TD height=25>
										<INPUT class=button id=button1 type=button value="添加模块" name=button1 onclick="showAdd(1)">
										<div id="columnDiv" style="display: none;">
											模块名：
											<input type="text" name="columName" id="columnName">
											<input type="hidden" value="" id="colid" name="colid">
											<input type="hidden" value="add" id="op" >
											<input type="hidden" value="<%=request.getParameter("t") %>" name="model" id="model" >
											<INPUT class=button id=button2 type=button value="新增"  onclick="addOrUpdateColum()">
											<INPUT class=button id=button3 type=button value="取消"  onclick="showAdd(0)">
										</div>
									</TD>
								</TR>
								<TR>
									<TD>
										<TABLE id=grid
											style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
											cellSpacing=1 cellPadding=2 rules=all border=0>
											<TBODY id="dataList">
												<TR style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
													<TD>序号</TD>
													<TD>模块名</TD>
													<TD>
														<INPUT onclick=selectallbox(); type=checkbox name=checkAll>
														操作
													</TD>
												</TR>
												<TR class="rsmTr">
													<TD colspan="3">
														暂时没有记录...
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD align=right height=25>
										<INPUT id=boxListValue type=hidden name=boxListValue>
									</TD>
								</TR>
								<TR>
									<TD>
										<SPAN id="pageBar"> </SPAN>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
					<TD width=15 background="<%=request.getContextPath()%>/index/YHChannelApply.files/new_023.jpg">
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_023.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15>
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_024.jpg" border=0>
					</TD>
					<TD align=middle width="100%" background="<%=request.getContextPath()%>/index/YHChannelApply.files/new_025.jpg" height=15>=</TD>
					<TD width=15>
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_026.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</HTML>
