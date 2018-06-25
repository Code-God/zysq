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

<script type="text/javascript">
	function toProductsList(){
		window.location.href = "<%=request.getContextPath()%>/admin/products_manager.Q";
	}
</script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
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
          <form action="<%=request.getContextPath()%>/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm">
          	<input type="hidden" name="products.id" id="productsId" value="${products.id }">
            <div class="control-group">
              <label class="control-label">商品名 :</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="products.name" value="${products.name }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">商品编码 :</label>
              <div class="controls">
                <input type="text" id="prdCode" class="span5" name="products.prdCode" value="${products.prdCode }" disabled/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">所属分类 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdCatName" value="${products.prdCatName }" id="prdDisPrice" disabled/>&nbsp;&nbsp;
                <input type="hidden" class="span5" name="products.prdCatCode" value="${products.prdCatCode }" id="prdCatCode"/>&nbsp;&nbsp;
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">商品规格 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdStandard" value="${products.prdStandard }" id="prdStandard" disabled/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品价格 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdPrice" value="${products.prdPrice }" id="prdPrice" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">折扣价 :</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdDisPrice" value="${products.prdDisPrice }" id="prdDisPrice" disabled/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">适用门店 :</label>
              <div class="controls">
                <s:property value="products.serviceName"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">卡券类型 :</label>
              <div class="controls">
                <s:property value="products.cardTypeStr"/>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">有效期 :</label>
              <div class="controls">
                <s:property value="products.expireDays"/> 天
              </div>
            </div>
           
             <div class="control-group">
              <label class="control-label">产品图展示方式</label>
              <div class="controls">
                <select name="products.recommend" id="recommend" class="span3" disabled="disabled">
                  <option value="0" <s:if test="products.recommend==0">selected</s:if> >一行两个(默认)</option>
                  <!-- 
                  <option value="1" <s:if test="products.recommend==1">selected</s:if>>推荐</option>
                   -->
                  <option value="2" <s:if test="products.recommend==2">selected</s:if>>横向大图</option>
                </select>
                <font color=red>（注意：此设置仅对缩略图列表方式有效）</font>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图1 </label>
              <div class="controls">
                 <s:if test="products.picUrl1==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl1"/>' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl1"/>' width="24" height="16" alt="点击查看图片"/> </a>
					<!--<a rel="attachment" href='${imgServer }<s:property value="products.prdCode"/>_small.jpg' title="点击查看图片" target="_blank">
					<img src='${imgServer }<s:property value="products.prdCode"/>_small.jpg' width="24" height="16" alt="点击查看图片"/> </a>-->
            	</s:else>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图2 </label>
              <div class="controls">
              <s:if test="products.picUrl2==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl2"/>' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl2"/>' width="24" height="17" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图3</label>
              <div class="controls">
               <s:if test="products.picUrl3==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl3"/>' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl3"/>' width="24" height="17" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图4</label>
              <div class="controls">
                <s:if test="products.picUrl4==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl4"/>' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl4"/>' width="24" height="17" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图5</label>
              <div class="controls">
                <s:if test="products.picUrl5==null">
            		暂无图片
            	</s:if>
            	<s:else>
            		<a rel="attachment" href='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl5"/>' title="点击查看图片" target="_blank">
					<img src='<%=request.getContextPath() %>/private/UploadImages<s:property value="products.picUrl5"/>' width="24" height="17" alt="点击查看图片"/> </a>
            	</s:else>
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品描述 ：</label>
              <div class="controls">
                <textarea name="products.prdDesc" id="prdDesc" style="width:800px;height:300px;" disabled>${products.prdDesc }</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdDesc');
				</script>
              </div>
            </div>
            
            <div class="control-group" style="display: none;">
              <label class="control-label">品牌故事 ：</label>
              <div class="controls">
                <textarea name="products.prdStory" id="prdStory" style="width:800px;height:300px;" disabled>${products.prdStory }</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdStory');
				</script>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
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
