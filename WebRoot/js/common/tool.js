
function DBC2SBC(str) {
	var i;
	var result = "";
	for (i = 0; i < str.length; i++) {
		code = str.charCodeAt(i);
		if (code == 12290) {
			result += String.fromCharCode(46);
		} else {
			if (code == 183) {
				result += String.fromCharCode(64);
			} else {
				if (code >= 65281 && code < 65373) {
					result += String.fromCharCode(str.charCodeAt(i) - 65248);
				} else {
					result += str.charAt(i);
				}
			}
		}
	}
	return result;
}
/**
 * proSelId - select id of province
 */
function build_province(proSelId, provinceArray) {
	if(document.getElementById(proSelId)){
		for (key in provinceArray) {
			var sOption = document.createElement("OPTION");
			sOption.text = provinceArray[key];
			sOption.value = key;
			document.getElementById(proSelId).options.add(sOption);
		}
	}
	
}
function build_select(first_id, second_id, first_array, second_array, def_value) {
	if (def_value == "" || def_value == "0") {
		var k = 1000;
		for (key in first_array) {
			var sOption = document.createElement("OPTION");
			sOption.text = first_array[key];
			sOption.value = key;
			document.getElementById(first_id).options.add(sOption, k);
			k--;
		}
	} else {
		pro_key = def_value.substr(0, 2);
		var k = 1000;
		for (key in first_array) {
			var sOption = document.createElement("OPTION");
			sOption.text = first_array[key];
			sOption.value = key;
			if (pro_key == key) {
				sOption.id = "sele_pro" + first_id;
			}
			document.getElementById(first_id).options.add(sOption, k);
			k--;
		}
		document.getElementById("sele_pro" + first_id).selected = true;
		var k = 1000;
		for (key in second_array[pro_key]) {
			var sOption = document.createElement("OPTION");
			sOption.text = second_array[pro_key][key];
			sOption.value = key;
			if (def_value == key) {
				sOption.id = "sele_city" + second_id;
			}
			document.getElementById(second_id).options.add(sOption, k);
		}
		k--;
		document.getElementById("sele_city" + second_id).selected = true;
	}
}
function build_second(first_value, second_id, second_array) {
	document.getElementById(second_id).innerHTML = "";
   // var k=1000;
	for (key in second_array[first_value]) {
		var sOption = document.createElement("OPTION");
		sOption.text = second_array[first_value][key];
		sOption.value = key;
		document.getElementById(second_id).options.add(sOption);
	}
    //k--;
}
function readSelectData(id, select_array, N) {
	var k = 1;
	for (key in select_array) {
		if (key == 0 && N == 1) {
			continue;
		}
		var sOption = document.createElement("OPTION");
		sOption.text = select_array[key];
		sOption.value = key;
		document.getElementById(id).options.add(sOption, k);
		k++;
	}
}
/*
function build_day(first,def_value)
{
    document.getElementById("day").innerHTML = "";
    switch(first)
    {
        case 0:
            break;
        case "01":
            var second = 31;
            break;
        case "02":
            var second = 29;
            break;
        case "03":
            var second = 31;
            break;
        case "04":
            var second = 30;
            break;
        case "05":
            var second = 31;
            break;
        case "06":
            var second = 30;
            break;
        case "07":
            var second = 31;
            break;
        case "08":
            var second = 31;
            break;
        case "09":
            var second = 30;
            break;
        case "10":
            var second = 31;
            break;
        case "11":
            var second = 30;
            break;
        case "12":
            var second = 31;
            break;
        default:
            var second = 31;
            break;
    }

    if(def_value != "")
    {
        var sOption = document.createElement("OPTION");
        sOption.text = def_value;
        sOption.value = def_value;
        document.getElementById("day").options.add(sOption);
    }
    else
    {
        var k=1;
        for(var count=0; count<=second; count++)
        {
            var sOption = document.createElement("OPTION");
		    if(count == 0)
		    {
                sOption.text = "--请选择--";
                sOption.value = "0";
            }
            else if(count < 10)
            {
                sOption.text = "0" + count;
                sOption.value = "0" + count;
            }
            else
            {
                sOption.text = count;
                sOption.value = count;
            }
            document.getElementById("day").options.add(sOption,k);
            k++;
        }
    }
}
*/

