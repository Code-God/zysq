<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script type="text/javascript">
	function resetForm(){
		$("#name").val("");
		$("#recommend").val("");
		$("#prdCatCode").val("");
		$("#prdCatName").val("");
	}
	function viewStock(code){
		window.location.href="<%=request.getContextPath()%>/admin/products_stock.Q?code="+code;
	}
	
	function addOrUpdate(id){
		var url = "<%=request.getContextPath()%>/admin/products_productInput.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function detail(id){
		var url = "<%=request.getContextPath()%>/admin/products_detail.Q?id="+id;
		window.location.href = url;
	}
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一件商品删除',
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
			    content: '你确定要删除选中的商品吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/products_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/products_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '删除失败！'
								});
							}
					    }
					});
			    },
			    cancelVal: '取消',
			    cancel: true //为true等价于function(){}
			});
		}
	}
	function openPrdCatTree(){
    	var url = "<%=request.getContextPath()%>/admin/products_openPrdCatTree.Q";
       art.dialog.open(url, {limit:false,lock:true,title:'<b>商品类型</b>',width:'260px',height:'400px'});
	}
	
	function openOrgTree(){
    	var url = "<%=request.getContextPath()%>/admin/products_openOrgTree.Q";
        art.dialog.open(url, {limit:false,lock:true,title:'<b>选择分销商</b>',width:'360px',height:'400px',
        	ok: function () {
		    	$("#fxOrgName").val(art.dialog.data("text"));
		    	$("#fxCode").val(art.dialog.data("orgCode"));
		       	return true;
		    },
		    cancel: true});
	}
	
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub11"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/products_manager.Q" method="post" id="productsQueryForm">
           	<c:if test="${superAdmin }">
	            <div class="controls controls-row" style="margin-left: 20px">
					<div class="alert alert-error">
	              	<button data-dismiss="alert" class="close">×</button>
	              <strong>提示：</strong> <hr>系统管理员可以根据不同的总销商来查看属于各公司的商品。<br>平台管理员请不要擅自修改商家的商品信息。 </div>
				</div>          	
          	</c:if>
            <div class="controls controls-row" style="margin-left: 20px">
            	<c:if test="${superAdmin }">
		            <label class="span1" for="inputSuccess" style="margin-top:5px">总销商:</label>
		            <input name="fxOrgName" id="fxOrgName" type="text" class="span3" style="margin-left:5px;margin-right:25px" value="${fxOrgName }" onclick="openOrgTree();">
		            <input name="fxCode" id="fxCode" type="hidden" value="${fxCode }">
            	</c:if>
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品名:</label>
	            <input name="name" id="name" type="text" class="span3" style="margin-left:5px;margin-right:25px" value="${ name}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品编号:</label>
	            <input name="prdCode" id="prdCode" type="text" class="span3" style="margin-left:5px;margin-right:25px" value="${ prdCode}">
	            <!-- 
	            <label class="span1" for="inputSuccess" style="margin-top:5px">商品分类:</label>
	            <input name="prdCatName" id="prdCatName" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ prdCatName}" onclick="openPrdCatTree();">
	             <input name="prdCatCode" id="prdCatCode" type="hidden" value="${ prdCatCode}">
	             -->
	         </div>
	         <div  class="controls controls-row" style="text-align: center;">
	         <!-- 
	         	 <label class="span1" for="inputSuccess" style="margin-top:5px">是否推荐:</label>
	             <select name="recommend" id="recommend" style="margin-left:5px;margin-right:35px;width:156px">
	            	 <option value="">请选择</option>
	             	<option value="0" <s:if test="#request.recommend==0">selected</s:if> >不推荐</option>
                  	<option value="1" <s:if test="#request.recommend==1">selected</s:if>>推荐</option>
                  	<option value="2" <s:if test="#request.recommend==2">selected</s:if>>大图推荐</option>
	             </select>
	          -->
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>商品列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            <button class="label label-info btn btn-primary btn-mini" onclick="delByIds();">删除</button>
         </div>
         <div class="widget-content nopadding" id="productTableId">
            <%@include file="productList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
