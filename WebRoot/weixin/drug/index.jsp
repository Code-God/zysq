<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>找药神器</title>
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/banner.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
</head>

<body>
    <div class="main">
        <!---->
        <div class="row">
            <div class="pro-switch">
                <div class="slider">
                    <div class="flexslider">
                        <ul class="slides">
                        <c:forEach items="${bannerList}" var="banner">
                         <li>
                                <div class="img"><img src="${path}${banner.imgpath}" alt="" /></div>
                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <script defer src="js/banner_slider.js"></script>
        <script type="text/javascript">
            $(function() {
                $('.flexslider').flexslider({
                    animation: "slide"
                });
            });
        </script>
        <!---->
        <section class="search row">
            <form>
                <div class="col-2 search_icon"><span></span></div>
                <div class="col-10 row form_options">
                    <input id="search_input" class="col-8" type="text" placeholder="aaabbb项目名称/药物名称">
                    <button class="col-3" type="submit"><i class="iconfont icon-sousuo"></i>查询a</button>
                </div>
            </form>
        </section>
        <!---->
        <section class="row m-t-10 modular">
            <div>
                <img src="<%=request.getContextPath()%>/weixin/drug/images/hot_a.png" width="96%" />
            </div>
            <div class="m-t-10">
                <img src="<%=request.getContextPath()%>/weixin/drug/images/hot_b.png" width="96%" />
            </div>
            <div class="clear more_disease">
                <a href="disease_list.html"><i class="iconfont icon-73316"></i>更多项目</a>
            </div>
        </section>
    </div>
    <!---->
    <%@include file="footer.jsp"%>
    <!---->
</body>

</html>
