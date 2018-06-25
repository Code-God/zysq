﻿<%@ page language="java" pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<footer>
    <!--悬浮菜单-->
<section>
    <div class="fixed-menu">
        <ul class="clearafter">
            <li class="cur">
                <a class="fixed-menu-item" href="<%=request.getContextPath()%>/drug/index_toIndex.Q">
                    <span class="fixed-menu-img fixed-menu-img-mora"></span>
                    <p>找新药</p>
                </a>
            </li>
            <li>
                <a class="fixed-menu-item" href="javascript:void(0);" onclick="showDialog();return false;">
                    <span class="fixed-menu-img fixed-menu-img-pk"></span>
                    <p>咨询</p>
                </a>
            </li>
            <li>
                <a class="fixed-menu-item" href="<%=request.getContextPath()%>/drug/user_userCenter.Q">
                    <span class="fixed-menu-img fixed-menu-img-my"></span>
                    <p>我的</p>
                </a>
            </li>
        </ul>
    </div>
</section>
<!--悬浮菜单end-->
</footer>


<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?ec82594d63e2a8ead0d208564bba4c0e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script>
(function (doc, win) {
var docEl = doc.documentElement,
resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
recalc = function () {
var clientWidth = docEl.clientWidth;
if (!clientWidth) return;
docEl.style.fontSize = 16 * (clientWidth / 320) + 'px';
};

if (!doc.addEventListener) return;
win.addEventListener(resizeEvt, recalc, false);
doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
</script>

<script src="${ctx}/weixin/drug/js/dialog/layer.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${ctx}/weixin/js/wxjs.js"></script>
<script type="text/javascript" src="${ctx}/js/common/config.js"></script>
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
					showClass();
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