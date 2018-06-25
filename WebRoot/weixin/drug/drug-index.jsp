<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">

    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/plugin/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css?v=1.1">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/index.css?v=1.2">
    
    <script type="text/javascript" src="${ctx}/weixin/drug/js/plugin/jquery-2.1.4.min.js"></script>
    <title>找药神器</title>
    <script type="text/javascript">
    var activejsure=1;//设置footer激活
    
    function checkSubmitForm(){
		var search_name = $("#search_input").val();
		if($.trim(search_name) == ""){
	        
			return;
		}
		$("#searchForm").submit();
		 
	}
	
	function labelSubmit(key)
	{
	   $("#search_input").val(key);
	   $("#searchForm").submit();
	}
    
    document.onkeydown = function (e) {  
        if (!e) e = window.event;  
        if ((e.keyCode || e.which) == 13) {
        	checkSubmitForm();
             return false;
           
        }  
    }  
    </script>
</head>

<body class="bg">

<div class="index-mark"></div>
<header>
    <div class="head">
        <div class="swiper-container">
            <div class="swiper-wrapper">
				<c:forEach items="${bannerList}" var="banner">
					<div class="swiper-slide banner-item">
						<a href="${banner.imglink}">
							<img src="${imgurlpath}${banner.imgpath}" alt=""  width="100%" />
						</a>
					</div>
				</c:forEach>
            </div>
            <div class="swiper-pagination"></div>
        </div>

        <div class="search">
            <div class="search-contend">
            <form action="<%=request.getContextPath()%>/drug/index_indexSearch.Q" method="get" id="searchForm">
                <div class="search-input clearafter">
                    <i class="multiple"></i>
                    <input type="text" id="search_input" name="itemname" placeholder="请输入药物或疾病名称"/>
                </div>
                <a class="search-btn" href="javascript:void (0);" onclick="checkSubmitForm();">搜索</a>
                </form>
            </div>
            <ul class="search-list clearafter">
                <li>
                    <a class="search-label" href="javascript:void(0);" onclick="labelSubmit($(this).text());">肺癌</a>
                </li>
                <li>
                    <a class="search-label" href="javascript:void(0);" onclick="labelSubmit($(this).text());">肝癌</a>
                </li>
                <li>
                    <a class="search-label" href="javascript:void(0);" onclick="labelSubmit($(this).text());">多发性骨髓瘤</a>
                </li>
            </ul>
        </div>
    </div>
</header>

<section>
    <div class="contend">
        <div class="index-contend">
            <h2>现在招募中的临床（${diseaseCount}）</h2>
            <div class="index-list">
            <c:forEach items="${diseaseList}" var="disease">
	         <a class="index-item" href="${ctx}/drug/index_getDetailed.Q?id=${disease[0]}">
                    <div class="index-item-img">
                        <img src="${imgurlpath}${disease[3]}" alt="img"/>
                    </div>
                    <h3>${disease[1]}</h3>
                    <p>药物: ${disease[2]}</p>
                    <p>已报名<b>${disease[0]+disease[4]}人</b></p>
                    <i class="arrow multiple"></i>
                </a>
	         </c:forEach>
               
            </div>
        </div>
    </div>
</section>

    <!---->
    <%@include file="footer.jsp" %>
    <!---->
    <script src="${ctx}/weixin/drug/js/plugin/swiper-3.4.0.jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/weixin/drug/js/main.js"></script>
    <script type="text/javascript" src="${ctx}/weixin/drug/js/index.js"></script>
</body>

</html>