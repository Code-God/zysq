<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <title>搜索结果</title>
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
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/page.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/iconfont/iconfont.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/load.css">
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/shared.css">
    
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/load.js"></script>
    
<script type="text/javascript">
function encode(s){
  return s.replace(/&/g,"&").replace(/</g,"<").replace(/>/g,">").replace(/([\\\.\*\[\]\(\)\$\^])/g,"\\$1");
}
function decode(s){
  return s.replace(/\\([\\\.\*\[\]\(\)\$\^])/g,"$1").replace(/>/g,">").replace(/</g,"<").replace(/&/g,"&");
}
function highlight(s){
  s=encode(s);
  var obj=document.getElementsByTagName("body")[0];
  var t=obj.innerHTML.replace(/<i\s+class=.?highlight.?>([^<>]*)<\/i>/gi,"$1");
  obj.innerHTML=t;
  var cnt=loopSearch(s,obj);
  t=obj.innerHTML
  var r=/{searchHL}(({(?!\/searchHL})|[^{])*){\/searchHL}/g
  t=t.replace(r,"<i class='highlight'>$1</i>");
  obj.innerHTML=t;
}
function loopSearch(s,obj){
  var cnt=0;
  if (obj.nodeType==3){
    cnt=replace(s,obj);s
    return cnt;
  }
  for (var i=0,c;c=obj.childNodes[i];i++){
    if (!c.className||c.className!="highlight")
      cnt+=loopSearch(s,c);
  }
  return cnt;
}
function replace(s,dest){
  var r=new RegExp(s,"g");
  var tm=null;
  var t=dest.nodeValue;
  var cnt=0;
  if (tm=t.match(r)){
    cnt=tm.length;
    t=t.replace(r,"{searchHL}"+decode(s)+"{/searchHL}")
    dest.nodeValue=t;
  }
  return cnt;
}

</script>
</head>
<body onload="highlight(this.itemname.value);">
    <div class="main">
    <input type="hidden"  id="itemname" value="${requestScope.itemName}">
        <!---->
        <div class="panel_search">
       <c:if test="${diseaseItemSize==0 && diseaseDictSize==0}">
       		没有搜索到相关信息!
       </c:if>
        <c:forEach items="${mapDiseaseItem}" var="mapDisease">
            <!---->
            <c:forEach items="${mapDisease.value}" var="diseaseItem">
	            <a class="panel_search_body" href="<%=request.getContextPath()%>/drug/index_getDetailed.Q?id=${diseaseItem[0]}"">
	                    <strong>${diseaseItem[1]}</strong>
	                    <p>${diseaseItem[2]}</p>
	                    <span>来源：找药神器</span>
	                </a>
	            </a>
                </c:forEach>
        </c:forEach>
        
            
        </div>
        
        <!--加载中的效果-->
		<div class="show-more-kj">
			<div class="circle"></div>
			<div class="circle1"></div>
		</div>
		<!--加载中的效果end-->
		
        <!---->
    </div>
    <!---->
    <%@include file="footer.jsp" %>
    <!---->
    
    <script type="text/javascript">
       var activejsure = 1;
    </script>
    <script src="${ctx}/weixin/drug/js/main.js"></script>
</body>
</html>
<script>
</script>
