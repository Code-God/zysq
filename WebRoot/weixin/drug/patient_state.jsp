<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>我推荐的更多患者</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=5,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/user.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/shared.css">
    <script type="text/javascript">
       var activejsure=1;
    </script>
</head>

<body>

    <div class="main">
        <!---->
        <!---->
        <div class="state">
            <!---->
            <s:if test="#request.recommendlist!=null">
            	<s:iterator value="#request.recommendlist" var="recommend">
		           	 <div class="state_body_list">
		                <h1>${recommend[0]}(年龄：${recommend[1]}；性别：${recommend[2]})</h1>
		                <div class="stepInfo">
		                    <ul>
		                        <li></li>
		                        <li></li>
		                    </ul>
	                    	<div class="stepIco stepIco1 <s:if test="#request.recommend[3]>=11"> create_node </s:if> ">
	                    		<span class="icon iconfont">
	                    		
	                    			<s:if test="#request.recommend[3] < 11 ">
		                        		1
		                        	</s:if>
	                    			<s:elseif test="#request.recommend[3]>=11&&#request.recommend[3]!=12">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.recommend[3]==12">
		                        		&#xe608;
		                        	</s:elseif>		                        	
		                      		
		                       </span>
		                        <div class="stepText">病历审核</div>
		                    </div>
			                    
		                    <div class="stepIco stepIco2 <s:if test="#request.recommend[3]>=21"> create_node </s:if>">
		                        <span class="icon iconfont">
		                        	<s:if test="#request.recommend[3] < 21 ">
		                        		2
		                        	</s:if>
	                    			<s:elseif test="#request.recommend[3]>=21&&#request.recommend[3]!=22">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.recommend[3]==22">
		                        		&#xe608;
		                        	</s:elseif>	
		                        </span>
		                        <div class="stepText">电话联系</div>
		                    </div>
		                    <div class="stepIco stepIco3 <s:if test="#request.recommend[3]>=31"> create_node </s:if>">
		                        <span class="icon iconfont">
		                        	<s:if test="#request.recommend[3] < 31 ">
		                        		3
		                        	</s:if>
	                    			<s:elseif test="#request.recommend[3]>=31&&#request.recommend[3]!=32">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.recommend[3]==32">
		                        		&#xe608;
		                        	</s:elseif>	
		                        
								</span>
		                        <div class="stepText">医院筛选</div>
		                    </div>
		                    <div class="stepIco stepIco4 <s:if test="#request.recommend[3]>=41"> create_node </s:if>" >
		                        <span class="icon iconfont">
		                        	<s:if test="#request.recommend[3] < 41 ">
		                        		4
		                        	</s:if>
	                    			<s:elseif test="#request.recommend[3]>=41&&#request.recommend[3]!=42">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.recommend[3]==42">
		                        		&#xe608;
		                        	</s:elseif>
		                        </span>
		                        <div class="stepText">通过</div>
		                    </div>	
		                </div>
		            </div>
           		 <!---->
           		 </s:iterator>
 			</s:if>
        </div>
        <!---->
    </div>
    <!---->
 	  <%@include file="footer.jsp"%>
    <!---->
</body>

</html>