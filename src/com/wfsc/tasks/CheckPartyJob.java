/**
 * 
 */
package com.wfsc.tasks;

import java.util.Date;

import org.apache.log4j.Logger;

import com.base.job.SimpleTask;
import com.base.log.LogUtil;

/**
 * 
 * 检查活动是否到期，到期就设置其状态为 Party.P_STATUS_2
 * @author jacky
 * @version 1.0
 * @since amorism v1.0
 */
public class CheckPartyJob extends SimpleTask {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public Object execute(Object param, Date nextFireTime) {
		this.execute();
		logger.info(".............................检查活动是否到期.............................");
		return null;
	}
	
	public void execute(){
		
	}

}

