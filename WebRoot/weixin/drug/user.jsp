<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>我的</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=5,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/user.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/iconfont/iconfont.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css">
    <script src="${ctx}/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="${ctx}/weixin/drug/js/html5shiv.js"></script>
    <script src="${ctx}/weixin/drug/js/medicine.js"></script>
<script type="text/javascript">
        var activejsure=3;//设置footer选中项
		function checkuserInfo(){
			var userid='${requestScope.userid}';
			var realName='${requestScope.userinfo.realName}';
			var mobilePhone='${requestScope.userinfo.mobilePhone}';
			var sex='${requestScope.userinfo.sex}';
			//var age='${requestScope.userinfo.age}';
			if(realName==""|| mobilePhone=="" || sex==""){
                               alert("请先完善信息");
			       window.location.href="<%=request.getContextPath() %>/drug/user_perfectUserInfo.Q?userid="+userid;
			}else{
				 window.location.href="<%=request.getContextPath() %>/drug/qualification_getQualificationByUserId.Q?userid="+userid;
			}	
		}
	</script>
</head>

<body onload="">

    <div class="main">
        <!---->
        <div class="user_head">
           <a href="<%=request.getContextPath() %>/drug/user_perfectUserInfo.Q?userid=${userid}">
                <i class="icon iconfont"><img src="${userinfo.userPic }" width="50dp" height="50dp"></i>
                <div class="u_info">
                    <strong>昵称：${userinfo.nickName }</strong>
                    <p>积分：${userinfo.points}</p>
                </div>
                <div class="perfect_btn">完善信息</div>
            </a>
            <div class="clear"></div>
        </div>
        <!---->
        <div class="user_panel">
            <a href="javascript:void(0);" onclick="checkuserInfo();" >
                             医疗专业认证
            <span class="c_state">
            <s:if test="#request.qualification.reviewState==0">等待审核</s:if>
            <s:elseif test="#request.qualification.reviewState==1">已认证</s:elseif>
            <s:elseif test="#request.qualification.reviewState==2">审核未通过</s:elseif>
            <s:else><span>未认证</span></s:else>
            </span></a>
        </div>
        <!--
        <div class="user_panel">
            <a href="<%=request.getContextPath() %>/drug/user_userQRCode.Q?userid=${userid}">我的找药神器二维码</a>
        </div>
        -->
        <s:if test="#request.recommend!=null">
	        <div class="state">
	        	
	            <div class="state_haed">我推荐的患者
	                <a href="<%=request.getContextPath() %>/drug/user_getMoreRecommendList.Q?userid=${userid}">查看更多</a></div>
	            <div class="state_body">
	                <!-- <h1>张三（年龄：30；性别：男）</h1> -->
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
	        </div>
	       </s:if>
        <!---->
        <!---->
        <s:if test="#request.active!=null">
	        <div class="state m-t-10">
	            <div class="state_haed">我的报名状态</div>
	            <div class="state_body">
	                <div class="stepInfo">
	                    <ul>
	                        <li></li>
	                        <li></li>
	                    </ul>
	                    	<div class="stepIco stepIco1 <s:if test="#request.active[3]>=11"> create_node </s:if> ">
	                    		<span class="icon iconfont">
	                    		
	                    			<s:if test="#request.active[3] < 11 ">
		                        		1
		                        	</s:if>
	                    			<s:elseif test="#request.active[3]>=11&&#request.active[3]!=12">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.active[3]==12">
		                        		&#xe608;
		                        	</s:elseif>		                        	
		                      		
		                       </span>
		                        <div class="stepText">病历审核</div>
		                    </div>
			                    
		                    <div class="stepIco stepIco2 <s:if test="#request.active[3]>=21"> create_node </s:if>">
		                        <span class="icon iconfont">
		                        	<s:if test="#request.active[3] < 21 ">
		                        		2
		                        	</s:if>
	                    			<s:elseif test="#request.active[3]>=21&&#request.active[3]!=22">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.active[3]==22">
		                        		&#xe608;
		                        	</s:elseif>	
		                        </span>
		                        <div class="stepText">电话联系</div>
		                    </div>
		                    <div class="stepIco stepIco3 <s:if test="#request.active[3]>=31"> create_node </s:if>">
		                        <span class="icon iconfont">
		                        	<s:if test="#request.active[3] < 31 ">
		                        		3
		                        	</s:if>
	                    			<s:elseif test="#request.active[3]>=31&&#request.active[3]!=32">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.active[3]==32">
		                        		&#xe608;
		                        	</s:elseif>	
		                        
								</span>
		                        <div class="stepText">医院筛选</div>
		                    </div>
		                    <div class="stepIco stepIco4 <s:if test="#request.active[3]>=41"> create_node </s:if>" >
		                        <span class="icon iconfont">
		                        	<s:if test="#request.active[3] < 41 ">
		                        		4
		                        	</s:if>
	                    			<s:elseif test="#request.active[3]>=41&&#request.active[3]!=42">
		                        		&#xe630;
		                        	</s:elseif>
		                        	<s:elseif test="#request.active[3]==42">
		                        		&#xe62f;
		                        	</s:elseif>
		                        </span>
		                        <div class="stepText">通过</div>
		                    </div>	
	                </div>
	            </div>
	        </div>
	     </s:if>   
        <!---->
        <!---->
        <div class="state m-t-10">
            <div class="state_haed">相关项目</div>
            <div class="state_body">
            
            
 <%--           <a href="#">
                    <strong>高血脂</strong>
                    <p>针对高危人群进行强化降脂治疗，可以使更多的冠心病极其高危者受益！</p>
                </a>
                <a href="#">
                    <strong>高血脂</strong>
                    <p>针对高危人群进行强化降脂治疗，可以使更多的冠心病极其高危者受益！</p>
                </a>
                <a href="#">
                    <strong>高血脂</strong>
                    <p>针对高危人群进行强化降脂治疗，可以使更多的冠心病极其高危者受益！</p>
                </a> --%>
                <s:iterator value="#request.relationlist" var="relationitem">
	                <a href="<%=request.getContextPath() %>/drug/index_getDetailed.Q?id=${relationitem[0]}">
	                    <strong>${relationitem[1]}</strong>
	                    <s:if test="#relationitem[2].length()>20">
	                    	<p><s:property value="#relationitem[2].substring(0,20)"/>...</p>
	                    </s:if>
	                    <s:else>
	                    	 <p>${relationitem[2]}</p>
	                    </s:else>	                   
	                </a>
                </s:iterator>
                
            </div>
        </div>
        <!---->
    </div>
    <!---->
   <%@include file="footer.jsp"%>
    <!---->
    
    <script src="${ctx}/weixin/drug/js/main.js"></script>
</body>

</html>