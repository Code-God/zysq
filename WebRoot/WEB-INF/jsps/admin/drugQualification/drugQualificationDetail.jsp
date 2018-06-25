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
<script type="text/javascript">
	function checkSubmitForm(){

		$("#qualificationForm").submit();
		 
	}
	function toItemClassList(){
		window.location.href = "<%=request.getContextPath()%>/admin/qualification_qualManager.Q";
	}
</script>


</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m7,msub71"/>
  <s:bean name="com.wfsc.actions.common.SelAction" id="selMap"></s:bean>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/qualification_reviewQualification.Q" method="post" class="form-horizontal" id="qualificationForm">
            <input type="hidden" id="qualificationid" class="span5" name="qualification.id" value="${qualification[0]}"  />
            <div class="control-group">
              <label class="control-label">姓名 :</label>
              <div class="controls">
                <input type="text"  value="${qualification[1]}"  readonly="readonly" />&nbsp;&nbsp;
              </div>
  			</div>
            <div class="control-group">
              <label class="control-label">性别 :</label>
              <div class="controls">
              
									<c:if test="${qualification[2]==2}">								
               			     <input type="text" value="女"  readonly="readonly" />&nbsp;&nbsp;
												</c:if>
												<c:if test="${qualification[2]==1}">				
               					 <input type="text" value="男"  readonly="readonly" />&nbsp;&nbsp;
												</c:if>
                        </div>
  			</div>
            <div class="control-group">
              <label class="control-label">医院 :</label>
              <div class="controls">
                <input type="text"  value="${qualification[3]}"  readonly="readonly" />&nbsp;&nbsp;
              </div>
  			</div>
             <div class="control-group">
              <label class="control-label">科室 :</label>
              <div class="controls">
                <input type="text"  value="${qualification[4]}"  readonly="readonly" />&nbsp;&nbsp;
              </div>
  			</div>
             <div class="control-group">
              <label class="control-label">职称 :</label>
              <div class="controls">
                <input type="text"  class="span5"  value="${qualification[5]}" readonly="readonly" />&nbsp;&nbsp;
              </div>
  			</div> 	
             <div class="control-group">
              <label class="control-label">认证图片 :</label>
              <div class="controls">
                 <a href="${readpath }${qualification[7]}" target="_blank"><img  src="${readpath }${smallpic}"></a>
                 &nbsp;&nbsp;点击图片查看大图
              </div>
  			</div> 	  			
             <div class="control-group">
              <label class="control-label">审核状态:</label>
              <div class="controls">
             	  
            	      审核通过 <input type="radio" style="margin-left: 0;" name="qualification.reviewState" value="1" <c:if test="${qualification[6]==1 }">checked </c:if> > &nbsp;&nbsp;
               	      审核未通过 <input type="radio" style="margin-left: 0;" name="qualification.reviewState" value="2" <c:if test="${qualification[6]==2 }">checked </c:if> > &nbsp;&nbsp;
          		   
              </div>
  			</div> 		  					 			  			  			
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkSubmitForm()" id="saveButton">确定</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toItemClassList();">返回</button>
            </div>
          </form>       
        </div>
      </div>
      </div>
      </div>
      
</div>

<script src="<%=contextPath %>/mm/js/bootstrap.min.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.uniform.js"></script> 
<script src="<%=contextPath %>/mm/js/select2.min.js"></script> 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
