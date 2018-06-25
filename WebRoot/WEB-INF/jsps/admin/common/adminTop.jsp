<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<!--Header-part-->
<div id="header">
  <h2 style="padding:20px 0 0 20px; font-size: 16px;color:#e2e2e2;font-family: Microsoft YaHei, simsun; display:block;font-weight: normal;">找药神器管理系统</h2>
</div>
<!--close-Header-part--> 

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav" style="width: 100%;">
    <li class="" ><a title="" style="font-size: 10pt" href="<%=request.getContextPath() %>/admin/admin_modifyPwd.Q"><i class="icon icon-cog"></i> <span class="text">修改密码</span></a></li>
    <li class="" ><a title="" style="font-size: 10pt" href="<%=request.getContextPath() %>/admin/admin_logout.Q"><i class="icon icon-share-alt"></i> <span class="text">安全退出</span></a></li>
    <li><a style="color: #ffffff; font-size: 10pt"><i class="icon-info-sign"></i><span class="text" > 欢迎您：<s:property value="#session.SESSION_ADMIN.username"/>&nbsp;&nbsp;
    		<s:if test="#session.orgCode != null">(
	    			<s:if test="#session.orgCode.length() == 1">
	              		  您的角色是：【系统超级管理员】
	              	</s:if>
	              	<s:if test="#session.orgCode.length() == 3">
	              		 您的角色是：【平台管理员】负责区域：<s:property value="#session.adminOrg.city"/>
	              	</s:if>
	              	<s:if test="#session.orgCode.length() == 6">
	              		  您的角色是：【平台管理员】负责区域：<s:property value="#session.adminOrg.city"/>
	              	</s:if>)
              	</s:if>
              	</span>
              	<s:if test="#session.orgCode == null">
              		您的角色：<s:property value="#session.adminLevel"/>(<font color=#00FFFF><s:property value="#session.SESSION_ADMIN.province"/>-<s:property value="#session.SESSION_ADMIN.area"/></font>)
              	</s:if>
          </a>
    </li>
  </ul>
</div>
<input type="hidden" id="currentOrgCode" value="<s:property value="#session.orgCode"/>">
<!--close-top-Header-menu-->
<!--start-top-serch-->
<!-- 
<div id="search">
  <input type="text" placeholder="Search here..."/>
  <button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>
</div>
 -->
<!--close-top-serch-->
<!--sidebar-menu-->