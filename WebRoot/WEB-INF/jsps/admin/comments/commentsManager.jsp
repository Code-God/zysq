<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="<%=request.getContextPath()%>/js/dpicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function resetForm(){
		$("#startTime").val("");
		$("#endTime").val("");
		$("#prdCode").val("");
	}
	function addOrUpdate(id){
		var url = "<%=request.getContextPath()%>/admin/comments_commentsInput.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function detail(id){
		var url = "<%=request.getContextPath()%>/admin/comments_detail.Q?id="+id;
		window.location.href = url;
	}
	function delByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条评价删除',
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
			    content: '你确定要删除选中的评价吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=request.getContextPath()%>/admin/comments_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=request.getContextPath()%>/admin/comments_manager.Q";
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
			    cancel: true //为true等价于function(){}
			});
		}
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
      <div class="widget-box">
        <div class="widget-content">
          <form action="<%=request.getContextPath()%>/admin/comments_manager.Q" method="post" id="commentsQueryForm">
            <div class="controls controls-row">
             <label class="span1" for="inputSuccess" style="margin-top:5px">商品编号:</label>
	            <input name="prdCode" id="prdCode" type="text" class="span2" style="margin-left:5px;margin-right:25px" value="${ prdCode}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px">评价时间:</label>
	            
	            <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${startTime}" class="wdateinput" />
		                  	&nbsp;至&nbsp;
		         <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" value="${endTime}" class="wdateinput" />
	            &nbsp; &nbsp;
	           
	            <button type="submit" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	         </div>
          </form>
        </div>
        <br/>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>评价列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="delByIds();">删除</button>
         </div>
         <div class="widget-content nopadding" id="commentsTableId">
            <%@include file="commentsList.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
