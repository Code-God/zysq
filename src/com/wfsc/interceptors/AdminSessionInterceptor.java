package com.wfsc.interceptors;

import java.util.Map;

import com.constants.CupidStrutsConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 判断session过期的拦截器
 * 仅仅对除了login,regist之外的方法进行拦截
 * @author Administrator
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class AdminSessionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -4814135572974717882L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		Object object = session.get(CupidStrutsConstants.SESSION_ADMIN);
		if(object != null){
			return invocation.invoke();
		}else{
			return Action.LOGIN;
		}
	}
	

}
