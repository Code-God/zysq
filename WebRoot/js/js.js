//虽然本函数参数较多，但是完美兼容IE
var focRoll=function(objPics,bt,objBtns,wd,play,del,dur,eas){
/*定义一些所需变量*/
	var $focImgs = objPics,$focImg = $focImgs.find("li"),getNextIndex = function(){ return $focImgs.p+1>$focImg.length?$focImgs.p=0:$focImgs.p+1};
/*焦点初始化*/
	$focImgs.p=0;
	$focImg.eq($focImgs.p).clone().appendTo($focImgs);
	if(wd){
		$focImgs.css("width",$focImg.outerWidth(true)*($focImg.length+1));
	};
/*先判断是否有焦点按钮再加点击事件*/
	if(bt){
		var $focBtns = objBtns,$focBtn = $focBtns.find("li");
		$focBtn.click(function(){
			if(!$focImgs.is(":animated")){
				$focImgs.p=$(this).index();
				Roll(wd);	
			};	
		});	
		$focBtn.eq($focImgs.p).addClass("curBtn");
	};
	if(play){
/*焦点图自动播放*/
		$focImgs.t=setInterval(function(){
			$focImgs.p=getNextIndex();
			Roll(wd);		
		},del);	
/*鼠标移入焦点事件*/
		$focImgs.parent().hover(function(){clearInterval($focImgs.t)},function(){
			$focImgs.t=setInterval(function(){
				$focImgs.p=getNextIndex();
				Roll(wd);		
			 },del);
		});
	};
/*单张焦点运动执行函数*/
	function Roll(wd){
		if(wd){
			$focImgs.animate({"left":-$focImgs.p*$focImg.outerWidth(true)},{duration:dur, easing:eas,complete:function(){rollEnd(wd)}});
		}else{
			$focImgs.animate({"top":-$focImgs.p*$focImg.outerHeight(true)},{duration:dur, easing:eas,complete:function(){rollEnd(wd)}});
		}
	};
/*单张焦点运动执行函数*/
	function rollEnd(wd){
		if(wd){
			if($focImgs.p==$focImg.length){$focImgs.css("left",0);$focImgs.p=0;};
		}else{
			if($focImgs.p==$focImg.length){$focImgs.css("top",0);$focImgs.p=0;};			
		};
		if(bt){$focBtn.eq($focImgs.p).addClass("curBtn").siblings().removeClass("curBtn")};
	};
};