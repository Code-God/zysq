﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/plugin/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/details.css">
    
    <script src="${ctx}/weixin/drug/js/plugin/jquery-2.1.4.min.js"></script>
    <script src="${ctx}/weixin/drug/js/plugin/swiper-3.4.0.jquery.min.js"></script>
    <title>找药神器</title>
</head>

<body>

<header>
    <div class="head">
        <h1>${diseaseDict.dicDiseaseName}</h1>
        <p>${diseaseDict.dicDiseaseIntroduction}</p>
        <p>来源：CDE平台</p>
        <p>状态：
                            <c:if test="${diseaseDict.state==1}">
	                     	 进行中_尚未招募
	                     	</c:if>
	                     	<c:if test="${diseaseDict.state==2}">
	                     	进行中_招募中
	                     	</c:if>
	                     	<c:if test="${diseaseDict.state==3}">
	                     	  进行中_招募完成
	                     	</c:if>
	                     	<c:if test="${diseaseDict.state==4}">
	                     	已完成
	                     	</c:if>
	                     	<c:if test="${diseaseDict.state==5}">
	                     	  主动暂停
	                     	</c:if>
	                     	<c:if test="${diseaseDict.state==6}">
	                     	  被叫停
	                     	</c:if>
        </p>
    </div>
</header>

<section>
    <div class="contend">
        <!--列表切换选项卡-->
        <div class="maple-tab clearFix">
            <ul>
                <li class="active"><p class="m-border-right">基本信息</p></li>
                <li><p>药物信息</p></li>
                <li><p>项目信息</p></li>
                <li><p>项目中心</p></li>
            </ul>
        </div>

        <!--列表内容-->
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <div class="tab-item">
                        <ul class="essential">
                <li><strong>登记号：</strong>
                    <p>${diseaseDict.ctrId}</p>
                </li>
                <li><strong>发布日期：</strong>
                    <p>${fn:substring(diseaseDict.publishDate,0,10)}</p>
                    
                </li>
                <li><strong>适应症：</strong>
                    <p>${diseaseDict.adaptation}</p>
                </li>
                <li><strong>实验通俗题目：</strong>
                    <p>${diseaseDict.generalTitle}</p>
                </li>
            </ul>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="tab-item">
                        <ul class="essential">
                <li><strong>药物名称：</strong>
                <p>${diseaseDict.dicMedicineName}</p>
                </li>
                 <li><strong>药物类型：</strong>
                <p>${diseaseDict.dicMedicineType}</p>
                </li>
                 <li><strong>试验目的：</strong>
                <p>${diseaseDict.designPurpose}</p>
                </li>
                 <li><strong>对照药：</strong>
                <p>${diseaseDict.groupComparedMedicine}</p>
                </li>
            </ul>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="tab-item">
                        <ul class="essential">
                <li><strong>申办者名称：</strong>
                <p>${diseaseDict.sponsorinfo}</p>
                </li>
                <li><strong>试验分期：</strong>
                <p>${diseaseDict.designStage}</p>
                </li>
                <li><strong>设计类型：</strong>
                <p>${diseaseDict.designType}</p>
                </li>
                <li><strong>随机化：</strong>
                <p>${diseaseDict.randomize}</p>
                </li>
                <li><strong>盲法：</strong>
                <p>${diseaseDict.blindMethod}</p>
                </li>           
                <li><strong>入选标准：</strong></li>
                <li><div class="ex_resr">${diseaseDict.subjectConditionIn}</div>
                </li>
                <li><strong>排除标准：</strong></li>
                <li><div class="ex_resr">${diseaseDict.subjectConditionout}</div>
                </li></ul>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="tab-item">
                        <table>
                    <tr>
                        <th>机构名称</th>
                        <th>主要研究者</th>
                        <th>省/市</th>
                        </tr>
                        <c:forEach items="${drCenterList}" var="drCenter">                     
                        <tr>
                        <td>${drCenter.institutionName}</td>
                        <td>${drCenter.resercher}</td>
                        <td>${drCenter.ctContry}${drCenter.ctProvince}${drCenter.ctCity}</td>
                        </tr>
                        </c:forEach>
                    </table>  
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

    <!---->
    
  <%@include file="footer.jsp" %>
    <!---->
    <script type="text/javascript">
    $(function () {
    	var height = $(window).height() - 241;
	$(".swiper-slide").css("height",height);
	$(".swiper-slide").eq(0).css("height","auto");
        var mySwiper = new Swiper('.swiper-container', {
            onSlideChangeEnd: function (swiper) {
                var j=mySwiper.activeIndex;
                $(".swiper-slide").css("height",height);
                $(".swiper-slide").eq(j).css("height","auto");
                $('.maple-tab li').removeClass('active').eq(j).addClass('active');
            }
        })
        /*列表切换*/
        $('.maple-tab li').on('click', function (e) {
            e.preventDefault();
            //得到当前索引
            var i=$(this).index();
            //var height = $(window).height() - 220;
            $(".swiper-slide").css("height",height);
            $(".swiper-slide").eq(i).css("height","auto");
            $('.maple-tab li').removeClass('active').eq(i).addClass('active');
            mySwiper.slideTo(i,600,false);
        });
    });
</script>


<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${ctx}/weixin/js/wxjs.js"></script>
<script type="text/javascript" src="${ctx}/js/common/config.js"></script>
<script>
var activejsure=1;
</script>
<script type="text/javascript" src="${ctx}/weixin/drug/js/main.js"></script>

<script>



//微信是否初始化过,接入微信JS API
$(document).ready(function(){
	//初始化参数
	initWxParams();
});
/////////////////////////////////////////////////////////////////////////
</script>
</body>
</html>
