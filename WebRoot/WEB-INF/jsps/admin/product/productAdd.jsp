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
<script src="<%=request.getContextPath()%>/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
<script type="text/javascript">
	function checkProductsForm(){
		var spec = $("#prdStandard").val();	
		if(spec == ""){
			$("#prdStandard").val($("#prdSpecList option:selected").text());
		}
		var pass = true;
		var name = $("#name").val();
		if(!name){
			pass = false;
		}
		var prdCatCode = $("#prdCatCode").val();
		if(!prdCatCode){
			pass = false;
		}
		var prdPrice = $("#prdPrice").val();
		if(!prdPrice || isNaN(prdPrice)){
			pass = false;
		}
		var prdDisPrice = $("#prdDisPrice").val();
		if(!prdDisPrice || isNaN(prdDisPrice)){
			pass = false;
		}
		var picUrl1Value = $("#picUrl1File").val();
		var productsId = $("#productsId").val();
		if(!picUrl1Value && !productsId){
			pass = false;
		}
		if(pass){
			$("#productsForm").submit();
		}else{
			art.dialog({fixed : true,content : '请检查是否有未填写的项并正确填写',okVal:'确定',ok:true});
		}
		
	}
	function chekRecommend(recommend){
		if(1==recommend || 2==recommend){
			var prdCatCode = $("#prdCatCode").val();
			if(!prdCatCode){
				$("#recommend").val(0);
				art.dialog({fixed : true,content : '请先选择商品所属分类',okVal:'确定',ok:true});
				return;
			}
			$.ajax({
				url:"<%=request.getContextPath()%>/admin/products_chekRecommend.Q",
				data:{"recommend":recommend,"prdCatCode":prdCatCode},
				dataType:'text',
				success:function(data){
					if(1==data){
						$("#recommend").val(0);
						art.dialog({fixed : true,content : '一个大类里面只能有6个推荐，目前已超过6个。',okVal:'确定',ok:true});
					}else if(2==data){
						$("#recommend").val(0);
						art.dialog({fixed : true,content : '一个大类里面只能有1个大图推荐，目前已超过1个。',okVal:'确定',ok:true});
					}
				}
			});
		}
	}
	function toProductsList(){
		window.location.href = "<%=request.getContextPath()%>/admin/products_manager.Q";
	}
	function openPrdCatTree(){
    	var url = "<%=request.getContextPath()%>/admin/products_openPrdCatTree.Q";
       art.dialog.open(url, {limit:false,lock:true,title:'<b>商品类型</b>',width:'260px',height:'400px'});
	}
	
	function setIndex(obj, index){
		//pic1Index
		$("#pic"+index+"Index").val(index);
	}
	
	//把选中的规格，设置到隐藏文本区
	function setPrdSpec(id){
		$("#prdStandard").val($("#prdSpecList option:selected").text());
	}
	/** 清除图片 */
	function clearPic(index){
		var prdId = $("#productsId").val();
		//修改时， 清除图片是要去后台清除		
		if(parseInt(prdId) > 0){//
			$.ajax({
				type : "POST",
				url :  "<%=request.getContextPath()%>/admin/fx_clearPrdPic.Q?index="+index+"&id=${products.id }",
				dataType : "json",
				success : function(data) {
					art.dialog("清除成功！");
					window.location.reload();
				}
			});
		}else{
			$("#picUrl"+ index +"File").val("");
			$("#picUrl"+ index +"Value").val("");//picUrl2Value
			$("#picUrl"+ index +"File").siblings(".filename").html("");	
		}
	}
	
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub11"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="<%=request.getContextPath()%>/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="<%=request.getContextPath()%>/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm" enctype="multipart/form-data">
          	<input type="hidden" name="products.id" id="productsId" value="${products.id }">
          	<input type="hidden" name="products.prdCode" id="prdCode" value="${products.prdCode }">
            <div class="control-group">
              <label class="control-label">商品名</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="products.name" value="${products.name }" />&nbsp;&nbsp;
              </div>
            </div>
             
            <div class="control-group">
              <label class="control-label">所属分类</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdCatName" value="${products.prdCatName }" id="prdCatName" onclick="openPrdCatTree();"/>&nbsp;&nbsp;
                <input type="hidden" class="span5" name="products.prdCatCode" value="${products.prdCatCode }" id="prdCatCode"/>&nbsp;&nbsp;
              </div>
            </div>
            <!-- 
             <div class="control-group">
	              <label class="control-label">商品规格</label>
	              <div class="controls" >
	              		<select id="prdSpecList" class="span3" onchange="setPrdSpec(this)">
	              			<option>--请选择分类--</option>
	              		</select>
	              		<font color=red>* 注意：请选择分类后再选择对应的规格。</font>
	              </div>
                <input type="text" style="display: none" class="span5" name="products.prdStandard" value="${products.prdStandard }" id="prdStandard"/>&nbsp;&nbsp;
            </div>
             -->
             <div class="control-group">
              <label class="control-label">商品价格</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdPrice" value="${products.prdPrice }" id="prdPrice"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">折扣价</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdDisPrice" value="${products.prdDisPrice }" id="prdDisPrice"/>&nbsp;&nbsp;
             	<font color=red>* 这就是商品的最终价格。</font>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">适用门店：</label>
              <div class="controls">
                	<select name="products.serviceId" id="serviceId" class="span3">
                		<option value="0">--请选择--</option>
                		<s:iterator var="ss" value="#request.serviceList">
                			<c:if test="${ss.id == serviceId}">
	                			<option selected="selected" value="<s:property value="id"/>"><s:property value="company"/> </option>
                			</c:if>
                			<c:if test="${ss.id != serviceId}">
	                			<option value="<s:property value="id"/>"><s:property value="company"/> </option>
                			</c:if>
                		</s:iterator>
                	</select>&nbsp; &nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">卡券类型：</label>
              <div class="controls">
                	<select name="products.cardType" id="cardType" class="span3">
                		<option value="0">--请选择--</option>
                		<option value="M" selected="selected">现金抵扣优惠券 </option>
                		<!-- 
                		<option value="C_10">洗车卡（10次） </option>
                		<option value="C_20">洗车卡（20次） </option>
                		<option value="C_30">洗车卡（30次） </option>
                		<option value="C_50">洗车卡（50次） </option>
                		<option value="C_100">洗车卡（100次） </option>
                		<option value="C_1M">洗车卡（月卡） </option>
                		<option value="C_3M">洗车卡（季度卡） </option>
                		<option value="C_6M">洗车卡（半年卡） </option>
                		<option value="C_12M">洗车卡（年卡） </option>
                		 -->
                	</select>&nbsp; &nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">有效期：</label>
              <div class="controls">
                <input type="text" class="span1" name="products.expireDays" value="${products.expireDays}" id="expireDays"/>天&nbsp; &nbsp;&nbsp;
                
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">库存数量：</label>
              <div class="controls">
                <input type="text" class="span1" name="products.stock" value="${products.stock }" id="stock"/>&nbsp; &nbsp;&nbsp;
                
              </div>
            </div>
            
              <div class="control-group">
              <label class="control-label">展示方式</label>
              <div class="controls">
                <select name="products.recommend" id="recommend" class="span3">
                  <option value="0" <s:if test="products.recommend==0">selected</s:if> >一行2个（默认）</option>
                  <option value="1" <s:if test="products.recommend==1">selected</s:if>>推荐</option>
                  <option value="2" <s:if test="products.recommend==2">selected</s:if>>横向大图</option>
                </select>
                <font color=red>（注意：设置为推荐即显示在首页）</font>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">主图 </label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl1Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl1File" size="0" onchange="setIndex(this,1);" style="width:71px;"/>
                 &nbsp;${products.picUrl1 }
				<button id="saveButton" onclick="clearPic(1)" class="btn btn-info" type="button">清除图片</button>
				<font color=red>用来显示在商品列表的图片缩略图，建议压缩后上传，可大幅提高页面加载速度。尺寸： 300px*300px</font>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图1 </label>
              <div class="controls">
               <input type="hidden" class="span5" name="picUrl" id="picUrl2Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl2File" onchange="setIndex(this,2);" style="width:71px;"/>
                &nbsp;${products.picUrl2 }
                <button id="saveButton" onclick="clearPic(2)" class="btn btn-info" type="button">清除图片</button>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图2</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl3Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl3File" onchange="setIndex(this,3);" style="width:71px;"/>
                 &nbsp;${products.picUrl3 }
                 <button id="saveButton" onclick="clearPic(3)" class="btn btn-info" type="button">清除图片</button>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图3</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl4Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl4" onchange="setIndex(this,4);" style="width:71px;"/>
                 &nbsp;${products.picUrl4 }
                 <button id="saveButton" onclick="clearPic(4)" class="btn btn-info" type="button">清除图片</button>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图4</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl5Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl5File" onchange="setIndex(this,5);" style="width:71px;"/>
                 &nbsp;${products.picUrl5 }
                 <button id="saveButton" onclick="clearPic(5)" class="btn btn-info" type="button">清除图片</button>
              </div>
            </div>
            <input type="hidden" name="pic1Index" id="pic1Index" value="0"/>
			<input type="hidden" name="pic2Index" id="pic2Index" value="0"/>
			<input type="hidden" name="pic3Index" id="pic3Index" value="0"/>
			<input type="hidden" name="pic4Index" id="pic4Index" value="0"/>
			<input type="hidden" name="pic5Index" id="pic5Index" value="0"/>
             <div class="control-group">
              <label class="control-label">商品描述</label>
              <div class="controls">
                <textarea name="products.prdDesc" id="prdDesc" style="width:800px;height:300px;">${products.prdDesc }</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdDesc');
				</script>
              </div>
            </div>
            
            <div class="control-group" style="display:none">
              <label class="control-label">品牌故事</label>
              <div class="controls">
                <textarea name="products.prdStory" id="prdStory" style="width:800px;height:300px;">${products.prdStory }&nbsp;</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdStory');
				</script>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkProductsForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
<script type="text/javascript">
function setServiceId(){
	var sid = ${products.serviceId};
	$("#serviceId").val(sid);
}


$(document).ready(function(){
	setServiceId();
});

</script>
</body>
</html>
