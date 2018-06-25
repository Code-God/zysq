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
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function saveOrUpdate(){
		if($.trim($("#fxsApplyTxt").val()) == "" || $.trim($("#fxkApplyTxt").val()) == ""){
			art.dialog("对不起，分销商（客）申请文字不得为空！");
			return;
		}
	
		$("form").submit();
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
          <h5> 分销商（客）申请页面设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/fx_saveOrUpdateFxApplyConfig.Q" method="post" class="form-horizontal" id="theForm" enctype="multipart/form-data">
          	<input type="hidden" name="fac.id"  value="${ac.id }">
          	<input type="hidden" name="fac.orgCode" id="orgCode" value="${ac.orgCode }">
            <div class="control-group">
              <label class="control-label">分销商申请文字</label>
              <div class="controls">
               	<input type="text" style="width: 200px"   name="fac.fxsApplyTxt" id="fxsApplyTxt" value="${ac.fxsApplyTxt }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">分销客申请文字</label>
              <div class="controls">
               		<input type="text" style="width: 200px" name="fac.fxkApplyTxt" id="fxkApplyTxt" value="${ac.fxkApplyTxt }"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">分销客申请门槛</label>
              <div class="controls">
              	<c:if test="${ac.fxkApplyCondition == 1 }">
               		<input type="checkbox" name="fac.fxkApplyCondition" id="fxkApplyCondition" value="1" checked="checked"> 需要消费后才能申请
              	</c:if>
              	<c:if test="${ac.fxkApplyCondition == 0 }">
               		<input type="checkbox" name="fac.fxkApplyCondition" id="fxkApplyCondition" value="1"  > 需要消费后才能申请
              	</c:if>
              </div>
            </div>
            
             <div class="control-group">
              <label class="control-label">分销商申请宣传图片（非必选）</label>
              <div class="controls">
               	<input type="hidden" name="fxsPic" id="fxsPic" value="${ac.fxsDescPic }"/>
               	<s:if test="#request.ac.fxsDescPic != ''">
	                清除图片：<input type="checkbox" name="clearFxsPic" value="1"><br>
               		<img src="<%=request.getContextPath() %>${ac.fxsDescPic }"  width="300px" height="200px"><br>
               	</s:if>
                <input type="file" name="fxsFile" onchange="changeTargetValue('fxsPic',this);" style="width:72px;"/>
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;图片宽度推荐：不超过640px</font><br>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">分销客申请宣传图片（非必选）</label>
              <div class="controls">
               	<input type="hidden" name="fxkPic" id="fxkPic"  value="${ac.fxkDescPic }"/>
               	 	<s:if test="#request.ac.fxkDescPic != ''">
		                清除图片：<input type="checkbox" name="clearFxkPic" value="1"><br>
               		<img src="<%=request.getContextPath() %>${ac.fxkDescPic }" width="300px" height="200px"><br>
               	</s:if>
                <input type="file" name="fxkFile" onchange="changeTargetValue('fxkPic',this);" style="width:72px;"/>
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;图片宽度推荐：不超过640px</font><br>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">更新</button>&nbsp;&nbsp;
              <s:if test="#request.info != null">
              	<span class="label label-important">${info }</span>
              </s:if>
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
