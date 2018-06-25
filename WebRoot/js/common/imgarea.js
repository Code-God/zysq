/*
 * setImagePreview() @param fileObj Strimg 表示上传域的ID @param previewObj String
 * 表示显示图片的ID @param localImg String 表示显示图片外层的div的ID @param width 表示显示图片的宽 @param
 * height 表示显示图片的高
 */
function setImagePreview(fileObj, previewObj, localImg, width, height) {
	var docObj = document.getElementById(fileObj);
	var imgObjPreview = document.getElementById(previewObj);
	//alert(docObj.files + "|" + docObj.files[0]);
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
//		imgObjPreview.style.display = 'block';
//		imgObjPreview.style.width = width + 'px';
//		imgObjPreview.style.height = height + 'px';
		// imgObjPreview.src = docObj.files[0].getAsDataURL();
		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		if(!window.URL){
			imgObjPreview.src = window.webkitURL.createObjectURL(docObj.files[0]);
		}else{
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById(localImg);
		// 必须设置初始大小
//		localImagId.style.width = width + 'px';
//		localImagId.style.height = height + 'px';
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}
//放大
function zoomOut(){
	var w = $("#preview").attr("width");
	var h = $("#preview").attr("height");
	$("#preview").attr("width", w*1.1);
	$("#preview").attr("height", h*1.1);
	updateRecWH(w*1.1, h*1.1);	
}
//缩小
function zoomIn(){
	var w = $("#preview").attr("width");
	var h = $("#preview").attr("height");
	$("#preview").attr("width", w*0.9);
	$("#preview").attr("height", h*0.9);
	updateRecWH(w*0.9, h*0.9);
}
//更新图片展示大小的区域尺寸到隐藏文本区域
function updateRecWH(w, h){
//	alert(w + "||" + h);
	$("#recw").val(w);
	$("#rech").val(h);
	
	$('#localImag').imgAreaSelect({ x1: 10, y1: 10, x2: 110, y2: 110,  handles: true });
}

function cc(){
	//等比例调整
	var srcWidth = $("#preview").attr("width");
	var srcHeight = $("#preview").attr("height");
	var recw = (parseInt(srcWidth) > 800 ? srcWidth/2 : srcWidth);
	var rech = (parseInt(srcWidth) > 800 ? srcHeight/2 : srcHeight);
	
	$("#preview").attr("width", recw);
	$("#preview").attr("height", rech);
	 
	updateRecWH(recw, rech);
	
	clearInterval(inta);
}

var inta = null;
$(function() {
	// 给上传区添加onchang事件，掉用函数setImagePreview()
	$("#Member_headimg").change(function() {
		setImagePreview('Member_headimg', 'preview', 'localImag', 200, 300);
		setImagePreview('Member_headimg', 'small_photo', 'small_img', 75, 75);
		setImagePreview('Member_headimg', 'big_photo', 'big_img', 100, 100);
		//清除preview的高度和宽度
		$("#preview").removeAttr("width");
		$("#preview").removeAttr("height");
		inta=self.setInterval("cc()",500)
		
		$("#toolDiv").show();
	});
	
	// 调用截图插件需用的
	$("#localImag").imgAreaSelect({
		aspectRatio : '3:4', // 选中区域是正方形
		handles : true,
		fadeSpeed : 200, // 出来效果
		onSelectChange : set
			// 执行选择后执行的函数
			});
	function set(img, selection) {
		if (!selection.width || !selection.height)
			return;
		var scaleX = 100 / selection.width;
		var scaleY = 100 / selection.height;
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			$('#big_img').css({
				width : Math.round(scaleX * 200),
				height : Math.round(scaleY * 300),
				marginLeft : -Math.round(scaleX * selection.x1),
				marginTop : -Math.round(scaleY * selection.y1)
			});
			$('#small_img').css({
				width : Math.round(scaleX * 200 * 3 / 4),
				height : Math.round(scaleY * 300 * 3 / 4),
				marginLeft : -Math.round(scaleX * selection.x1 * 3 / 4),
				marginTop : -Math.round(scaleY * selection.y1 * 3 / 4)
			});
		} else {
			$('#big_photo').css({
				width : Math.round(scaleX * 200),
				height : Math.round(scaleY * 300),
				marginLeft : -Math.round(scaleX * selection.x1),
				marginTop : -Math.round(scaleY * selection.y1)
			});
			$('#small_photo').css({
				width : Math.round(scaleX * 200 * 3 / 4),
				height : Math.round(scaleY * 300 * 3 / 4),
				marginLeft : -Math.round(scaleX * selection.x1 * 3 / 4),
				marginTop : -Math.round(scaleY * selection.y1 * 3 / 4)
			});
		}
		$("#x1").val(selection.x1);
		$("#y1").val(selection.y1);
		$("#w").val(selection.width);
		$("#h").val(selection.height);
		
	}
})