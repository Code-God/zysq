<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@include file="common/commonHeader.jsp"%>
<!doctype html>
<html>
<head>
<base href="<%=basePath %>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>${seoConfig.title }</title>
<meta name="description" content="${seoConfig.description } ">
<meta name="Keywords" content="${seoConfig.keywords }">

<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery.easing-1.3.js"></script>
<script type="text/javascript" src="js/js.js"></script>
<script src="js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
</head>
<body>
<%@include file="/WEB-INF/jsps/header.jsp"%>
<%@include file="/WEB-INF/jsps/searchHeader.jsp"%>
<%@include file="/WEB-INF/jsps/navigate.jsp"%>
<div class="wf_content">
  <div class="core">
    <div class="grid_c1 pb_10">
      <div class="grid_s"></div>
      <div class="grid_m">
        <div class="slider" id="main_slider">
          <ul class="slider_img">
            <c:forEach var="ad" items="${adList}" varStatus="vs">
			<%// 幻灯片广告 %>
            <c:if test="${ad.adType == 2}">
			<li><a target="_blank" href="${ad.url }"><img src="${imgPath }${ad.picUrl}"></a></li>  
            </c:if>
			</c:forEach>
          </ul>
          <ul class="focusBtn">
            <c:forEach var="ad" items="${adList}" varStatus="vs">
			<li>${vs.index+1}</li>
			</c:forEach>
          </ul>
        </div>
        <div class="new_starter">
          <div class="new_starter_bd">
            <div class="new_starter_glide" id="new_products">
              <h3>新品首发</h3>
              <ul class="new_starter_glide_img">
              	<c:forEach var="x" items="${recommendList}">
              	<c:if test="${x.type == 1}">
                <li>
                <a target="_blank" href="prd/${x.product.id }">
	                <p class="new_p_img">
	                	<img src="${imgPath }${x.product.prdCode }_small.jpg" width="245" height="310">
	                </p>
	                <h4>${x.product.name }</h4>
	                <h5>${x.product.prdStandard }</h5>
	                <p>新品尝鲜价<span class="new_prince">￥${x.product.prdDisPrice }元</span></p>
                </a>
                </li>
              	</c:if>
              	</c:forEach>
              </ul>
              <ul class="new_starter_glide_trig" id="j_glide_trig">
              	<c:forEach var="x" items="${recommendList}">
              	<c:if test="${x.type == 1}">
                <li>•</li>
              	</c:if>
              	</c:forEach>
              </ul>
            </div>
          </div>
        </div>
        <div class="clear"></div>
        <i class="daily_bod"></i>
      </div>
    </div>
    <div class="grid_c1">
      <div class="dailybeta" id="j_daily">
          <div class="dailybeta_hd">
            <p class="dailybeta_tit"><i class="dailybeta_tit_ico">&nbsp;</i><b>本周特惠</b></p>
          </div>
          <div class="dailybeta_bd">
            <ul class="dailybeta_goods">
              <c:forEach var="x" items="${recommendList}">
              <c:if test="${x.type == 2}">
              <li>
                <div class="mod_goods mod_goods_w100">
                  <div class="mod_goods_img load_effect"> <a href="prd/${x.product.id }"><img src="${imgPath }${x.product.prdCode }_small.jpg" alt="${x.product.name }"></a> </div>
                  <div class="mod_goods_info">
                    <p class="mod_goods_tit"><a>${x.product.name }</a></p>
                    <p class="mod_goods_price"><a><span class="mod_price mod_price_now"><i>¥</i><span>${x.product.prdDisPrice }</span></span></a></p>
                    <p class="dailybeta_goods_btn_wrap"><a class="dailybeta_goods_btn" title="立即抢" href="prd/${x.product.id }">立即抢购</a></p>
                  </div>
                </div>
              </li>
              </c:if>
              </c:forEach>
            </ul>
          </div>
        </div>
    </div>
  </div>
  <!--楼层部分开始-->
  <div class="grid_c1 sy_mod_f f1">
    <c:forEach var="pc" items="${pcMap}" varStatus="vs">
    <c:if test="${pc.value.recommend == 1}">
    <div class="wf_chose2 mb_10">
      <div class="left st1">
        <div class="title crz" style="background:${pc.value.bgcolor }">
          <p><a><i class="in_f1"><img src="${imgPath }${pc.value.picUrl }" /></i>${pc.value.name }</a></p>
        </div>
        <ul class="rHot">
        	<%// 显示推荐主分类的 二级分类 %>
        	<c:forEach var="two" items="${pc.value.childList}" varStatus="vs">
	        <li><a class="trackref" href="public/list.Q?code=${two.code }">${two.name }</a></li>
	       	</c:forEach>
        </ul>
      </div>
      <%// 显示当前一级类型的推荐商品 %>
      <div class="wf_chose_lista right">
        <div class="list swiper-wrapper">
          <c:if test="${pc.value.bigTop != null}">
          <div class="lCont"> <a href="prd/${pc.value.bigTop.id }"><img src="${imgPath }${pc.value.bigTop.picUrl2 }"></a> </div>
          </c:if>
          <div class="subCont">
            <ul class="pList">
              <c:forEach var="pro" items="${pc.value.proList}">
              <li class="price_list0">
                <div class="pImg"><a href="prd/${pro.id }"><img src="${imgPath }${pro.prdCode }_small.jpg"></a></div>
                <div class="title-a"><a>${pro.name }(${pro.prdStandard })</a></div>
                <div class="price"><b>￥${pro.prdDisPrice }</b><font>￥${pro.prdPrice }</font></div>
              </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
      <div class="clr"></div>
    </div>
	<c:if test="${pc.value.ad != nul}">
    <div class="advertise mb_10"><a href="${pc.value.ad.url }"><img src="${imgPath }${pc.value.ad.picUrl }"></a></div>  
	</c:if>
	</c:if>
    </c:forEach>
  </div>
</div>

<jsp:include page="foot.jsp"></jsp:include>

<script type="text/javascript">
$(function(){
	
	/*调用js文件中写的focRoll函数*/
	focRoll($("#main_slider .slider_img"),true,$("#main_slider .focusBtn"),false,true,2500,300,"easeOutQuad");
	focRoll($("#new_products .new_starter_glide_img"),true,$("#new_products .new_starter_glide_trig"),false,true,3000,400,"easeInOutElastic");
	/*楼层一滚动切换*/
	$('#left_1 ul.wf_chose_tila').addClass('active').find('> li:eq(0)').addClass('current');
	$('#left_1 ul.wf_chose_tila li').click(function (g) { 
		var tab = $(this).closest('#left_1'), 
			index = $(this).index();
		
		tab.find('ul.wf_chose_tila > li').removeClass('current');
		$(this).addClass('current');
		
		tab.find('.wf_chose_lista').find('div.chose_item').not('div.chose_item:eq(' + index + ')').slideUp();
		tab.find('.wf_chose_lista').find('div.chose_item:eq(' + index + ')').slideDown();
		
		g.preventDefault();
	} );
  /*本周特惠*/
  $(".dailybeta_goods li:eq(3) ,.dailybeta_goods li:eq(7) ").css("borderRight","none");
});
</script>
</body>
</html>