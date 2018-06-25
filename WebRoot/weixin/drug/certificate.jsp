<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8">
    <title>医疗专业证书</title>
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/animate.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/user.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/iconfont/iconfont.css">
    <script src="<%=request.getContextPath()%>/weixin/drug/js/jquery-1.12.3.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/medicine.js"></script>
    <script language=javascript src="<%=request.getContextPath()%>/js/admin/ajaxfileupload.js"></script>
    <script src="<%=request.getContextPath()%>/weixin/drug/js/uploadPreview.js" type="text/javascript"></script>
    
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/weixin/drug/css/shared.css">
    <script>
       var activejsure=3;//设置footer选中项
    </script>
    <script type="text/javascript">
    $(function(){	
	    // 弹窗关闭事件
	    $(".close_box").click(function(){
	    	var id = $(this).parent().attr("id");
	        $("#mask").hide();
	        $(".box").hide();
			$(document.body).css({"overflow-x":"hidden","overflow-y":"scroll"});
	    	if (id == 'submit-success-id') {
	    			// 我要推荐，进入个人中心
	    			window.location.href="<%=request.getContextPath()%>/drug/user_userCenter.Q";
	    	}
	    });	 
    });
    function checkinput(){
		var qualification_id=$("#qualification_id").val();
		var hospital=$("#hospital").val();
		var office=$("#office").val();
		var userid=$("#userid").val();
		var professionalTitle=$("#professionalTitle").val();
		var pic=imagepath;		
		var errMsg = [];
		var filepath=$("#qualificationPic").val();	
		var qulifipic="${qualification.qualificationPic}";
		if($.trim(filepath)==""&&qulifipic==""){
			errMsg.push("请上传证书；");
		}
		
		if($.trim(hospital) == ""){
			errMsg.push("医院不能为空；");
		}
		
		if($.trim(office) == ""){
			errMsg.push("科室不能为空；");
		}
		
		if($.trim(professionalTitle) == ""){
			errMsg.push("护理证书不能为空；");
		}		
		
		if (errMsg.length > 0) {
			
			showMsg(errMsg.join("<br>"));
			return false;
		} else {
			$("#data-validated-id").hide();
			return true;
		} 
    }
    
	function showMsg(msg){
		$("#mask").css("height", $(document).height());
        $("#mask").css("width", $(document).width());
        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
        $("#mask").show();
        $("#err-msg-id").html(msg);		        
		$("#data-validated-id").show();
	}
	
   
    
    function showmask(){
	    		$("#mask").css("height", $(document).height());
		        $("#mask").css("width", $(document).width());
		        $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
		        $("#mask").show();
	    	}
    //
	function saveQualification(imagepath){		
		var qualification_id=$("#qualification_id").val();
		var hospital=$("#hospital").val();
		var office=$("#office").val();
		var userid=$("#userid").val();
		var professionalTitle=$("#professionalTitle").val();
		var pic=imagepath;
	 	$.ajax({
	 		type:"POST",
			url:"<%=request.getContextPath()%>/drug/qualification_saveQualification.Q",
			data:"id=" + qualification_id + "&hospital="+hospital+"&office="+office+"&professionalTitle="+professionalTitle+"&userid="+userid+"&pic="+pic,
			dataType:'text',	
			async:false,
			success:function(data){
				if("true"==data){
					//上传图片
					$("#submit-success-id").show();
					
				}else{
					var errMsg = [];
					errMsg.push("上传认证失败；");
					showMsg(errMsg.join("<br>"));
				}
		    }
		});
	}	
	
	function imageSelect(){		
		$("#qualificationPic").click();
	}
	
	 window.onload = function () { 
         new uploadPreview({ UpBtn: "qualificationPic", DivShow: "showdiv", ImgShow: "imagepath" });
     }

	function uploadQual(){
		var fileid="qualificationPic";		
		var filepath=$("#qualificationPic").val();	
		if(checkinput()){
			if($.trim(filepath)!=""){
					$.ajaxFileUpload({
						url:"<%=request.getContextPath()%>/drug/qualification_uploadQualification.Q",
						secureuri:false,
						fileElementId: fileid,
						dataType: 'text/html',
						async:false,
						success: function (data) {	
							if(""!=data){
								//图片上传成功后返回图片地址，保存数据库	
								saveQualification(data);
							}else{
	    						//alert("失败");
	    						var errMsg = [];
	    						errMsg.push("图片上传失败；");
	    						showMsg(errMsg.join("<br>"));
							}					
						},
						error: function (data, status, e){
    						var errMsg = [];
    						errMsg.push("图片上传失败；");
    						showMsg(errMsg.join("<br>"));
						}
					}
				);	
			}else{
				saveQualification("");
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
        <div class="certificate_form">
            <form>
                    <figure id="showdiv">
                    	 <input type="file" name="qualificationPic" style="display: none"  id="qualificationPic">
                    	 <s:if test="#request.qualification.qualificationPic!=null && #request.qualification.qualificationPic!=''">
                    		 <img src="${readpath}${qualification.qualificationPic}" id="imagepath" width="50%" onclick="imageSelect()">
                    	 </s:if>
                    	 <s:else>
                    		 <img src="<%=request.getContextPath()%>/weixin/drug/images/certificate.png" id="imagepath" width="50%" onclick="imageSelect()">
                    	 </s:else>
                    </figure>
                    <div class="prompt m-t-10">带“*”为必填项</div>
                    <s:if test="#qualification.reviewState==1">
                    	 <div class="prompt m-t-10">认证审核通过</div>
                    </s:if>
                    <s:if test="#qualification.reviewState==1">
                    	
                    	<div class="prompt m-t-10">认证审核不通过</div>
                    </s:if>                    
                    <div class="prompt m-t-10"></div>	
                    <ul>
                        <li class="required"><strong>医院：</strong>
                      		<input type="hidden" id="userid" name="qualification.userId" value="${userid}">
                        	<input type="hidden" id="qualification_id" name="qualification.id" value="${qualification.id }">
                            <input type="text" id="hospital" name="qualification.hospital" value="${qualification.hospital}" class="in_text">
                        </li>
                        <li class="required"><strong>科室：</strong>
                            <input type="text" id="office" name="qualification.office" value="${qualification.office}" class="in_text">
                        </li>
                        <li class="required"><strong>职称：</strong>
                            <select class="in_text" id="professionalTitle" name="professionalTitle">
                                <option value="临床医师" <s:if test='#request.qualification.professionalTitle eq "临床医师"'>selected="selected"</s:if> >临床医师</option>
                                <option value="护理证书 " <s:if test='#request.qualification.professionalTitle eq "护理证书"'>selected="selected"</s:if> >护理证书</option>
                            </select>
                        </li>
                    </ul>
                <div class="clear"></div>
                <div>
                    <button type="button" onclick="javascript:uploadQual();showmask()">上传认证</button>
                </div>

            </form>
        </div>
        <!---->
    </div>
    <!---->
    <%@include file="footer.jsp"%>
    <!---->
</body>

</html>
<script>
</script>