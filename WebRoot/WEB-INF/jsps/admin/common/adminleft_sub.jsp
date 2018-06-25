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
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 后台管理系统</a>
  <ul>
    <!-- 只能看到自己或者下级分销商 -->
     <li class="submenu" id="m2"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>分销商管理</span> </a>
      <ul style="display: block;">
        <li id="msub22"><a href="<%=request.getContextPath()%>/admin/fx_fxAuditList.Q" id="auditMenu"> 待审核 </a></li>
        <li id="msub21"><a href="<%=request.getContextPath()%>/admin/fx_fxMember.Q">分销商管理</a></li>
	    <li id="msub23"><a href="<%=request.getContextPath()%>/admin/fx_fxPerson.Q">分销客管理</a></li>
	    <li id="msub24"><a href="<%=request.getContextPath()%>/admin/fx_fxYj.Q">我的佣金</a></li>
	    <li id="msub25"><a href="<%=request.getContextPath()%>/admin/fx_cashApllyRecord.Q">提现记录</a></li>
      </ul>
    </li>
    
     <w:permission permissionId="<%=PermissionId.PERMISSION_MGT%>">
     <li class="submenu" id="m7"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>权限管理</span> </a>
      <ul style="display: block;">
       <w:permission permissionId="<%=PermissionId.PERMISSION_MGT_ADMIN%>"> <li id="msub71"><a href="<%=request.getContextPath()%>/admin/admin_manager.Q">管理员列表</a></li></w:permission>
      </ul>
    </li>
    </w:permission>
    
    <w:permission permissionId="<%=PermissionId.ORDER_MGT%>"><li id="m5"><a href="<%=request.getContextPath()%>/admin/orders_manager.Q"><i class="icon icon-money"></i> <span>订单管理</span></a></li></w:permission>
    <w:permission permissionId="<%=PermissionId.REPORT_MGT%>">
    <li id="m10" class="submenu"> <a href="javascript:void(0);"><i class="icon icon-bar-chart"></i> <span>统计报表</span> </a>
      <ul style="display: block;">
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_MEMBER%>"><li id="msub101"><a href="<%=request.getContextPath() %>/admin/report_generateUserReport.Q">会员统计</a></li></w:permission>
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_FINANCE%>"><li id="msub102"><a href="<%=request.getContextPath() %>/admin/report_financeReport.Q">财务统计</a></li></w:permission>
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_PRODUCT%>"><li id="msub103"><a href="<%=request.getContextPath() %>/admin/report_productSales.Q">商品统计</a></li></w:permission>
      </ul>
    </li>
    </w:permission>
    
    <li class="submenu" id="m8"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>公众平台管理</span> </a>
      <ul style="display: block;">
      <!-- 
        <li id="msub81"><a href="<%=request.getContextPath()%>/admin/admin_queryMenu.Q">菜单管理</a></li>
       -->
        <li id="msub81"><a href="<%=request.getContextPath()%>/admin/admin_wxmenu.Q">自定义菜单管理</a></li>
        <li id="msub82"><a href="<%=request.getContextPath()%>/admin/admin_keywords.Q">关键字回复</a></li>
      </ul>
    </li>
    
  </ul>
</div>
