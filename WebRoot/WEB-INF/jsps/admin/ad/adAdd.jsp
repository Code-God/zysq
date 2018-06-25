<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function saveOrUpdate(){
		$("#adType").attr("disabled",false);
		$("form").submit();
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
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m2,msub25" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5><c:if test="${advConfig.id == null }">新增</c:if><c:if test="${advConfig.id != null }">编辑</c:if>广告</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/ad_save.Q" method="post" class="form-horizontal" id="adForm" enctype="multipart/form-data">
          	<input type="hidden" name="advConfig.id" id="advConfigId" value="${advConfig.id }">
          	<c:if test="${advConfig.id != null }">
	            <div class="control-group">
	              <label class="control-label">广告图片</label>
	              <div class="controls">
	                <img src="<%=request.getContextPath()%>${advConfig.picUrl}"/>&nbsp;&nbsp;
	              </div>
	            </div>
            </c:if>
            <div class="control-group">
              <label class="control-label">广告类型</label>
              <div class="controls">
              	<select name="advConfig.adType" id="adType" style="width:579px;" <c:if test="${advConfig.id != null }">disabled</c:if>>
                  <option value="1" <c:if test="${advConfig.adType==1 }">selected</c:if>>普通广告</option>
                  <option value="2" <c:if test="${advConfig.adType==2 }">selected</c:if>>幻灯片广告</option>
                </select>
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">广告地址(对应商品编码)</label>
              <div class="controls">
                <input type="text" class="span5" name="advConfig.url" value="${advConfig.url }" id="url"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group" <c:if test="${advConfig.id != null }">style="display:none;"</c:if>>
              <label class="control-label">广告图片</label>
              <div class="controls">
               	<input type="hidden" class="span5" name="picName" id="picName" readonly="readonly"/>
                <input type="file" name="picFile" id="picUrlFile" onchange="changeTargetValue('picName',this);" style="width:72px;"/>
                
              </div>
            </div>
            <!-- 
            <div class="control-group">
              <label class="control-label">有效期</label>
              <div class="controls">
                <input type="text" name="advConfig.dueDate" value="${advConfig.dueDate }" id="dueDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="span5 wdateinput"/>&nbsp;&nbsp;
              </div>
            </div>
             -->
            <div class="control-group">
            <div class="controls"><font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;幻灯片广告尺寸（640 x 295 px）<br/>*&nbsp;&nbsp;&nbsp;&nbsp;普通广告尺寸（640 x 100 px）</font></div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="backToLisk();">返回</button>
            </div>
            <!-- 当前分销用户的分销商ID -->
            <input type="hidden" value="${orgId }" name="advConfig.orgId">
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

</body>
</html>
