<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="wf_toolbar">
  <div class="grid_c1">
    <div class="mod_entry"> <span class="mr-10">${sessionScope.SESSION_USER == null ? '您好':sessionScope.SESSION_USER.nickName }，欢迎来到吴方商城！</span>
    <c:if test="${sessionScope.SESSION_USER == null }">
    	<a href="<%=request.getContextPath() %>/public/GoLogin.Q">登录</a>
    </c:if>
    <c:if test="${sessionScope.SESSION_USER != null }">
    	<a href="<%=request.getContextPath() %>/public/Logout.Q">退出</a>
    </c:if>
    
    <i class="mod_entry_gap">|</i><a href="<%=request.getContextPath() %>/public/regist_gotoreg.Q">注册</a> </div>
    <div class="mod_sitemap">
      <ul class="mod_sitemap_ul" id="j_sitemap">
        <li class="mod_sitemap_li"><a href="<%=request.getContextPath() %>/private/shopping_list.Q"><i class="sitemap_cart"></i>购物车<span class="green">${fn:length(sessionScope.SESSION_SHOP) }</span>件</a></li>
        <li class="mod_sitemap_li"><a href="<%=request.getContextPath() %>/private/account_userInfo.Q">我的吴方</a></li>
        <li class="mod_sitemap_li"><a>关于我们</a></li>
        <li class="mod_sitemap_li"><a>帮助中心</a></li>
      </ul>
    </div>
  </div>
</div>