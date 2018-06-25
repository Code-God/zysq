
/**
 * 该文件中的所有函数是系统中共用的，该文件被引入到commonJs.jsp中
 * 增加新函数请将注释写得详细点，并发邮件给同组开发人员，
 * 修改的时候请慎重，以防止出现衰退性问题
 */
/**
 * 功能：打开一个居中的窗口
 * @param url 新窗口的URL地址
 * @param w 新窗口的宽度
 * @param h 新窗口的高度
 */
function openCenterWindow(url, w, h) {
	var leftPosition = (screen.width) ? (screen.width - w) / 2 : 0;
	var topPosition = (screen.height) ? (screen.height - h) / 2 : 0;
	var wid = w ? w : 800;
	var hei = h ? h : 600;
	var settings = "height=" + hei + ", width=" + wid + ", top=" + topPosition + ", left=" + leftPosition + ", toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
	var win = window.open(url, "_blank", settings);
	win.focus();
	return win;
}
function openArtDialogWindow(openUrl, openTitle, w, h) {
	if(w && h){
		return art.dialog.open(openUrl, {limit:false, lock:true, title:openTitle, width: w > 1 ? w : getClientWidth() * w, height:h > 1 ? h : getClientHeight() * h});
	}else{
		return art.dialog.open(openUrl, {limit:false, lock:true, title:openTitle, width:getClientWidth() * 0.8, height:getClientHeight() * 0.9});
	}
}
function cwindow(msg, id, callBack) {
	art.dialog({
	    content: msg,
	    ok: function () {
	    	if(callBack){
				callBack();
			}
	        return true;
	    },
	    cancelVal: '关闭',
	    cancel: true //为true等价于function(){}
	});
}

function errMsg(msg, id, callBack) {
	art.dialog({content:msg, skin:"aero", icon:"error", yesFn:function () {
		if (id) {
			this.follow(id).time(2);
		}
		if(callBack){
			callBack();
		}
	}, noFn:true});
}
/**
 * 功能：将一个Table的奇偶行以不同的颜色显示
 * @param tableId Table的Id
 */
function toggleTableColor(tableId) {
	jQuery("#" + tableId + " tr:even:gt(0)").children().attr("class", "tbody_td");
//	bindMouseOver(tableId, 'white', '#F0F5F9','#FF9');//鼠标移动显示效果
}
/**
 * 页面加载完就执行，完成表格的奇偶行区别显示(将id为toggleTable表格默认设置奇偶显示颜色)
 */
jQuery(document).ready(function () {
	toggleTableColor("toggleTable");//表格奇偶行区别显示
	//alert(jQuery.height());
//	jQuery(".left").height(650);//设置左侧高度
});
/**
*鼠标在表格上移动显示效果
*/
jQuery(function () {
	bindMouseOver("table1", "white", "#EFEFEF", "#DDE8EE");
});
function bindMouseOver(tableId, firstColor, secondColor, thisColor) {
	(jQuery("#" + tableId).find("tbody") ? jQuery("#" + tableId).find("tbody") : jQuery("#" + tableId)).children("tr").each(function (index) {
		if (index == 0) {
			return;
		}
//	jQuery(this).bind("mouseover", function(){jQuery(this).children().css("backgroundColor", thisColor);});
		jQuery(this).bind("mouseover", function () {
			jQuery(this).children().toggleClass("tbody_tr_on");
		});
//	jQuery(this).bind("mouseout", function(){index % 2 ? jQuery(this).children().css("backgroundColor", firstColor) : jQuery(this).children().css("backgroundColor", secondColor);});
		jQuery(this).bind("mouseout", function () {
			index % 2 ? jQuery(this).children().toggleClass("tbody_tr2") : jQuery(this).children().toggleClass("tbody_tr_on");
		});
	});
}
/********************
 * 取窗口滚动条高度 
 ******************/
function getScrollTop() {
	var scrollTop = 0;
	if (document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else {
		if (document.body) {
			scrollTop = document.body.scrollTop;
		}
	}
	return scrollTop;
}
/********************
 * 取窗口可视范围的高度 
 *******************/
function getClientHeight() {
	var clientHeight = 0;
	if (document.body.clientHeight && document.documentElement.clientHeight) {
		var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	} else {
		var clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
	}
	return clientHeight;
}
/********************
 * 取窗口可视范围的宽度 
 *******************/
function getClientWidth() {
	var clientWidth = 0;
	if (document.body.clientWidth && document.documentElement.clientWidth) {
		var clientWidth = (document.body.clientWidth < document.documentElement.clientWidth) ? document.body.clientWidth : document.documentElement.clientWidth;
	} else {
		var clientWidth = (document.body.clientWidth > document.documentElement.clientWidth) ? document.body.clientWidth : document.documentElement.clientWidth;
	}
	return clientWidth;
}
/********************
 * 取文档内容实际高度 
 *******************/
function getScrollHeight() {
	return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
/********************
 * 取文档内容实际高度 
 *******************/
function getScrollHeight() {
	return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
/********************
 * OssWorks确认对话框 
 * @param message 确认窗口中的提示信息
 * @param callbackFn 点击确定后的回调函数
 *******************/
function showOssConfirmDialog(message, callbackFn) {
	var oBuffer = new Array();
	oBuffer.push("<div id=\"windownbg\" class=\"windownbg\" style=\"height:" + jQuery(document).height() + "px;filter:alpha(opacity=0);opacity:1;z-index: 999901;\"></div>");
	oBuffer.push("<div id='ossDialog' style='position: absolute;_position: absolute;z-index: 999902'>");
	oBuffer.push("<div class=\"apdiv_top_yz\">");
	oBuffer.push("<h1><img src=\"/love/images/knowledge/newindex/rr_r1_c22.gif\" /></h1>");
	oBuffer.push("</div>");
	oBuffer.push("<div class=\"apdiv_coun_yz\">");
	oBuffer.push("<div class=\"zuo_er_yz\"></div>");
	oBuffer.push("<div class=\"ap_neirong_yz\">");
	oBuffer.push("<div class=\"ap_nei_yz\">");
	oBuffer.push("<div id='ossDialogTitle' class=\"tuo_op\">");
	oBuffer.push("<img src=\"/love/images/knowledge/newindex/r2_c2.jpg\" />");
	oBuffer.push("<span>\u786e\u8ba4</span><em><a href=\"#\" onclick=closeOssDialog()>\u5173\u95ed</a></em>");
	oBuffer.push("</div>");
	oBuffer.push("<div class=\"hei_11\"></div>");
	oBuffer.push("<div class=\"tihshi_ans\">");
	oBuffer.push(message);
	oBuffer.push("</div>");
	oBuffer.push("<div class=\"annie2\">");
	oBuffer.push("<input id=\"ossConfirmDialogBtn\" type=\"button\" class=\"but_p\" value=\"\u786e \u8ba4\" />");
	oBuffer.push("<input type=\"button\" class=\"but_p\" value=\"\u53d6 \u6d88\" onclick=\"closeOssDialog()\" />");
	oBuffer.push("</div>");
	oBuffer.push("</div>");
	oBuffer.push("</div>");
	oBuffer.push("</div>");
	oBuffer.push("<div class=\"apdiv_bottom_yz\"></div>");
	oBuffer.push("</div>");
	jQuery("body").append(oBuffer.join(""));
	jQuery("#ossConfirmDialogBtn").bind("click", function () {
		closeOssDialog();
		if (callbackFn) {
			callbackFn();
		}
	});
	jQuery("#windownbg").show();
	//jQuery("#windownbg").animate({opacity:"0.5"},"fast");
	enOssDialogMovable("ossDialog", "ossDialogTitle");
	var height = jQuery(document).scrollTop() + 300;
	jQuery("#windownbg").css("opacity", "0.5");
	jQuery("#ossDialog").css({"top":height + "px", "left":"450px"}).show("fast");
}
/********************
 * OssWorks错误提示框 
 * @param message 错误提示信息
 * @param callbackFn 点击确定后的回调函数
 *******************/
function showOssErrorDialog(message, callbackFn) {
	var oBuffer = new Array();
	oBuffer.push("<div id=\"windownbg\" class=\"windownbg\" style=\"height:" + jQuery(document).height() + "px;filter:alpha(opacity=0);opacity:1;z-index: 999901;\"></div>");
	oBuffer.push("<div id='ossDialog' style='position: fixed;_position: absolute;z-index: 999902'>");
	oBuffer.push("<div class=\"new_jinggaocenter border_solid\">");
	oBuffer.push("<div class=\"subcenter_title\">");
	oBuffer.push("<div id='ossDialogTitle' class=\"baowei_c\">");
	oBuffer.push("<h1 class=\"tubiao7\">\u9519\u8bef\u4fe1\u606f</h1>");
	oBuffer.push("</div></div>");
	oBuffer.push("<div class=\"space\"></div>");
	oBuffer.push("<div class=\"jinggao_content\">");
	oBuffer.push("<div class=\"tishi_main4\">");
	oBuffer.push(message);
	oBuffer.push("<br/></div>");
	oBuffer.push("<div class=\"tishi_butt\">");
	oBuffer.push("<input id=\"ossErrorDialogBtn\" type=\"button\" class=\"tishi_queren\"  value=\"\u786e  \u5b9a\"/>");
	oBuffer.push("</div></div></div>");
	jQuery("body").append(oBuffer.join(""));
	jQuery("#ossErrorDialogBtn").bind("click", function () {
		closeOssDialog();
		if (callbackFn) {
			callbackFn();
		}
	});
	enOssDialogMovable("ossDialog", "ossDialogTitle");
	var height = jQuery(document).scrollTop() + 200;
	jQuery("#windownbg").show();
	jQuery("#windownbg").css("opacity", "0.5");
	jQuery("#ossDialog").css({"top":height + "px", "left":"500px"}).show("fast");
}
/********************
 * 响应Oss各对话框中的确定按钮事件
 * @param callbackFn 
 *******************/
function ossDialong_confirm(callBackFn) {
	jQuery("#ossDialog").remove();
	jQuery("#windownbg").remove();
	callBackFn();
}
/********************
 * 响应Oss各对话框中的取消按钮事件
 *******************/
function closeOssDialog() {
	jQuery("#ossDialog").remove();
	jQuery("#windownbg").remove();
}
/********************
 * 为OssWorks中的对话框加上移动功能
 * @param ossDialogId 对话框的Id
 * @param ossDialogTitleId 对话框标题的Id
 *******************/
function enOssDialogMovable(ossDialogId, ossDialogTitleId) {
	var clientWidth = document.documentElement.clientWidth;//当前窗口的宽度
	var clientHeight = document.documentElement.clientHeight;//当前窗口的高度
	var scrollTop = document.documentElement.scrollTop; //滚动条的高度
	var _version = jQuery.browser.version;//浏览器类型
	var Drag_ID = document.getElementById(ossDialogId);
	var DragHead = document.getElementById(ossDialogTitleId);
	var moveX = 0;
	var moveY = 0;
	var moveTop;
	var moveLeft = 0;
	var moveable = false;
	if (_version == 6) {
		moveTop = scrollTop;
	} else {
		moveTop = 0;
	}
	var drag = "true";
	var sw = Drag_ID.scrollWidth, sh = Drag_ID.scrollHeight;
	DragHead.onmouseover = function (e) {
		if (drag == "true") {
			DragHead.style.cursor = "move";
		} else {
			DragHead.style.cursor = "default";
		}
	};
	DragHead.onmousedown = function (e) {
		if (drag == "true") {
			moveable = true;
		} else {
			moveable = false;
		}
		e = window.event ? window.event : e;
		var ol = Drag_ID.offsetLeft;
		var ot = Drag_ID.offsetTop - moveTop;
		moveX = e.clientX - ol;
		moveY = e.clientY - ot;
		document.onmousemove = function (e) {
			if (moveable) {
				e = window.event ? window.event : e;
				var x = e.clientX - moveX;
				var y = e.clientY - moveY;
				if (x > 0 && (x + sw < clientWidth) && y > 0 && (y + sh < clientHeight)) {
					Drag_ID.style.left = x + "px";
					Drag_ID.style.top = parseInt(y + moveTop) + "px";
					Drag_ID.style.margin = "auto";
				}
			}
		};
		document.onmouseup = function () {
			moveable = false;
		};
		Drag_ID.onselectstart = function (e) {
			return false;
		};
	};
}
/**
 * 这些组件会遮盖弹出窗口，所以要先隐藏一下。弹出窗口关闭后，再显示出来。
 */
function showSelectAndTextArea(isVisible) {
	jQuery.each(jQuery("select"), function (i, obj) {
		obj.style.display = (isVisible ? "block" : "none");
	});
	jQuery.each(jQuery("textArea"), function (i, obj) {
		obj.style.display = (isVisible ? "block" : "none");
	});
}
/**
 * 用来切换div的显示和隐藏的
 * arrowId - 非必须参数。
 */
function switchDiv(divId, arrowId) {
	var state = (jQuery("#" + divId).css("display") == "block" ? "none" : "block");
	jQuery("#" + divId).css("display", state);
	if (arrowId) {
		var arrCss = (jQuery("#" + divId).css("display") == "block" ? "logBlockOperationOpen" : "logBlockOperationClose");
		jQuery("#" + arrowId).attr("class", arrCss);
	}
}

