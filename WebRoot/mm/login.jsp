<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="com.base.tools.Version"%>
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
    
<head>
        <title>【<%=Version.getInstance().getSystemTitle() %>后台管理系统】</title><meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/mm/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/mm/css/bootstrap-responsive.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/mm/css/matrix-login.css" />
        <link href="<%=request.getContextPath()%>/mm/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <!-- 
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>
         -->
         <script language="javascript">
		
	
	if(document.addEventListener){//如果是Firefox   
		document.addEventListener("keypress",fireFoxHandler, true);   
	}else{
		document.attachEvent("onkeypress",ieHandler);  
	}
	
	function fireFoxHandler(evt){ 
		if(evt.keyCode==13){ 
			doLogin();
		}  
	} 

	function ieHandler(evt){
		if(evt.keyCode==13){
			doLogin();
		} 
	}
	
</script>
    </head>
    <body>
        <div id="loginbox">      
            <s:form action="admin_login.Q" namespace="/admin" method="POST" onsubmit="return checkLogin();" id="lgForm">
				 <div class="control-group normal_text"> <h3>
				 <!-- <img src="<%=request.getContextPath()%>/mm/img/logo.png" alt="Logo" /> -->
				 </h3></div>
				 
				  <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                           <span style="color: #FFF;font-family: Microsoft YaHei, simsun; font-size: 24pt">找药神器后台管理系统</span>
                        </div>
                    </div>
                </div>
				 
				 <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                           <span style="color: #F00">${errorMsg }</span>
                        </div>
                    </div>
                </div>
				
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i class="icon-user"></i></span><input name="admin.username" id="loginName" type="text" placeholder="Username" />
                           </br> <span style="color: #F00;display:none" id="loginNameSpan">请输入登录名</span>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input name="admin.password" id="loginPassword"  type="password" placeholder="Password" />
                           </br>  <span style="color: #F00;display:none" id="loginPasswordSpan">请输入登录密码</span>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box" style="position: relative;">
                            <span class="add-on bg_ls"><i class="icon-th"></i></span><input name="verifyCode" id="verifyCode" type="text" placeholder="请输入验证码" />
                           </br> <span style="color: #F00;display:none" id="verifyCodeSpan">请输入验证码</span>
                            </br> <img src="<%=request.getContextPath()%>/getCodeImage?flag=admin" style="position:absolute; top:0;right:0; width: 100px;height: 38px; cursor: pointer;z-index: 9999999;" alt="点击切换验证码" title="点击切换验证码" onclick="changeCode(this);">
                        </div>
                    </div>
                </div>
                <div class="form-actions" style="text-align:center;">
                    <span class="pull-left" style="display: none"><a href="#" class="flip-link btn btn-info" id="to-recover">忘记密码?</a></span>
                    <span><a type="submit" href="###" class="btn btn-success" onclick="doLogin()"/> 登录</a></span>
                </div>
			</s:form>            
            <form id="recoverform" action="#" class="form-vertical">
				<p class="normal_text">Enter your e-mail address below and we will send you instructions how to recover a password.</p>
				
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lo"><i class="icon-envelope"></i></span><input type="text" placeholder="E-mail address" />
                        </div>
                    </div>
               
                <div class="form-actions">
                    <span class="pull-left"><a href="#" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a></span>
                    <span class="pull-right"><a class="btn btn-info"/>Reecover</a></span>
                </div>
            </form>
        </div>
        <script type="text/javascript">
        	function changeCode(obj){
				obj.src = '<%=request.getContextPath()%>/getCodeImage?s=' + Math.random();
        	}
        	
        	function doLogin(){
       			$("#lgForm").submit();
        	}
        	
        	function checkLogin(){
				if($.trim($("#loginName").val()) == ""){
					$("#loginNameSpan").show();
					return false;
				}
				if($.trim($("#loginPassword").val()) == ""){
					$("#loginPasswordSpan").show();
					return false;
				}
				if($.trim($("#verifyCode").val()) == ""){
					$("#verifyCodeSpan").show();
					return false;
				}
				return true;
			}
        </script>
        <script src="<%=request.getContextPath()%>/mm/js/jquery.min.js"></script>  
        <script src="<%=request.getContextPath()%>/mm/js/matrix.login.js"></script> 
    </body>

</html>
