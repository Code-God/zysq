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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function checkSubmitForm(){
		var dname = $("#diseaseName").val();
		var mname = $("#medicineName").val();
		var dprofile = $("#diseaseProfile").val();
        var projectName = $("#mapId").val();
		var dintroduction = CKEDITOR.instances.diseaseIntroduction.getData();
		var classificationid = $("#classificationId").val();
	
		if($.trim(dname) == ""){
			$("#nameSpan").show();
			return;
		}else{
			$("#nameSpan").hide();
		}
		if($.trim(classificationid) == ""){
			$("#classificationSpan").show();
			return ;
		}else{
			$("#classificationSpan").hide();
		}
		if($.trim(mname) == ""){
			$("#medicinenameSpan").show();
			return;
		}else{
			$("#medicinenameSpan").hide();
		}
//         if($.trim(projectName) == ""){
//             $("#projectSpan").show();
//             return;
//         }else{
//             $("#projectSpan").hide();
//         }
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
		window.location.href = "<%=request.getContextPath()%>/admin/drugDisease_diseaseManager.Q";
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
          <form action="<%=request.getContextPath()%>/admin/drugDisease_saveDiseaseItem.Q" method="post" enctype="multipart/form-data" class="form-horizontal" id="diseaseForm" >
            <input type="hidden" id="diseaseId" class="span5" name="disease.id" value="${disease.id}"  />
            <div class="control-group">
              <label class="control-label">名称 :</label>
              <div class="controls">
                <input type="text" id="diseaseName" class="span5" name="disease.diseaseName" value="${disease.diseaseName}"  />&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="nameSpan">名称不能为空！</span>
              </div>
            </div>  

            <div class="control-group">
              <label class="control-label">分类 :</label>
              <div class="controls">
                <select  style="width:579px" id="classificationId" name="disease.classificationId" >
                	<s:iterator value="#request.classlist" var="item">
                	<option value="${item.id}" <s:if test="#request.disease.classificationId == #item.id">selected</s:if> >${item.classificationName}</option>
                	</s:iterator>
                </select>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="classificationSpan">请至少选择类型！${disease.classificationId }</span>
              </div>
            </div>  
            
            <div class="control-group">
              <label class="control-label">药物名称 :</label>
              <div class="controls">
                <input type="text" class="span5" name="disease.medicineName" value="${disease.medicineName}" id="medicineName"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="medicinenameSpan">药物名称不能为空！</span>
              </div>
            </div>
            
           <div class="control-group">
              <label class="control-label">概要 :</label>
              <div class="controls">
                <input type="text" class="span5" name="disease.diseaseProfile" value="${disease.diseaseProfile}" id="diseaseProfile"/>&nbsp;&nbsp;
                <span style="color: #F00;display:none" id="profileSpan">概要不能为空！</span>
              </div>
            </div>
              <div class="control-group">
                  <label class="control-label">大数据关联项目 :</label>
                  <div class="controls">
                      <input type="text" class="span5" name="disease.projectName" value="${disease.projectName}" id="projectName"/>&nbsp;&nbsp;
                      <input type="hidden" class="span5" name="disease.projectId" value="${disease.projectId}" id="projectId"/>
                      <span style="color: #F00;display:none" id="projectSpan"></span>
                  </div>
              </div>
            <div class="control-group">
              <label class="control-label">是否推荐 :</label>
              <div class="controls">
                <input type="checkbox" class="span5" name="disease.isshow" value="1"  <c:if test="${disease.isshow ==1}"> checked="checked" </c:if>>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">上传图片 :</label>
              <div class="controls">
                <input type="file" class="span5" name="file1" />&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">效果图</label>
              <div class="controls">
                <img alt="" src="${readpath}${disease.imgpath}" width="150" height="150">
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">描述 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" id="diseaseIntroduction" name="disease.diseaseIntroduction">${disease.diseaseIntroduction}</textarea>
               
                <span style="color: #F00;display:none" id="introductionSpan">描述不能为空！</span>
              </div>
            </div>
             <script type="text/javascript">CKEDITOR.replace( 'disease.diseaseIntroduction',     
                	    {     
                    toolbar :     
                    [     
                        ['Styles', 'Format'],     
                        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],     
                        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],     
                        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock']    
                    ]     
                });  </script>
            
            <div class="control-group">
              <label class="control-label">入选条件 :</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" id="chosenCondition" name="disease.chosenCondition">${disease.chosenCondition}</textarea>
              </div>
            </div>
             <script type="text/javascript">CKEDITOR.replace( 'disease.chosenCondition',     
                	    {     
                    toolbar :     
                    [     
                        ['Styles', 'Format'],     
                        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],     
                        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],     
                        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock']    
                    ]     
                });  </script>
             
             <div class="control-group">
              <label class="control-label">搜索关键字 :<br>(每个关键字以","分隔，<br>如肺癌,非小细胞肺癌)</label>
              <div class="controls">
               <textarea  class="span11"  rows="6" id="searchKey" name="disease.searchKey">${disease.searchKey}</textarea>
               
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
<script type="text/javascript" src="<%=contextPath %>/mm/js/jquery.mockjax.js"></script>
<script type="text/javascript" src="<%=contextPath %>/mm/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="<%=contextPath %>/mm/js/diseaseLx.js"></script>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
