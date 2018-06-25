<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/cust.tld"%>
<!-- 经销商总公司管理员菜单 -->
<script type="text/javascript">
	$(function(){
	    var menuId;
	    var subMenuId;
	    var tab = $("#tab").val();
	    console.log(tab)
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
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i>后台管理系统</a>
  <ul>
   
  <w:permission permissionId="<%=PermissionId.PERMISSION_MGT%>">
     <li class="submenu" id="m7"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>首页设置</span> </a>
      <ul style="display: block;">
       <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub11"><a href="<%=request.getContextPath()%>/admin/banner_getList.Q">图片设置</a></li></w:permission>
      </ul>
    </li>
    </w:permission>
      
     <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="#"><i class="icon icon-user"></i> <span>捷信项目管理</span></a>
      <ul style="display: block;">
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/itemClass_itemClassManager.Q">项目类型</a></li></w:permission>
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/drugDisease_diseaseManager.Q">疾病管理</a></li></w:permission>
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/drugDisease_diseaseCenterCountManager.Q">中心統計</a></li></w:permission>
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/drugs_drugsManager.Q">药物管理</a></li></w:permission>
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/durgProjectConter_manager.Q">项目中心管理</a></li></w:permission>
      </ul>
    </li>
    </w:permission>
    
         <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="#"><i class="icon icon-user"></i> <span>第三方项目管理</span></a>
      <ul style="display: block;">
      <w:permission permissionId="<%=PermissionId.PRODUCT_MGT_PRO%>"> <li id="msub73"><a href="<%=request.getContextPath()%>/admin/drugDiseaseDict_diseaseDictManager.Q">信息管理</a></li></w:permission>
    
      </ul>
    </li>
    
    </w:permission>
   <%--  <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m6"><a href="<%=request.getContextPath()%>/admin/user_manager.Q"><i class="icon icon-user"></i> <span>患者信息管理</span></a></li>
    </w:permission> --%>

   <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>"><li id="m6"><a href="<%=request.getContextPath()%>/admin/user_manager.Q"><i class="icon icon-user"></i> <span>用户管理</span></a></li></w:permission>
   <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="<%=request.getContextPath()%>/admin/qualification_qualManager.Q"><i class="icon icon-user"></i> <span>医疗认证审核</span></a>
    </li>
    </w:permission>
    <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="<%=request.getContextPath()%>/admin/drugUDRelation_relationManager.Q"><i class="icon icon-user"></i> <span>报名人员(患者)</span></a>
    </li>
    </w:permission>
       <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="<%=request.getContextPath()%>/admin/drugUDRelation_inviterManager.Q"><i class="icon icon-user"></i> <span>邀请人管理</span></a>
    </li>
    </w:permission> 
    <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m2"><a href="<%=request.getContextPath()%>/admin/drugUDRelation_recommendedManager.Q"><i class="icon icon-user"></i> <span>推荐人管理</span></a>
    </li>
    </w:permission>
    <w:permission permissionId="<%=PermissionId.AREA_CONFIG%>"><li id="m8"><a href="<%=request.getContextPath() %>/admin/city_index.Q"><i class="icon icon-pencil"></i><span>配送区域管理</span></a></li></w:permission>
    <li class="submenu" id="m8"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>微信公众平台管理</span> </a>
      <ul style="display: block;">
        <li id="msub81"><a href="<%=request.getContextPath()%>/admin/admin_wxmenu.Q">自定义菜单管理</a></li>
        <li id="msub82"><a href="<%=request.getContextPath()%>/admin/admin_keywords.Q">关键字回复</a></li>
        <li id="msub83"><a href="<%=request.getContextPath()%>/admin/admin_welcome.Q">公众号关注回复</a></li>
        <li id="msub84"><a href="<%=request.getContextPath()%>/admin/admin_imageKeywords.Q">图片消息回复</a></li>
        <li id="msub85"><a href="<%=request.getContextPath()%>/admin/qualification_imageWordsManager.Q">图片消息审核</a></li>
      </ul>
    </li>      
     
    <w:permission permissionId="<%=PermissionId.REPORT_MGT%>">
    <!-- 
    <li id="m10" class="submenu"> <a href="javascript:void(0);"><i class="icon icon-bar-chart"></i> <span>统计报表</span> </a>
      <ul style="display: block;">
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_MEMBER%>"><li id="msub101"><a href="<%=request.getContextPath() %>/admin/report_generateUserReport.Q">会员统计</a></li></w:permission>
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_FINANCE%>"><li id="msub102"><a href="<%=request.getContextPath() %>/admin/report_financeReport.Q">财务统计</a></li></w:permission>
        <w:permission permissionId="<%=PermissionId.REPORT_MGT_PRODUCT%>"><li id="msub103"><a href="<%=request.getContextPath() %>/admin/report_productSales.Q">商品统计</a></li></w:permission>
      </ul>
    </li>
     -->
    </w:permission>
      <%-- <w:permission permissionId="<%=PermissionId.COMMEN_MEMBER_MGT%>">
    <li id="m5"><a href="<%=request.getContextPath()%>/admin/user_manager.Q"><i class="icon icon-user"></i> <span>报名人员</span></a></li>
    </w:permission> --%>
  </ul>
</div>
