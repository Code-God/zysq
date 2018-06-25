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
<script src="<%=contextPath %>/js/common/city.js"></script> 
<script type="text/javascript">
	function checkSubmitForm(){
		var drug_name = $("#medicineName").val();
		var disease_id = $("#diseaseId").val();
	
		if($.trim(drug_name) == ""){
			$("#nameSpan").show();
			return;
		}else{
			$("#nameSpan").hide();
		}
		if($.trim(disease_id) == ""){
			$("#diseaseidSpan").show();
			return;
		}else{
			$("#diseaseidSpan").hide();
		}
	
		$("#drugForm").submit();
		 
	}
	function toDrugList(){
		window.location.href = "<%=request.getContextPath()%>/admin/drugs_drugsManager.Q";
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
          <form action="<%=request.getContextPath()%>/admin/drugs_saveDrug.Q" method="post" class="form-horizontal" id="drugForm">
            <input type="hidden" id="drugId" class="span5" name="drug.id" value="${drug.id}"  />
            <div class="control-group">
              <label class="control-label">药物名称 :</label>
              <div class="controls">
                <input type="text" id="medicineName" class="span5" name="drug.medicineName" value="${drug.medicineName}"  />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="nameSpan">药物名称不能为空！</span>
              </div>
            </div>  

             
           <div class="control-group">
              <label class="control-label">对症疾病 :</label>
              <div class="controls">                
                <select  style="width:579px" id="diseaseId" name="drug.diseaseId" >
                	<s:iterator value="#request.diseaseList" var="disease">
                	<option value="${disease.id}" <s:if test="#request.drug.diseaseId == #disease.id">selected</s:if> >${disease.diseaseName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="diseaseidSpan">请至少选择一种疾病！</span>
              </div>
            </div>       
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkSubmitForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toDrugList();">返回</button>
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
