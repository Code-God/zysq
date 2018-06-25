<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【分类详情】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function toProductCatList(){
		window.location.href = "<%=request.getContextPath()%>/admin/productcat_index.Q";
	}
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub12"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品分类</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
          	<input type="hidden" name="products.id" id="productsId" value="${productCat.id }">
            <div class="control-group">
              <label class="control-label">分类名:</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="productCat.name" value="${productCat.name }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label"> 商品编码:</label>
              <div class="controls">
                <input type="text" class="span5" name="productCat.code" value="${productCat.code }" id="code" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">分类图片</label>
              <div class="controls">
                 <s:if test="productCat.picUrl==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath()%>/${productCat.picUrl}' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath()%>/${productCat.picUrl}' width="24" height="16" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="toProductCatList();">返回</button>
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
