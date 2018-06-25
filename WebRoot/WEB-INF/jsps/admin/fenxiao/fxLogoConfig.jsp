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
		if($.trim($("#bannerName").val()) == "" || $.trim($("#logoName").val()) == ""){
			art.dialog("对不起， banner或Logo不得为空！");
			return;
		}
	
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
<input type="hidden" id="tab" value="m1,msub14" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5> 微商城banner，logo设置</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/ad_fxLogoSave.Q" method="post" class="form-horizontal" id="adForm" enctype="multipart/form-data">
          	<input type="hidden" name="org.id" id="orgId" value="${org.id }">
          	<c:if test="${org.wxMallIndexBanner != null}">
	            <div class="control-group">
	              <label class="control-label">banner图片</label>
	              <div class="controls">
	                <img src="<%=request.getContextPath()%>${org.wxMallIndexBanner}"/>&nbsp;&nbsp;
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">LOGO图片</label>
	              <div class="controls">
	                <img src="<%=request.getContextPath()%>${org.wxMallLogo}"/>&nbsp;&nbsp;
	              </div>
	            </div>
          	</c:if>
           
            <div class="control-group">
              <label class="control-label">微商城Banner图片</label>
              <div class="controls">
               	<input type="hidden"  name="bannerName" id="bannerName" readonly="readonly"/>
                <input type="file" name="bannerFile"  onchange="changeTargetValue('bannerName',this);" style="width:72px;"/>
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;Banner尺寸（640 x 130 px）</font>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">微商城LOGO图片</label>
              <div class="controls">
               	<input type="hidden" name="logoName" id="logoName" readonly="readonly"/>
                <input type="file" name="logoFile" onchange="changeTargetValue('logoName',this);" style="width:72px;"/>
                <font color="red">*&nbsp;&nbsp;&nbsp;&nbsp;公司LOGO尺寸（110 x 110 px）</font>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="saveOrUpdate();" id="saveButton">更新</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="backToLisk();">返回</button>
            </div>
          
          
          <!-- 首页列表显示部分 -->
           <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5> 微商城首页商品列表显示方式</h5>
        </div>
          	<div class="control-group">
              <label class="control-label">首页商品列表显示方式</label>
              <div class="controls">
                <label onclick="setIndexShow(0)">
                  <input type="radio" name="radios" value="0" <c:if test="${org.indexShow == 0}">checked="checked"</c:if>/>
                  	列表方式</label>
                <label onclick="setIndexShow(1)">
                  <input type="radio" name="radios" value="1" <c:if test="${org.indexShow == 1}">checked="checked"</c:if>/>
                  缩略图方式</label>
                <label onclick="setIndexShow(2)">
                  <input type="radio" name="radios" value="2" <c:if test="${org.indexShow == 2}">checked="checked"</c:if>/>
                  	我要打爆款</label>
                  
             <div class="alert alert-error alert-block" style="width: 50%">
              <h4 class="alert-heading">如何打造爆款？</h4>
              	1）选择了【我要打爆款】之后，左侧功能菜单将会出现【打造爆款】菜单。<br>
              	2）进入商品管理， 发布需要爆款的商品信息，并记住商品编码。<br>
              	3）进入【打造爆款】菜单设置爆款图片， 并设置第2）步获得的商品编码。
              </div>
                  
              </div>
              <input type="hidden" value="0" id="indexShow" name="indexShow">
            </div>
          	<div class="form-actions">
              <button type="button" class="btn btn-success" onclick="updateIndexShow();" id="saveButton">更新</button>&nbsp;&nbsp;
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
