<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【商城后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
<script type="text/javascript">
	function checkCommentsForm(){
		var pass = true;
		var content = $("#resContent").val();
		if(!content){
			pass = false;
		}
		if(pass){
			$("#commentsForm").submit();
		}else{
			art.dialog({fixed : true,content : '请正确填写相关信息',okVal:'确定',ok:true});
		}
		
	}
	function toCommentsList(){
		window.location.href = "<%=request.getContextPath()%>/admin/comments_manager.Q";
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m3"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>评价信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/comments_reply.Q" method="post" class="form-horizontal" id="commentsForm" enctype="multipart/form-data">
          	<input type="hidden" name="commentsId" id="commentsId" value="${comments.id }">
            <div class="control-group">
              <label class="control-label">商品名称</label>
              <div class="controls">
                ${comments.prdName }
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">商品编码</label>
              <div class="controls">
                ${comments.prdCode }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">评价用户</label>
              <div class="controls">
                ${comments.nickName }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">评价级别</label>
              <div class="controls">
              <s:if test="#comments.stars==0">不满意</s:if>
                  	<s:elseif test="#comments.stars==1">一般</s:elseif>
                  	<s:elseif test="#comments.stars==2">满意</s:elseif>
                  	<s:else>很满意</s:else>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">评价内容</label>
              <div class="controls">
               ${comments.content }
              </div>
            </div>
            
             <div class="control-group">
              <label class="control-label">评价日期</label>
              <div class="controls">
              <s:date name="comments.pdate" format="yyyy-MM-dd hh:mm:ss" />
              </div>
            </div>
            
             <div class="control-group">
              <label class="control-label">回复内容</label>
              <div class="controls">
                <textarea class="span11" name="resContent" id="resContent">${comments.resContent }</textarea>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkCommentsForm()" id="saveButton">提交</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toCommentsList();">返回</button>
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
