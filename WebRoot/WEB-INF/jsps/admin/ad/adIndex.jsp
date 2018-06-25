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
		<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
		<script type="text/javascript">
	function resetForm(){
		$("#adType").val("");
	}
	function queryForm(){
		$("form").submit();
	}
	function addOrUpdate(id){
		var url = "<%=request.getContextPath()%>/admin/ad_input.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请选择需要删除的广告配置',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要删除该广告配置吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/ad_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'json',
						success:function(data){
							if(data.result=="success"){
								$("form").submit();
							}else{
								art.dialog({
									time: 2,
									content: '删除失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true
			});
		}
	}
</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
		<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub13" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb">
					<a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
				</div>
			</div>
			<div class="container-fluid">
				<div class="widget-box">
					<div class="widget-content">
						<form action="<%=request.getContextPath()%>/admin/ad_index.Q" method="post" id="adQueryForm">
							<div class="controls controls-row">
								<label class="span1" for="inputSuccess" style="margin-top: 5px">
									广告类型:
								</label>
								<select name="adType" id="adType"
									style="margin-left: 5px; margin-right: 35px;width:156px">
									<option value="">
										请选择
									</option>
									<option value="1" <s:if test="#request.adType==1">selected</s:if>>
										普通广告
									</option>
									<option value="2" <s:if test="#request.adType==2">selected</s:if>>
										幻灯片广告
									</option>
								</select>
								<button type="submit" class="btn btn-success">
									查询
								</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-primary"
									onclick="resetForm();">
									重置
								</button>
							</div>
						</form>
					</div>
					<br />
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i> </span>
						<h5>
							广告维护列表
						</h5>
						<button class="label label-info btn btn-primary btn-mini"
							onclick="addOrUpdate();">
							新增
						</button>
						<button class="label label-info btn btn-primary btn-mini"
							onclick="delByIds();">
							删除
						</button>
					</div>
					<div class="widget-content nopadding" id="adTableId">
						<%@include file="adList.jsp"%>
					</div>
				</div>
			</div>
			
			<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
	</body>
</html>
