
var tips;
var theTop = 200;
/*这是默认高度,越大越往下*/
var old = theTop;
//浏览器可见宽度
var viewWidth = document.documentElement.clientWidth;
function initFloatTips() {
	tips = document.getElementById("floatTips");
	tips.style.left = viewWidth - 200  + "px";
	moveTips();
}
function moveTips() {
	var tt = 50;
	if (window.innerHeight) {
		pos = window.pageYOffset;
	} else {
		if (document.documentElement && document.documentElement.scrollTop) {
			pos = document.documentElement.scrollTop;
		} else {
			if (document.body) {
				pos = document.body.scrollTop;
			}
		}
	}
	pos = pos - tips.offsetTop + theTop;
	pos = tips.offsetTop + pos / 10;
	if (pos < theTop) {
		pos = theTop;
	}
	if (pos != old) {
		tips.style.top = pos + "px";
		tt = 10;
	}
	old = pos;
	setTimeout(moveTips, tt);
}
initFloatTips();

