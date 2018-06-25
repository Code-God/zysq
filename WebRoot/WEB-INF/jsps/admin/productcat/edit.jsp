<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【添加或修改分类】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script type="text/javascript">
	$(function(){
		$('.colorpicker').colorpicker();
	});
	function checkProductCatForm(){
		if($("#name").val().trim() ===''){
			alert("请输入分类名称");
			$("#name").focus();
			return false;
		}
		return true;
	}
	function toProductCatList(){
		window.location.href = "<%=request.getContextPath()%>/admin/productcat_index.Q";
	}
</script>

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
          <h5>商品分类信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/productcat_edit.Q" method="post" class="form-horizontal" id="productCatForm" enctype="multipart/form-data">
          	<input type="hidden" name="productCat.id" id="productcatId" value="${productCat.id }">
            <div class="control-group">
              <label class="control-label">分类名称</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="productCat.name" value="${productCat.name }" />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group" id="recommendDiv" <c:if test="${productCat.level !=1 }">style="display:none;"</c:if>>
              <label class="control-label">是否推荐</label>
              <div class="controls">
                <select name="productCat.recommend" id="recommend" style="width:579px;">
                  <option value="0" <c:if test="${productCat.recommend ==0 }">selected</c:if>>不推荐</option>
                  <option value="1" <c:if test="${productCat.recommend ==1 }">selected</c:if>>推荐</option>
                </select>
              </div>
            </div>
            <div class="control-group" id="bgcolorDiv" <c:if test="${productCat.level !=1 }">style="display:none;"</c:if>>
              <label class="control-label">推荐分类背景颜色</label>
              <div class="controls">
                <div class="input-append color colorpicker" data-color="${productCat.bgcolor==null?'#000000':productCat.bgcolor }" data-color-format="hex">
                  <input type="text" name="productCat.bgcolor" class="span11" value="${productCat.bgcolor==null?'#000000':productCat.bgcolor }">
                  <span class="add-on"><i style="background-color: ${productCat.bgcolor==null?'#000000':productCat.bgcolor }"></i></span> </div>
              </div>
            </div>
             
            <div class="form-actions">
              <button type="submit" class="btn btn-success" onclick="return checkProductCatForm()" id="saveButton">保存</button>&nbsp;&nbsp;
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
