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
		var ctrId = $("#ctrId").val();
		var dname = $("#diseaseName").val();
		var dprofile = $("#diseaseProfile").val();
		var dintroduction = $("#diseaseIntroduction").val();
		if($.trim(ctrId) == ""){
			$("#ctrIdSpan").show();
			return;
		}else{
			$("#ctrIdSpan").hide();
		}
		if($.trim(dname) == ""){
			$("#nameSpan").show();
			return;
		}else{
			$("#nameSpan").hide();
		}
		if($.trim(dprofile) == ""){
			$("#profileSpan").show();
			return;
		}else{
			$("#profileSpan").hide();
		}
		
		if($.trim(dintroduction) == ""){
			$("#introductionSpan").show();
			return;
		}else{
			$("#introductionSpan").hide();
		}
		$("#diseaseForm").submit();
		 
	}
	function toDiseaseList(){
		window.location.href = "<%=request.getContextPath()%>/admin/drugDiseaseDict_diseaseDictManager.Q";
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
          <form action="<%=request.getContextPath()%>/admin/drugDiseaseDict_saveDiseaseDict.Q" method="post" enctype="multipart/form-data" class="form-horizontal" id="diseaseForm" >
            <input type="hidden" id="diseaseId" class="span5" name="drugDiseaseDict.id" value="${drugDiseaseDict.id}"  />
            <div class="control-group">
              <label class="control-label">登记号 :</label>
              <div class="controls">
                <input type="text" id="ctrId" class="span5" name="drugDiseaseDict.ctrId" value="${drugDiseaseDict.ctrId}"  />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="ctrIdSpan">登记号不能为空！</span>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">疾病名称 :</label>
              <div class="controls">
                <input type="text" id="diseaseName" class="span5" name="drugDiseaseDict.dicDiseaseName" value="${drugDiseaseDict.dicDiseaseName}"  />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="nameSpan">疾病名称不能为空！</span>
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">概要 :</label>
              <div class="controls">
                <input type="text" class="span5" name="drugDiseaseDict.dicDiseaseProfile" value="${drugDiseaseDict.dicDiseaseProfile}" id="diseaseProfile"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="profileSpan">概要不能为空！</span>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">描述 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" id="diseaseIntroduction" name="drugDiseaseDict.dicDiseaseIntroduction">${drugDiseaseDict.dicDiseaseIntroduction}</textarea>
               
                <span style="color: #F00;display:none" id="introductionSpan">描述不能为空！</span>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">适应症 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" name="drugDiseaseDict.adaptation">${drugDiseaseDict.adaptation}</textarea>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">试验通俗题目 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" name="drugDiseaseDict.generalTitle">${drugDiseaseDict.generalTitle}</textarea>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">药物名称:</label>
              <div class="controls">
               <input type="text" class="span5" name="drugDiseaseDict.dicMedicineName" value="${drugDiseaseDict.dicMedicineName}" id="diseaseProfile"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">药物类型 :</label>
              <div class="controls">
               
               <input type="text" class="span5" name="drugDiseaseDict.dicMedicineType" value="${drugDiseaseDict.dicMedicineType}" id="diseaseProfile"/>&nbsp;&nbsp;

              </div>
            </div>
             <div class="control-group">
              <label class="control-label">申办者名称 :</label>
              <div class="controls">
               
               <input type="text" class="span5" name="drugDiseaseDict.sponsorinfo" value="${drugDiseaseDict.sponsorinfo}" id="diseaseProfile"/>&nbsp;&nbsp;

              </div>
            </div>
            <div class="control-group">
              <label class="control-label">试验目的 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6"  name="drugDiseaseDict.designPurpose">${drugDiseaseDict.designPurpose}</textarea>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">试验分期 :</label>
              <div class="controls">
               <input type="text"  class="span11"  name="drugDiseaseDict.designStage" value="${drugDiseaseDict.designStage}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">设计类型 :</label>
              <div class="controls">
               <input type="text"  class="span11"   name="drugDiseaseDict.designType" value="${drugDiseaseDict.designType}"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">随机化:</label>
              <div class="controls">
               <input type="text"  class="span11"  name="drugDiseaseDict.randomize" value="${drugDiseaseDict.randomize}"/>
               
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">盲法 :</label>
              <div class="controls">
               <input type="text"  class="span11" name="drugDiseaseDict.blindMethod" value="${drugDiseaseDict.blindMethod}"/>

              </div>
            </div>
            <div class="control-group">
              <label class="control-label">入选标准 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6"  name="drugDiseaseDict.subjectConditionIn">${drugDiseaseDict.subjectConditionIn}</textarea>

              </div>
            </div>
            <div class="control-group">
              <label class="control-label">排除标准 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6"  name="drugDiseaseDict.subjectConditionout">${drugDiseaseDict.subjectConditionout}</textarea>

              </div>
            </div>
            <div class="control-group">
              <label class="control-label">对照药 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6"  name="drugDiseaseDict.groupComparedMedicine">${drugDiseaseDict.groupComparedMedicine}</textarea>

              </div>
            </div>
            
             <div class="control-group">
              <label class="control-label">状态:</label>
              <div class="controls">
            	      进行中_尚未招募<input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="1" <s:if test="%{#request.drugDiseaseDict.state==1}">checked </s:if> > &nbsp;&nbsp;
               	      进行中_招募中 <input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="2" <s:if test="%{#request.drugDiseaseDict.state==2}">checked </s:if> > &nbsp;&nbsp;
               	      进行中_招募完成<input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="3" <s:if test="%{#request.drugDiseaseDict.state==3}">checked </s:if> > &nbsp;&nbsp;
               	      已完成<input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="4" <s:if test="%{#request.drugDiseaseDict.state==4}">checked </s:if> > &nbsp;&nbsp;
               	     主动暂停<input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="5" <s:if test="%{#request.drugDiseaseDict.state==5}">checked </s:if> > &nbsp;&nbsp;
               	      被叫停<input type="radio" style="margin-left: 0;" name="drugDiseaseDict.state" value="6" <s:if test="%{#request.drugDiseaseDict.state==6}">checked </s:if> > &nbsp;&nbsp;
          		   
              </div>
  			</div> 
  			
  			<div class="control-group">
              <label class="control-label">搜索关键字 :<br>(每个关键字以","分隔，<br>如肺癌,非小细胞肺癌)</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" name="drugDiseaseDict.searchKey">${drugDiseaseDict.searchKey}</textarea>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkSubmitForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toDiseaseList();">返回</button>
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
