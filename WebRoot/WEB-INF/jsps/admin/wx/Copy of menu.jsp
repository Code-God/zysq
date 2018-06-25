<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/common/taglib.jsp" %>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -10);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD id=Head1>
		<TITLE>【修改公众平台菜单】</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<LINK href="<%=request.getContextPath()%>/index/YHChannelApply.files/Style.css" type=text/css rel=stylesheet>
		<LINK href="<%=request.getContextPath()%>/index/YHChannelApply.files/Manage.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jQuery1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/artDialog.js?skin=green"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<META HTTP-EQUIV="pragma" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="Cache-Control" CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
		<META HTTP-EQUIV="expires" CONTENT="-1">
		<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css" />
		<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
	</HEAD>
	<BODY>
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
									当前位置：系统管理 > 微信公众平台菜单管理
								</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<html:form action="/admin?m=updateMenu" onsubmit="return validatePwd();"  method="POST" styleId="form">
						<table width="780" border="0" align="center" cellSpacing=0 cellPadding=5 class="resTable">
							<tr>
								<td colspan="4"  class="thead" style="background: #98FB98">
									菜单结构代码（直接修改后，点保存即可更新, <font  color=red>由于微信客户端缓存问题，24小时才能看到效果，如果需要看到即时效果，请取消关注后再添加关注即可</font>）
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<c:if test="${menu != null}">
										<textarea rows="30" cols="120" name="menu" id="menu" style="overflow: auto">${menu }</textarea>
									</c:if>
									<c:if test="${menu == null}">
									<textarea rows="30" cols="120" name="menu" id="menu" style="overflow: auto">
{
    "button": [
        {
            "name": "邮人课堂", 
            "sub_button": [
                {
                    "type": "click", 
                    "name": "邮政资讯", 
                    "key": "U_1"
                }, 
                {
                    "type": "click", 
                    "name": "业务学习", 
                    "key": "U_2"
                }, 
                {
                    "type": "click", 
                    "name": "在线辅导", 
                    "key": "U_3"
                },
                {
                    "type": "click", 
                    "name": "生活常识", 
                    "key": "U_4"
                }
            ]
        }, 
        {
            "name": "考试调查", 
            "sub_button": [
                {
                    "type": "click", 
                    "name": "在线考试", 
                    "key": "K_1"
                }, 
                {
                    "type": "click", 
                    "name": "在线调查", 
                     "key": "K_2"
                }
            ]
        }, 
        {
            "name": "培训信息", 
            "sub_button": [
                {
                    "type": "click", 
                    "name": "培训信息", 
                     "key": "P_1"
                },
                {
                    "type": "click", 
                    "name": "意见反馈",
                    "key": "P_2"
                },
                {
                    "type": "click", 
                    "name": "联系我们",
                     "key": "P_3"
                }
            ]
        }
    ]
}
									</textarea>
									</c:if>
									<span class='org'>*</span>&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center">
								<c:if test="${info != null}">
									${info }
								</c:if>
									<input type="submit" id="submitId" value="保存" class="hrbt">&nbsp;
								</td>
							</tr>
							
						</table>
						</html:form>
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
					<TD align=middle width="100%" background="<%=request.getContextPath()%>/index/YHChannelApply.files/new_025.jpg" height=15></TD>
					<TD width=15>
						<IMG src="<%=request.getContextPath()%>/index/YHChannelApply.files/new_026.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	<script type="text/javascript">
		/**
			 * 校验： 公司名称必须输入
			 */
			function validatePwd(){
				 var pass = true;
				
				if(pass){
					//禁用按钮
					$("#submitId").attr("disabled", "disabled");
					return true;
				}else{
					art.dialog({fixed : true,content : msg});
					return false;
				}
			}
	
	
	</script>		
	</BODY>
</HTML>
