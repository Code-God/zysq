<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
<div id="mask"></div>
<header>
    <div class="head">
        <h1>${disease.medicineName}</h1>
        <p>${disease.diseaseProfile}</p>
        <c:if test="${isSubscrib == 0}">
						<span class="toAttention"><i></i>关注找药神器</span>
				    </c:if>
    </div>
</header>
<!---->
<div id="ewm-box" class="old-box">
    <div class="box-head">
        长按二维码关注公众号
        <a class="close_box multiple" href="javascript:(0);"></a>
    </div>
    <div class="ewm-box-img">
        <img src="http://zhaoyao.medforce.cn/zysq/weixin/drug/imgs/ewm-img.png">
    </div>
</div>
<!---->
<section>
    <div class="contend">
        <!--列表切换选项卡-->
        <div class="maple-tab clearFix">
            <ul>
                <li class="active"><p class="m-border-right">内容介绍</p></li>
                <li><p>入选条件</p></li>
                <li><p>项目中心</p></li>
            </ul>
        </div>

        <!--列表内容-->
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <div class="tab-item">
                        ${disease.diseaseIntroduction}
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="tab-item">
                        ${disease.chosenCondition}
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="tab-item">
                        <table>
                    <tr>
                        <th>医院</th>
                        <th>主要研究者</th>
                        <th>省/市</th>
                        </tr>
                        <c:forEach items="${projectList}" var="project">                     
                       <tr>
                        <td>${project.organizationName}</td>
                        <td>${project.researcher}</td>
                        <td>${project.province}${project.city}</td>
                        </tr>
                        </c:forEach>
                    </table>  
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!--悬浮菜单-->
<section>
    <div class="details-fixed">
        <div class="details-fixed-contend">
            <a class="details-fixed-item tj" href="<%=request.getContextPath()%>/drug/user_medicineInfo.Q?diseaseId=${disease.id}&singupType=1">
                <div class="kj">
                    <i class="multiple"></i>
                    <span>推荐</span>
                </div>
            </a>
            <a class="details-fixed-item zx" href="javascript:void(0);" onclick="showDialog();return false;">
                <div class="kj">
                    <i class="multiple"></i>
                    <span>咨询</span>
                </div>
            </a>
            <a class="details-fixed-btn" href="<%=request.getContextPath()%>/drug/user_singup.Q?diseaseId=${disease.id}&singupType=0">我要报名</a>
        </div>
    </div>
</section>
<!--悬浮菜单end-->

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
            //var height = $(window).height() - 241;
            $(".swiper-slide").css("height",height);
            $(".swiper-slide").eq(i).css("height","auto");
            $('.maple-tab li').removeClass('active').eq(i).addClass('active');
            mySwiper.slideTo(i,600,false);
        });
        //关注按钮弹框
        $(".toAttention").click(function(){
            $("#mask").css("height", $(document).height());
            $("#mask").css("width", $(document).width());
            $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
            $("#mask").show();
            $("#ewm-box").show();
        });
        // 弹窗关闭事件
        $(".close_box").click(function(){
            $("#mask").hide();
            $(".old-box").hide();
            $(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
        });
    });
</script>


<script src="${ctx}/weixin/drug/js/dialog/layer.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${ctx}/weixin/js/wxjs.js"></script>
<script type="text/javascript" src="${ctx}/js/common/config.js"></script>
<script>
var activejsure=0;
</script>
<script type="text/javascript" src="${ctx}/weixin/drug/js/main.js"></script>

<script>
layer.config({
  skin: 'dialog-class'
});

function showClass(){
    $(".layui-layer").css({
        'top': '50%',
        'left': '50%',
        'transform': 'translate(-50%, -50%)',
        '-moz-transform': 'translate(-50%, -50%)',
        '-webkit-transform': 'translate(-50%, -50%)',
        '-o-transform': 'translate(-50%, -50%)'
    });
    
}
	
function showDialog(){
      $.ajax({
		  url: "<%=request.getContextPath()%>/wx/wx_contactDKF.Q",
		  dataType:'json',
		  success:function(data){
			//alert(data.msg);
			if(data.msg == "ok"){
				layer.confirm('您好，您已接入多客服，请点击左下角后输入文字或者语音，客服人员将及时与您取得联系。', {
				title:'咨询提示',
				closeBtn:1,
				move:false,
				shadeClose: true,
				shift: 4,
				btn: ['转到咨询']
		      }, function(){
		           // 回调处理写在这里
		           //layer.msg('关闭回调', {icon: 1});
			       wx.closeWindow();	  
		         }
	           );
	              showClass();
			   }else{
						layer.msg('多客服接入失败，请稍后重试。', {icon: 2,shift: 4});
					}
					//showClass();
				}
			});
}
//联系多客服
function contactDKF(){
	layer.open({
		  content: '确认要咨询在线客服吗？'
		  ,btn: ['是的', '否' ]
		  ,yes: function(index, layero){
		    //按钮【按钮一】的回调
		    //http://drug.yin-teng.com/zysq/wx/wx_verify.Q 
		    $.ajax({
				url: "<%=request.getContextPath()%>/wx/wx_contactDKF.Q",
				dataType:'json',
				success:function(data){
					//alert(data.msg);
					if(data.msg == "ok"){
						showDialog();
					}else{
						layer.msg('多客服接入失败，请稍后重试。', {icon: 2});
					}
					showClass();
				}
			});
		    
		    layer.close(index);
		  },btn2: function(index, layero){
			  layer.close(index);
		  } 
		  ,cancel: function(){ 
		    //右上角关闭回调
		  }
		});
		showClass();
}


//微信是否初始化过,接入微信JS API
$(document).ready(function(){
	//初始化参数
	initWxParams();
});
/////////////////////////////////////////////////////////////////////////
</script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?ec82594d63e2a8ead0d208564bba4c0e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</body>
</html>
