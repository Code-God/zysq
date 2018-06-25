<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【<%=Version.getInstance().getSystemTitle() %>】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="${ctx}/js/common/city.js"></script>
<script type="text/javascript">

$(function(){			
	// 初始化行政区划
    var cs = [];
    cs.push('<option value="" selected>请选择</option>');
    $.each(cities, function(idx){
    	cs.push('<option value="'+ idx +'">'+ idx +'</option>');
    });
    $("#province").empty();
    $("#province").append(cs.join(""));
    // 行政区划选择事件，级联改变所在城市
    $("#province").change(function(){
    	var val = $(this).val(); 
        if (val != '') {
        	var cs2 = cities[val];
        	var c2 = [];
 		    c2.push('<option value="" selected>请选择</option>');
 		    $.each(cs2, function(idx){
 		    	
 		    	c2.push('<option value="'+ cs2[idx] +'">'+ cs2[idx] +'</option>');
 		    });
 		    
        	$("#city").empty();
		    $("#city").append(c2.join(""));
        }
    }); 
    
    if("${drugProjectConter.province}"!=""){
    	$("#province").val("${drugProjectConter.province}");
    	$("#province").change();
    	$("#city").val("${drugProjectConter.city}");
    	$("#city").change();
    }
    
    
})
	function checkSubmitForm(){
	var diseaseId = $("#diseaseId").val();
	var organizationName = $("#organizationName").val();
	var researcher = $("#researcher").val();
	var country = $("#country").val();
	var province = $("#province").val();
	var city = $("#city").val();
	if($.trim(diseaseId) == ""){
		$("#diseaseSpan").show();
		return ;
	}else{
		$("#diseaseSpan").hide();
	}

	if($.trim(organizationName) == ""){
		$("#organizationNameSpan").show();
		return;
	}else{
		$("#organizationNameSpan").hide();
	}
	if($.trim(researcher) == ""){
		$("#researcherSpan").show();
		return;
	}else{
		$("#researcherSpan").hide();
	}
	
	if($.trim(country) == "请选择"){
		$("#countrySpan").show();
		return;
	}else{
		$("#countrySpan").hide();
	}
	if($.trim(province) == ""){
		$("#provinceSpan").show();
		return;
	}else{
		$("#provinceSpan").hide();
	}
	if($.trim(city) == ""){
		$("#citySpan").show();
		return;
	}else{
		$("#citySpan").hide();
	}
		$("#projectForm").submit();
		 
	}
	function toProjectList(){
		window.location.href = "<%=request.getContextPath()%>/admin/durgProjectConter_manager.Q";
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
          <form action="<%=request.getContextPath()%>/admin/durgProjectConter_saveProjectConter.Q" method="post" enctype="multipart/form-data" class="form-horizontal" id="projectForm" >
            <input type="hidden" id="projectid" class="span5" name="drugProjectConter.id" value="${drugProjectConter.id}"  />
             <div class="control-group">
              <label class="control-label">所属疾病:</label>
              <div class="controls">
                <select  style="width:579px" id="diseaseId" name="drugProjectConter.diseaseId" >
                	<s:iterator value="#request.diseaselist" var="item">
                	<option value="${item.id}" <s:if test="#request.drugProjectConter.diseaseId == #item.id">selected</s:if> >${item.diseaseName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="diseaseSpan">请至少选择一种疾病！${drugProjectConter.diseaseId}</span>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">机构名称 :</label>
              <div class="controls">
                <input type="text" class="span5" name="drugProjectConter.organizationName" value="${drugProjectConter.organizationName}" id="organizationName"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="organizationNameSpan">机构名称不能为空！</span>
              </div>
              <div class="control-group">
              <label class="control-label">主要研究者:</label>
              <div class="controls">
                <input type="text" class="span5" name="drugProjectConter.researcher" value="${drugProjectConter.researcher}" id="researcher"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="researcherSpan">主要研究者不能为空！</span>
              </div>
              <div class="control-group">
              <label class="control-label">国家/省/市:</label>
              <div class="controls">
               <select class="in_text"  name="drugProjectConter.country" id="country"  style="margin-left:5px;margin-right:35px;width:85px">
               			<option value="请选择">请选择</option>
               			<option value="中国" <s:if test="#request.drugProjectConter.country=='中国' ">selected</s:if>>中国</option>
                        </select>
                        <span style="color: #F00;display:none" id="countrySpan">国家不能为空！</span>
             	 <select class="in_text"  name="drugProjectConter.province" id="province" style="margin-left:5px;margin-right:35px;width:85px">
                        </select>
                        <span style="color: #F00;display:none" id="provinceSpan">省份不能为空！</span>
                        <select class="in_text" name="drugProjectConter.city" id="city" style="margin-right:35px;width:85px">
                        </select>
                        <span style="color: #F00;display:none" id="citySpan">城市不能为空！</span>
              </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkSubmitForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toProjectList();">返回</button>
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
