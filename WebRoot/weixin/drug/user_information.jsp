<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>完善信息</title>
    <meta name="author" content="">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/plugin/mobiscroll.2.13.2.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/user.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <!--<script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>-->
    <!--<script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>-->
	<script type="text/javascript" src="${ctx}/weixin/drug/js/plugin/mobiscroll.js"></script>
	
    <script src="<%=request.getContextPath()%>/js/common/city.js"></script>
    <!--<script language=javascript src="<%=request.getContextPath()%>/js/admin/ajaxfileupload.js"></script>-->
    <!--<script src="<%=request.getContextPath()%>/weixin/drug/js/uploadPreview.js" type="text/javascript"></script>-->
    <script src="<%=request.getContextPath()%>/js/jquery/formValidatorRegex.js"></script>
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/shared.css">
    <script type="text/javascript">
       var activejsure=3;//设置footer选中项
    </script>
    <script type="text/javascript">
	    $(function(){			
			// 初始化行政区划
		    var cs = [];
		    cs.push('<option value="" selected>请选择</option>');
		    $.each(cities, function(idx){
		    	cs.push('<option value="'+ idx +'">'+ idx +'</option>');
		    });
		    $("#province").empty();
		    $("#province").append(cs.join(""));
	
		    // 弹窗关闭事件
		    $(".close_box").click(function(){
		    	var id = $(this).parent().attr("id");
		        $("#mask").hide();
		        $(".box").hide();
				$(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
		    	if (id == 'submit-success-id') {
		    			// 我要推荐，进入首页
		    			window.location.href="<%=request.getContextPath()%>/drug/user_userCenter.Q";
		    	}
		    });	    
		    
		    // 行政区划选择事件，级联改变所在城市
		    $("#province").change(function(){
		    	var val = $(this).val(); 
		        if (val != '') {
		        	var cs2 = cities[val];
		        	var c2 = [];
		 		    c2.push('<option value="" selected>请选择</option>');
		 		    $.each(cs2, function(idx){
		 		    	
		 		    	c2.push('<option value="'+ cs2[idx] +'">'+ cs2[idx] +'</option>');
		 		    });
		 		    
		        	$("#city").empty();
				    $("#city").append(c2.join(""));
		        }
		    }); 
		    
		    if("${userinfo.province}"!=""){
		    	$("#province").val("${userinfo.province}");
		    	$("#province").change();
		    	$("#city").val("${userinfo.city}");
		    }
		    
		    
	    })


	    //选择图像
	    function selectpic(){
	    	//触发file的点击时间
	    	$("#userPicfile").click();
	    }
	    
		 //window.onload = function () { 			
	      //   new uploadPreview({ UpBtn: "userPicfile", DivShow: "showdiv", ImgShow: "userpicimg" });
	     //}
		 
		 //表单验证
		 function checkInput(){			 
			 var errMsg = [];
			 	/*  var userpic="${userinfo.userPic}";
			 	var userPicfile=$("#userPicfile").val();
				if($.trim(userPicfile)==""&&userpic==""){
					errMsg.push("请上传头像；");
				} */
	    		var realName = $("#realName").val();
	    		if ($.trim(realName) == '') {
	    			errMsg.push("真实姓名必填；");
	    		}else{
		    		if ($.trim(realName).length > 50) {
		    			errMsg.push("真实姓名最多允许输入50个字符；");
		    		}
	    		}
	    		
	    		var mobileReg = new RegExp(regexEnum['mobile']);
	    		var mobilePhone = $("#mobilePhone").val();
	    		if ($.trim(mobilePhone) == '') {
	    			errMsg.push("手机号码必填；");
	    		}else{
		    		if(!mobileReg.test(mobilePhone)){
		    			errMsg.push("手机号码输入不正确；");
		    		}
	    		}
	    	
	    		var ageReg = new RegExp(regexEnum['intege1']);
	    		var birthDate = $("#birthDate").val();
	    		if ($.trim(birthDate) == '') {
	    			errMsg.push("出生日期必填；");
	    		}else{
// 		    		if(!ageReg.test(age)){
// 		    			errMsg.push("年龄输入不正确出生日期；");
// 		    		}
	    		}
	    		
	    		var province = $("#province").val();
	    		if ($.trim(province) == '') {
	    			errMsg.push("行政区划必填；");
	    		}
	    		var city = $("#city").val();
	    		if ($.trim(city) == '') {
	    			errMsg.push("所在城市必填；");
	    		}
	    		
	    		var address = $("#address").val();
	    		if ($.trim(address) != '' && $.trim(address).length > 200) {
		    		errMsg.push("通讯地址最多允许输入200个字符；");
	    		}	    		
	    		
	    		
	    		if (errMsg.length > 0) {
	    			
	    			showMsg(errMsg.join("<br>"));
	    			return false;
	    		} else {
	    			$("#data-validated-id").hide();
	    			return true;
	    		} 
		 }
		 
		 function saveUserInfo(pic){	    	
	    			var realName = $("#realName").val();
	    			var mobilePhone = $("#mobilePhone").val();
	    			var sex=$("#sex").val();
	    			var birthDate = $("#birthDate").val();
	    			var province = $("#province").val();
	    			var city = $("#city").val();
	    			var address = $("#address").val();
	    			var hospital = $("#hospital").val();
	    			var department = $("#department").val();
	    			var position = $("#position").val();
	    			var userid="${userinfo.id}";
	    			var param="realName=" + realName + "&mobilePhone="+mobilePhone
	    					+"&sex="+sex+"&birthDate="+birthDate+"&province="+province
	    					+"&city="+city+"&address="+address+"&hospital="+hospital+"&department="+department+"&position="+position+"&picpath="+pic+"&userid="+userid;
	    		 	$.ajax({
	    		 		type:"POST",
	    				url:"<%=request.getContextPath()%>/drug/user_savePerfectUserInfo.Q",
	    				data:param,
	    				dataType:'text',	
	    				async:false,
	    				success:function(data){
	    					if("true"==data){
	    						$("#submit-success-id").show();
	    						
	    					}else{
	    						//alert("失败");
	    						var errMsg = [];
	    						errMsg.push("保存信息失败；");
	    						showMsg(errMsg.join("<br>"));
	    					}
	    			    }
	    			});	    			
		 }
		 
	    	
	    	function showMsg(msg){
	    		$("#mask").css("height", $(document).height());
		        $("#mask").css("width", $(document).width());
		        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
		        $("#mask").show();
		        $("#err-msg-id").html(msg);		        
				$("#data-validated-id").show();
	    	}
	    	
	    	//
	    	
	    	function showmask(){
	    		$("#mask").css("height", $(document).height());
		        $("#mask").css("width", $(document).width());
		        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
		        $("#mask").show();
	    	}
	    	
	    	//

	    	function uploadpic(){
	    		var userPicfile=$("#userPicfile").val();
	    		var fileid="userPicfile";
	    		if(checkInput()){	    			
		    		//修改上传头像
		    		if($.trim(userPicfile)!=""){
			    	 		$.ajaxFileUpload({
								url:"<%=request.getContextPath()%>/drug/user_uploadUserpic.Q",
								secureuri:false,
								fileElementId: fileid,
								dataType: 'text/html',
								async:false,
								success: function (data) {	
									if(""!=data){
										//图片上传成功后返回图片地址，保存数据库	
										saveUserInfo(data);
									}else{
			    						var errMsg = [];
			    						errMsg.push("头像上传失败；");
			    						showMsg(errMsg.join("<br>"));
									}					
								},
								error: function (data, status, e){
		    						var errMsg = [];
		    						errMsg.push("头像上传失败；");
		    						showMsg(errMsg.join("<br>"));
								}
							}
							);
		    	 
		    	 		//不修改头像
		    		}else{
		    			saveUserInfo("");
		    		}
	    		}
	    	}
    </script>
</head>

<body>
    <!---->
    <div id="mask"></div>
    <!---->
    <div id="submit-success-id" class="box">
    	<i class="close_box"></i>
        <figure></figure>
        <ul><li>提交成功！</li>
      
    </div>
    <!---->
    
    <!---->
    <div id="data-validated-id" class="box">
    	<i class="close_box"></i>
        <figure></figure>
        <ul>
        <li>提示：</li>
        <li><p id="err-msg-id"></p></li>
        </ul>
    </div>
    <!---->
    <div class="main">
        <!---->
        <!---->
        <div class="patient_form">
            <div class="prompt">带“*”为必填项</div>
            <form>
                <ul>
                    <li><strong style="margin-top:10px;">上传头像：</strong>
                    <div class="user_img"><i class="icon iconfont" id="showdiv">
                    	 <s:if test="#request.userinfo.headimgurl!=null && #request.userinfo.headimgurl!=''">
                    		<%-- <img id="userpicimg" src="${userinfo.userPic}" onclick="selectpic()" width="40px" height="40px"/> --%>
                    		<img id="userpicimg" src="${userinfo.headimgurl}"  width="40px" height="40px"/>
                    	 </s:if>
                    	 <s:else>	
                    	 	<!-- 没有图像读取默认图片 -->
                       		 <img id="userpicimg" src="<%=request.getContextPath()%>/weixin/drug/images/search_icon.png" width="40px" height="40px"/>
                        </s:else>
                        </i>
                        <!-- <input id="userPicfile" name="userPicfile" type="file" class="file_text"> -->
                    </div>
                    </li>
                    <li class="required"><strong>真实姓名：</strong>
                        <input type="text" class="in_text" id="realName" name="realName" value="${userinfo.realName}">
                    </li>
                    <li class="required"><strong>手机号码：</strong>
                        <input type="text" class="in_text" id="mobilePhone" name="mobilePhone" value="${userinfo.mobilePhone}">
                    </li>
                    <li class="required"><strong>性别：</strong>
                         <select class="in_text"  name="sex" id="sex">
                            <option value="1" <s:if test="#request.userinfo.sex==1 || #request.userinfo.sex=='' ">selected</s:if> >男</option>
                            <option value="2" <s:if test="#request.userinfo.sex==2 ">selected</s:if>>女</option>
                         </select>
                    </li>
                    <li class="required"><strong>出生日期：</strong>
                        <input type="text" class="in_text" name="birthDate" id="birthDate" value="${birthDate}">
                    </li>
                    <li class="required"><strong>所在省份：</strong>
                        <select class="in_text"  name="province" id="province">
                        </select>
                    </li>
                    <li class="required"><strong>所在城市：</strong>
                        <select class="in_text" name="city" id="city">
                        </select>
                    </li>
                    <li><strong>通讯地址：</strong>
                       <input type="text" class="in_text" name="address" id="address" value="${userinfo.address}">
                    </li>
                    <li><strong>医院：</strong>
                       <input type="text" class="in_text" name="hospital" id="hospital" value="${userinfo.hospital}">
                    </li>
                    <li><strong>科室：</strong>
                       <input type="text" class="in_text" name="department" id="department" value="${userinfo.department}">
                    </li>
                    <li><strong>职位：</strong>
                        <select class="in_text" id="position" name="position" >
                            <option value=""  <s:if test="#request.userinfo.position ==''">selected</s:if>>请选择</option>
		                  	<option value="1" <s:if test="#request.userinfo.position==1">selected</s:if>>主任医师</option>
		                  	<option value="2" <s:if test="#request.userinfo.position==2">selected</s:if>>副主任医师</option>
			             	<option value="3" <s:if test="#request.userinfo.position==3">selected</s:if>>主治医生</option>
			             	<option value="4" <s:if test="#request.userinfo.position==4">selected</s:if>>住院总</option>
			             	<option value="5" <s:if test="#request.userinfo.position==5">selected</s:if>>实习医生</option>
		                  	<option value="9" <s:if test="#request.userinfo.position==9">selected</s:if>>其他</option>
                        </select>
                    </li>
                    <li>
                        <button type="button" onclick="saveUserInfo('');showmask()">提交信息</button>
                    </li>
                </ul>

            </form>
        </div>
        <!---->
    </div>
    <!---->
   <%@include file="footer.jsp"%>
    <!---->
</body>

</html>
<script src="<%=request.getContextPath()%>/weixin/drug/js/doDatetimepicker.js"></script>
<script>
</script>