function initWxParams(){
	var url = CONFIG.context + "/private/pri_getWxJsParam.Q";
	$.ajax({
		type : "POST",
		url : url,
		data: "url="+ escape(window.location.href),
		dataType : "json",
		success : function(param) {
			//alert(param.result);
			if(param.result != 'fail'){
				//JS API
				var appId = param.appId;
				var time = param.timeStamp;
				var nonceStr = param.nonceStr;
				var signature = param.paySign;
				//alert("1111-" + appId + "|" + time + "|" + nonceStr + "|" + signature);
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: appId, // 必填，公众号的唯一标识
				    timestamp: time, // 必填，生成签名的时间戳
				    nonceStr: nonceStr, // 必填，生成签名的随机串
				    signature: signature,// 必填，签名，见附录1
				    jsApiList: [
				        'checkJsApi',
				        'onMenuShareTimeline',
				        'onMenuShareAppMessage',
				        'onMenuShareQQ',
				        'onMenuShareWeibo',
				        'hideMenuItems',
				        'showMenuItems',
				        'hideAllNonBaseMenuItem',
				        'showAllNonBaseMenuItem',
				        'translateVoice',
				        'startRecord',
				        'stopRecord',
				        'onRecordEnd',
				        'chooseImage',
				        'previewImage',
				        'uploadImage',
				        'downloadImage',
				        'getNetworkType',
				        'openLocation',
				        'getLocation',
				        'hideOptionMenu',
				        'showOptionMenu',
				        'closeWindow',
				        'scanQRCode',
				        'chooseWXPay'
				      ]
				});
			}else{
				//art.dialog("对不起，JS接口参数加载失败。");
				//layer.msg('对不起，JS接口参数加载失败。', {icon: 2});
			}
		}
	});
}




wx.ready(function(){
	wx.hideAllNonBaseMenuItem();
	wx.showMenuItems({
	    menuList: ['menuItem:share:appMessage','menuItem:share:timeline','menuItem:share:qq','menuItem:share:weiboApp','menuItem:refresh'] // 要显示的菜单项，所有menu项见附录3
	});
	//getShareConfig();
	
});	


wx.error(function(res){
	//alert("res-----"+JSON.stringify(res));
});
