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
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 代理商后台管理系统</a>
  <ul>
    <li class="submenu" id="m8"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>公众平台管理</span> </a>
      <ul style="display: block;">
        <li id="msub81"><a href="<%=request.getContextPath()%>/admin/car_rescueManageList.Q">本地救援服务</a></li>
        <li id="msub83"><a href="<%=request.getContextPath()%>/admin/car_cleaningManageList.Q">本地洗车服务</a></li>
        <!-- 
        <li id="msub82"><a href="<%=request.getContextPath()%>/admin/admin_keywords.Q">财务管理</a></li>
         -->
      </ul>
    </li>
     <li id="m5"><a href="<%=request.getContextPath()%>/admin/orders_manager.Q"><i class="icon icon-money"></i> <span>订单管理</span></a></li> 
     <li class="submenu" id="m2"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>用户管理</span> </a>
      <ul style="display: block;">
	    <li id="msub23"><a href="<%=request.getContextPath()%>/admin/car_areaUsers.Q">本区域用户</a></li>
      </ul>
    </li>
    
    <li class="submenu" id="m7"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>权限管理</span> </a>
      <ul style="display: block;">
       	<li id="msub71"><a href="<%=request.getContextPath()%>/admin/admin_manager.Q">服务商管理</a></li> 
      </ul>
    </li>
    
  </ul>
</div>
