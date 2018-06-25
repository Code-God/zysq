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
	var levelData;
	function changeLevel(el){
		if(levelData == null){
			$.ajax({
				url:'<%=request.getContextPath()%>/admin/productcat_allPrdCat.Q',
				dataType:'json',
				type:'get',
				async:false,
				success:function(data){
					levelData = data;
				},
				error:function(data){
					alert("数据加载异常");
				}
			});
		}
		var level = $(el).val()*1;
		if(level == 1){
			$("#bgcolorDiv").show();
			$("#picDiv").show();
			$("#recommendDiv").show();
			$("#oneLevelDiv").hide();
			$("#twoLevelDiv").hide();
		}else if(level == 2){
			$("#bgcolorDiv").hide();
			$("#picDiv").hide();
			$("#recommendDiv").hide();
			initPrdCatOne();
			$("#oneLevelDiv").show();
			$("#twoLevelDiv").hide();
		}else if(level == 3){
			$("#bgcolorDiv").hide();
			$("#picDiv").hide();
			$("#recommendDiv").hide();
			initPrdCatOne();
			var id = $('#oneLevel :first').val()*1;
			loadPrdCatTwo(id);
			$("#oneLevelDiv").show();
			$("#twoLevelDiv").show();
		}
		
	}
	function initPrdCatOne(){
		var options = "";
		for(var i = 0;i<levelData.length;i++){
			var prdCat = levelData[i];
			if(prdCat.level == 1){
				var option = '<option value="'+ prdCat.id + '">' + prdCat.name + '</option>';
				options += option;
			}
		}
		$("#oneLevel").html(options);
	}
	
	function loadPrdCatTwo(id){
		var options = "";
		for(var i = 0;i<levelData.length;i++){
			var prdCat = levelData[i];
			if(prdCat.parentId == id){
				var option = '<option value="'+ prdCat.id + '">' + prdCat.name + '</option>';
				options += option;
			}
		}
		$("#twoLevel").html(options);
	}
	function changeTargetValue(id,obj){
		var path=getPath(obj);
		$("#"+id).val(path);
	}
	function getPath(obj) {
	         if(obj)
	     {  
	         if (window.navigator.userAgent.indexOf("MSIE")>=1)
	        {	var textValue = "";
	        	try{
	        		obj.select();
	           	 	textValue = document.selection.createRange().text;
	        	}catch(e){
	        		if(!textValue){
	            		textValue = obj.value;
	            	}
	        	}
	            
	            
	            return textValue;
	        }   
	         else 
	         {
	            if(obj.files)
	             {
	                   return obj.value;
	              }
	              return obj.value;
	         }
	        return obj.value;
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
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品分类信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/productcat_save.Q" method="post" class="form-horizontal" id="productCatForm" enctype="multipart/form-data">
          	<div class="control-group">
              <label class="control-label">分类等级</label>
              <div class="controls">
                <select name="productCat.level" id="catLevel" style="width:579px;" onchange="changeLevel(this)" autocomplete="off">
                  <option value="1" <s:if test="productCat.level==1">selected</s:if> >一级分类</option>
                  <option value="2" <s:if test="productCat.level==2">selected</s:if>>二级分类</option>
                  <option value="3" <s:if test="productCat.level==3">selected</s:if>>三级分类</option>
                </select>
              </div>
            </div>
            <div class="control-group" id="oneLevelDiv" style="display:none;">
              <label class="control-label">一级分类</label>
              <div class="controls">
                <select name="oneLevel" id="oneLevel" style="width:579px;" onchange="loadPrdCatTwo($(this).val()*1)" autocomplete="off">
                </select>
              </div>
            </div>
            <div class="control-group" id="twoLevelDiv" style="display:none;" autocomplete="off">
              <label class="control-label">二级分类</label>
              <div class="controls">
                <select name="twoLevel" id="twoLevel" style="width:579px;" autocomplete="off">
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">分类名称</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="productCat.name" value="" />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group" id="recommendDiv">
              <label class="control-label">是否推荐</label>
              <div class="controls">
                <select name="productCat.recommend" id="recommend" style="width:579px;">
                  <option value="0" <s:if test="productCat.recommend==0">selected</s:if> >不推荐</option>
                  <option value="1" <s:if test="productCat.recommend==1">selected</s:if>>推荐</option>
                </select>
              </div>
            </div>
            <!-- 
            <div class="control-group" id="picDiv">
              <label class="control-label">分类图标</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picName" id="picName" readonly="readonly"/>
                <input type="file" name="picFile" id="picUrlFile" onchange="changeTargetValue('picName',this);"/>
              </div>
            </div>
             -->
            <div class="control-group" id="bgcolorDiv">
              <label class="control-label">推荐分类背景颜色</label>
              <div class="controls">
                <div class="input-append color colorpicker" data-color="#000000" data-color-format="hex">
                  <input type="text" name="productCat.bgcolor" class="span11" value="#000000">
                  <span class="add-on"><i style="background-color: #000000"></i></span> </div>
              </div>
            </div>
            <div class="control-group">
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
