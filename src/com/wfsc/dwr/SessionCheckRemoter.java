package com.wfsc.dwr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.Calls;
import org.directwebremoting.extend.Replies;
import org.directwebremoting.impl.DefaultRemoter;
import org.directwebremoting.proxy.dwr.Util;

import com.base.log.LogUtil;
import com.constants.CupidStrutsConstants;

/**
 * dwr框架session过期的检查
 * 
 * @author Administrator
 * @version 5.0
 * @since Apex Ocean V5
 */
public class SessionCheckRemoter extends DefaultRemoter {

	private Logger logger = Logger.getLogger(LogUtil.SERVER);
	
	private List<String> excludedMethods = new ArrayList<String>();
	{
		excludedMethods.add("nickNameExists");
		excludedMethods.add("emailExists");
		excludedMethods.add("checkIdCard");
		excludedMethods.add("recommendUser");
		excludedMethods.add("cancelRecommendUser");
		excludedMethods.add("passMember");
		excludedMethods.add("maskMember");
		excludedMethods.add("unMask");
		excludedMethods.add("auditCertify");
		excludedMethods.add("getWxJsParam");
		excludedMethods.add("getFaxianList");
	}
	
	public Replies execute(Calls calls) {
		HttpSession session = WebContextFactory.get().getSession();
		String requestURI = WebContextFactory.get().getHttpServletRequest().getRequestURI();
		Object attribute = session.getAttribute(CupidStrutsConstants.SESSION_USER);
		Object adminUser = session.getAttribute(CupidStrutsConstants.SESSION_ADMIN);
		if (attribute == null && adminUser == null) {
			//如果是排除的方法,那么直接放行
			if(isExcluded(requestURI)){
				return super.execute(calls);
			}else{
				logger.info("ajax session 过期。。。。。");
				logOut();
				return super.execute(new Calls());
			}
		}
		return super.execute(calls);
	}
	/**
	 * 检查该DWR URL是不是排除在外的请求 
	 * @param requestURI
	 * @return
	 */
	private boolean isExcluded(String requestURI) {
		for (String method : excludedMethods) {
			if(requestURI.indexOf(method) != -1){
				return true;
			}
		}
		return false;
	}

	private void logOut() {
		WebContext wct = WebContextFactory.get();
		Util utilThis = new Util(wct.getScriptSession());
		ScriptBuffer str = new ScriptBuffer("logOut()");
		utilThis.addScript(str);
	}
}
