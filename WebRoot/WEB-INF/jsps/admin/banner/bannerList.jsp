<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@page import="com.base.tools.Version"%>

<!DOCTYPE html>
<html lang="en">
	<head>
	
		<title><%=Version.getInstance().getSystemTitle()%></title>
		<meta charset="UTF-8" />
		<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
		<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
		<script type="text/javascript">
		 
		function addBanner(){
			window.location.href="<%=request.getContextPath()%>/admin/banner_addBanner.Q";
		}
		function modBanner(id){
			window.location.href="<%=request.getContextPath()%>/admin/banner_getBannerById.Q?id="+id;
		}
		function delBanner(id){
			window.location.href="<%=request.getContextPath()%>/admin/banner_delBanner.Q?id="+id;
		}
		</script>
		
		
	</head>
	<body onload="initData(1)">
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
		<input type="hidden" id="tab" value="m3,msub31" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i> </span>
							<h5>
								图片管理 
							</h5>
							<button type="button" class="label label-info btn btn-primary btn-mini" onclick="addBanner()"> 新增 </button>
							</div>
				          <div class="widget-content nopadding">
				            <table class="table table-bordered table-striped">
				              <thead>
				                <tr>
				                  <th>序号</th>
				                  <th>图片名</th>
				                  <th>链接地址</th>
				                  <th>操作</th>
				                </tr>
				              </thead>
				              <tbody id="dataList">
				              <c:forEach items="${requestScope.list}" var="banner"  varStatus="status">
				              <tr>
				              <Td>
				              	${ status.index + 1}
				              </Td>
				              <Td>
				              	${banner.imgname}
				              </Td>
				              <Td>
				              	${banner.imglink}
				              </Td>
				               <Td>
								<a class="label label-info btn btn-primary btn-mini" href="#" onclick="delBanner('${banner.id}')">删除</a>
								<a class="label label-info btn btn-primary btn-mini" href="#" onclick="modBanner('${banner.id}');">修改</a>
				              </Td>
				              </tr>
				              </c:forEach>
				              </tbody>
				            </table>
				            <div id="pageBar">
				            </div>
				          </div>
				        </div>
				</div>
			</div>

	<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
