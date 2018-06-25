<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../WEB-INF/jsps/common/commonHeader.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <!-- uc强制竖屏 -->
    <meta name="screen-orientation" content="portrait">
    <!-- QQ强制竖屏 -->
    <meta name="x5-orientation" content="portrait">

    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/reset.css?v=1.0">
	<link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/plugin/mobiscroll.2.13.2.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/shared.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/information.css">
    
    <script src="${ctx}/weixin/drug/js/plugin/jquery-2.1.4.min.js"></script>

    <script src="${ctx}/js/common/city.js"></script>
    <script src="${ctx}/js/jquery/formValidatorRegex.js"></script>
    <title>找药神器</title>
    
    <%-- <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/css/page.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/weixin/drug/iconfont/iconfont.css">
    <script src="${ctx}/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="${ctx}/weixin/drug/js/html5shiv.js"></script>
    <script src="${ctx}/js/jquery/ajaxupload.3.6.js"></script>
    <script src="${ctx}/js/common/city.js"></script> --%>
    
    <script>
        var activejsure=1;
        
    	// 上传图片临时存放路径
    	var g_tempDir = "${ctx}/attachments/medical_history/temp/";

		//获取地址栏参数值
		$.getUrlParam = function(name)
		{
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r  = window.location.search.substr(1).match(reg);
			if (r!=null) return unescape(r[2]); return null;
		};
    
    	$(function(){
			//id为74的固定表单放到下面  隐藏其中四个选项
			var diseaseId = parseInt($("input[name=diseaseId]").val());
			if(diseaseId==74){
				$("#sex").parent().hide();
				$("#birthDate").parent().hide();
				$("#address").parent().hide();
				$("#medicalHistoryDescription").parent().hide();
			}
			
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
		    	var id = $(this).parent().parent().attr("id");
		    	// 如果保存成功，点击关闭窗口
		    	if (id == 'submit-result-id') {
					// 进入个人中心
					window.location.href="${ctx}/drug/user_userCenter.Q";
		    	}
		    	
		        $("#mask").hide();
		        $(".old-box").hide();
				$(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
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

			//关注按钮弹框
			$(".toAttention").click(function(){
				$("#mask").css("height", $(document).height());
				$("#mask").css("width", $(document).width());
				$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
				$("#mask").show();
				$("#ewm-box").show();
			})
		});


	//上传图片验证及设置id
    	function showMedicalHistoryImg(flag, imgId, imgName){
	        //var flag = response.flag;
	        if (flag == "1") {
	            alert("您上传的病历格式不对，请重新选择！");
	        }
	        else if (flag == "2") {
	            alert("您上传的病历大于3M，请重新选择！");
	        }
	        else if (flag == "3") {
	            alert("病历上传失败！");
	        }
	        else {
	            $(".up-img").prev(".form-img").attr("imgname",imgName);
	        }
    	}
    	
    	/**
    	 * 表单校验
    	 */
    	function validateForm(){
    		var errMsg = [];
    		var realName = $("#realName").val();
    		if ($.trim(realName) == '') {
    			errMsg.push("请填写真实姓名；");
    		}else{
	    		if ($.trim(realName).length > 50) {
	    			errMsg.push("真实姓名最多允许输入50个字符；");
	    		}
    		}
    		
    		var mobileReg = new RegExp(regexEnum['mobile']);
    		var mobilePhone = $("#mobilePhone").val();
    		if ($.trim(mobilePhone) == '') {
    			errMsg.push("请填写手机号码；");
    		}else{
	    		if(!mobileReg.test(mobilePhone)){
	    			errMsg.push("手机号码输入不正确；");
	    		}
    		}
    		
    		if ('${singupType}' != '1') {
	    		var verificationCode = $("#verificationCode").val();
	    		if ($.trim(verificationCode) == '') {
	    			errMsg.push("请填写短信验证码；");
	    		}
	    	}

			//id为74的固定表单放到下面  隐藏其中四个选项
			var diseaseId = parseInt($("input[name=diseaseId]").val());
			if(diseaseId!=74){
				//var ageReg = new RegExp(regexEnum['intege1']);
				var birthDate = $("#birthDate").val();
				if ($.trim(birthDate) == '') {
					errMsg.push("请填写出生日期；");
				}else{
// 	    		if(!ageReg.test(age)){
// 	    			errMsg.push("年龄输入不正确，只能输入数字；");
// 	    		}
				}

				var address = $("#address").val();
				if ($.trim(address) != '' && $.trim(address).length > 200) {
					errMsg.push("通讯地址最多允许输入200个字符；");
				}

				var mhd = $("#medicalHistoryDescription").val();
				if ($.trim(mhd) != '' && $.trim(mhd).length > 500) {
					errMsg.push("病史描述最多允许输入500个字符；");
				}
			}


    		var province = $("#province").val();
    		if ($.trim(province) == '') {
    			errMsg.push("请选择行政区划；");
    		}
    		var city = $("#city").val();
    		if ($.trim(city) == '') {
    			errMsg.push("请选择所在城市；");
    		}

    		if ($(".form-xy input").attr("checked")!="checked"){
			errMsg.push("提交信息前必须阅读并同意《申请协议》!");
		    }
    		
    		if (errMsg.length > 0) {
    			showMsg(errMsg.join("<br>"));
    			return false;
    		} else {
				// 隐藏病历附件名
				var images = [];
				$("#upload-file-li").find("img").each(function(){
					images.push("<input type=\"hidden\" name=\"images\" value=\""+ $(this).attr("imgname") +"\">");
				});
    			$("#theForm").append(images.join(""));
				
    			$("#data-validated-id").hide();
    			return true;
    		}
    	}
    	
    	/**
    	 * 显示异常信息
    	 */
    	function showMsg(msg){
    		$("#mask").css("height", $(document).height());
	        $("#mask").css("width", $(document).width());
	        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	        $("#mask").show();
	        $("#err-msg-id").html(msg);	        
			$("#data-validated-id").show();
    	}

    	/**
    	 * 验证并方式提交表单
    	 */
    	function savePatientInfo() {
    		if (validateForm()) {
    			submitPatientInfo();
    		}
    	}
    	
    	/**
    	 * ajax方式提交表单
    	 */
    	function submitPatientInfo() {
			//判断必填字段是否为空
			var submitStatus = true;
			$(".form .required").each(function(){
				var requiredStr = $(this).serializeNew();
				console.log(requiredStr);
				var nowArr = requiredStr.split("=");
				var value = nowArr[nowArr.length-1];
				if(value==""||value==null||value==undefined){
					showMsg("*必填字段不能为空！");
					submitStatus = false;
				}
			});
			var submitDynamicForm = function(){
				//提交动态表单
				var mobile = "&user.mobilePhone="+$("#mobilePhone").val();
				var dynamicData = $('.dynamicForm').serializeNew() + mobile;
				$.ajax({
					url: '${ctx}/drug/user_postCustomData.Q',
					type: "post",
					data: dynamicData,
					dataType:"json",
					success:function(result){
						setTimeout(function(){
							window.location.href='zysq/drug/user_userCenter.Q';
						},2000)
					}
				});
			};
			if(submitStatus){
				//提交静态表单
				$.ajax({
					url: '${ctx}/drug/user_savePatientInfo.Q',
					type: "post",
					data: $('.staticForm').serializeNew(),
					dataType:"json",
					success:function(result){
						var flag = result.flag;
						if (flag == "0") {
							$("#submit-result-id").show();
							$("#mask").show();
							//提交动态表单
							if(lodeFormStatus){
								submitDynamicForm();
							}
						} else if (flag == "1") {
							showMsg("验证码输入不正确！");
						} else if (flag == "3") {
							// 我要报名
							showMsg("您已报名过该项目！");
						} else {
							showMsg("提交信息异常，请稍后重试！");
						}
					}
				});
			}
    	}

    	/**
    	 * 获取短信验证码
    	 */
    	function getVerificationCode(){
    		var mobileReg = new RegExp(regexEnum['mobile']);
      		var mobile = $("#mobilePhone").val();
    		if ($.trim(mobile) == '') {
    			showMsg("手机号码必填！");
    			return;
    		}else{
	    		if(!mobileReg.test(mobile)){
	    			showMsg("手机号码输入不正确！");
	    			return;
	    		}
    		}
    		
    		$.ajax({
				url: '${ctx}/drug/user_getVerificationCode.Q?_d='+(new Date().getTime()),
				type: "post",
				data: {"mobile" : mobile},
				dataType:"json",
				success:function(result){
					var flag = result.flag;  
					if (flag == "1") {  
						//成功发送短信
						// $("#verificationCode").val(result.code);
					} else {
						showMsg("短信接口异常，请稍后重试！");
					}
				}
			});
		}
    </script>
</head>

<body>
		    <div id="mask"></div>
		    <div class="mark"></div>
		    <!---->
		    <div id="submit-result-id" class="old-box">
				<div class="box-head">
				提示
				<a class="close_box multiple" href="javascript:(0);"></a>
				</div>
		        <ul><li>感谢您的报名参与！</li>
		        <li><a href="<%=request.getContextPath()%>/drug/user_userCenter.Q">（请在“我的”查看状态。）</a></li></ul>
		    </div>
		    <!---->

		    <!---->
		    <div id="data-validated-id" class="old-box">
				<div class="box-head">
				提示
				<a class="close_box multiple" href="javascript:(0);"></a>
				</div>
		        <ul>
					<li><p id="err-msg-id"></p></li>
		        </ul>
		    </div>
		    <!---->

			<!---->
			<div id="ewm-box" class="old-box">
				<div class="box-head">
					长按二维码关注公众号
					<a class="close_box multiple" href="javascript:(0);"></a>
				</div>
				<div class="ewm-box-img">
					<img src="http://zhaoyao.medforce.cn/zysq/weixin/drug/imgs/ewm-img.png">
				</div>
			</div>
			<!---->

		    <!---->
		    <div id="preview-div-id" class="old-box">
				<div class="box-head">
				提示
				<a class="close_box multiple" href="javascript:(0);"></a>
				</div>
		        <ul>
		        <li id="image-preview-id"></li>
		        </ul>
		    </div>
		    <!---->

          <header>
              <div class="head">
                    <h1>
                       <a href="zysq/drug/index_getDetailed.Q?id=${diseaseId}">
							<c:if test="${drugDiseaseItem != null}">${drugDiseaseItem.medicineName}</c:if>
							<i class="link-img"></i>
						</a>
                    </h1>
                    <div class="head-contend clearafter">
                       <span class="head-contend-label">
                            <c:if test="${drugDiseaseItem != null}">${drugDiseaseItem.diseaseName}</c:if>
                       </span>
                       <!--<span>已报名<b> ${drugDiseaseItem.id+userCount}人</b></span>-->
                    </div>
                    <c:if test="${isSubscrib == 0}">
						<span class="toAttention"><i></i>关注找药神器</span>
				    </c:if>
              </div>
        </header>


        <section>
    <div class="contend">
            <div class="form">
	            <form action="" id="theForm">
					<c:if test="${diseaseId == 74}">
						<div class="dynamicForm showed1">
							<input type="hidden" name="projectId" value="${projectId}">
						</div>
					</c:if>
					<div class="staticForm">
						<input type="hidden" name="singupType" value="${singupType}">
						<input type="hidden" name="diseaseId" value="${diseaseId}">
						<input type="hidden" name="mapProjectId" value="${projectId}">
						<input type="hidden" name="user.wechatNickname" value="">
						<input type="hidden" name="user.wechatId" value="">
						<div class="form-item">
							<label>姓名</label>
							<input class="form-item-input" type="text" name="user.realName" placeholder="请填写" id="realName" value="${userinfo.realName }"/>
						</div>
						<div class="form-item">
							<label>手机</label>
							<input class="form-item-input mobile" type="number" name="user.mobilePhone" placeholder="请填写" id="mobilePhone" value="${userinfo.mobilePhone }"/>
						</div>
						<div class="form-item2">
								<label>验证码</label>
								<input class="form-item-input" type="number" name="verificationCode" placeholder="请填写" id="verificationCode" value=""/>
								<a class="get-yzm" href="javascript:(0);" onclick="getVerificationCode(this);">获取验证码</a>
							</div>
						<div class="form-item">
							<label>性别</label>
							<select class="form-item-input sp-status"  name="user.sex" id="sex">
								<option value="1" <s:if test="#request.userinfo.sex==1 ||#request.userinfo.sex==''">selected</s:if>>男</option>
								<option value="2" <s:if test="#request.userinfo.sex==2">selected</s:if>>女</option>
							</select>
						</div>
						<div class="form-item">
							<label>出生日期</label>
							<input class="form-item-input" type="text" name="user.birthDate" id="birthDate" placeholder="请填写" value="${birthDate }"/>
						</div>
						<div class="form-item">
							<label>所在省</label>
							<select class="form-item-input sp-status" name="user.province" id="province">
							</select>
						</div>
						<div class="form-item">
							<label>所在市</label>
							<select class="form-item-input sp-status" name="user.city" id="city">
							</select>
						</div>
						<div class="form-item">
							<label>通讯地址</label>
							<input class="form-item-input" type="text" name="user.address" placeholder="请填写" id="address" value="${userinfo.address }">
						</div>
						<div class="form-item">
							<label>病史描述</label>
							<textarea class="form-item-input" rows="3" cols="" name="user.medicalHistoryDescription" id="medicalHistoryDescription">${userinfo.medicalHistoryDescription}</textarea>
						</div>
					</div>
					<c:if test="${diseaseId != 74}">
						<div class="dynamicForm showed2">
							<input type="hidden" name="projectId" value="${projectId}">
						</div>
					</c:if>
	            </form>
	            <form action="${ctx}/drug/user_uploadMedicalHistory.Q" method="post" id="theUploadForm" class="form-horizontal" enctype="multipart/form-data" target="tarframe">
                    <iframe src="" width="0" height="0" style="display:none;" name="tarframe"></iframe>
	                <div class="form-item">
	                    <label>上传病例</label>
	                    <div class="form-imgs clearafter">
	                        <div class="template form-img">
	                            <img src="">
	                            <i class="img-delete multiple"></i>
	                        </div>
	                        <a class="up-img multiple" href="javascript:(0);"></a>
	                    </div>
	                    <input class="file" id="myFile" name="myFile" type="file"/>
	                </div>
	            </form>
                <div class="form-xy">
                    <i class="form-xy-icon multiple"></i>
                    <input id="xy" type="checkbox" name="xy"/>
                    <label class="xy-btn">已阅读并同意申请协议</label>
                </div>
                <a id="patientInfoBut" class="form-btn" href="javascript:(0);" onclick="savePatientInfo();">提交信息</a>
            </div>
    </div>

    <div class="img-box">
        <a class="img-box-close multiple" href="javascript:(0);"></a>
        <img src="" alt=""/>
    </div>

    <div class="box">
        <div class="box-head">
            申请协议
            <a class="box-close multiple" href="javascript:(0);"></a>
        </div>
        <div class="box-contend">
            <p>
                                欢迎您的登陆，本网站为找药神器网站所有。本网站隐私制度旨在告诉你本网站如何收集、使用和披露用户可能通过本网站提供的信息。在使用本网站或向本网站提供信息前，请务必阅读本网站隐私制度之全部内容。使用本网站必须遵守相关的法律、法规及本网站隐私制度的规定。
                        </p>
                        <h2>同意</h2>
                        <p>
一经使用本网站，你会被视为已同意本网站隐私制度所列之各项条款。不论你何时通过本网站提供信息，都被视为同意本网站依照其隐私制度收集、使用和披露此等信息。<br>
（1）您在匿名的状态下即可访问我们的网站并获取信息。在本网站请求您提供有关信息之前，本网站会解释这些信息的用途，有些服务需要您注册后才能够提供。在通常情况下，这类注册只要求您提供一些基本信息。如果在某些情况下请您提供更多的信息，是为了使本网站更好的理解您的需求，以便向您提供更为有效的服务。本网站收集的信息包括姓名，地址和移动电话号码等，您有权随时决定不接受来自我们的任何资料。<br>
（2）  您的注册义务：  您如希望使用本网站需注册后才能够提供的服务，应同意以下事项∶依本服务注册表之提示提供您本人真实、正确、最新及完整的资料，并随时更新登记资料，确保其为真实、正确、最新及完整的资料。若您提供任何错误、不实、过时或不完整或具误导性的资料，或者本网站有理由怀疑前述资料为错误、不实、过时或不完整或具误导性的，本网站有权暂停或终止您的帐号，并拒绝您于现在和未来使用本网站所提供服务之全部或任何部分。<br>
（3）  本网站将采取合理的安全手段保护您已存储的个人信息，本网站不会公布或传播用户在本网站注册的任何资料，但下列情况除外：<br>
1.事先获得用户的明确授权；<br>
2.根据有关法律法规的要求；<br>
3.依据法院或仲裁机构的裁判或裁决，以及其他司法程序的要求；<br>
4.按照相关政府主管部门的要求；<br>
5.您违反使用条款的规定或有其他损害北京电通利益的行为。<br>
（4）  您应了解：本网站可能会应法律之要求公开个人资料，或者因善意确信这样的作法对于下列各项有其必要性：<br>
1.符合法律公告；<br>
2.保护本网站用户的权利或财产；<br>
3.在紧急的情况下，为了保护本网站及其用户之个人或公众安全。
                        </p>
        </div>
        <a class="again" href="javascript:(0);">同意</a>
    </div>
</section>
        
    <!---->
    <%@include file="footer.jsp"%>
    <!---->
	<script type="text/javascript" src="${ctx}/weixin/drug/js/plugin/mobiscroll.js"></script>
    <script type="text/javascript" src="${ctx}/weixin/drug/js/main.js"></script>
   <script type="text/javascript" src="${ctx}/weixin/drug/js/information.js"></script>
</body>

</html>
<script>
</script>