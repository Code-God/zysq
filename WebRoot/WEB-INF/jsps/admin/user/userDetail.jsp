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

<script type="text/javascript">
	function toProductsList(){
		window.location.href = "<%=request.getContextPath()%>/admin/user_manager.Q";
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m6"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>会员信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
          	<input type="hidden" name="products.id" id="productsId" value="${user.id }">
            <div class="control-group">
              <label class="control-label">昵称</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="user.name" value="${user.nickName }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">手机号码</label>
              <div class="controls">
                <input type="text" class="span5" name="user.prdStandard" value="${user.telphone }" id="prdStandard" disabled/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">邮箱</label>
              <div class="controls">
                <input type="text" class="span5" name="user.prdPrice" value="${user.email }" id="prdPrice" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">QQ号码</label>
              <div class="controls">
                <input type="text" class="span5" name="user.prdDisPrice" value="${user.qq }" id="prdDisPrice" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">注册日期</label>
              <div class="controls">
                <input type="text" class="span5" name="user.prdCatName" value='<s:date name="user.regDate" format="yyyy-MM-dd hh:mm:ss" />' id="prdDisPrice" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">最后登陆时间</label>
              <div class="controls">
                <input type="text" class="span5" name="user.stock" value='<s:date name="user.lastLogin" format="yyyy-MM-dd hh:mm:ss" />' id="stock" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            
             <div class="control-group">
              <label class="control-label">状态</label>
              <div class="controls">
                <input type="text" class="span5" name="user.stock" value="${user.statusStr }" id="stock" disabled/>&nbsp;&nbsp;
              </div>
            </div>
  
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
