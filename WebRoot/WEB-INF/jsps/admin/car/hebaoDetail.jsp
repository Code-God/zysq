<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.base.tools.Version"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>核保明细,车牌号:【${carInfo.carNo }】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function back(){
		window.location.href = "<%=request.getContextPath()%>/admin/car_hebaoIndex.Q";
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
          <h5>车辆核保信息</h5>
        </div>
        <form  class="form-horizontal">
        <div class="widget-content nopadding">
          	<input type="hidden" name="products.id" id="productsId" value="${products.id }">
            <div class="control-group">
              <label class="control-label">车主姓名 :</label>
              <div class="controls">
              	${carInfo.userName }
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">联系电话 :</label>
              <div class="controls">
                ${carInfo.telephone }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">车牌号 :</label>
              <div class="controls">
              	${carInfo.carNo }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">行驶城市 :</label>
              <div class="controls">
              	${carInfo.city }
              </div>
            </div>
           <div class="control-group">
              <label class="control-label">车架号:</label>
              <div class="controls">
				${carInfo.city }
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">车辆型号 :</label>
              <div class="controls">
				${carInfo.carModel}
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">发动机号 :</label>
              <div class="controls">
              	${carInfo.enginSn }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">注册日期 :</label>
              <div class="controls">
				${carInfo.regDate }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">是否过户 :</label>
              <div class="controls">
				<c:if test="${carInfo.guohu ==1 }"><font color=red>是</font></c:if>
				<c:if test="${carInfo.guohu ==0 }">否</c:if>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">商业险起保日期 :</label>
              <div class="controls">
					${carInfo.bizInsStartDate }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">交强险起保日期 :</label>
              <div class="controls">
					${carInfo.jqxStartDate }
              </div>
            </div>
           <!-- 以下是保险组合方案 -->
             <div class="control-group">
              <label class="control-label">用户预计购买的保险组合 :</label>
              <div class="controls">
              		<c:if test="${carInfo.ciInfo.jiaoqiangxian==1 }">
              			<span class="label label-important">交强险</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.jiaoqiangxian==0 }">
              			<span class="label">交强险</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.chechuan==1 }">
              			<span class="label label-important">车船税</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.chechuan==0 }">
              			<span class="label">车船税</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.cheliangsunshi==1 }">
              			<span class="label label-important">车损险不计免赔</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.cheliangsunshi==0 }">
              			<span class="label">车损险不计免赔</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		
              		<c:if test="${carInfo.ciInfo.disanfang==1 }">
              			<span class="label label-important">第三者责任险不计免赔</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.disanfang==0 }">
              			<span class="label">第三者责任险不计免赔</span>
              		</c:if>
              		<br>
              		<br>
              		
              		
              		<c:if test="${carInfo.ciInfo.sijibujimianpei==1 }">
              			<span class="label label-important">司机座位责任险不计免赔</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.sijibujimianpei==0 }">
              			<span class="label">司机座位责任险不计免赔</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.chengkebujimianpei==1 }">
              			<span class="label label-important">乘客座位责任险不计免赔</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.chengkebujimianpei==0 }">
              			<span class="label">乘客座位责任险不计免赔</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.quanchedaoqiang==1 }">
              			<span class="label label-important">全车盗抢险</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.quanchedaoqiang==0 }">
              			<span class="label">全车盗抢险</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.bolidandu==1 }">
              			<span class="label label-important">玻璃单独破碎险</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.bolidandu==0 }">
              			<span class="label">玻璃单独破碎险</span>
              		</c:if>
              			<br>
              			<br>
              			
              		<c:if test="${carInfo.ciInfo.huaheng==1 }">
              			<span class="label label-important">车辆划痕损失险</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.huaheng==0 }">
              			<span class="label">车辆划痕损失险</span>
              		</c:if>
              		&nbsp;&nbsp;&nbsp;&nbsp;
              		<c:if test="${carInfo.ciInfo.sheshuisunshi==1 }">
              			<span class="label label-important">涉水损失险</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.sheshuisunshi==0 }">
              			<span class="label">涉水损失险</span>
              		</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;              		
              		<c:if test="${carInfo.ciInfo.ziransunshi==1 }">
              			<span class="label label-important">自燃损失险不计免赔</span>
              		</c:if>
              		<c:if test="${carInfo.ciInfo.ziransunshi==0 }">
              			<span class="label">自燃损失险不计免赔</span>
              		</c:if>
              		
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="back();">返回</button>
            </div>
        </div>
		</form>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
