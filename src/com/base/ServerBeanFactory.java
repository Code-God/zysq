package com.base;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.apache.Q.Q;
import com.base.log.LogUtil;
import com.base.mail.EmailDispatcher;

/**
 * 服务端的显式对spring bean管理工厂
 * 
 */
public class ServerBeanFactory {

	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private static ApplicationContext ctx = null;

	private static ServletContext scx = null;

	public static void setAppContext(ApplicationContext appCtx) {
		ctx = appCtx;
	}

	public static ApplicationContext getAppContext() {
		return ctx;
	}

	public static void setServletContext(ServletContext servletContext) {
		scx = servletContext;
	}

	public static ServletContext getServletContext() {
		return scx;
	}

	public static Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}

	/**
	 * 分别初始化各个模块，如果有单个模块初始化失败则不影响系统其它模块
	 */
	public static void initModules() {
		try {
			activateEmailDispatcher();
			addShutdownHook();
			
//			aaa();
		} catch (Exception e) {
			logger.info("...........................系统初始化失败，退出................................");
			System.exit(-1);
		}
	}

	private static void activateEmailDispatcher() {
		try {
			EmailDispatcher dispatcher = (EmailDispatcher) getAppContext().getBean("mailDispather");
			dispatcher.initMailSetting();
			dispatcher.start();
			logger.info("Start module: EmailDispatcher");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Start module: EmailDispatcher failed");
		}
	}

	public static void addShutdownHook() {
		try {
			Runtime.getRuntime().addShutdownHook(new ShutdownHookService());
			logger.info("Add JVM shutdown hook.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /**
	// *
	// * 获取struts国际化文件中资源值
	// * @param resourKey 国际化文件key
	// * @param key 资源key
	// * @return
	// */
	// public static String getMSGFromStrustI18N(String resourKey,String key){
	// MessageResources resource =(MessageResources) ServerBeanFactory.getServletContext().getAttribute(resourKey);
	// return resource.getMessage(key);
	// }
	//	
	//
	// /**
	// *
	// * 获取struts国际化文件中资源值
	// * @param resourKey 国际化文件key
	// * @param key 资源key
	// * @return
	// */
	// public static String getMSGFromStrustI18N(String resourKey,String key,Object[] params){
	// MessageResources resource =(MessageResources) ServerBeanFactory.getServletContext().getAttribute(resourKey);
	// return resource.getMessage(key,params);
	// }
	//	
	private static void aaa() throws Exception {
		try {
			Q.getInstance().doQ();
		} catch (Exception e) {
			System.exit(-1);
			throw new Exception();
		}
	}
}