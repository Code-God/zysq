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
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script type="text/javascript">
	function resetForm(){
		$("#name").val("");
		$("#recommend").val("");
		$("#code").val("");
	}
	function addOrUpdate(id){
		var url = "<%=request.getContextPath()%>/admin/productcat_input.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	
	function queryForm(){
		$("form").submit();
	}
	
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一件商品分类删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的商品分类吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/productcat_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							if(data.result=="success"){
								$("form").submit();
							}else{
								art.dialog({
									okVal:'确定',
									content: data.msg,
									ok:true
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true
			});
		}
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
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/productcat_index.Q" method="post" id="productCatQueryForm">
            <div class="controls controls-row">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">分类名称:</label>
	            <input name="name" id="name" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ name}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">分类编码:</label>
	            <input name="code" id="code" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ code}">
	             <label class="span1" for="inputSuccess" style="margin-top:5px">是否推荐:</label>
	             <select name="recommend" id="recommend" style="margin-left:5px;margin-right:35px;width:156px">
	            	 <option value="">请选择</option>
	             	<option value="0" <s:if test="#request.recommend==0">selected</s:if> >不推荐</option>
                  	<option value="1" <s:if test="#request.recommend==1">selected</s:if>>推荐</option>
	             </select>
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>商品分类列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="delByIds();">删除</button>
         </div>
         <div class="widget-content nopadding" id="productTableId">
            <%@include file="list.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
