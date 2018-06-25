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
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m8,msub81"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath() %>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>公众号菜单设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath() %>/admin/admin_updateMenu.Q" method="post" class="form-horizontal">
						<table width="780" border="0" align="center" cellSpacing=0 cellPadding=5 class="resTable">
							<tr>
								<td colspan="4"  class="thead" style="background: #98FB98">
									菜单结构代码（直接修改后，点保存即可更新, <font  color=red>由于微信客户端缓存问题，24小时才能看到效果，如果需要看到即时效果，请取消关注后再添加关注即可</font>）
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<c:if test="${menu != null}">
										<textarea rows="30" cols="120" name="menu" id="menu" style="overflow: auto;width: 100%">${menu }</textarea>
									</c:if>
									<c:if test="${menu == null}">
									<textarea rows="30" cols="120" name="menu" id="menu" style="overflow: auto;width: 100%">
{
    "button": [
        {
            "name": "关于我们",
            "sub_button": [
                {
                    "type": "click",
                    "name": "品牌故事",
                    "key": "U_1",
                    "sub_button": []
                }
            ]
        },
        {
            "type": "view",
            "name": "微商城",
            "url": "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx788f0bb83a4a427e&redirect_uri=http%3A%2F%2Fwww.91lot.com%2Ffenxiao%2Fwx%2Fwx_wxMall.Q&response_type=code&scope=snsapi_userinfo&state=20#wechat_redirect",
            "sub_button": []
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
									<button type="submit" id="submitId" class="btn btn-primary">保存</button>
									 &nbsp;
								</td>
							</tr>
							
						</table>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
