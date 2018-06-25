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
	    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
	    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
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
<input type="hidden" id="tab" value="m4,msub42" />
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>新增</h5>
        </div>
        <div class="widget-content nopadding">
           <s:form id="caseForm" action="/admin/admin_addCase.Q"  method="POST"  enctype="multipart/form-data">
						<table width="780" border="0" align="center" cellSpacing=0 cellPadding=5 class="resTable">
							<tr>
								<td colspan="4"  class="thead" style="background: #98FB98">
									
								</td>
							</tr>
							<tr>
								<td  class="thead" width="100px">
									标题（只允许英文+数字）
								</td>
								<td colspan="3">
									<input name="shCase.title" id="title" size="30" value="${shCase.title}" maxlength="30"/>&nbsp;
									<span class='org'>*</span>&nbsp;
								</td>
							</tr>
							<tr>
								<td  class="thead" width="100px">
									所属栏目
								</td>
								<td colspan="3">
									<select id="columnId" name="shCase.columns.id" style="width: 200px">
										<c:forEach var="col" items="${cols}">
											<option value="${col.id }" <c:if test="${shCase.columns.id == col.id}"> selected </c:if>>${col.title }</option>
										</c:forEach>
									</select>
									<span class='org'>*</span>&nbsp;
								</td>
							</tr>
							<!-- 
							<tr>
								<td class="thead" width="100px">
									作者
								</td>
								<td colspan="3">
									<input name="shCase.authorName" id="authorName" size="30" value="${shCase.authorName}" maxlength="30"/>&nbsp;
									<span class='org'>*</span>&nbsp;
								</td>
							</tr>
							 -->
							<tr>
								<td class="thead" width="100px">
									宣传大图
								</td>
								<td colspan="3">
									<div class="control-group">
						              <label class="control-label">图片 :</label>
						              <div class="controls">
						                 <c:if test="${shCase != null}">
											<img src="<%=request.getContextPath()%>/${shCase.picUrl }" width="120px" height="120px">
											<input type="hidden" value="${shCase.picUrl }" name="picUrl" id="thumbPic">
										</c:if><br>
										选择宣传图片（建议最佳尺寸720*330）<input type="file" id="attachment" name="myFile">
										<span class='org'>*</span>&nbsp;
						              </div>
						            </div>
								</td>
							</tr>
							
							<tr><td colspan="4">内容</td></tr>
							<tr>
								<td colspan="4">
									<script id="editor" type="text/plain" style="width:800px;height:300px;">${shCase.thecontent}</script>
									<textarea rows="0" cols="0" style="display: none;" name="shCase.thecontent" id="thecontent"></textarea>
									<span class='org'>*</span>&nbsp;
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="center">
									<input type="hidden" name="shCase.id" value="${shCase.id }">
									<input type="hidden" name="t" id="t" value="1">
									<input type="hidden" name="model" id="t" value="1">
									<input type="button" id="submitId" value="保存" class="hrbt" onclick="validateAdmin();">&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
						</s:form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
<script type="text/javascript">
			/**
				提交校验
			 */
			function validateAdmin(){
				var pass = true;
				var msg = "";
				var title = $("#title").val();
				var author = $("#authorName").val();
				var attachment = $("#attachment").val();
				var content = UE.getEditor('editor').getContent();
				if($.trim(title) == ""){
					pass = false;
					$("#title").focus();
					msg += '<li>标题必须输入！';
				}
				if($.trim(content) == ""){
					pass = false;
					UE.getEditor('editor').focus();
					msg += '<li>内容必须输入！';
				}
				
				if(pass){
					//禁用按钮
					$("#submitId").attr("disabled", "disabled");
					//设置textArea的值
					$("#thecontent").val(content);
					//alert($("#content").val());
					$("#caseForm").submit();
				}else{
					art.dialog({fixed : true,content : msg});
				}
			}
	

	    //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    UE.getEditor('editor');
	</script>	
</body>
</html>
