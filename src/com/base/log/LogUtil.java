/*
 *Copyright (C) 2008 Tekview Co.,Ltd. All rights reserved.
 *
 *Project  : Apex Ocean
 *FileName : LogUtil.java
 *
 *Created  : version(5.0), jimsu@tekview.com, Jan 30, 2008/5:27:52 PM
 */
package com.base.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Jacky
 */
public class LogUtil {

	public static final String SECURITY_LOG_ID = "SecurityLog";

	public static final String SERVER = "ServerLog";

	public static final String PAY = "PayLog";
	
	public static final String SYSTEM_LOG_ID = "systemLog";


	public static Logger getLogger(String name) {
		return LogManager.getLogger(name);
	}
}