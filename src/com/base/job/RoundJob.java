/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : RoundTask.java
 * Create   : jimsu@tekview.com, Jan 22, 2008/4:38:35 PM
 */
package com.base.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.base.log.LogUtil;

/**
 * 定期轮询的quartz任务
 * 
 * @author jimsu
 * 
 */
public class RoundJob implements StatefulJob {

	private static Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		try {
			// 解析参数,得到jobFullName,inteval
			JobDetail jobDetail = jobexecutioncontext.getJobDetail();
			if (jobDetail == null)
				return;
			logger.debug("开始执行任务：" + jobDetail.getFullName());
			if (jobDetail.getJobDataMap() == null)
				return;
			Object param = jobDetail.getJobDataMap().get(ScheduleUtil.TaskCoreParamKey);
			// 执行任务
			SimpleTask task = (SimpleTask) jobDetail.getJobDataMap().get(ScheduleUtil.TaskInterfaceKey);
			long t1 = System.currentTimeMillis();
			//下次执行时间
			Date nextFireTime = jobexecutioncontext.getNextFireTime();
			task.execute(param, nextFireTime);
			
			// 性能测量
			if (logger.isDebugEnabled()) {
				long t2 = System.currentTimeMillis();
				logger.debug("任务：" + jobDetail.getFullName() + "执行结束，cost " + (t2 - t1) + " ms.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}