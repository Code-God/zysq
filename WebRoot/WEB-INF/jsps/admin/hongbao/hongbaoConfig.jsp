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
		if($.trim($("#hongbaoName").val()) == "" ){
			art.dialog("对不起， 红包背景图片不得为空！");
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
	
	
	function setIndexShow(val){
		$("#indexShow").val(val);
	}
	
	//更新微商城首页显示
	function updateIndexShow(){
		$.ajax({
			type : "POST",
			url : CONFIG.context + "/admin/ad_updateIndexShow.Q",
			data : {indexShow : $("#indexShow").val()},
			dataType : "json",
			success : function(data) {
				if(data.result == 'ok'){//
					 art.dialog("更新成功！");
					 window.location.reload();
				}else{
					 art.dialog("更新失败！");
				}
			}
		});
	
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m3,msub34" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5> 红包推广页面个性化设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/ad_hongbaoBgSave.Q" method="post" class="form-horizontal" id="adForm" enctype="multipart/form-data">
          	<input type="hidden" name="org.id" id="orgId" value="${org.id }">
          	<c:if test="${org.hongbaoPic != null}">
	            <div class="control-group">
	              <label class="control-label">红包背景图片</label>
	              <div class="controls">
	                <img style="width: 800px; height: 330px" src="<%=request.getContextPath()%>${org.hongbaoPic}"/>&nbsp;&nbsp;
	              </div>
	            </div>
	            <!-- 
	            <div class="control-group">
	              <label class="control-label">LOGO图片</label>
	              <div class="controls">
	                <img src="<%=request.getContextPath()%>${org.wxMallLogo}"/>&nbsp;&nbsp;
	              </div>
	            </div>
	             -->
          	</c:if>
           
            <div class="control-group">
              <label class="control-label">红包背景图片</label>
              <div class="controls">
               	<input type="hidden"  name="hongbaoName" id="hongbaoName" readonly="readonly"/>
                <input type="file" name="hongbaoFile"  onchange="changeTargetValue('hongbaoName',this);" style="width:72px;"/>
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;背景图尺寸（800 x 330 px）,仅支持jpg，png，gif</font>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">更新</button>&nbsp;&nbsp;
            </div>
          
          
          <!-- 首页列表显示部分 -->
          
          </form>
          
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

</body>
</html>
