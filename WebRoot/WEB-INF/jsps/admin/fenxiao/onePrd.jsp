<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%=Version.getInstance().getSystemTitle() %></title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/artDialog.js?skin=opera"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
   	<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/config.js"></script>
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function saveOrUpdate(){
		/*
		if($.trim($("#prdCode").val()) == ""){
			art.dialog("对不起， 请填写关联的商品编码！");
			return;
		}
		*/
	
		$("#adForm").submit();
	}
	function backToLisk(){
		window.location.href = "<%=request.getContextPath()%>/admin/ad_index.Q";
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
	
	
	function setIndexShow(val){
		$("#indexShow").val(val);
	}
	
	
	function setIndex(obj, index){
		//pic1Index
		$("#pic"+index+"Index").val(index);
	}
	
	/** 清除图片 */
	function clearPic(index){
		$.ajax({
			type : "POST",
			url : CONFIG.context +"/admin/fx_clearOnePrdPic.Q?index="+index+"&id=${one.id}",
			dataType : "json",
			success : function(data) {
				art.dialog("清除成功！");
				window.location.reload();
			}
		});
	}
	
</script>

</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub16" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5> 爆款商品资料输入</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/products_saveOnePrd.Q" method="post" class="form-horizontal" id="adForm" enctype="multipart/form-data">
          	<input type="hidden" name="one.id" id="id" value="${one.id }">
          	<!-- 
           <div class="control-group" style="display: none;">
              <label class="control-label">关联的商品编号</label>
              <div class="controls">
               	<input type="text"  name="one.prdCode" id="prdCode" value="${one.prdCode }" />
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp; </font>
              </div>
            </div>
          	 -->
            
            <div class="control-group">
              <label class="control-label">图1 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl1Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl1File" size="0" onchange="setIndex(this,1);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic1 != null && one.pic1 != ''}">
                 	<img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic1 }" width="100px" height="30px"/>
                 	<button id="saveButton" onclick="clearPic(1)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic1PrdCode" id="pic1PrdCode" value="${one.pic1PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图2 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl2Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl2File" size="0" onchange="setIndex(this,2);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic2 != null && one.pic2 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic2 }" width="100px" height="30px"/>
                 <button id="saveButton" onclick="clearPic(2)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic2PrdCode" id="pic2PrdCode" value="${one.pic2PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图3 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl3Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl3File" size="0" onchange="setIndex(this,3);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic3 != null && one.pic3 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic3 }" width="100px" height="30px"/>
                 <button id="saveButton" onclick="clearPic(3)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic3PrdCode" id="pic3PrdCode" value="${one.pic3PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图4 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl4Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl4File" size="0" onchange="setIndex(this,4);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic4 != null && one.pic4 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic4 }" width="100px" height="30px"/>
                 <button id="saveButton" onclick="clearPic(4)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic4PrdCode" id="pic4PrdCode" value="${one.pic4PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图5</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl5Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl5File" size="0" onchange="setIndex(this,5);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic5 != null && one.pic5 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic5 }" width="100px" height="30px"/>
                 <button id="saveButton" onclick="clearPic(5)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic5PrdCode" id="pic5PrdCode" value="${one.pic5PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图6 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl6Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl6File" size="0" onchange="setIndex(this,6);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic6!= null && one.pic6 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic6 }" width="100px" height="30px"/>
                  <button id="saveButton" onclick="clearPic(6)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic6PrdCode" id="pic6PrdCode" value="${one.pic6PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图7 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl7Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl7File" size="0" onchange="setIndex(this,7);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic7!= null && one.pic7 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic7}" width="100px" height="30px"/>
                  <button id="saveButton" onclick="clearPic(7)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic7PrdCode" id="pic7PrdCode" value="${one.pic7PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图8 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl8Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl7File" size="0" onchange="setIndex(this,8);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic8 != null && one.pic8 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic8 }" width="100px" height="30px"/>
                  <button id="saveButton" onclick="clearPic(8)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic8PrdCode" id="pic8PrdCode" value="${one.pic8PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图9 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl9Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl7File" size="0" onchange="setIndex(this,9);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic9 != null && one.pic9 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic9 }" width="100px" height="30px"/>
                  <button id="saveButton" onclick="clearPic(9)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic9PrdCode" id="pic9PrdCode" value="${one.pic9PrdCode }" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图10 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl10Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl7File" size="0" onchange="setIndex(this,10);" style="width:71px;"/>
                 &nbsp;<c:if test="${one.pic10 != null && one.pic10 != ''}">
                 <img src="<%=request.getContextPath() %>/private/UploadImages/${one.pic10 }" width="100px" height="30px"/>
                  <button id="saveButton" onclick="clearPic(10)" class="btn btn-info" type="button">清除图片</button>
                 </c:if>
                 关联的商品编号:<input type="text"  name="one.pic10PrdCode" id="pic10PrdCode" value="${one.pic10PrdCode }" />
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">更新</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="backToLisk();">返回</button>
            </div>
          
          	<input type="hidden" name="pic1Index" id="pic1Index" value="0"/>
			<input type="hidden" name="pic2Index" id="pic2Index" value="0"/>
			<input type="hidden" name="pic3Index" id="pic3Index" value="0"/>
			<input type="hidden" name="pic4Index" id="pic4Index" value="0"/>
			<input type="hidden" name="pic5Index" id="pic5Index" value="0"/>
			<input type="hidden" name="pic6Index" id="pic6Index" value="0"/>
			<input type="hidden" name="pic7Index" id="pic7Index" value="0"/>
			<input type="hidden" name="pic8Index" id="pic8Index" value="0"/>
			<input type="hidden" name="pic9Index" id="pic9Index" value="0"/>
			<input type="hidden" name="pic10Index" id="pic10Index" value="0"/>
          </form>
          
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

</body>
</html>
