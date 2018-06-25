<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/cust.tld"%>
<!-- 系统管理员菜单 -->
<script type="text/javascript">
	$(function(){
	    var menuId;
	    var subMenuId;
	    var tab = $("#tab").val();
	    if(tab && tab.length > 0){
	    	var menuIds = tab.split(",");
		    if(menuIds.length ==2){
		    	menuId = menuIds[0];
		    	subMenuId = menuIds[1];
		    }else{
		    	menuId = menuIds[0];
		    }
		    
		    if(subMenuId != null){
		    	$("#" + subMenuId).addClass("active");
		    	$("#" + menuId).addClass("open");
		    }else{
		    	$("#" + menuId).addClass("active");
		    }
	    }
	});
	
</script>
<style>
#sidebar ul li ul li{
text-indent:30px;}

</style>
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 后台管理系统</a>
  <ul>
 
        <li class="submenu" id="m2"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>公众号管理</span> </a>
      <ul style="display: block;">
      <!-- 
       <li id="msub22"><a href="<%=request.getContextPath()%>/admin/fx_fxAuditList.Q" id="auditMenu"> 待审核 </a></li>
       -->
       <li id="msub21"><a href="<%=request.getContextPath()%>/admin/fx_fxMember.Q">公众号管理</a></li>
      </ul>
    </li>
    <w:permission permissionId="<%=PermissionId.SYSTEM_MGMT%>">
    <li class="submenu" id="m4"> <a href="javascript:void(0);"><i class="icon icon-cog"></i> <span>系统管理</span></a>
    	<ul style="display: block;">
    	 <%--<li id="msub81"><a href="<%=request.getContextPath()%>/admin/admin_wxmenu.Q">自定义菜单管理</a></li>
    	  --%>
    	  <w:permission permissionId="<%=PermissionId.SYSTEM_MGMT_BACKUP%>">
    	 <li id="msub90"><a href="<%=request.getContextPath() %>/admin/system_paramConfig.Q">微信公众号参数设置</a></li>
    	 </w:permission> 
	    </ul>
    </li>
    </w:permission>
    <li class="submenu" id="m7"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>权限管理</span> </a>
      <ul style="display: block;">
       <w:permission permissionId="<%=PermissionId.PERMISSION_MGT_ADMIN%>"> <li id="msub71"><a href="<%=request.getContextPath()%>/admin/admin_manager.Q">管理员列表</a></li></w:permission>
       <%-- <w:permission permissionId="<%=PermissionId.PERMISSION_MGT_ROLE%>"> <li id="msub72"><a href="<%=request.getContextPath()%>/admin/sec_roleManager.Q">角色管理</a></li></w:permission> --%>
      </ul>
    </li>
  
  </ul>
</div>
