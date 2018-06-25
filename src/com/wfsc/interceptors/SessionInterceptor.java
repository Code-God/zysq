package com.wfsc.interceptors;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.constants.CupidStrutsConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.wfsc.annotation.Login;
/**
 * 判断session过期的拦截器
 * 仅仅对除了login,regist之外的方法进行拦截
 * @author Administrator
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 6574746004090789248L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext(); 
		Map<String, Object> session = actionContext.getSession();
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		if(!StringUtils.equals(invocation.getProxy().getMethod(), "login") || StringUtils.equals(invocation.getProxy().getMethod(), "goLogin")
				|| StringUtils.equals(invocation.getProxy().getMethod(), "logout")){
			setToGoingURL(request, session, invocation);
		}
		
		boolean auth = false;
		Login login = invocation.getAction().getClass().getAnnotation(Login.class);
		if(login != null){
			auth = true;
		}else{
			Method method = invocation.getAction().getClass().getMethod(invocation.getProxy().getMethod(), null);
			if(method != null){
				login = method.getAnnotation(Login.class);
				if(login != null){
					auth = true;
				}
			}
			
		}
		auth = false;
		if(auth){
			Object object = session.get(CupidStrutsConstants.SESSION_USER);
			if(object != null){
				return invocation.invoke();
			}else{
				//如果需要登录，而且是ajax的，则返回一个sessionOut的标记给ajax请求，前台拦截到以后，统一跳转到登录界面
			    //如果是ajax请求响应头会有，x-requested-with；  
			    if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){  
			        //在响应头设置session状态   
			        response.setHeader("sessionstatus", "sessionOut");  
			        response.getWriter().print("sessionOut");  
			        return null;  
			    }
			  //以下是非AJAX请求，继续
			    //商城的登录界面
			    
			    response.sendRedirect(request.getContextPath() + "/weixin/login.jsp?to=y");
				return null;
			}
		}else{
			return invocation.invoke();
		}
	}
	
	private void setToGoingURL(HttpServletRequest request, Map<String, Object> session, ActionInvocation invocation) {
		if(StringUtils.equalsIgnoreCase("post", request.getMethod())){
			return;
		}
		String url = request.getHeader("referer");
		if (StringUtils.isEmpty(url)) {
			url = request.getRequestURI();
			Map<String, String[]> params = request.getParameterMap();
			if (params != null) {
				for (String key : params.keySet()) {
					String[] value = params.get(key);
					for (String val : value) {
						url = url + key + "=" + val + "&";
					}
				}
			}
		}
		if(url.endsWith("&")){
			url = url.substring(0, url.length() -1);
		}
		session.put(CupidStrutsConstants.GOTO_URL_KEY, url);
	}

}
