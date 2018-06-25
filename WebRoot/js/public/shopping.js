function initBind(o){
	// 产品编码
	var code = $(o).attr('prdCode');
	// 库存
	var max = parseInt($(o).attr('stock'));
	
	var txtNum = $('#num_' + code);			
	var btnAdd = $('#add_' + code);
	var btnReduce = $('#reduce_' + code);

	btnAdd.click(function(){
		if(txtNum.val() < max){
			// ajax +1
			var url = "private/shopping_increase.Q?prdCode=" + code;
			$.getJSON(url, funAdd);
		}
		adds(max, txtNum, code);
		updateCss(txtNum.val(), max, btnAdd, btnReduce);
	});
	btnReduce.click(function(){
		if(txtNum.val() > 1){
			// ajax -1
			var url = "private/shopping_reduce.Q?prdCode=" + code;
			$.getJSON(url, funReduce);
		}
		txtNum = $('#num_' + code);
		reduce(max, txtNum, code);
		updateCss(txtNum.val(), max, btnAdd, btnReduce);
	});
	txtNum.change(function(){
		updateInfo($(this).val(), max, code);
		updateCss($(this).val(), max, btnAdd, btnReduce);
	});
	// +- css init
	updateCss(txtNum.val(), max, btnAdd, btnReduce);
}

var funAdd = function(obj){
	if(obj.code == 0){
		console.log('操作失败');
	}
}
var funReduce = function(obj){
	if(obj.code == 0){
		console.log('操作失败');
	}
}

// num++
function adds(max, txtNum, code){
	var num = txtNum.val();
	num++;
	if(num<=1){
		num=1;
	}
	if(num >= max){
		num = max;
	}
	txtNum.val(num);
	
	updateInfo(num, max, code)
}

// num--
function reduce(max, txtNum, code){
	var num = txtNum.val();
	num--;
	if(num<=1){
		num=1;
	}
	if(num >= max){
		num = max;
	}
	
	txtNum.val(num);
	
	updateInfo(num, max, code)
}

// 更新+-css,合计
function updateInfo(num, max, code){
	// code 不存在为商品详情
	if(code == 'undefined'){
		return;
	}
	var price = $('#price_' + code).html() * 1 * num;
	var disPrice = $("#disprice_" + code).val() * 1 * num;
	
	//金额
	$('#sum_' + code).html(disPrice.toFixed(2));
	//优惠
	$('#disSum_' + code).html((price - disPrice).toFixed(2));
	total();
	
}
// +- css update
function updateCss(num, max, btnAdd, btnReduce){
	if(num <= 1){
		btnReduce.addClass('disable');
	}else{
		btnReduce.removeClass('disable');
	}
	if(num >= max){
		btnAdd.addClass('disable');
	}else{
		btnAdd.removeClass('disable');
	}
}

// 总计
function total(){
	var totalPrice = 0.0;
	var totalDisPrice = 0.0;
	$("#shopping .product").each(function(i, o){
		var prdCode = $(this).attr("prdCode");
		var price = $("#price_" + prdCode).html() * 1.0;
		var count = $("#num_" + prdCode).val() * 1;
		var disprice = $("#disprice_" + prdCode).val() * 1.0;
		
		totalPrice += price * count;
		totalDisPrice += disprice * count;
	});
	
	$("#totalPrice").html(totalPrice.toFixed(2));
	$("#totalDisPrice").html((totalPrice - totalDisPrice).toFixed(2));
	$("#totalPay").html(totalDisPrice.toFixed(2));
	
	if($("#shopping tr").size() == 1){
		window.location.reload();
	}
}