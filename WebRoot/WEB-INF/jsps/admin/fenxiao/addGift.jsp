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
 <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
 
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m3,msub31" />

<div id="content">
	<div id="content-header">
	    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
	</div>
	<div class="container-fluid">
	    <div class="widget-box">
	      <div class="widget-title"> <span class="icon"> <i class="icon-move"></i> </span>
	      <s:if test="gift.id==0">
            <h5>新增礼品</h5>
           </s:if>
           <s:else>
            <h5>编辑礼品信息</h5>
           </s:else>
          </div>
           <div class="widget-content nopadding">
            <div class="alert alert-error" style="display:none" id="errorDiv">
	              <button class="close" data-dismiss="alert">×</button>
	              <strong id="errorMsg">角色名称必填!</strong>  
	         </div>
          	 <form action="<%=request.getContextPath()%>/admin/admin_addGift.Q" method="post" id="theForm" class="form-horizontal" enctype="multipart/form-data">
	            <div class="control-group">
	              <label class="control-label">礼品名称 :</label>
	              <div class="controls">
	                <input type="text" class="span6" name="gift.giftName" id="giftName" value="${gift.giftName }" />
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">礼品描述 :</label>
	              <div class="controls">
	               <script id="editor" type="text/plain" style="width:800px;height:300px;">${gift.giftDesc}</script>
					<textarea rows="0" cols="0" style="display: none;" name="gift.giftDesc" id="giftDesc"></textarea>
					<span class='org'>*</span>&nbsp;
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">图片 :</label>
	              <div class="controls">
	                 <c:if test="${gift != null}">
						<img src="<%=request.getContextPath()%>/${gift.giftPic }" width="120px" height="120px">
						<input type="hidden" value="${gift.giftPic }" name="picUrl" id="thumbPic">
					</c:if>
					选择照片（建议最佳尺寸200*120）<input type="file" id="attachment" name="myFile">
					<span class='org'>*</span>&nbsp;
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">兑换积分 :</label>
	              <div class="controls">
                  	<input type="text" class="span6" name="gift.score" id="score" value="${gift.score }" />
					<span class='org'>*</span>&nbsp;
	              </div>
	            </div>
	            
	            <div class="form-actions">
	            	<input type="hidden" name="gift.id" id="giftId" value="${gift.id }">
	                <button type="button" class="btn btn-success" onclick="validateSubmit();">保存</button>&nbsp;
	            </div>
             </form>
            </div>
	      	
	    </div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsps/admin//common/adminFooter.jsp" />
<script type="text/javascript">
			/**
				提交校验
			 */
			function validateSubmit(){
				var pass = true;
				var msg = "";
				var name = $("#giftName").val();
				var attachment = $("#attachment").val();
				var score = $("#score").val();
				if($.trim(name) == ""){
					pass = false;
					$("#giftName").focus();
					msg += '<li>礼品名称必须输入！';
				}
				if($("#giftId").val() == 0){
					if($.trim(attachment) == ""){
						pass = false;
						$("#attachment").focus();
						msg += '<li>礼品图片必须选择！';
					}
				}
				if($.trim(score) == ""){
					pass = false;
					$("#score").focus();
					msg += '<li>兑换积分必须输入！';
				}
				
				 
				if(pass){
					//禁用按钮
					$("#submitId").attr("disabled", "disabled");
					//设置textArea的值
					//alert(UE.getEditor('editor').getContent());
					$("#giftDesc").val(UE.getEditor('editor').getContent());
					$("#theForm").submit();
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
