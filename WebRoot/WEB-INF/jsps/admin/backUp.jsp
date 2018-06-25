<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【商城后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function checkBackUpPlanForm(){
		var storeUrl = $("#storeURL").val();
		var pass = true;
		if(!storeUrl){
			pass = false;
			
		}
		if(pass){
			$("#BackUpPlanForm").submit();
		}else{
			art.dialog({fixed : true,content : '请正确填写备份文件的存放位置',okVal:'确定',ok:true});
		}
	}
	function toAdminList(){
		window.location.href = "<%=request.getContextPath()%>/admin/admin_manager.Q";
	}
	
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m9,msub91"/>

<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>备份信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/sec_updateBackUpPlan.Q" method="post" class="form-horizontal" id="BackUpPlanForm">
          	<input type="hidden" name="backUpPlan.id" id="backUpPlan" value="${backUpPlan.id }">
            <div class="control-group">
              <label class="control-label">备份时间(每天)</label>
              <div class="controls">
               <select name="backUpPlan.backUpTime" id="backUpTimeStr">
					<option value="0" <c:if test="${backUpPlan.backUpTime==24}">selected</c:if>>00:00</option>
					<option value="1" <c:if test="${backUpPlan.backUpTime==1}">selected</c:if>>01:00</option>
					<option value="2" <c:if test="${backUpPlan.backUpTime==2}">selected</c:if>>02:00</option>
					<option value="3" <c:if test="${backUpPlan.backUpTime==3}">selected</c:if>>03:00</option>
					<option value="4" <c:if test="${backUpPlan.backUpTime==4}">selected</c:if>>04:00</option>
					<option value="5" <c:if test="${backUpPlan.backUpTime==5}">selected</c:if>>05:00</option>
					<option value="6" <c:if test="${backUpPlan.backUpTime==6}">selected</c:if>>06:00</option>
					<option value="7" <c:if test="${backUpPlan.backUpTime==7}">selected</c:if>>07:00</option>
					<option value="8" <c:if test="${backUpPlan.backUpTime==8}">selected</c:if>>08:00</option>
					<option value="9" <c:if test="${backUpPlan.backUpTime==9}">selected</c:if>>09:00</option>
					<option value="10" <c:if test="${backUpPlan.backUpTime==10}">selected</c:if>>10:00</option>
					<option value="11" <c:if test="${backUpPlan.backUpTime==11}">selected</c:if>>11:00</option>
					<option value="12" <c:if test="${backUpPlan.backUpTime==12}">selected</c:if>>12:00</option>
					<option value="13" <c:if test="${backUpPlan.backUpTime==13}">selected</c:if>>13:00</option>
					<option value="14" <c:if test="${backUpPlan.backUpTime==14}">selected</c:if>>14:00</option>
					<option value="15" <c:if test="${backUpPlan.backUpTime==15}">selected</c:if>>15:00</option>
					<option value="16" <c:if test="${backUpPlan.backUpTime==16}">selected</c:if>>16:00</option>
					<option value="17" <c:if test="${backUpPlan.backUpTime==17}">selected</c:if>>17:00</option>
					<option value="18" <c:if test="${backUpPlan.backUpTime==18}">selected</c:if>>18:00</option>
					<option value="19" <c:if test="${backUpPlan.backUpTime==19}">selected</c:if>>19:00</option>
					<option value="20" <c:if test="${backUpPlan.backUpTime==20}">selected</c:if>>20:00</option>
					<option value="21" <c:if test="${backUpPlan.backUpTime==21}">selected</c:if>>21:00</option>
					<option value="22" <c:if test="${backUpPlan.backUpTime==22}">selected</c:if>>22:00</option>
					<option value="23" <c:if test="${backUpPlan.backUpTime==23}">selected</c:if>>23:00</option>
				</select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">是否启用备份</label>
              <div class="controls">
                <select id="startBackUp" name="backUpPlan.startBackUp" >
					<option value="1" <c:if test="${backUpPlan.startBackUp==1}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${backUpPlan.startBackUp==0}">selected</c:if>>不启用</option>
				</select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">备份文件保存份数</label>
              <div class="controls">
                <select id="selectStoreFileNum" name="backUpPlan.storeFileNum">
					<option value="1" <c:if test="${backUpPlan.storeFileNum==1}">selected</c:if>>一份</option>
					<option value="2" <c:if test="${backUpPlan.storeFileNum==2}">selected</c:if>>二份</option>
					<option value="3" <c:if test="${backUpPlan.storeFileNum==3}">selected</c:if>>三份</option>
				</select>
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">备份文件的存放位置</label>
              <div class="controls">
                <input type="text" class="span11" name="backUpPlan.storeURL" value="${backUpPlan.storeURL }" id="storeURL"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkBackUpPlanForm()" id="saveButton">保存</button>&nbsp;&nbsp;
             <!--  <button type="button" class="btn btn-success" onclick="toAdminList();">返回</button> -->
            </div>
          </form>
          <c:if test="${success==1}">
          <div class="alert alert-info alert-block"> <a class="close" data-dismiss="alert" href="#">×</a>
              <h4 class="alert-heading">保存成功!</h4><br>
          </div>
          </c:if>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
