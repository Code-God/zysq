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
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 服务商后台管理系统</a>
  <ul>
     
    <li class="submenu" id="m1"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>公众平台管理</span> </a>
      <ul style="display: block;">
      	<!-- 
        <li id="msub11"><a href="<%=request.getContextPath()%>/admin/car_cleaningCardsManageList.Q">洗车卡管理</a></li>
      	 -->
        <li id="msub12"><a href="<%=request.getContextPath()%>/admin/car_couponManageList.Q">消费券管理</a></li>
        <!-- 
        <li id="msub13"><a href="<%=request.getContextPath()%>/admin/admin_wxmenu.Q">统计管理</a></li>
         -->
      </ul>
    </li>
    
  </ul>
</div>
