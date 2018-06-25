
var n = 0;
var showNum = document.getElementById("num");
function Mea(value) {
	n = value;
	setBg(value);
	plays(value);
}
function setBg(value) {
	for (var i = 0; i < 6; i++) {
		if (value == i) {
			document.getElementById("a" + value).className = "act";
		} else {
			document.getElementById("a" + i).className = "nor";
		}
	}
}
function plays(value) {
	for (i = 0; i < 6; i++) {
		if (i == value) {
			document.getElementById("pc_" + value).style.display = "block";
			  	//alert(document.getElementById("pc_"+value).style.display)
		} else {
			document.getElementById("pc_" + i).style.display = "none";
		}
	}
}
function clearAuto() {
	clearInterval(autoStart);
}
function setAuto() {
	autoStart = setInterval("auto(n)", 3000);
}
function auto() {
	n++;
	if (n > 5) {
		n = 0;
	}
	Mea(n);
}
function sub() {
	n--;
	if (n < 0) {
		n = 5;
	}
	Mea(n);
}
setAuto();

